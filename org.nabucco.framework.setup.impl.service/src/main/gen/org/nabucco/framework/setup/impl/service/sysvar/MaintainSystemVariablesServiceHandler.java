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

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.setup.sysvar.SystemVariableMsg;
import org.nabucco.framework.base.impl.service.ServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;

/**
 * MaintainSystemVariablesServiceHandler<p/>Service managing system variables.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-04-07
 */
public abstract class MaintainSystemVariablesServiceHandler extends PersistenceServiceHandlerSupport implements
        ServiceHandler, PersistenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.framework.setup.impl.service.sysvar.MaintainSystemVariablesServiceHandler";

    /** Constructs a new MaintainSystemVariablesServiceHandler instance. */
    public MaintainSystemVariablesServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<SystemVariableMsg>.
     * @return the ServiceResponse<SystemVariableMsg>.
     * @throws MaintainException
     */
    protected ServiceResponse<SystemVariableMsg> invoke(ServiceRequest<SystemVariableMsg> rq) throws MaintainException {
        ServiceResponse<SystemVariableMsg> rs;
        SystemVariableMsg msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.maintainSystemVariables(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<SystemVariableMsg>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (MaintainException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            MaintainException wrappedException = new MaintainException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new MaintainException("Error during service invocation.", e);
        }
    }

    /**
     * Maintaining all system variables.
     *
     * @param msg the SystemVariableMsg.
     * @return the SystemVariableMsg.
     * @throws MaintainException
     */
    protected abstract SystemVariableMsg maintainSystemVariables(SystemVariableMsg msg) throws MaintainException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
