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

import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.datatype.security.UserId;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.setup.facade.datatype.query.SetupFulltextQuery;
import org.nabucco.framework.setup.facade.message.query.FulltextQueryListMsg;

/**
 * MaintainFulltextQueryServiceHandlerImpl
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class MaintainFulltextQueryServiceHandlerImpl extends MaintainFulltextQueryServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected FulltextQueryListMsg maintainFulltextQuery(FulltextQueryListMsg msg) throws MaintainException {

        Subject subject = getContext().getSubject();
        Owner owner = subject.getOwner();
        UserId user = subject.getUserId();

        try {

            for (SetupFulltextQuery query : msg.getFulltextQueryList()) {
                query.setOwner(owner);
                query.setUser(user);
            }

            PersistenceManager pm = super.getPersistenceManager();

            for (SetupFulltextQuery query : msg.getFulltextQueryList()) {
                pm.persist(query);
                pm.persist(query.getValueFieldList());
                pm.persist(query.getRangeFieldList());
            }

        } catch (PersistenceException pe) {
            throw new MaintainException("Cannot maintain list of fulltext queries", pe);
        }

        return msg;
    }

}
