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
package org.nabucco.framework.setup.ui.rcp.communication.query;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.setup.facade.message.query.FulltextQueryListMsg;
import org.nabucco.framework.setup.facade.service.query.FulltextQuery;

/**
 * FulltextQueryDelegate<p/>Service for managing fulltext queries.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-04-13
 */
public class FulltextQueryDelegate extends ServiceDelegateSupport {

    private FulltextQuery service;

    /**
     * Constructs a new FulltextQueryDelegate instance.
     *
     * @param service the FulltextQuery.
     */
    public FulltextQueryDelegate(FulltextQuery service) {
        super();
        this.service = service;
    }

    /**
     * MaintainFulltextQuery.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the FulltextQueryListMsg.
     * @return the FulltextQueryListMsg.
     * @throws ClientException
     */
    public FulltextQueryListMsg maintainFulltextQuery(FulltextQueryListMsg message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<FulltextQueryListMsg> request = new ServiceRequest<FulltextQueryListMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<FulltextQueryListMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.maintainFulltextQuery(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(FulltextQuery.class, "maintainFulltextQuery", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: FulltextQuery.maintainFulltextQuery");
    }

    /**
     * LoadFulltextQueries.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the EmptyServiceMessage.
     * @return the FulltextQueryListMsg.
     * @throws ClientException
     */
    public FulltextQueryListMsg loadFulltextQueries(EmptyServiceMessage message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<EmptyServiceMessage> request = new ServiceRequest<EmptyServiceMessage>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<FulltextQueryListMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.loadFulltextQueries(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(FulltextQuery.class, "loadFulltextQueries", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: FulltextQuery.loadFulltextQueries");
    }
}
