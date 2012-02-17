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
package org.nabucco.framework.setup.facade.datatype.journal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.FullQualifiedClassName;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.date.Timestamp;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.security.UserId;

/**
 * Journal<p/>A journal entry.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-03-17
 */
public class Journal extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final Name TITLE_DEFAULT = new Name("");

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,12;u0,n;m1,1;", "l3,32;u0,n;m1,1;", "l0,n;u0,n;m1,1;",
            "l3,1000;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "l0,255;u0,n;m1,1;" };

    public static final String OWNER = "owner";

    public static final String USER = "user";

    public static final String TIMESTAMP = "timestamp";

    public static final String CLASSNAME = "className";

    public static final String IDENTIFIER = "identifier";

    public static final String TITLE = "title";

    private Owner owner;

    private UserId user;

    private Timestamp timestamp;

    private FullQualifiedClassName className;

    private Identifier identifier;

    private Name title;

    /** Constructs a new Journal instance. */
    public Journal() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        title = TITLE_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the Journal.
     */
    protected void cloneObject(Journal clone) {
        super.cloneObject(clone);
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getUser() != null)) {
            clone.setUser(this.getUser().cloneObject());
        }
        if ((this.getTimestamp() != null)) {
            clone.setTimestamp(this.getTimestamp().cloneObject());
        }
        if ((this.getClassName() != null)) {
            clone.setClassName(this.getClassName().cloneObject());
        }
        if ((this.getIdentifier() != null)) {
            clone.setIdentifier(this.getIdentifier().cloneObject());
        }
        if ((this.getTitle() != null)) {
            clone.setTitle(this.getTitle().cloneObject());
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
        propertyMap
                .put(TIMESTAMP, PropertyDescriptorSupport.createBasetype(TIMESTAMP, Timestamp.class, 5,
                        PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(CLASSNAME, PropertyDescriptorSupport.createBasetype(CLASSNAME, FullQualifiedClassName.class, 6,
                PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(IDENTIFIER, PropertyDescriptorSupport.createBasetype(IDENTIFIER, Identifier.class, 7,
                PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(TITLE,
                PropertyDescriptorSupport.createBasetype(TITLE, Name.class, 8, PROPERTY_CONSTRAINTS[5], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(Journal.getPropertyDescriptor(OWNER), this.owner, null));
        properties.add(super.createProperty(Journal.getPropertyDescriptor(USER), this.user, null));
        properties.add(super.createProperty(Journal.getPropertyDescriptor(TIMESTAMP), this.timestamp, null));
        properties.add(super.createProperty(Journal.getPropertyDescriptor(CLASSNAME), this.className, null));
        properties.add(super.createProperty(Journal.getPropertyDescriptor(IDENTIFIER), this.identifier, null));
        properties.add(super.createProperty(Journal.getPropertyDescriptor(TITLE), this.title, null));
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
        } else if ((property.getName().equals(TIMESTAMP) && (property.getType() == Timestamp.class))) {
            this.setTimestamp(((Timestamp) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CLASSNAME) && (property.getType() == FullQualifiedClassName.class))) {
            this.setClassName(((FullQualifiedClassName) property.getInstance()));
            return true;
        } else if ((property.getName().equals(IDENTIFIER) && (property.getType() == Identifier.class))) {
            this.setIdentifier(((Identifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TITLE) && (property.getType() == Name.class))) {
            this.setTitle(((Name) property.getInstance()));
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
        final Journal other = ((Journal) obj);
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
        if ((this.timestamp == null)) {
            if ((other.timestamp != null))
                return false;
        } else if ((!this.timestamp.equals(other.timestamp)))
            return false;
        if ((this.className == null)) {
            if ((other.className != null))
                return false;
        } else if ((!this.className.equals(other.className)))
            return false;
        if ((this.identifier == null)) {
            if ((other.identifier != null))
                return false;
        } else if ((!this.identifier.equals(other.identifier)))
            return false;
        if ((this.title == null)) {
            if ((other.title != null))
                return false;
        } else if ((!this.title.equals(other.title)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.user == null) ? 0 : this.user.hashCode()));
        result = ((PRIME * result) + ((this.timestamp == null) ? 0 : this.timestamp.hashCode()));
        result = ((PRIME * result) + ((this.className == null) ? 0 : this.className.hashCode()));
        result = ((PRIME * result) + ((this.identifier == null) ? 0 : this.identifier.hashCode()));
        result = ((PRIME * result) + ((this.title == null) ? 0 : this.title.hashCode()));
        return result;
    }

    @Override
    public Journal cloneObject() {
        Journal clone = new Journal();
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
     * Missing description at method getTimestamp.
     *
     * @return the Timestamp.
     */
    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    /**
     * Missing description at method setTimestamp.
     *
     * @param timestamp the Timestamp.
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Missing description at method setTimestamp.
     *
     * @param timestamp the Long.
     */
    public void setTimestamp(Long timestamp) {
        if ((this.timestamp == null)) {
            if ((timestamp == null)) {
                return;
            }
            this.timestamp = new Timestamp();
        }
        this.timestamp.setValue(timestamp);
    }

    /**
     * Missing description at method getClassName.
     *
     * @return the FullQualifiedClassName.
     */
    public FullQualifiedClassName getClassName() {
        return this.className;
    }

    /**
     * Missing description at method setClassName.
     *
     * @param className the FullQualifiedClassName.
     */
    public void setClassName(FullQualifiedClassName className) {
        this.className = className;
    }

    /**
     * Missing description at method setClassName.
     *
     * @param className the String.
     */
    public void setClassName(String className) {
        if ((this.className == null)) {
            if ((className == null)) {
                return;
            }
            this.className = new FullQualifiedClassName();
        }
        this.className.setValue(className);
    }

    /**
     * Missing description at method getIdentifier.
     *
     * @return the Identifier.
     */
    public Identifier getIdentifier() {
        return this.identifier;
    }

    /**
     * Missing description at method setIdentifier.
     *
     * @param identifier the Identifier.
     */
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    /**
     * Missing description at method setIdentifier.
     *
     * @param identifier the Long.
     */
    public void setIdentifier(Long identifier) {
        if ((this.identifier == null)) {
            if ((identifier == null)) {
                return;
            }
            this.identifier = new Identifier();
        }
        this.identifier.setValue(identifier);
    }

    /**
     * Missing description at method getTitle.
     *
     * @return the Name.
     */
    public Name getTitle() {
        return this.title;
    }

    /**
     * Missing description at method setTitle.
     *
     * @param title the Name.
     */
    public void setTitle(Name title) {
        this.title = title;
    }

    /**
     * Missing description at method setTitle.
     *
     * @param title the String.
     */
    public void setTitle(String title) {
        if ((this.title == null)) {
            if ((title == null)) {
                return;
            }
            this.title = new Name();
        }
        this.title.setValue(title);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(Journal.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(Journal.class).getAllProperties();
    }
}
