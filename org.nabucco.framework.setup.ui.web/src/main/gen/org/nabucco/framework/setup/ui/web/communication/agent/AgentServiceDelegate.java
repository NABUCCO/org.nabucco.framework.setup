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
package org.nabucco.framework.setup.ui.web.communication.agent;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
import org.nabucco.framework.setup.facade.exception.AgentException;
import org.nabucco.framework.setup.facade.message.agent.AgentNameRq;
import org.nabucco.framework.setup.facade.service.agent.AgentService;

/**
 * AgentServiceDelegate<p/>Service for agent administration.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-09
 */
public class AgentServiceDelegate extends ServiceDelegateSupport {

    private AgentService service;

    /**
     * Constructs a new AgentServiceDelegate instance.
     *
     * @param service the AgentService.
     */
    public AgentServiceDelegate(AgentService service) {
        super();
        this.service = service;
    }

    /**
     * StartAgent.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the AgentNameRq.
     * @return the EmptyServiceMessage.
     * @throws AgentException
     */
    public EmptyServiceMessage startAgent(AgentNameRq message, NabuccoSession session, ServiceSubContext... subContexts)
            throws AgentException {
        ServiceRequest<AgentNameRq> request = new ServiceRequest<AgentNameRq>(super.createServiceContext(session,
                subContexts));
        request.setRequestMessage(message);
        ServiceResponse<EmptyServiceMessage> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.startAgent(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(AgentService.class, "startAgent", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new AgentException("Cannot execute service operation: AgentService.startAgent");
    }

    /**
     * StopAgent.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the AgentNameRq.
     * @return the EmptyServiceMessage.
     * @throws AgentException
     */
    public EmptyServiceMessage stopAgent(AgentNameRq message, NabuccoSession session, ServiceSubContext... subContexts)
            throws AgentException {
        ServiceRequest<AgentNameRq> request = new ServiceRequest<AgentNameRq>(super.createServiceContext(session,
                subContexts));
        request.setRequestMessage(message);
        ServiceResponse<EmptyServiceMessage> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.stopAgent(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(AgentService.class, "stopAgent", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new AgentException("Cannot execute service operation: AgentService.stopAgent");
    }
}
