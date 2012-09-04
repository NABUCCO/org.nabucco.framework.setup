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
package org.nabucco.framework.setup.facade.datatype.sequence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.date.Timestamp;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.SequenceGeneratorExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.setup.facade.datatype.sequence.SequenceType;

/**
 * SequenceGenerator<p/>A generated sequence.<p/>
 *
 * @version 1
 * @author Nicolas Moser, PRODYNA AG, 2011-06-01
 */
public class SequenceGenerator extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "m1,1;", "l0,n;u0,n;m1,1;",
            "l0,n;u0,n;m1,1;", "m1,1;" };

    public static final String GENERATORID = "generatorId";

    public static final String TYPE = "type";

    public static final String VALUE = "value";

    public static final String MODIFICATIONTIME = "modificationTime";

    public static final String EXTENSION = "extension";

    /** The sequence generator ID. */
    private Name generatorId;

    /** The sequence type; */
    private SequenceType type;

    /** The sequence value. */
    private SequenceValue value;

    /** The modification time. */
    private Timestamp modificationTime;

    /** The configured generator extension. */
    private SequenceGeneratorExtension extension;

    /** Constructs a new SequenceGenerator instance. */
    public SequenceGenerator() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the SequenceGenerator.
     */
    protected void cloneObject(SequenceGenerator clone) {
        super.cloneObject(clone);
        if ((this.getGeneratorId() != null)) {
            clone.setGeneratorId(this.getGeneratorId().cloneObject());
        }
        clone.setType(this.getType());
        if ((this.getValue() != null)) {
            clone.setValue(this.getValue().cloneObject());
        }
        if ((this.getModificationTime() != null)) {
            clone.setModificationTime(this.getModificationTime().cloneObject());
        }
        if ((this.getExtension() != null)) {
            clone.setExtension(this.getExtension().cloneObject());
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
        propertyMap.put(GENERATORID,
                PropertyDescriptorSupport.createBasetype(GENERATORID, Name.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(TYPE, PropertyDescriptorSupport.createEnumeration(TYPE, SequenceType.class, 4,
                PROPERTY_CONSTRAINTS[1], false));
        propertyMap
                .put(VALUE, PropertyDescriptorSupport.createBasetype(VALUE, SequenceValue.class, 5,
                        PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(MODIFICATIONTIME, PropertyDescriptorSupport.createBasetype(MODIFICATIONTIME, Timestamp.class,
                6, PROPERTY_CONSTRAINTS[3], false));
        propertyMap
                .put(EXTENSION, PropertyDescriptorSupport.createDatatype(EXTENSION, SequenceGeneratorExtension.class,
                        7, PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPONENT));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(SequenceGenerator.getPropertyDescriptor(GENERATORID), this.generatorId,
                null));
        properties.add(super.createProperty(SequenceGenerator.getPropertyDescriptor(TYPE), this.getType(), null));
        properties.add(super.createProperty(SequenceGenerator.getPropertyDescriptor(VALUE), this.value, null));
        properties.add(super.createProperty(SequenceGenerator.getPropertyDescriptor(MODIFICATIONTIME),
                this.modificationTime, null));
        properties.add(super.createProperty(SequenceGenerator.getPropertyDescriptor(EXTENSION), this.getExtension(),
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(GENERATORID) && (property.getType() == Name.class))) {
            this.setGeneratorId(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TYPE) && (property.getType() == SequenceType.class))) {
            this.setType(((SequenceType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(VALUE) && (property.getType() == SequenceValue.class))) {
            this.setValue(((SequenceValue) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MODIFICATIONTIME) && (property.getType() == Timestamp.class))) {
            this.setModificationTime(((Timestamp) property.getInstance()));
            return true;
        } else if ((property.getName().equals(EXTENSION) && (property.getType() == SequenceGeneratorExtension.class))) {
            this.setExtension(((SequenceGeneratorExtension) property.getInstance()));
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
        final SequenceGenerator other = ((SequenceGenerator) obj);
        if ((this.generatorId == null)) {
            if ((other.generatorId != null))
                return false;
        } else if ((!this.generatorId.equals(other.generatorId)))
            return false;
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        if ((this.value == null)) {
            if ((other.value != null))
                return false;
        } else if ((!this.value.equals(other.value)))
            return false;
        if ((this.modificationTime == null)) {
            if ((other.modificationTime != null))
                return false;
        } else if ((!this.modificationTime.equals(other.modificationTime)))
            return false;
        if ((this.extension == null)) {
            if ((other.extension != null))
                return false;
        } else if ((!this.extension.equals(other.extension)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.generatorId == null) ? 0 : this.generatorId.hashCode()));
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((PRIME * result) + ((this.value == null) ? 0 : this.value.hashCode()));
        result = ((PRIME * result) + ((this.modificationTime == null) ? 0 : this.modificationTime.hashCode()));
        result = ((PRIME * result) + ((this.extension == null) ? 0 : this.extension.hashCode()));
        return result;
    }

    @Override
    public SequenceGenerator cloneObject() {
        SequenceGenerator clone = new SequenceGenerator();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The sequence generator ID.
     *
     * @return the Name.
     */
    public Name getGeneratorId() {
        return this.generatorId;
    }

    /**
     * The sequence generator ID.
     *
     * @param generatorId the Name.
     */
    public void setGeneratorId(Name generatorId) {
        this.generatorId = generatorId;
    }

    /**
     * The sequence generator ID.
     *
     * @param generatorId the String.
     */
    public void setGeneratorId(String generatorId) {
        if ((this.generatorId == null)) {
            if ((generatorId == null)) {
                return;
            }
            this.generatorId = new Name();
        }
        this.generatorId.setValue(generatorId);
    }

    /**
     * The sequence type;
     *
     * @return the SequenceType.
     */
    public SequenceType getType() {
        return this.type;
    }

    /**
     * The sequence type;
     *
     * @param type the SequenceType.
     */
    public void setType(SequenceType type) {
        this.type = type;
    }

    /**
     * The sequence type;
     *
     * @param type the String.
     */
    public void setType(String type) {
        if ((type == null)) {
            this.type = null;
        } else {
            this.type = SequenceType.valueOf(type);
        }
    }

    /**
     * The sequence value.
     *
     * @return the SequenceValue.
     */
    public SequenceValue getValue() {
        return this.value;
    }

    /**
     * The sequence value.
     *
     * @param value the SequenceValue.
     */
    public void setValue(SequenceValue value) {
        this.value = value;
    }

    /**
     * The sequence value.
     *
     * @param value the String.
     */
    public void setValue(String value) {
        if ((this.value == null)) {
            if ((value == null)) {
                return;
            }
            this.value = new SequenceValue();
        }
        this.value.setValue(value);
    }

    /**
     * The modification time.
     *
     * @return the Timestamp.
     */
    public Timestamp getModificationTime() {
        return this.modificationTime;
    }

    /**
     * The modification time.
     *
     * @param modificationTime the Timestamp.
     */
    public void setModificationTime(Timestamp modificationTime) {
        this.modificationTime = modificationTime;
    }

    /**
     * The modification time.
     *
     * @param modificationTime the Long.
     */
    public void setModificationTime(Long modificationTime) {
        if ((this.modificationTime == null)) {
            if ((modificationTime == null)) {
                return;
            }
            this.modificationTime = new Timestamp();
        }
        this.modificationTime.setValue(modificationTime);
    }

    /**
     * The configured generator extension.
     *
     * @param extension the SequenceGeneratorExtension.
     */
    public void setExtension(SequenceGeneratorExtension extension) {
        this.extension = extension;
    }

    /**
     * The configured generator extension.
     *
     * @return the SequenceGeneratorExtension.
     */
    public SequenceGeneratorExtension getExtension() {
        return this.extension;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(SequenceGenerator.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SequenceGenerator.class).getAllProperties();
    }
}
