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
package org.nabucco.framework.setup.facade.message.sequence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;

/**
 * SequenceRq<p/>Request message holding the sequence generator id.<p/>
 *
 * @version 1
 * @author Nicolas Moser, PRODYNA AG, 2011-06-01
 */
public class SequenceRq extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "l0,255;u0,n;m1,1;" };

    public static final String SEQUENCENAME = "sequenceName";

    public static final String LOCALE = "locale";

    /** The id of the sequence generator. */
    private Name sequenceName;

    /** The name of the locale. */
    private Name locale;

    /** Constructs a new SequenceRq instance. */
    public SequenceRq() {
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
        propertyMap.put(SEQUENCENAME,
                PropertyDescriptorSupport.createBasetype(SEQUENCENAME, Name.class, 0, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(LOCALE,
                PropertyDescriptorSupport.createBasetype(LOCALE, Name.class, 1, PROPERTY_CONSTRAINTS[1], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(SequenceRq.getPropertyDescriptor(SEQUENCENAME), this.sequenceName));
        properties.add(super.createProperty(SequenceRq.getPropertyDescriptor(LOCALE), this.locale));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(SEQUENCENAME) && (property.getType() == Name.class))) {
            this.setSequenceName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LOCALE) && (property.getType() == Name.class))) {
            this.setLocale(((Name) property.getInstance()));
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
        final SequenceRq other = ((SequenceRq) obj);
        if ((this.sequenceName == null)) {
            if ((other.sequenceName != null))
                return false;
        } else if ((!this.sequenceName.equals(other.sequenceName)))
            return false;
        if ((this.locale == null)) {
            if ((other.locale != null))
                return false;
        } else if ((!this.locale.equals(other.locale)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.sequenceName == null) ? 0 : this.sequenceName.hashCode()));
        result = ((PRIME * result) + ((this.locale == null) ? 0 : this.locale.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * The id of the sequence generator.
     *
     * @return the Name.
     */
    public Name getSequenceName() {
        return this.sequenceName;
    }

    /**
     * The id of the sequence generator.
     *
     * @param sequenceName the Name.
     */
    public void setSequenceName(Name sequenceName) {
        this.sequenceName = sequenceName;
    }

    /**
     * The name of the locale.
     *
     * @return the Name.
     */
    public Name getLocale() {
        return this.locale;
    }

    /**
     * The name of the locale.
     *
     * @param locale the Name.
     */
    public void setLocale(Name locale) {
        this.locale = locale;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(SequenceRq.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SequenceRq.class).getAllProperties();
    }
}
