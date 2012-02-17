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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.date.Timestamp;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.AgentCronExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.AgentExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.setup.facade.datatype.agent.Agent;
import org.nabucco.framework.setup.facade.datatype.agent.CronAgent;
import org.nabucco.framework.setup.facade.datatype.agent.CronTrigger;
import org.nabucco.framework.setup.facade.datatype.agent.DurationAgent;
import org.nabucco.framework.setup.facade.exception.AgentException;
import org.nabucco.framework.setup.facade.service.agent.AgentExecutor;

/**
 * AgentExtensionMapper
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class AgentFactory {

    /** The factor converting hours to milliseconds. */
    private static final int FACTOR_HOUR = 3600000;

    /** The factor converting minutes to milliseconds. */
    private static final int FACTOR_MINUTE = 60000;

    /** The logger. */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(AgentFactory.class);

    /**
     * Singleton instance.
     */
    private static AgentFactory instance = new AgentFactory();

    /**
     * Private constructor.
     */
    private AgentFactory() {
    }

    /**
     * Singleton access.
     * 
     * @return the AgentExtensionMapper instance.
     */
    public static AgentFactory getInstance() {
        return instance;
    }

    /**
     * Map the configured agent extension to an agent.
     * 
     * @param extension
     *            the agent extension
     * 
     * @return the agent
     * 
     * @throws AgentException
     *             when the agent extension cannot be mapped to an agent
     */
    public Agent createAgent(AgentExtension extension) throws AgentException {

        try {
            Agent agent;

            if (!extension.getCronExtensions().isEmpty()) {
                agent = this.createCronAgent(extension);
            } else {
                agent = this.createDurationAgent(extension);
            }

            agent.setName(extension.getIdentifier().getValue());
            agent.setActive(extension.getAutoActivate().getValue().getValue());
            agent.setExecutor(this.getExecutor(extension));

            this.calculateNextRun(agent);

            return agent;

        } catch (AgentException ae) {
            logger.error(ae, "Error mapping agent extension [", extension.getName(), "].");
            throw ae;
        } catch (Exception e) {
            logger.error(e, "Error mapping agent extension [", extension.getName(), "].");
            throw new AgentException("Error mapping agent extension [" + extension.getName() + "].", e);
        }
    }

    /**
     * Create a cron agent from the extension.
     * 
     * @param extension
     *            the extension holding cron configurations
     * 
     * @return the cron agent
     * 
     * @throws AgentException
     *             when the cron agent cannot be created
     */
    private CronAgent createCronAgent(AgentExtension extension) throws AgentException {

        if (extension.getCronExtensions().isEmpty()) {
            logger.error("Agent extension ", extension.getName(), " does not configure any cron trigger.");
            throw new AgentException("Agent extension " + extension.getName() + " does not configure any cron trigger.");
        }

        CronAgent agent = new CronAgent();

        for (AgentCronExtension cronExtension : extension.getCronExtensions()) {
            CronTrigger trigger = new CronTrigger();
            trigger.setYear(cronExtension.getYear().getValue().getValue());
            trigger.setWeekday(cronExtension.getWeekday().getValue().getValue());
            trigger.setMonth(cronExtension.getMonth().getValue().getValue());
            trigger.setDay(cronExtension.getDay().getValue().getValue());
            trigger.setHour(cronExtension.getHour().getValue().getValue());
            trigger.setMinute(cronExtension.getMinute().getValue().getValue());

            agent.getCronList().add(trigger);
        }

        return agent;
    }

    /**
     * Create a cron agent from the extension.
     * 
     * @param extension
     *            the extension holding cron configurations
     * 
     * @return the cron agent
     * 
     * @throws AgentException
     *             when the cron agent cannot be created
     */
    private DurationAgent createDurationAgent(AgentExtension extension) throws AgentException {

        long hours = extension.getDurationExtension().getHours().getValue().getValue() * FACTOR_HOUR;
        long minutes = extension.getDurationExtension().getMinutes().getValue().getValue() * FACTOR_MINUTE;

        long duration = hours + minutes;

        if (duration <= 0) {
            logger.error("Agent extension ", extension.getName(), " does not configure a valid duration.");
            throw new AgentException("Agent extension " + extension.getName() + " does not configure a valid duration.");
        }

        DurationAgent agent = new DurationAgent();
        agent.setDuration(duration);

        return agent;
    }

    /**
     * Resolve the agent executor.
     * 
     * @param extension
     *            the agent extension
     * 
     * @throws AgentException
     *             when the executor cannot be resolved
     */
    private String getExecutor(AgentExtension extension) throws AgentException {
        if (extension.getExecutor() == null
                || extension.getExecutor().getValue() == null || extension.getExecutor().getValue().getValue() == null) {
            throw new AgentException("No executor defined for agent [" + extension.getName() + "].");
        }

        String executor = extension.getExecutor().getValue().getValue();
        ClassLoader classLoader = super.getClass().getClassLoader();

        try {
            @SuppressWarnings("unchecked")
            Class<AgentExecutor> executorClass = (Class<AgentExecutor>) classLoader.loadClass(executor);
            AgentExecutor.class.isAssignableFrom(executorClass);

        } catch (ClassNotFoundException cnfe) {
            throw new AgentException("Cannot find executor for agent [" + extension.getName() + "].", cnfe);
        }

        return executor;
    }

    /**
     * Calculate the time of the next run of the given agent and set it into the agent's nextRun
     * field.
     * 
     * @param agent
     *            the agent to calculate the time for
     */
    public void calculateNextRun(Agent agent) {

        long now = NabuccoSystem.getCurrentTimeMillis();

        long nextRun = -1;

        switch (agent.getType()) {

        case CRON: {
            CronAgent cronAgent = (CronAgent) agent;
            nextRun = this.calculateCron(cronAgent, now);
            break;
        }

        case DURATION: {
            DurationAgent durationAgent = (DurationAgent) agent;
            nextRun = this.calculateDuration(durationAgent, now);
            break;
        }

        }

        if (nextRun != -1) {
            if (logger.isDebugEnabled()) {
                DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                String date = format.format(new Date(nextRun));
                logger.debug("Calculated next run for agent ", agent.getName(), " at '", date, "'.");
            }

            agent.setNextRun(nextRun);

        } else {
            logger.warning("No further run calculated for agent '", agent.getName(), "'.");
            logger.warning("Deactivating agent '", agent.getName(), "'.");

            agent.setNextRun((Timestamp) null);
            agent.setActive(false);
        }

    }

    /**
     * Calculate the time for the cron agent.
     * 
     * @param agent
     *            the cron agent
     * @param time
     *            the current time
     * 
     * @return the time of the next run
     */
    private long calculateCron(CronAgent agent, long time) {

        Calendar now = new GregorianCalendar();
        now.setTimeInMillis(time);

        int currentYear = now.get(Calendar.YEAR);
        int currentMonth = now.get(Calendar.MONTH);
        int currentDay = now.get(Calendar.DAY_OF_MONTH);
        int currentHour = now.get(Calendar.HOUR_OF_DAY);
        int currentMinute = now.get(Calendar.MINUTE);

        Calendar current = null;

        for (CronTrigger trigger : agent.getCronList()) {

            int minute = trigger.getMinute().getValue();
            int hour = trigger.getHour().getValue();
            int day = trigger.getDay().getValue();
            int month = trigger.getMonth().getValue();
            int year = trigger.getYear().getValue();

            if (hour == -1) {
                hour = currentHour;
                if (currentMinute >= minute) {
                    if (hour == 23) {
                        hour = 0;
                    } else {
                        hour++;
                    }
                }
            }

            if (day == -1) {
                day = currentDay;
                if (currentHour >= hour && currentMinute >= minute) {
                    day++;
                }
            }

            if (month == -1) {
                month = currentMonth;
                if (currentDay >= day && currentHour >= hour && currentMinute >= minute) {
                    if (month == 11) {
                        month = 0;
                    } else {
                        month++;
                    }
                }
            } else {
                month--;
            }

            if (year == -1) {
                year = currentYear;
                if (currentMonth >= month && currentDay >= day && currentHour >= hour && currentMinute >= minute) {
                    year++;
                }
            }

            Calendar next = new GregorianCalendar(year, month, day, hour, minute);

            if (next.after(now)) {
                if (current == null || current.after(next)) {
                    current = next;
                }
            }
        }

        if (current == null) {
            return -1;
        }

        return current.getTimeInMillis();
    }

    /**
     * Calculate the time for the cron agent.
     * 
     * @param agent
     *            the cron agent
     * @param time
     *            the current time
     * 
     * @return the time of the next run
     */
    private long calculateDuration(DurationAgent durationAgent, long now) {
        return now + durationAgent.getDuration().getValue();
    }
}
