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
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.setup.facade.datatype.geo.SetupGeoLocation;
import org.nabucco.framework.setup.facade.datatype.geo.SetupGeoRegion;

/**
 * SetupGeoRelation<p/>GeoLocation for setup component.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-05-06
 */
public class SetupGeoRelation extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    public static final String LOCATION = "location";

    public static final String REGION = "region";

    private SetupGeoLocation location;

    private SetupGeoRegion region;

    /** Constructs a new SetupGeoRelation instance. */
    public SetupGeoRelation() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the SetupGeoRelation.
     */
    protected void cloneObject(SetupGeoRelation clone) {
        super.cloneObject(clone);
        if ((this.getLocation() != null)) {
            clone.setLocation(this.getLocation().cloneObject());
        }
        if ((this.getRegion() != null)) {
            clone.setRegion(this.getRegion().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(LOCATION, PropertyDescriptorSupport.createDatatype(LOCATION, SetupGeoLocation.class, 3,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.AGGREGATION));
        propertyMap.put(REGION, PropertyDescriptorSupport.createDatatype(REGION, SetupGeoRegion.class, 4,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.AGGREGATION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties
                .add(super.createProperty(SetupGeoRelation.getPropertyDescriptor(LOCATION), this.getLocation(), null));
        properties.add(super.createProperty(SetupGeoRelation.getPropertyDescriptor(REGION), this.getRegion(), null));
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
        final SetupGeoRelation other = ((SetupGeoRelation) obj);
        if ((this.location == null)) {
            if ((other.location != null))
                return false;
        } else if ((!this.location.equals(other.location)))
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
        result = ((PRIME * result) + ((this.region == null) ? 0 : this.region.hashCode()));
        return result;
    }

    @Override
    public SetupGeoRelation cloneObject() {
        SetupGeoRelation clone = new SetupGeoRelation();
        this.cloneObject(clone);
        return clone;
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
     * Missing description at method getLocation.
     *
     * @return the SetupGeoLocation.
     */
    public SetupGeoLocation getLocation() {
        return this.location;
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
     * Missing description at method getRegion.
     *
     * @return the SetupGeoRegion.
     */
    public SetupGeoRegion getRegion() {
        return this.region;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(SetupGeoRelation.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SetupGeoRelation.class).getAllProperties();
    }
}
