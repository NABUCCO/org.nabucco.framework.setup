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
package org.nabucco.framework.setup.ui.rcp.communication.sequence;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.setup.facade.message.sequence.SequenceRq;
import org.nabucco.framework.setup.facade.message.sequence.SequenceRs;
import org.nabucco.framework.setup.facade.service.sequence.SequenceService;

/**
 * SequenceServiceDelegate<p/>Service for sequence generation.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-01
 */
public class SequenceServiceDelegate extends ServiceDelegateSupport {

    private SequenceService service;

    /**
     * Constructs a new SequenceServiceDelegate instance.
     *
     * @param service the SequenceService.
     */
    public SequenceServiceDelegate(SequenceService service) {
        super();
        this.service = service;
    }

    /**
     * GenerateSequence.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the SequenceRq.
     * @return the SequenceRs.
     * @throws ClientException
     */
    public SequenceRs generateSequence(SequenceRq message, ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<SequenceRq> request = new ServiceRequest<SequenceRq>(super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<SequenceRs> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.generateSequence(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(SequenceService.class, "generateSequence", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: SequenceService.generateSequence");
    }
}
