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

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.ServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;
import org.nabucco.framework.setup.facade.message.query.FulltextQueryListMsg;

/**
 * LoadFulltextQueriesServiceHandler<p/>Service for managing fulltext queries.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-04-13
 */
public abstract class LoadFulltextQueriesServiceHandler extends PersistenceServiceHandlerSupport implements
        ServiceHandler, PersistenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.framework.setup.impl.service.query.LoadFulltextQueriesServiceHandler";

    /** Constructs a new LoadFulltextQueriesServiceHandler instance. */
    public LoadFulltextQueriesServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<EmptyServiceMessage>.
     * @return the ServiceResponse<FulltextQueryListMsg>.
     * @throws SearchException
     */
    protected ServiceResponse<FulltextQueryListMsg> invoke(ServiceRequest<EmptyServiceMessage> rq)
            throws SearchException {
        ServiceResponse<FulltextQueryListMsg> rs;
        FulltextQueryListMsg msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.loadFulltextQueries(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<FulltextQueryListMsg>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (SearchException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            SearchException wrappedException = new SearchException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new SearchException("Error during service invocation.", e);
        }
    }

    /**
     * Loading all fulltextqueries.
     *
     * @param msg the EmptyServiceMessage.
     * @return the FulltextQueryListMsg.
     * @throws SearchException
     */
    protected abstract FulltextQueryListMsg loadFulltextQueries(EmptyServiceMessage msg) throws SearchException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
