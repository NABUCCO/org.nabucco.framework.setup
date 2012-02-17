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
package org.nabucco.framework.setup.impl.service.geo;

import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.setup.facade.datatype.geo.SetupGeoLocation;
import org.nabucco.framework.setup.facade.datatype.geo.SetupGeoRegion;
import org.nabucco.framework.setup.facade.datatype.geo.SetupGeoRelation;
import org.nabucco.framework.setup.facade.message.geo.GeoImportMsg;

/**
 * ImportElementServiceHandlerImpl
 * 
 * @author Silas Schwarz, PRODYNA AG
 */
public class ImportElementServiceHandlerImpl extends ImportElementServiceHandler {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private PersistenceManager pManager;

    @Override
    protected GeoImportMsg importElement(GeoImportMsg msg) throws ServiceException {
        pManager = getPersistenceManager();

        SetupGeoRelation locationRelation = msg.getLocationRelation();
        SetupGeoLocation location = msg.getLocation();
        SetupGeoRegion region = msg.getRegion();

        if (region != null) {
            msg.setRegion(preformImport(region));
        }
        if (locationRelation != null) {
            msg.setLocationRelation(preformImport(locationRelation));
        }
        if (location != null) {
            msg.setLocation(preformImport(location));
        }

        return msg;
    }

    /**
     * Imports a {@link NabuccoDatatype}.
     * 
     * @param element
     *            to import
     * @throws ServiceException
     *             if unable to persist.
     */
    private <T extends NabuccoDatatype> T preformImport(T element) throws ServiceException {

        try {
            return pManager.persist(element);
        } catch (PersistenceException e) {
            throw new ServiceException("unable to import :" + element, e);
        }
    }

}
