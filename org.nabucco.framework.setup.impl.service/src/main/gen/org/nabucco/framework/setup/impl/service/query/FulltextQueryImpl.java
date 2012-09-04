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
package org.nabucco.framework.setup.impl.service.query;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.setup.facade.message.query.FulltextQueryListMsg;
import org.nabucco.framework.setup.facade.service.query.FulltextQuery;

/**
 * FulltextQueryImpl<p/>Service for managing fulltext queries.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-04-13
 */
public class FulltextQueryImpl extends ServiceSupport implements FulltextQuery {

    private static final long serialVersionUID = 1L;

    private static final String ID = "FulltextQuery";

    private static Map<String, String[]> ASPECTS;

    private MaintainFulltextQueryServiceHandler maintainFulltextQueryServiceHandler;

    private LoadFulltextQueriesServiceHandler loadFulltextQueriesServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new FulltextQueryImpl instance. */
    public FulltextQueryImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.maintainFulltextQueryServiceHandler = injector.inject(MaintainFulltextQueryServiceHandler.getId());
        if ((this.maintainFulltextQueryServiceHandler != null)) {
            this.maintainFulltextQueryServiceHandler.setPersistenceManager(persistenceManager);
            this.maintainFulltextQueryServiceHandler.setLogger(super.getLogger());
        }
        this.loadFulltextQueriesServiceHandler = injector.inject(LoadFulltextQueriesServiceHandler.getId());
        if ((this.loadFulltextQueriesServiceHandler != null)) {
            this.loadFulltextQueriesServiceHandler.setPersistenceManager(persistenceManager);
            this.loadFulltextQueriesServiceHandler.setLogger(super.getLogger());
        }
    }

    @Override
    public void preDestroy() {
        super.preDestroy();
    }

    @Override
    public String[] getAspects(String operationName) {
        if ((ASPECTS == null)) {
            ASPECTS = new HashMap<String, String[]>();
            ASPECTS.put("maintainFulltextQuery", NO_ASPECTS);
            ASPECTS.put("loadFulltextQueries", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<FulltextQueryListMsg> maintainFulltextQuery(ServiceRequest<FulltextQueryListMsg> rq)
            throws MaintainException {
        if ((this.maintainFulltextQueryServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for maintainFulltextQuery().");
            throw new InjectionException("No service implementation configured for maintainFulltextQuery().");
        }
        ServiceResponse<FulltextQueryListMsg> rs;
        this.maintainFulltextQueryServiceHandler.init();
        rs = this.maintainFulltextQueryServiceHandler.invoke(rq);
        this.maintainFulltextQueryServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<FulltextQueryListMsg> loadFulltextQueries(ServiceRequest<EmptyServiceMessage> rq)
            throws SearchException {
        if ((this.loadFulltextQueriesServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for loadFulltextQueries().");
            throw new InjectionException("No service implementation configured for loadFulltextQueries().");
        }
        ServiceResponse<FulltextQueryListMsg> rs;
        this.loadFulltextQueriesServiceHandler.init();
        rs = this.loadFulltextQueriesServiceHandler.invoke(rq);
        this.loadFulltextQueriesServiceHandler.finish();
        return rs;
    }
}
