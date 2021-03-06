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
package org.nabucco.framework.setup.ui.rcp.communication.journal.maintain;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.setup.journal.JournalMsg;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.setup.facade.service.journal.maintain.MaintainJournal;

/**
 * MaintainJournalDelegate<p/>Service for storing the journal.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-03-17
 */
public class MaintainJournalDelegate extends ServiceDelegateSupport {

    private MaintainJournal service;

    /**
     * Constructs a new MaintainJournalDelegate instance.
     *
     * @param service the MaintainJournal.
     */
    public MaintainJournalDelegate(MaintainJournal service) {
        super();
        this.service = service;
    }

    /**
     * MaintainJournal.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the JournalMsg.
     * @return the EmptyServiceMessage.
     * @throws ClientException
     */
    public EmptyServiceMessage maintainJournal(JournalMsg message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<JournalMsg> request = new ServiceRequest<JournalMsg>(super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<EmptyServiceMessage> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.maintainJournal(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(MaintainJournal.class, "maintainJournal", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: MaintainJournal.maintainJournal");
    }
}
