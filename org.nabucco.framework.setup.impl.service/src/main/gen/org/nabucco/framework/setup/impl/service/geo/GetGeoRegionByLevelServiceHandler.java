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
package org.nabucco.framework.setup.impl.service.geo;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.ServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;
import org.nabucco.framework.setup.facade.message.geo.GeoLevelRq;
import org.nabucco.framework.setup.facade.message.geo.GeoLevelRs;

/**
 * GetGeoRegionByLevelServiceHandler<p/>Service for geo information.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-05-06
 */
public abstract class GetGeoRegionByLevelServiceHandler extends PersistenceServiceHandlerSupport implements
        ServiceHandler, PersistenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.framework.setup.impl.service.geo.GetGeoRegionByLevelServiceHandler";

    /** Constructs a new GetGeoRegionByLevelServiceHandler instance. */
    public GetGeoRegionByLevelServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<GeoLevelRq>.
     * @return the ServiceResponse<GeoLevelRs>.
     * @throws SearchException
     */
    protected ServiceResponse<GeoLevelRs> invoke(ServiceRequest<GeoLevelRq> rq) throws SearchException {
        ServiceResponse<GeoLevelRs> rs;
        GeoLevelRs msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.getGeoRegionByLevel(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<GeoLevelRs>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (SearchException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            SearchException wrappedException = new SearchException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new SearchException("Error during service invocation.", e);
        }
    }

    /**
     * Get GeoRegions for a given Level, by requesting page -1 all results will be returned at once
     *
     * @param msg the GeoLevelRq.
     * @return the GeoLevelRs.
     * @throws SearchException
     */
    protected abstract GeoLevelRs getGeoRegionByLevel(GeoLevelRq msg) throws SearchException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
