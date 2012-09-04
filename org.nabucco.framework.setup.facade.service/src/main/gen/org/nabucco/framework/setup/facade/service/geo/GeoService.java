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
package org.nabucco.framework.setup.facade.service.geo;

import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.setup.facade.message.geo.GeoImportMsg;
import org.nabucco.framework.setup.facade.message.geo.GeoLevelRq;
import org.nabucco.framework.setup.facade.message.geo.GeoLevelRs;
import org.nabucco.framework.setup.facade.message.geo.GeoRequestMsg;
import org.nabucco.framework.setup.facade.message.geo.GeoResponseMsg;

/**
 * GeoService<p/>Service for geo information.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-05-06
 */
public interface GeoService extends Service {

    /**
     * Loads geo informations.
     *
     * @param rq the ServiceRequest<GeoRequestMsg>.
     * @return the ServiceResponse<GeoResponseMsg>.
     * @throws SearchException
     */
    ServiceResponse<GeoResponseMsg> getGeoRelations(ServiceRequest<GeoRequestMsg> rq) throws SearchException;

    /**
     * Get GeoRegions for a given Level, by requesting page -1 all results will be returned at once
     *
     * @param rq the ServiceRequest<GeoLevelRq>.
     * @return the ServiceResponse<GeoLevelRs>.
     * @throws SearchException
     */
    ServiceResponse<GeoLevelRs> getGeoRegionByLevel(ServiceRequest<GeoLevelRq> rq) throws SearchException;

    /**
     * Imports geo informations.
     *
     * @param rq the ServiceRequest<GeoImportMsg>.
     * @return the ServiceResponse<GeoImportMsg>.
     * @throws ServiceException
     */
    ServiceResponse<GeoImportMsg> importElement(ServiceRequest<GeoImportMsg> rq) throws ServiceException;
}
