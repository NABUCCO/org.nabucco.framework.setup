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
package org.nabucco.framework.setup.impl.service.journal.search;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.security.UserId;
import org.nabucco.framework.base.facade.datatype.setup.journal.JournalRef;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.setup.journal.JournalRefMsg;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.framework.setup.facade.component.SetupConfiguration;
import org.nabucco.framework.setup.facade.datatype.journal.Journal;

/**
 * SearchJournalServiceHandlerImpl
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class SearchJournalServiceHandlerImpl extends SearchJournalServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String QUERY = "FROM Journal j WHERE j.owner = :owner AND j.user = :user ORDER BY j.timestamp DESC";

    @Override
    protected JournalRefMsg searchJournal(EmptyServiceMessage msg) throws SearchException {

        final int maxResults = SetupConfiguration.getInstance().getJournalConfig().getMaxEntries();

        try {
            UserId id = getContext().getSubject().getUserId();
            Owner owner = getContext().getSubject().getOwner();

            JournalRefMsg result = new JournalRefMsg();

            NabuccoQuery<Journal> query = super.getPersistenceManager().createQuery(QUERY);

            query.setParameter(Journal.OWNER, owner);
            query.setParameter(Journal.USER, id);

            query.setMaxResults(maxResults);

            List<Journal> journalList = query.getResultList();

            List<JournalRef> refList = result.getJournalRefList();

            for (Journal journal : journalList) {
                JournalRef jref = new JournalRef();
                jref.setClassName(journal.getClassName());
                jref.setRefId(journal.getIdentifier());
                jref.setTimestamp(journal.getTimestamp());
                refList.add(jref);
            }

            return result;

        } catch (Exception e) {
            super.getLogger().error(e, "Cannot search journal entries.");
            throw new SearchException("Cannot search journal entries.", e);
        }

    }

}
