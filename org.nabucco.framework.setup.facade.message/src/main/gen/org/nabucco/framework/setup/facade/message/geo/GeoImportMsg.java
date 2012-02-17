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
package org.nabucco.framework.setup.facade.message.geo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.framework.setup.facade.datatype.geo.SetupGeoLocation;
import org.nabucco.framework.setup.facade.datatype.geo.SetupGeoRegion;
import org.nabucco.framework.setup.facade.datatype.geo.SetupGeoRelation;

/**
 * GeoImportMsg<p/>Generic Import Message for Geo Data<p/>
 *
 * @version 1
 * @author Silas Schwarz, PRODYNA AG, 2011-05-16
 */
public class GeoImportMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;", "m0,1;", "m0,1;" };

    public static final String LOCATION = "location";

    public static final String LOCATIONRELATION = "locationRelation";

    public static final String REGION = "region";

    private SetupGeoLocation location;

    private SetupGeoRelation locationRelation;

    private SetupGeoRegion region;

    /** Constructs a new GeoImportMsg instance. */
    public GeoImportMsg() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(LOCATION, PropertyDescriptorSupport.createDatatype(LOCATION, SetupGeoLocation.class, 0,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(LOCATIONRELATION, PropertyDescriptorSupport.createDatatype(LOCATIONRELATION,
                SetupGeoRelation.class, 1, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(REGION, PropertyDescriptorSupport.createDatatype(REGION, SetupGeoRegion.class, 2,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(GeoImportMsg.getPropertyDescriptor(LOCATION), this.getLocation()));
        properties.add(super.createProperty(GeoImportMsg.getPropertyDescriptor(LOCATIONRELATION),
                this.getLocationRelation()));
        properties.add(super.createProperty(GeoImportMsg.getPropertyDescriptor(REGION), this.getRegion()));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(LOCATION) && (property.getType() == SetupGeoLocation.class))) {
            this.setLocation(((SetupGeoLocation) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LOCATIONRELATION) && (property.getType() == SetupGeoRelation.class))) {
            this.setLocationRelation(((SetupGeoRelation) property.getInstance()));
            return true;
        } else if ((property.getName().equals(REGION) && (property.getType() == SetupGeoRegion.class))) {
            this.setRegion(((SetupGeoRegion) property.getInstance()));
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if ((this == obj)) {
            return true;
        }
        if ((obj == null)) {
            return false;
        }
        if ((this.getClass() != obj.getClass())) {
            return false;
        }
        if ((!super.equals(obj))) {
            return false;
        }
        final GeoImportMsg other = ((GeoImportMsg) obj);
        if ((this.location == null)) {
            if ((other.location != null))
                return false;
        } else if ((!this.location.equals(other.location)))
            return false;
        if ((this.locationRelation == null)) {
            if ((other.locationRelation != null))
                return false;
        } else if ((!this.locationRelation.equals(other.locationRelation)))
            return false;
        if ((this.region == null)) {
            if ((other.region != null))
                return false;
        } else if ((!this.region.equals(other.region)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.location == null) ? 0 : this.location.hashCode()));
        result = ((PRIME * result) + ((this.locationRelation == null) ? 0 : this.locationRelation.hashCode()));
        result = ((PRIME * result) + ((this.region == null) ? 0 : this.region.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Missing description at method getLocation.
     *
     * @return the SetupGeoLocation.
     */
    public SetupGeoLocation getLocation() {
        return this.location;
    }

    /**
     * Missing description at method setLocation.
     *
     * @param location the SetupGeoLocation.
     */
    public void setLocation(SetupGeoLocation location) {
        this.location = location;
    }

    /**
     * Missing description at method getLocationRelation.
     *
     * @return the SetupGeoRelation.
     */
    public SetupGeoRelation getLocationRelation() {
        return this.locationRelation;
    }

    /**
     * Missing description at method setLocationRelation.
     *
     * @param locationRelation the SetupGeoRelation.
     */
    public void setLocationRelation(SetupGeoRelation locationRelation) {
        this.locationRelation = locationRelation;
    }

    /**
     * Missing description at method getRegion.
     *
     * @return the SetupGeoRegion.
     */
    public SetupGeoRegion getRegion() {
        return this.region;
    }

    /**
     * Missing description at method setRegion.
     *
     * @param region the SetupGeoRegion.
     */
    public void setRegion(SetupGeoRegion region) {
        this.region = region;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(GeoImportMsg.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(GeoImportMsg.class).getAllProperties();
    }
}
