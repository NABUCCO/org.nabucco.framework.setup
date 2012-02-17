/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.setup.impl.component;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.date.Timestamp;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.message.context.ServiceContextFactory;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.impl.service.timer.Timer;
import org.nabucco.framework.base.impl.service.timer.TimerExecutionException;
import org.nabucco.framework.base.impl.service.timer.TimerPriority;
import org.nabucco.framework.setup.facade.datatype.agent.Agent;
import org.nabucco.framework.setup.facade.datatype.agent.AgentRegistry;
import org.nabucco.framework.setup.facade.exception.AgentException;
import org.nabucco.framework.setup.facade.service.agent.AgentExecutionContext;
import org.nabucco.framework.setup.facade.service.agent.AgentExecutor;

/**
 * AgentTimer
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class AgentTimer extends Timer {

    private static final long serialVersionUID = 1L;

    /** The logger. */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(AgentTimer.class);

    /** Name of the timer. */
    private static final String NAME = "AGENT_TIMER";

    /** Time trigger range. */
    private static final int TIME_RANGE = 100000;

    /**
     * Creates a new {@link AgentTimer} instance.
     */
    public AgentTimer() {
        super(NAME, TimerPriority.MEDIUM);
    }

    @Override
    public void execute() throws TimerExecutionException {
        List<Agent> agents = AgentRegistry.getInstance().getAll();

        for (Agent agent : agents) {

            try {

                if (this.isScheduled(agent)) {

                    logger.debug("Start execution of agent '", agent.getName(), "'.");

                    this.execute(agent);

                    logger.debug("Finished execution of agent '", agent.getName(), "'.");
                }

            } catch (AgentException ae) {
                logger.error(ae, "Error executing agent [", agent.getName(), "].");
            } catch (Exception e) {
                logger.error(e, "Error executing agent [", agent.getName(), "].");
            }
        }
    }

    /**
     * Check whether the agent is scheduled or not.
     * 
     * @param agent
     *            the agent to check
     * 
     * @return <b>true</b> if the agent is scheduled now, <b>false</b> if not
     */
    private boolean isScheduled(Agent agent) {
        if (!agent.getActive().getValue()) {
            return false;
        }
        if (agent.getNextRun() == null || agent.getNextRun().getValue() == null) {
            return false;
        }

        long now = NabuccoSystem.getCurrentTimeMillis();

        long min = agent.getNextRun().getValue();
        long max = min + TIME_RANGE;

        if (now < min) {
            if (logger.isDebugEnabled()) {
                long seconds = (min - now) / 1000;
                logger.debug("Agent '", agent.getName(), "' has not yet been triggered. Missing ", seconds,
                        " seconds...");
            }
            return false;
        }

        if (now > max) {
            long seconds = (now - max) / 1000;
            logger.warning("Agent '", agent.getName(), "' is out of time by ", seconds, " seconds. Recalculating...");
            AgentFactory.getInstance().calculateNextRun(agent);

            if (!agent.getActive().getValue() || now > agent.getNextRun().getValue()) {
                logger.warning("No further run calculated for agent '", agent.getName(), "'.");
                logger.warning("Deactivating agent ", agent.getName(), ".");
                agent.setNextRun((Timestamp) null);
                agent.setActive(false);
            }

            return false;
        }

        return true;
    }

    /**
     * Execute the agent.
     * 
     * @param agent
     *            the agent to execute
     */
    private void execute(Agent agent) throws AgentException {

        String executorName = agent.getExecutor().getValue();

        try {
            @SuppressWarnings("unchecked")
            Class<? extends AgentExecutor> executorClass = (Class<? extends AgentExecutor>) super.getClass()
                    .getClassLoader().loadClass(executorName);

            AgentExecutor executor = executorClass.newInstance();

            ServiceMessageContext serviceContext = ServiceContextFactory.getInstance().newServiceMessageContext();
            AgentExecutionContext context = new AgentExecutionContext(agent, serviceContext);
            executor.execute(context);

            switch (agent.getType()) {

            case CRON:
                AgentFactory.getInstance().calculateNextRun(agent);
                break;

            case DURATION:
                agent.setActive(false);
                break;
            }

            agent.setLastRun(NabuccoSystem.getCurrentTimestamp());

        } catch (ClassNotFoundException cnfe) {
            logger.error(cnfe, "Cannot load executor [", executorName, "].");
            throw new AgentException("Cannot load executor [" + executorName + "].", cnfe);
        } catch (InstantiationException ie) {
            logger.error(ie, "Cannot instantiate executor [", executorName, "].");
            throw new AgentException("Cannot instantiate executor [" + executorName + "].", ie);
        } catch (IllegalAccessException iae) {
            logger.error(iae, "Cannot access executor [", executorName, "].");
            throw new AgentException("Cannot access executor [" + executorName + "].", iae);
        }
    }
}
