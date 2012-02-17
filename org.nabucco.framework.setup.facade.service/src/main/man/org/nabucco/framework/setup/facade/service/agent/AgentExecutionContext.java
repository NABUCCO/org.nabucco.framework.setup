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
package org.nabucco.framework.setup.facade.service.agent;

import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.setup.facade.datatype.agent.Agent;
import org.nabucco.framework.setup.facade.datatype.agent.AgentType;

/**
 * AgentExecutionContext
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class AgentExecutionContext {

    private Agent agent;

    private ServiceMessageContext serviceContext;

    /**
     * Creates a new {@link AgentExecutionContext} instance.
     * 
     * @param agent
     *            the current agent
     * @param serviceContext
     *            the service context
     */
    public AgentExecutionContext(Agent agent, ServiceMessageContext serviceContext) {
        this.agent = agent;
        this.serviceContext = serviceContext;
    }

    /**
     * Getter for the agent name.
     * 
     * @return the name of the agent
     */
    public String getAgentName() {
        return this.agent.getName().getValue();
    }

    /**
     * Whether the agent is active or not.
     * 
     * @return <b>true</b> if the agent is active, <b>false</b> if not
     */
    public boolean isActive() {
        return this.agent.getActive().getValue();
    }

    /**
     * Setting the agent active or inactive.
     * 
     * @param active
     *            <b>true</b> if the agent should be enabled, <b>false</b> if the agent should be
     *            disabled
     */
    public void setActive(boolean active) {
        this.agent.setActive(active);
    }

    /**
     * Getter for the serviceContext.
     * 
     * @return Returns the serviceContext.
     */
    public ServiceMessageContext getServiceContext() {
        return this.serviceContext;
    }

    /**
     * Retrieve the time of the last run, or <code>0</code> if the agent has not been run yet.
     * 
     * @return the timestamp of the last agent run
     */
    public long getLastRun() {
        return this.agent.getLastRun().getValue();
    }

    /**
     * Getter for the agent type.
     * 
     * @return the agent type
     */
    public AgentType getAgentType() {
        return this.agent.getType();
    }

}
