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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.setup.facade.service.journal.evict.EvictJournal;

/**
 * EvictJournalImpl<p/>Service for cleaning the journal.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-03-21
 */
public class EvictJournalImpl extends ServiceSupport implements EvictJournal {

    private static final long serialVersionUID = 1L;

    private static final String ID = "EvictJournal";

    private static Map<String, String[]> ASPECTS;

    private EvictJournalServiceHandler evictJournalServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new EvictJournalImpl instance. */
    public EvictJournalImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.evictJournalServiceHandler = injector.inject(EvictJournalServiceHandler.getId());
        if ((this.evictJournalServiceHandler != null)) {
            this.evictJournalServiceHandler.setPersistenceManager(persistenceManager);
            this.evictJournalServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("evictJournal", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<EmptyServiceMessage> evictJournal(ServiceRequest<EmptyServiceMessage> rq)
            throws MaintainException {
        if ((this.evictJournalServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for evictJournal().");
            throw new InjectionException("No service implementation configured for evictJournal().");
        }
        ServiceResponse<EmptyServiceMessage> rs;
        this.evictJournalServiceHandler.init();
        rs = this.evictJournalServiceHandler.invoke(rq);
        this.evictJournalServiceHandler.finish();
        return rs;
    }
}
