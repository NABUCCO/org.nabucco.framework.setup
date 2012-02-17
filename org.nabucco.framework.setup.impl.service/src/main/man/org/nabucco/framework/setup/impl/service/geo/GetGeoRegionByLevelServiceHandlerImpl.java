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

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Number;
import org.nabucco.framework.base.facade.datatype.geo.GeoLevel;
import org.nabucco.framework.base.facade.datatype.geo.GeoLocale;
import org.nabucco.framework.base.facade.datatype.geo.LocationType;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.setup.facade.datatype.geo.GeoConfiguration;
import org.nabucco.framework.setup.facade.datatype.geo.SetupGeoRegion;
import org.nabucco.framework.setup.facade.message.geo.GeoLevelRq;
import org.nabucco.framework.setup.facade.message.geo.GeoLevelRs;

/**
 * GetGeoRegionByLevelServiceHandlerImpl
 * 
 * @author Silas Schwarz, PRODYNA AG
 */
public class GetGeoRegionByLevelServiceHandlerImpl extends GetGeoRegionByLevelServiceHandler {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private static final Integer PAGE_SIZE = 25;

    private static final String WILDCARD = "%";

    @Override
    protected GeoLevelRs getGeoRegionByLevel(GeoLevelRq msg) throws SearchException {

        GeoLevelRs result = new GeoLevelRs();

        GeoConfiguration config = GeoConfiguration.getInstance();

        GeoLocale locale = msg.getLocale();
        LocationType locationType = msg.getLocationType();
        Integer startPage = msg.getStartPage() != null ? msg.getStartPage().getValue() : 0;
        Name nameToken = msg.getNameToken() != null ? msg.getNameToken() : null;

        // check for tailing '%'
        if (nameToken != null) {
            if (!nameToken.getValue().endsWith(WILDCARD)) {
                nameToken.setValue(nameToken.getValue() + WILDCARD);
            }
        }

        GeoLevel level = config.getLevel(locale.getValue(), locationType);

        try {

            String query = "FROM SetupGeoRegion WHERE level = :level";
            if (nameToken != null) {
                query += " AND name LIKE :nameToken";
            }

            PersistenceManager pm = getPersistenceManager();
            NabuccoQuery<SetupGeoRegion> q = pm.createQuery(query);
            if (startPage != -1) {
                q.setFirstResult(startPage != 0 ? startPage * PAGE_SIZE : startPage);
            }
            q.setParameter("level", level);
            if (nameToken != null) {
                q.setParameter("nameToken", nameToken);
            }

            List<SetupGeoRegion> resultList = q.getResultList();

            // special case is when requested page is -1
            if (startPage == -1) {
                result.getRegionList().addAll(resultList);
                result.setRemainingPages(new Number(0));
                return result;
            }

            int remainingPages = (resultList.size() / PAGE_SIZE) - 1;

            q = pm.createQuery(query);
            q.setFirstResult(startPage != 0 ? startPage * PAGE_SIZE : startPage);
            q.setParameter("level", level);
            if (nameToken != null) {
                q.setParameter("nameToken", nameToken);
            }
            q.setMaxResults(PAGE_SIZE);

            resultList = q.getResultList();

            for (SetupGeoRegion current : resultList) {
                result.getRegionList().add(current);
            }

            result.setRemainingPages(new Number(remainingPages));

        } catch (PersistenceException pe) {
            throw new SearchException(pe);
        }

        return result;
    }
}
