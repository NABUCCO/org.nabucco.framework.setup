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
package org.nabucco.framework.setup.ui.web.communication.geo;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
import org.nabucco.framework.setup.facade.message.geo.GeoImportMsg;
import org.nabucco.framework.setup.facade.message.geo.GeoLevelRq;
import org.nabucco.framework.setup.facade.message.geo.GeoLevelRs;
import org.nabucco.framework.setup.facade.message.geo.GeoRequestMsg;
import org.nabucco.framework.setup.facade.message.geo.GeoResponseMsg;
import org.nabucco.framework.setup.facade.service.geo.GeoService;

/**
 * GeoServiceDelegate<p/>Service for geo information.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-05-06
 */
public class GeoServiceDelegate extends ServiceDelegateSupport {

    private GeoService service;

    /**
     * Constructs a new GeoServiceDelegate instance.
     *
     * @param service the GeoService.
     */
    public GeoServiceDelegate(GeoService service) {
        super();
        this.service = service;
    }

    /**
     * Getter for the GeoRelations.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the GeoRequestMsg.
     * @return the GeoResponseMsg.
     * @throws SearchException
     */
    public GeoResponseMsg getGeoRelations(GeoRequestMsg message, NabuccoSession session,
            ServiceSubContext... subContexts) throws SearchException {
        ServiceRequest<GeoRequestMsg> request = new ServiceRequest<GeoRequestMsg>(super.createServiceContext(session,
                subContexts));
        request.setRequestMessage(message);
        ServiceResponse<GeoResponseMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.getGeoRelations(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(GeoService.class, "getGeoRelations", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new SearchException("Cannot execute service operation: GeoService.getGeoRelations");
    }

    /**
     * Getter for the GeoRegionByLevel.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the GeoLevelRq.
     * @return the GeoLevelRs.
     * @throws SearchException
     */
    public GeoLevelRs getGeoRegionByLevel(GeoLevelRq message, NabuccoSession session, ServiceSubContext... subContexts)
            throws SearchException {
        ServiceRequest<GeoLevelRq> request = new ServiceRequest<GeoLevelRq>(super.createServiceContext(session,
                subContexts));
        request.setRequestMessage(message);
        ServiceResponse<GeoLevelRs> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.getGeoRegionByLevel(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(GeoService.class, "getGeoRegionByLevel", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new SearchException("Cannot execute service operation: GeoService.getGeoRegionByLevel");
    }

    /**
     * ImportElement.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the GeoImportMsg.
     * @return the GeoImportMsg.
     * @throws ServiceException
     */
    public GeoImportMsg importElement(GeoImportMsg message, NabuccoSession session, ServiceSubContext... subContexts)
            throws ServiceException {
        ServiceRequest<GeoImportMsg> request = new ServiceRequest<GeoImportMsg>(super.createServiceContext(session,
                subContexts));
        request.setRequestMessage(message);
        ServiceResponse<GeoImportMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.importElement(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(GeoService.class, "importElement", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new ServiceException("Cannot execute service operation: GeoService.importElement");
    }
}
