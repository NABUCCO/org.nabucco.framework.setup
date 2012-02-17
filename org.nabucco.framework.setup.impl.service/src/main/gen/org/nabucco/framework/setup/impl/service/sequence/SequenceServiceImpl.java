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
package org.nabucco.framework.setup.impl.service.sequence;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.setup.facade.exception.SequenceException;
import org.nabucco.framework.setup.facade.message.sequence.SequenceRq;
import org.nabucco.framework.setup.facade.message.sequence.SequenceRs;
import org.nabucco.framework.setup.facade.service.sequence.SequenceService;

/**
 * SequenceServiceImpl<p/>Service for sequence generation.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-01
 */
public class SequenceServiceImpl extends ServiceSupport implements SequenceService {

    private static final long serialVersionUID = 1L;

    private static final String ID = "SequenceService";

    private static Map<String, String[]> ASPECTS;

    private GenerateSequenceServiceHandler generateSequenceServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new SequenceServiceImpl instance. */
    public SequenceServiceImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.generateSequenceServiceHandler = injector.inject(GenerateSequenceServiceHandler.getId());
        if ((this.generateSequenceServiceHandler != null)) {
            this.generateSequenceServiceHandler.setPersistenceManager(persistenceManager);
            this.generateSequenceServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("generateSequence", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<SequenceRs> generateSequence(ServiceRequest<SequenceRq> rq) throws SequenceException {
        if ((this.generateSequenceServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for generateSequence().");
            throw new InjectionException("No service implementation configured for generateSequence().");
        }
        ServiceResponse<SequenceRs> rs;
        this.generateSequenceServiceHandler.init();
        rs = this.generateSequenceServiceHandler.invoke(rq);
        this.generateSequenceServiceHandler.finish();
        return rs;
    }
}
