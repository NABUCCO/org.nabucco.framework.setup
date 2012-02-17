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
package org.nabucco.framework.setup.impl.service.query;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.datatype.security.UserId;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.framework.setup.facade.datatype.query.SetupFulltextQuery;
import org.nabucco.framework.setup.facade.message.query.FulltextQueryListMsg;

/**
 * LoadFulltextQueriesServiceHandlerImpl
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class LoadFulltextQueriesServiceHandlerImpl extends LoadFulltextQueriesServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String QUERY = "FROM SetupFulltextQuery q WHERE q.owner = :owner AND q.user = :user  ORDER BY q.name";

    @Override
    protected FulltextQueryListMsg loadFulltextQueries(EmptyServiceMessage msg)
            throws SearchException {

        FulltextQueryListMsg result;

        Subject subject = getContext().getSubject();
        Owner owner = subject.getOwner();
        UserId user = subject.getUserId();

        try {

            NabuccoQuery<SetupFulltextQuery> query = getPersistenceManager().createQuery(QUERY);
            query.setParameter(SetupFulltextQuery.OWNER, owner);
            query.setParameter(SetupFulltextQuery.USER, user);

            List<SetupFulltextQuery> queryList = query.getResultList();
            for (SetupFulltextQuery setupFulltextQuery : queryList) {
                setupFulltextQuery.getValueFieldList().size();
                setupFulltextQuery.getRangeFieldList().size();
            }

            result = new FulltextQueryListMsg();
            result.getFulltextQueryList().addAll(queryList);

        } catch (PersistenceException pe) {
            throw new SearchException("Cannot load saved fulltext queries.", pe);
        }

        return result;
    }

}
