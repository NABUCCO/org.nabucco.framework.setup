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
package org.nabucco.framework.setup.impl.service.agent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.setup.facade.exception.AgentException;
import org.nabucco.framework.setup.facade.message.agent.AgentNameRq;
import org.nabucco.framework.setup.facade.service.agent.AgentService;

/**
 * AgentServiceImpl<p/>Service for agent administration.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-09
 */
public class AgentServiceImpl extends ServiceSupport implements AgentService {

    private static final long serialVersionUID = 1L;

    private static final String ID = "AgentService";

    private static Map<String, String[]> ASPECTS;

    private StartAgentServiceHandler startAgentServiceHandler;

    private StopAgentServiceHandler stopAgentServiceHandler;

    /** Constructs a new AgentServiceImpl instance. */
    public AgentServiceImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        this.startAgentServiceHandler = injector.inject(StartAgentServiceHandler.getId());
        if ((this.startAgentServiceHandler != null)) {
            this.startAgentServiceHandler.setLogger(super.getLogger());
        }
        this.stopAgentServiceHandler = injector.inject(StopAgentServiceHandler.getId());
        if ((this.stopAgentServiceHandler != null)) {
            this.stopAgentServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("startAgent", NO_ASPECTS);
            ASPECTS.put("stopAgent", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<EmptyServiceMessage> startAgent(ServiceRequest<AgentNameRq> rq) throws AgentException {
        if ((this.startAgentServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for startAgent().");
            throw new InjectionException("No service implementation configured for startAgent().");
        }
        ServiceResponse<EmptyServiceMessage> rs;
        this.startAgentServiceHandler.init();
        rs = this.startAgentServiceHandler.invoke(rq);
        this.startAgentServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<EmptyServiceMessage> stopAgent(ServiceRequest<AgentNameRq> rq) throws AgentException {
        if ((this.stopAgentServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for stopAgent().");
            throw new InjectionException("No service implementation configured for stopAgent().");
        }
        ServiceResponse<EmptyServiceMessage> rs;
        this.stopAgentServiceHandler.init();
        rs = this.stopAgentServiceHandler.invoke(rq);
        this.stopAgentServiceHandler.finish();
        return rs;
    }
}
