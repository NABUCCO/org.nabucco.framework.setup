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
package org.nabucco.framework.setup.ui.rcp.communication.geo;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
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
     * @param message the GeoRequestMsg.
     * @return the GeoResponseMsg.
     * @throws ClientException
     */
    public GeoResponseMsg getGeoRelations(GeoRequestMsg message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<GeoRequestMsg> request = new ServiceRequest<GeoRequestMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<GeoResponseMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
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
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: GeoService.getGeoRelations");
    }

    /**
     * Getter for the GeoRegionByLevel.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the GeoLevelRq.
     * @return the GeoLevelRs.
     * @throws ClientException
     */
    public GeoLevelRs getGeoRegionByLevel(GeoLevelRq message, ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<GeoLevelRq> request = new ServiceRequest<GeoLevelRq>(super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<GeoLevelRs> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
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
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: GeoService.getGeoRegionByLevel");
    }

    /**
     * ImportElement.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the GeoImportMsg.
     * @return the GeoImportMsg.
     * @throws ClientException
     */
    public GeoImportMsg importElement(GeoImportMsg message, ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<GeoImportMsg> request = new ServiceRequest<GeoImportMsg>(super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<GeoImportMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
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
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: GeoService.importElement");
    }
}
