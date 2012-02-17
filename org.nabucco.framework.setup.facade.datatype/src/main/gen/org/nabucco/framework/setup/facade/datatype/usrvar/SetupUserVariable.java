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
package org.nabucco.framework.setup.facade.datatype.usrvar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.BasetypeType;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.security.UserId;
import org.nabucco.framework.setup.facade.datatype.usrvar.UserVariableValue;

/**
 * SetupUserVariable<p/>Entity representation of a system variable.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-04-07
 */
public class SetupUserVariable extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,12;u0,n;m1,1;", "l3,32;u0,n;m1,1;", "l0,255;u0,n;m1,1;",
            "m1,1;", "l0,255;u0,n;m1,1;" };

    public static final String OWNER = "owner";

    public static final String USER = "user";

    public static final String NAME = "name";

    public static final String TYPE = "type";

    public static final String VALUE = "value";

    private Owner owner;

    private UserId user;

    private Name name;

    private BasetypeType type;

    private UserVariableValue value;

    /** Constructs a new SetupUserVariable instance. */
    public SetupUserVariable() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the SetupUserVariable.
     */
    protected void cloneObject(SetupUserVariable clone) {
        super.cloneObject(clone);
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getUser() != null)) {
            clone.setUser(this.getUser().cloneObject());
        }
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        clone.setType(this.getType());
        if ((this.getValue() != null)) {
            clone.setValue(this.getValue().cloneObject());
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
        propertyMap.put(OWNER,
                PropertyDescriptorSupport.createBasetype(OWNER, Owner.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(USER,
                PropertyDescriptorSupport.createBasetype(USER, UserId.class, 4, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, Name.class, 5, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(TYPE, PropertyDescriptorSupport.createEnumeration(TYPE, BasetypeType.class, 6,
                PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(VALUE, PropertyDescriptorSupport.createBasetype(VALUE, UserVariableValue.class, 7,
                PROPERTY_CONSTRAINTS[4], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(SetupUserVariable.getPropertyDescriptor(OWNER), this.owner, null));
        properties.add(super.createProperty(SetupUserVariable.getPropertyDescriptor(USER), this.user, null));
        properties.add(super.createProperty(SetupUserVariable.getPropertyDescriptor(NAME), this.name, null));
        properties.add(super.createProperty(SetupUserVariable.getPropertyDescriptor(TYPE), this.getType(), null));
        properties.add(super.createProperty(SetupUserVariable.getPropertyDescriptor(VALUE), this.value, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(OWNER) && (property.getType() == Owner.class))) {
            this.setOwner(((Owner) property.getInstance()));
            return true;
        } else if ((property.getName().equals(USER) && (property.getType() == UserId.class))) {
            this.setUser(((UserId) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAME) && (property.getType() == Name.class))) {
            this.setName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TYPE) && (property.getType() == BasetypeType.class))) {
            this.setType(((BasetypeType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(VALUE) && (property.getType() == UserVariableValue.class))) {
            this.setValue(((UserVariableValue) property.getInstance()));
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
        final SetupUserVariable other = ((SetupUserVariable) obj);
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.user == null)) {
            if ((other.user != null))
                return false;
        } else if ((!this.user.equals(other.user)))
            return false;
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
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
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.user == null) ? 0 : this.user.hashCode()));
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((PRIME * result) + ((this.value == null) ? 0 : this.value.hashCode()));
        return result;
    }

    @Override
    public SetupUserVariable cloneObject() {
        SetupUserVariable clone = new SetupUserVariable();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method getOwner.
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * Missing description at method setOwner.
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * Missing description at method setOwner.
     *
     * @param owner the String.
     */
    public void setOwner(String owner) {
        if ((this.owner == null)) {
            if ((owner == null)) {
                return;
            }
            this.owner = new Owner();
        }
        this.owner.setValue(owner);
    }

    /**
     * Missing description at method getUser.
     *
     * @return the UserId.
     */
    public UserId getUser() {
        return this.user;
    }

    /**
     * Missing description at method setUser.
     *
     * @param user the UserId.
     */
    public void setUser(UserId user) {
        this.user = user;
    }

    /**
     * Missing description at method setUser.
     *
     * @param user the String.
     */
    public void setUser(String user) {
        if ((this.user == null)) {
            if ((user == null)) {
                return;
            }
            this.user = new UserId();
        }
        this.user.setValue(user);
    }

    /**
     * Missing description at method getName.
     *
     * @return the Name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * Missing description at method setName.
     *
     * @param name the Name.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Missing description at method setName.
     *
     * @param name the String.
     */
    public void setName(String name) {
        if ((this.name == null)) {
            if ((name == null)) {
                return;
            }
            this.name = new Name();
        }
        this.name.setValue(name);
    }

    /**
     * Missing description at method getType.
     *
     * @return the BasetypeType.
     */
    public BasetypeType getType() {
        return this.type;
    }

    /**
     * Missing description at method setType.
     *
     * @param type the BasetypeType.
     */
    public void setType(BasetypeType type) {
        this.type = type;
    }

    /**
     * Missing description at method setType.
     *
     * @param type the String.
     */
    public void setType(String type) {
        if ((type == null)) {
            this.type = null;
        } else {
            this.type = BasetypeType.valueOf(type);
        }
    }

    /**
     * Missing description at method getValue.
     *
     * @return the UserVariableValue.
     */
    public UserVariableValue getValue() {
        return this.value;
    }

    /**
     * Missing description at method setValue.
     *
     * @param value the UserVariableValue.
     */
    public void setValue(UserVariableValue value) {
        this.value = value;
    }

    /**
     * Missing description at method setValue.
     *
     * @param value the String.
     */
    public void setValue(String value) {
        if ((this.value == null)) {
            if ((value == null)) {
                return;
            }
            this.value = new UserVariableValue();
        }
        this.value.setValue(value);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(SetupUserVariable.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SetupUserVariable.class).getAllProperties();
    }
}
