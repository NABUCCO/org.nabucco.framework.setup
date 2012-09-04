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
package org.nabucco.framework.setup.facade.message.geo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.geo.GeoLocale;
import org.nabucco.framework.base.facade.datatype.geo.GeoName;
import org.nabucco.framework.base.facade.datatype.geo.LocationType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;

/**
 * GeoRequestMsg<p/>todo.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-05-06
 */
public class GeoRequestMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l2,255;u0,n;m1,1;", "l2,2;u0,n;m1,1;", "m1,1;" };

    public static final String NAME = "name";

    public static final String LOCALE = "locale";

    public static final String TYPE = "type";

    private GeoName name;

    private GeoLocale locale;

    private LocationType type;

    /** Constructs a new GeoRequestMsg instance. */
    public GeoRequestMsg() {
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
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, GeoName.class, 0, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(LOCALE,
                PropertyDescriptorSupport.createBasetype(LOCALE, GeoLocale.class, 1, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(TYPE, PropertyDescriptorSupport.createEnumeration(TYPE, LocationType.class, 2,
                PROPERTY_CONSTRAINTS[2], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(GeoRequestMsg.getPropertyDescriptor(NAME), this.name));
        properties.add(super.createProperty(GeoRequestMsg.getPropertyDescriptor(LOCALE), this.locale));
        properties.add(super.createProperty(GeoRequestMsg.getPropertyDescriptor(TYPE), this.getType()));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(NAME) && (property.getType() == GeoName.class))) {
            this.setName(((GeoName) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LOCALE) && (property.getType() == GeoLocale.class))) {
            this.setLocale(((GeoLocale) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TYPE) && (property.getType() == LocationType.class))) {
            this.setType(((LocationType) property.getInstance()));
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
        final GeoRequestMsg other = ((GeoRequestMsg) obj);
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.locale == null)) {
            if ((other.locale != null))
                return false;
        } else if ((!this.locale.equals(other.locale)))
            return false;
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.locale == null) ? 0 : this.locale.hashCode()));
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Missing description at method getName.
     *
     * @return the GeoName.
     */
    public GeoName getName() {
        return this.name;
    }

    /**
     * Missing description at method setName.
     *
     * @param name the GeoName.
     */
    public void setName(GeoName name) {
        this.name = name;
    }

    /**
     * Missing description at method getLocale.
     *
     * @return the GeoLocale.
     */
    public GeoLocale getLocale() {
        return this.locale;
    }

    /**
     * Missing description at method setLocale.
     *
     * @param locale the GeoLocale.
     */
    public void setLocale(GeoLocale locale) {
        this.locale = locale;
    }

    /**
     * Missing description at method getType.
     *
     * @return the LocationType.
     */
    public LocationType getType() {
        return this.type;
    }

    /**
     * Missing description at method setType.
     *
     * @param type the LocationType.
     */
    public void setType(LocationType type) {
        this.type = type;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(GeoRequestMsg.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(GeoRequestMsg.class).getAllProperties();
    }
}
