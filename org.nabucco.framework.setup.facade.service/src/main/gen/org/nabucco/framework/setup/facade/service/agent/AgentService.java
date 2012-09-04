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
package org.nabucco.framework.setup.facade.service.agent;

import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.setup.facade.exception.AgentException;
import org.nabucco.framework.setup.facade.message.agent.AgentNameRq;

/**
 * AgentService<p/>Service for agent administration.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-09
 */
public interface AgentService extends Service {

    /**
     * Activate a configured agent.
     *
     * @param rq the ServiceRequest<AgentNameRq>.
     * @return the ServiceResponse<EmptyServiceMessage>.
     * @throws AgentException
     */
    ServiceResponse<EmptyServiceMessage> startAgent(ServiceRequest<AgentNameRq> rq) throws AgentException;

    /**
     * Deactivate a configured agent.
     *
     * @param rq the ServiceRequest<AgentNameRq>.
     * @return the ServiceResponse<EmptyServiceMessage>.
     * @throws AgentException
     */
    ServiceResponse<EmptyServiceMessage> stopAgent(ServiceRequest<AgentNameRq> rq) throws AgentException;
}
