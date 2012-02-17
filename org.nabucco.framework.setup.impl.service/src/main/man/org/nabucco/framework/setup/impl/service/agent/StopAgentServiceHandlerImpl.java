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
package org.nabucco.framework.setup.impl.service.agent;

import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.setup.facade.datatype.agent.Agent;
import org.nabucco.framework.setup.facade.datatype.agent.AgentRegistry;
import org.nabucco.framework.setup.facade.exception.AgentException;
import org.nabucco.framework.setup.facade.message.agent.AgentNameRq;

/**
 * StopAgentServiceHandlerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class StopAgentServiceHandlerImpl extends StopAgentServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected EmptyServiceMessage stopAgent(AgentNameRq msg) throws AgentException {

        if (msg.getAgentName() == null || msg.getAgentName().getValue() == null) {
            super.getLogger().warning("Cannot stop agent with name [null].");
            return new EmptyServiceMessage();
        }

        String agentName = msg.getAgentName().getValue();
        Agent agent = AgentRegistry.getInstance().get(agentName);

        if (agent == null) {
            super.getLogger().warning("Cannot stop agent with name [", agentName, "].");
            super.getLogger().warning("No agent configured with name [", agentName, "].");

            return new EmptyServiceMessage();
        }

        if (!agent.getActive().getValue()) {
            super.getLogger().info("Agent [", agentName, "] is already deactivated.");
        } else {
            super.getLogger().info("Deactivating agent with name [", agentName, "].");
            agent.setActive(true);
        }

        return new EmptyServiceMessage();
    }

}
