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
package org.nabucco.framework.setup.facade.datatype.geo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionResolver;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.GeoConfigExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.GeoRegionExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.GeoSchemaExtension;
import org.nabucco.framework.base.facade.datatype.geo.GeoLevel;
import org.nabucco.framework.base.facade.datatype.geo.LocationType;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;

/**
 * GeoConfiguration
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class GeoConfiguration {

    private static GeoConfiguration instance;

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            GeoConfiguration.class);

    private Map<String, Map<String, GeoRegionExtension>> geoconfigMap = new HashMap<String, Map<String, GeoRegionExtension>>();

    /**
     * Creates a new {@link GeoConfiguration} instance.
     */
    private GeoConfiguration() {
        configure();
    }

    /**
     * Getter for the singleton instance.
     * 
     * @return the {@link GeoConfiguration} instance
     */
    public static synchronized GeoConfiguration getInstance() {
        if (instance == null) {
            instance = new GeoConfiguration();
        }
        return instance;
    }

    /**
     * Getter for the geo level for the locale and location type.
     * 
     * @param locale
     *            the locale as string
     * @param type
     *            the location type
     * 
     * @return the geo level
     */
    public GeoLevel getLevel(String locale, LocationType type) {

        Map<String, GeoRegionExtension> regionMap = this.geoconfigMap.get(locale);

        if (regionMap == null) {
            return new GeoLevel(-1);
        }

        String typeID = type.getId();

        GeoRegionExtension region = regionMap.get(typeID);

        if (region == null) {
            return new GeoLevel(-1);
        }

        String id = region.getId().getValue().getValue();

        try {
            int level = Integer.parseInt(id);

            return new GeoLevel(level);

        } catch (Exception e) {
            return new GeoLevel(-1);
        }

    }

    /**
     * Configure the instance via extension point.
     */
    private void configure() {

        try {
            GeoConfigExtension geoConfig = (GeoConfigExtension) NabuccoSystem
                    .getExtensionResolver().resolveExtension(
                            ExtensionPointType.ORG_NABUCCO_FRAMEWORK_SETUP_GEO_CONFIG,
                            ExtensionResolver.DEFAULT_EXTENSION);

            List<GeoSchemaExtension> schemaList = geoConfig.getGeoSchemaList();

            for (GeoSchemaExtension gse : schemaList) {

                String locale = gse.getLocale().getValue().getValue();

                Map<String, GeoRegionExtension> map = new HashMap<String, GeoRegionExtension>();

                this.geoconfigMap.put(locale, map);

                List<GeoRegionExtension> regionList = gse.getGeoRegionList();

                for (GeoRegionExtension gre : regionList) {
                    String type = gre.getType().getValue().getValue();
                    map.put(type, gre);
                }

            }

        } catch (Exception e) {
            logger.error(e, "Cannot configure geo service");
        }
    }

}
