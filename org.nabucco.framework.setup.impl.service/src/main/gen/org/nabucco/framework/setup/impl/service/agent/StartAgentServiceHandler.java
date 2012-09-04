/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.framework.setup.impl.service.agent;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.ServiceHandler;
import org.nabucco.framework.base.impl.service.ServiceHandlerSupport;
import org.nabucco.framework.setup.facade.exception.AgentException;
import org.nabucco.framework.setup.facade.message.agent.AgentNameRq;

/**
 * StartAgentServiceHandler<p/>Service for agent administration.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-09
 */
public abstract class StartAgentServiceHandler extends ServiceHandlerSupport implements ServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.framework.setup.impl.service.agent.StartAgentServiceHandler";

    /** Constructs a new StartAgentServiceHandler instance. */
    public StartAgentServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<AgentNameRq>.
     * @return the ServiceResponse<EmptyServiceMessage>.
     * @throws AgentException
     */
    protected ServiceResponse<EmptyServiceMessage> invoke(ServiceRequest<AgentNameRq> rq) throws AgentException {
        ServiceResponse<EmptyServiceMessage> rs;
        EmptyServiceMessage msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.startAgent(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<EmptyServiceMessage>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (AgentException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            AgentException wrappedException = new AgentException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new AgentException("Error during service invocation.", e);
        }
    }

    /**
     * Activate a configured agent.
     *
     * @param msg the AgentNameRq.
     * @return the EmptyServiceMessage.
     * @throws AgentException
     */
    protected abstract EmptyServiceMessage startAgent(AgentNameRq msg) throws AgentException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
