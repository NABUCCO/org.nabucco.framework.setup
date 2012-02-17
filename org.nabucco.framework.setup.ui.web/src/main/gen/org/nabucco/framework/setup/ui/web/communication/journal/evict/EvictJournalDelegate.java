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
package org.nabucco.framework.setup.ui.web.communication.journal.evict;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
import org.nabucco.framework.setup.facade.service.journal.evict.EvictJournal;

/**
 * EvictJournalDelegate<p/>Service for cleaning the journal.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-03-21
 */
public class EvictJournalDelegate extends ServiceDelegateSupport {

    private EvictJournal service;

    /**
     * Constructs a new EvictJournalDelegate instance.
     *
     * @param service the EvictJournal.
     */
    public EvictJournalDelegate(EvictJournal service) {
        super();
        this.service = service;
    }

    /**
     * EvictJournal.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the EmptyServiceMessage.
     * @return the EmptyServiceMessage.
     * @throws MaintainException
     */
    public EmptyServiceMessage evictJournal(EmptyServiceMessage message, NabuccoSession session,
            ServiceSubContext... subContexts) throws MaintainException {
        ServiceRequest<EmptyServiceMessage> request = new ServiceRequest<EmptyServiceMessage>(
                super.createServiceContext(session, subContexts));
        request.setRequestMessage(message);
        ServiceResponse<EmptyServiceMessage> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.evictJournal(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(EvictJournal.class, "evictJournal", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new MaintainException("Cannot execute service operation: EvictJournal.evictJournal");
    }
}
