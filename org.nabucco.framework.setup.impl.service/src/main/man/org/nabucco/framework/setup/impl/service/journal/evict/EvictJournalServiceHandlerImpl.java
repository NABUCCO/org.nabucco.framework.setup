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
package org.nabucco.framework.setup.impl.service.journal.evict;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.security.UserId;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.framework.setup.facade.component.SetupConfiguration;
import org.nabucco.framework.setup.facade.datatype.journal.Journal;

/**
 * EvictJournalServiceHandlerImpl
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class EvictJournalServiceHandlerImpl extends EvictJournalServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String QUERY_LOAD = "FROM Journal j WHERE j.owner = :owner AND j.user = :user ORDER BY j.timestamp DESC";

    private static final String QUERY_COUNT = "SELECT COUNT(*) FROM Journal j WHERE j.owner = :owner AND j.user = :user";

    private static final String QUERY_DELETE = "DELETE FROM Journal j WHERE j.owner = :owner AND j.user = :user AND j.timestamp < :timestamp";

    @Override
    protected EmptyServiceMessage evictJournal(EmptyServiceMessage msg) throws MaintainException {

        UserId id = getContext().getSubject().getUserId();
        Owner owner = getContext().getSubject().getOwner();
        try {

            final int maxResults = SetupConfiguration.getInstance().getJournalConfig().getMaxEntries();

            NabuccoQuery<Long> countQuery = super.getPersistenceManager().createQuery(QUERY_COUNT);
            countQuery.setParameter(Journal.OWNER, owner);
            countQuery.setParameter(Journal.USER, id);

            Long count = countQuery.getSingleResult();

            if (count > maxResults) {

                NabuccoQuery<Journal> loadQuery = super.getPersistenceManager().createQuery(QUERY_LOAD);
                loadQuery.setParameter(Journal.OWNER, owner);
                loadQuery.setParameter(Journal.USER, id);
                loadQuery.setMaxResults(maxResults);

                List<Journal> journalList = loadQuery.getResultList();

                Journal lastJournal = journalList.get(journalList.size() - 1);

                NabuccoQuery<Journal> removeQuery = super.getPersistenceManager().createQuery(QUERY_DELETE);

                removeQuery.setParameter(Journal.OWNER, owner);
                removeQuery.setParameter(Journal.USER, id);
                removeQuery.setParameter(Journal.TIMESTAMP, lastJournal.getTimestamp());
                removeQuery.executeUpdate();
            }

        } catch (Exception e) {
            throw new MaintainException("Cannot evict journal for user[" + id.getValue() + "]", e);
        }

        return new EmptyServiceMessage();
    }

}
