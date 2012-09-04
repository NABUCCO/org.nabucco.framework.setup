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
package org.nabucco.framework.setup.impl.service.sequence;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.ServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;
import org.nabucco.framework.setup.facade.exception.SequenceException;
import org.nabucco.framework.setup.facade.message.sequence.SequenceRq;
import org.nabucco.framework.setup.facade.message.sequence.SequenceRs;

/**
 * GenerateSequenceServiceHandler<p/>Service for sequence generation.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-01
 */
public abstract class GenerateSequenceServiceHandler extends PersistenceServiceHandlerSupport implements
        ServiceHandler, PersistenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.framework.setup.impl.service.sequence.GenerateSequenceServiceHandler";

    /** Constructs a new GenerateSequenceServiceHandler instance. */
    public GenerateSequenceServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<SequenceRq>.
     * @return the ServiceResponse<SequenceRs>.
     * @throws SequenceException
     */
    protected ServiceResponse<SequenceRs> invoke(ServiceRequest<SequenceRq> rq) throws SequenceException {
        ServiceResponse<SequenceRs> rs;
        SequenceRs msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.generateSequence(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<SequenceRs>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (SequenceException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            SequenceException wrappedException = new SequenceException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new SequenceException("Error during service invocation.", e);
        }
    }

    /**
     * Generate a new sequence.
     *
     * @param msg the SequenceRq.
     * @return the SequenceRs.
     * @throws SequenceException
     */
    protected abstract SequenceRs generateSequence(SequenceRq msg) throws SequenceException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
