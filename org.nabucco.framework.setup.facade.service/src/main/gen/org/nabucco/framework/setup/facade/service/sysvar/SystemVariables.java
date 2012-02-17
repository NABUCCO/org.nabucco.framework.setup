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
package org.nabucco.framework.setup.facade.service.sysvar;

import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.setup.sysvar.SystemVariableMsg;
import org.nabucco.framework.base.facade.service.Service;

/**
 * SystemVariables<p/>Service managing system variables.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-04-07
 */
public interface SystemVariables extends Service {

    /**
     * Maintaining all system variables.
     *
     * @param rq the ServiceRequest<SystemVariableMsg>.
     * @return the ServiceResponse<SystemVariableMsg>.
     * @throws MaintainException
     */
    ServiceResponse<SystemVariableMsg> maintainSystemVariables(ServiceRequest<SystemVariableMsg> rq)
            throws MaintainException;

    /**
     * Loading all system variables.
     *
     * @param rq the ServiceRequest<EmptyServiceMessage>.
     * @return the ServiceResponse<SystemVariableMsg>.
     * @throws SearchException
     */
    ServiceResponse<SystemVariableMsg> loadSystemVariables(ServiceRequest<EmptyServiceMessage> rq)
            throws SearchException;
}