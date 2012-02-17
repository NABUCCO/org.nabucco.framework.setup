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
import org.nabucco.framework.base.facade.datatype.Number;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.geo.GeoRegion;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;

/**
 * GeoLevelRs<p/>Containing all Geo Region for a level<p/>
 *
 * @version 1.0
 * @author Silas Schwarz, PRODYNA AG, 2011-06-01
 */
public class GeoLevelRs extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;", "l0,n;u0,n;m1,1;" };

    public static final String REGIONLIST = "regionList";

    public static final String REMAININGPAGES = "remainingPages";

    private NabuccoList<GeoRegion> regionList;

    private Number remainingPages;

    /** Constructs a new GeoLevelRs instance. */
    public GeoLevelRs() {
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
        propertyMap.put(REGIONLIST, PropertyDescriptorSupport.createCollection(REGIONLIST, GeoRegion.class, 0,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPONENT));
        propertyMap.put(REMAININGPAGES, PropertyDescriptorSupport.createBasetype(REMAININGPAGES, Number.class, 1,
                PROPERTY_CONSTRAINTS[1], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(GeoLevelRs.getPropertyDescriptor(REGIONLIST), this.regionList));
        properties.add(super.createProperty(GeoLevelRs.getPropertyDescriptor(REMAININGPAGES), this.remainingPages));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(REGIONLIST) && (property.getType() == GeoRegion.class))) {
            this.regionList = ((NabuccoList<GeoRegion>) property.getInstance());
            return true;
        } else if ((property.getName().equals(REMAININGPAGES) && (property.getType() == Number.class))) {
            this.setRemainingPages(((Number) property.getInstance()));
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
        final GeoLevelRs other = ((GeoLevelRs) obj);
        if ((this.regionList == null)) {
            if ((other.regionList != null))
                return false;
        } else if ((!this.regionList.equals(other.regionList)))
            return false;
        if ((this.remainingPages == null)) {
            if ((other.remainingPages != null))
                return false;
        } else if ((!this.remainingPages.equals(other.remainingPages)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.regionList == null) ? 0 : this.regionList.hashCode()));
        result = ((PRIME * result) + ((this.remainingPages == null) ? 0 : this.remainingPages.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Missing description at method getRegionList.
     *
     * @return the NabuccoList<GeoRegion>.
     */
    public NabuccoList<GeoRegion> getRegionList() {
        if ((this.regionList == null)) {
            this.regionList = new NabuccoListImpl<GeoRegion>(NabuccoCollectionState.INITIALIZED);
        }
        return this.regionList;
    }

    /**
     * Missing description at method getRemainingPages.
     *
     * @return the Number.
     */
    public Number getRemainingPages() {
        return this.remainingPages;
    }

    /**
     * Missing description at method setRemainingPages.
     *
     * @param remainingPages the Number.
     */
    public void setRemainingPages(Number remainingPages) {
        this.remainingPages = remainingPages;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(GeoLevelRs.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(GeoLevelRs.class).getAllProperties();
    }
}
