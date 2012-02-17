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

import org.nabucco.framework.base.facade.datatype.geo.GeoLocale;
import org.nabucco.framework.base.facade.datatype.geo.GeoName;
import org.nabucco.framework.base.facade.datatype.geo.LocationType;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.setup.facade.datatype.geo.GeoConfiguration;
import org.nabucco.framework.setup.facade.datatype.geo.SetupGeoRelation;
import org.nabucco.framework.setup.facade.message.geo.GeoRequestMsg;
import org.nabucco.framework.setup.facade.message.geo.GeoResponseMsg;

/**
 * @author Frank Ratschinski, PRODYNA AG
 * 
 */
public class GetGeoRelationsServiceHandlerImpl extends GetGeoRelationsServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected GeoResponseMsg getGeoRelations(GeoRequestMsg msg) throws SearchException {

        GeoConfiguration config = GeoConfiguration.getInstance();
        GeoResponseMsg res = new GeoResponseMsg();

        GeoName name = msg.getName();
        GeoLocale locale = msg.getLocale();
        LocationType type = msg.getType();

        try {

            String query = "SELECT r FROM SetupGeoRelation r INNER JOIN r.region AS reg WHERE reg.name LIKE :name AND reg.level = :level AND reg.locale = :locale";
            PersistenceManager pm = getPersistenceManager();
            NabuccoQuery<SetupGeoRelation> q = pm.createQuery(query);

            q.setParameter("name", name);
            q.setParameter("locale", locale);
            q.setParameter("level", config.getLevel(locale.getValue(), type));

            for (SetupGeoRelation current : q.getResultList()) {
                res.getRelationList().add(current);
            }

        } catch (PersistenceException pe) {
            throw new SearchException(pe);
        }

        return res;
    }

}
