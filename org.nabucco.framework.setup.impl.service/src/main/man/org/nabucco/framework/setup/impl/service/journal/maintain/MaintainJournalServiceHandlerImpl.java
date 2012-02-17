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
package org.nabucco.framework.setup.impl.service.journal.maintain;

import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.facade.message.setup.journal.JournalMsg;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.setup.impl.service.journal.JournalServiceMaintenance;

/**
 * @author Frank Ratschinski, PRODYNA AG
 * 
 */
public class MaintainJournalServiceHandlerImpl extends MaintainJournalServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected EmptyServiceMessage maintainJournal(JournalMsg msg) throws MaintainException {

        JournalServiceMaintenance jm = new JournalServiceMaintenance();

        ServiceMessageContext context = super.getContext();
        PersistenceManager manager = super.getPersistenceManager();

        jm.maintainJournal(context, manager, msg);

        return new EmptyServiceMessage();

    }
}
