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
package org.nabucco.framework.setup.ui.rcp.communication.sysvar;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.setup.sysvar.SystemVariableMsg;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.setup.facade.service.sysvar.SystemVariables;

/**
 * SystemVariablesDelegate<p/>Service managing system variables.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-04-07
 */
public class SystemVariablesDelegate extends ServiceDelegateSupport {

    private SystemVariables service;

    /**
     * Constructs a new SystemVariablesDelegate instance.
     *
     * @param service the SystemVariables.
     */
    public SystemVariablesDelegate(SystemVariables service) {
        super();
        this.service = service;
    }

    /**
     * MaintainSystemVariables.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the SystemVariableMsg.
     * @return the SystemVariableMsg.
     * @throws ClientException
     */
    public SystemVariableMsg maintainSystemVariables(SystemVariableMsg message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<SystemVariableMsg> request = new ServiceRequest<SystemVariableMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<SystemVariableMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.maintainSystemVariables(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(SystemVariables.class, "maintainSystemVariables", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: SystemVariables.maintainSystemVariables");
    }

    /**
     * LoadSystemVariables.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the EmptyServiceMessage.
     * @return the SystemVariableMsg.
     * @throws ClientException
     */
    public SystemVariableMsg loadSystemVariables(EmptyServiceMessage message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<EmptyServiceMessage> request = new ServiceRequest<EmptyServiceMessage>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<SystemVariableMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.loadSystemVariables(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(SystemVariables.class, "loadSystemVariables", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: SystemVariables.loadSystemVariables");
    }
}
