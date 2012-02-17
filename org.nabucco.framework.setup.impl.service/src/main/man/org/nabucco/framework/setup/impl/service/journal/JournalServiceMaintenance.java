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
package org.nabucco.framework.setup.impl.service.journal;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.message.context.ServiceContext;
import org.nabucco.framework.base.facade.message.setup.journal.JournalMsg;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.setup.facade.datatype.journal.Journal;

/**
 * JournalServiceMaintenance
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class JournalServiceMaintenance {

    /**
     * Maintain the journal.
     * 
     * @param ctx
     *            the service context
     * @param manager
     *            the persistence manager
     * @param msg
     *            the service message
     * 
     * @throws MaintainException
     */
    public void maintainJournal(ServiceContext ctx, PersistenceManager manager, JournalMsg msg)
            throws MaintainException {
        try {

            Subject subject = ctx.getSubject();
            Journal journal = new Journal();
            journal.setDatatypeState(DatatypeState.INITIALIZED);
            journal.setClassName(msg.getClassName());
            journal.setOwner(subject.getOwner());
            journal.setIdentifier(msg.getDatatypeId());
            journal.setTimestamp(msg.getTimestamp());
            journal.setUser(subject.getUserId());

            manager.persist(journal);

        } catch (PersistenceException e) {
            throw new MaintainException("Cannot save journal entry", e);
        }
    }
}
