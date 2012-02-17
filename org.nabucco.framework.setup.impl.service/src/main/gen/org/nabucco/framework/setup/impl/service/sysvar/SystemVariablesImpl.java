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
package org.nabucco.framework.setup.impl.service.sysvar;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.setup.sysvar.SystemVariableMsg;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.setup.facade.service.sysvar.SystemVariables;

/**
 * SystemVariablesImpl<p/>Service managing system variables.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-04-07
 */
public class SystemVariablesImpl extends ServiceSupport implements SystemVariables {

    private static final long serialVersionUID = 1L;

    private static final String ID = "SystemVariables";

    private static Map<String, String[]> ASPECTS;

    private MaintainSystemVariablesServiceHandler maintainSystemVariablesServiceHandler;

    private LoadSystemVariablesServiceHandler loadSystemVariablesServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new SystemVariablesImpl instance. */
    public SystemVariablesImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.maintainSystemVariablesServiceHandler = injector.inject(MaintainSystemVariablesServiceHandler.getId());
        if ((this.maintainSystemVariablesServiceHandler != null)) {
            this.maintainSystemVariablesServiceHandler.setPersistenceManager(persistenceManager);
            this.maintainSystemVariablesServiceHandler.setLogger(super.getLogger());
        }
        this.loadSystemVariablesServiceHandler = injector.inject(LoadSystemVariablesServiceHandler.getId());
        if ((this.loadSystemVariablesServiceHandler != null)) {
            this.loadSystemVariablesServiceHandler.setPersistenceManager(persistenceManager);
            this.loadSystemVariablesServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("maintainSystemVariables", NO_ASPECTS);
            ASPECTS.put("loadSystemVariables", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<SystemVariableMsg> maintainSystemVariables(ServiceRequest<SystemVariableMsg> rq)
            throws MaintainException {
        if ((this.maintainSystemVariablesServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for maintainSystemVariables().");
            throw new InjectionException("No service implementation configured for maintainSystemVariables().");
        }
        ServiceResponse<SystemVariableMsg> rs;
        this.maintainSystemVariablesServiceHandler.init();
        rs = this.maintainSystemVariablesServiceHandler.invoke(rq);
        this.maintainSystemVariablesServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<SystemVariableMsg> loadSystemVariables(ServiceRequest<EmptyServiceMessage> rq)
            throws SearchException {
        if ((this.loadSystemVariablesServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for loadSystemVariables().");
            throw new InjectionException("No service implementation configured for loadSystemVariables().");
        }
        ServiceResponse<SystemVariableMsg> rs;
        this.loadSystemVariablesServiceHandler.init();
        rs = this.loadSystemVariablesServiceHandler.invoke(rq);
        this.loadSystemVariablesServiceHandler.finish();
        return rs;
    }
}
