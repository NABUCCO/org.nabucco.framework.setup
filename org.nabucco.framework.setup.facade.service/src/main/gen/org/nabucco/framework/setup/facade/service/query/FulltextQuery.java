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
package org.nabucco.framework.setup.facade.service.query;

import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.setup.facade.message.query.FulltextQueryListMsg;

/**
 * FulltextQuery<p/>Service for managing fulltext queries.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-04-13
 */
public interface FulltextQuery extends Service {

    /**
     * Maintaining a fulltext query.
     *
     * @param rq the ServiceRequest<FulltextQueryListMsg>.
     * @return the ServiceResponse<FulltextQueryListMsg>.
     * @throws MaintainException
     */
    ServiceResponse<FulltextQueryListMsg> maintainFulltextQuery(ServiceRequest<FulltextQueryListMsg> rq)
            throws MaintainException;

    /**
     * Loading all fulltextqueries.
     *
     * @param rq the ServiceRequest<EmptyServiceMessage>.
     * @return the ServiceResponse<FulltextQueryListMsg>.
     * @throws SearchException
     */
    ServiceResponse<FulltextQueryListMsg> loadFulltextQueries(ServiceRequest<EmptyServiceMessage> rq)
            throws SearchException;
}
