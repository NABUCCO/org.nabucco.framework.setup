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
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Number;
import org.nabucco.framework.base.facade.datatype.geo.GeoLocale;
import org.nabucco.framework.base.facade.datatype.geo.LocationType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;

/**
 * GeoLevelRq<p/>Request for a all Elements On a Given Level<p/>
 *
 * @version 1.0
 * @author Silas Schwarz, PRODYNA AG, 2011-06-01
 */
public class GeoLevelRq extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l2,2;u0,n;m1,1;", "m1,1;", "l0,n;u0,n;m0,1;",
            "l0,255;u0,n;m0,1;" };

    public static final String LOCALE = "locale";

    public static final String LOCATIONTYPE = "locationType";

    public static final String STARTPAGE = "startPage";

    public static final String NAMETOKEN = "nameToken";

    private GeoLocale locale;

    private LocationType locationType;

    private Number startPage;

    private Name nameToken;

    /** Constructs a new GeoLevelRq instance. */
    public GeoLevelRq() {
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
        propertyMap.put(LOCALE,
                PropertyDescriptorSupport.createBasetype(LOCALE, GeoLocale.class, 0, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(LOCATIONTYPE, PropertyDescriptorSupport.createEnumeration(LOCATIONTYPE, LocationType.class, 1,
                PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(STARTPAGE,
                PropertyDescriptorSupport.createBasetype(STARTPAGE, Number.class, 2, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(NAMETOKEN,
                PropertyDescriptorSupport.createBasetype(NAMETOKEN, Name.class, 3, PROPERTY_CONSTRAINTS[3], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(GeoLevelRq.getPropertyDescriptor(LOCALE), this.locale));
        properties.add(super.createProperty(GeoLevelRq.getPropertyDescriptor(LOCATIONTYPE), this.getLocationType()));
        properties.add(super.createProperty(GeoLevelRq.getPropertyDescriptor(STARTPAGE), this.startPage));
        properties.add(super.createProperty(GeoLevelRq.getPropertyDescriptor(NAMETOKEN), this.nameToken));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(LOCALE) && (property.getType() == GeoLocale.class))) {
            this.setLocale(((GeoLocale) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LOCATIONTYPE) && (property.getType() == LocationType.class))) {
            this.setLocationType(((LocationType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(STARTPAGE) && (property.getType() == Number.class))) {
            this.setStartPage(((Number) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAMETOKEN) && (property.getType() == Name.class))) {
            this.setNameToken(((Name) property.getInstance()));
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
        final GeoLevelRq other = ((GeoLevelRq) obj);
        if ((this.locale == null)) {
            if ((other.locale != null))
                return false;
        } else if ((!this.locale.equals(other.locale)))
            return false;
        if ((this.locationType == null)) {
            if ((other.locationType != null))
                return false;
        } else if ((!this.locationType.equals(other.locationType)))
            return false;
        if ((this.startPage == null)) {
            if ((other.startPage != null))
                return false;
        } else if ((!this.startPage.equals(other.startPage)))
            return false;
        if ((this.nameToken == null)) {
            if ((other.nameToken != null))
                return false;
        } else if ((!this.nameToken.equals(other.nameToken)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.locale == null) ? 0 : this.locale.hashCode()));
        result = ((PRIME * result) + ((this.locationType == null) ? 0 : this.locationType.hashCode()));
        result = ((PRIME * result) + ((this.startPage == null) ? 0 : this.startPage.hashCode()));
        result = ((PRIME * result) + ((this.nameToken == null) ? 0 : this.nameToken.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
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
     * Missing description at method getLocationType.
     *
     * @return the LocationType.
     */
    public LocationType getLocationType() {
        return this.locationType;
    }

    /**
     * Missing description at method setLocationType.
     *
     * @param locationType the LocationType.
     */
    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    /**
     * Missing description at method getStartPage.
     *
     * @return the Number.
     */
    public Number getStartPage() {
        return this.startPage;
    }

    /**
     * Missing description at method setStartPage.
     *
     * @param startPage the Number.
     */
    public void setStartPage(Number startPage) {
        this.startPage = startPage;
    }

    /**
     * Missing description at method getNameToken.
     *
     * @return the Name.
     */
    public Name getNameToken() {
        return this.nameToken;
    }

    /**
     * Missing description at method setNameToken.
     *
     * @param nameToken the Name.
     */
    public void setNameToken(Name nameToken) {
        this.nameToken = nameToken;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(GeoLevelRq.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(GeoLevelRq.class).getAllProperties();
    }
}
