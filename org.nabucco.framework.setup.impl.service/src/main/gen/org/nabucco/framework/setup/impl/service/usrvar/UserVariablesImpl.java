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
package org.nabucco.framework.setup.impl.service.usrvar;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.setup.usrvar.UserVariableMsg;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.setup.facade.service.usrvar.UserVariables;

/**
 * UserVariablesImpl<p/>Service for managing user variables.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-04-07
 */
public class UserVariablesImpl extends ServiceSupport implements UserVariables {

    private static final long serialVersionUID = 1L;

    private static final String ID = "UserVariables";

    private static Map<String, String[]> ASPECTS;

    private MaintainUserVariablesServiceHandler maintainUserVariablesServiceHandler;

    private LoadUserVariablesServiceHandler loadUserVariablesServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new UserVariablesImpl instance. */
    public UserVariablesImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.maintainUserVariablesServiceHandler = injector.inject(MaintainUserVariablesServiceHandler.getId());
        if ((this.maintainUserVariablesServiceHandler != null)) {
            this.maintainUserVariablesServiceHandler.setPersistenceManager(persistenceManager);
            this.maintainUserVariablesServiceHandler.setLogger(super.getLogger());
        }
        this.loadUserVariablesServiceHandler = injector.inject(LoadUserVariablesServiceHandler.getId());
        if ((this.loadUserVariablesServiceHandler != null)) {
            this.loadUserVariablesServiceHandler.setPersistenceManager(persistenceManager);
            this.loadUserVariablesServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("maintainUserVariables", NO_ASPECTS);
            ASPECTS.put("loadUserVariables", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<UserVariableMsg> maintainUserVariables(ServiceRequest<UserVariableMsg> rq)
            throws MaintainException {
        if ((this.maintainUserVariablesServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for maintainUserVariables().");
            throw new InjectionException("No service implementation configured for maintainUserVariables().");
        }
        ServiceResponse<UserVariableMsg> rs;
        this.maintainUserVariablesServiceHandler.init();
        rs = this.maintainUserVariablesServiceHandler.invoke(rq);
        this.maintainUserVariablesServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<UserVariableMsg> loadUserVariables(ServiceRequest<EmptyServiceMessage> rq)
            throws SearchException {
        if ((this.loadUserVariablesServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for loadUserVariables().");
            throw new InjectionException("No service implementation configured for loadUserVariables().");
        }
        ServiceResponse<UserVariableMsg> rs;
        this.loadUserVariablesServiceHandler.init();
        rs = this.loadUserVariablesServiceHandler.invoke(rq);
        this.loadUserVariablesServiceHandler.finish();
        return rs;
    }
}
