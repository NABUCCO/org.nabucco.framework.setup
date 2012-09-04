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
package org.nabucco.framework.setup.impl.service.journal.search;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.setup.journal.JournalRefMsg;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.setup.facade.service.journal.search.SearchJournal;

/**
 * SearchJournalImpl<p/>Service for storing the journal.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-03-17
 */
public class SearchJournalImpl extends ServiceSupport implements SearchJournal {

    private static final long serialVersionUID = 1L;

    private static final String ID = "SearchJournal";

    private static Map<String, String[]> ASPECTS;

    private SearchJournalServiceHandler searchJournalServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new SearchJournalImpl instance. */
    public SearchJournalImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.searchJournalServiceHandler = injector.inject(SearchJournalServiceHandler.getId());
        if ((this.searchJournalServiceHandler != null)) {
            this.searchJournalServiceHandler.setPersistenceManager(persistenceManager);
            this.searchJournalServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("searchJournal", new String[] { "org.nabucco.aspect.caching" });
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<JournalRefMsg> searchJournal(ServiceRequest<EmptyServiceMessage> rq) throws SearchException {
        if ((this.searchJournalServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for searchJournal().");
            throw new InjectionException("No service implementation configured for searchJournal().");
        }
        ServiceResponse<JournalRefMsg> rs;
        this.searchJournalServiceHandler.init();
        rs = this.searchJournalServiceHandler.invoke(rq);
        this.searchJournalServiceHandler.finish();
        return rs;
    }
}
