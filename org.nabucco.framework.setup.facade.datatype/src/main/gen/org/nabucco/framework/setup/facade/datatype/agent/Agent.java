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
package org.nabucco.framework.setup.facade.datatype.agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.FullQualifiedClassName;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.date.Timestamp;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.setup.facade.datatype.agent.AgentType;

/**
 * Agent<p/>A scheduled agent.<p/>
 *
 * @version 1
 * @author Nicolas Moser, PRODYNA AG, 2011-06-06
 */
public abstract class Agent extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final Timestamp LASTRUN_DEFAULT = new Timestamp(0L);

    private static final Flag ACTIVE_DEFAULT = new Flag(true);

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "l0,255;u0,n;m1,1;", "l0,n;u0,n;m1,1;",
            "l3,1000;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "l0,n;u0,n;m1,1;" };

    public static final String TYPE = "type";

    public static final String NAME = "name";

    public static final String ACTIVE = "active";

    public static final String EXECUTOR = "executor";

    public static final String LASTRUN = "lastRun";

    public static final String NEXTRUN = "nextRun";

    /** Type of the agent. */
    protected AgentType type;

    /** The agent name. */
    private Name name;

    /** Whether the agent is active or not. */
    private Flag active;

    /** The agent executor. */
    private FullQualifiedClassName executor;

    /** The timestamp of the last agent run. */
    private Timestamp lastRun;

    /** The timestamp of the next agent run. */
    private Timestamp nextRun;

    /** Constructs a new Agent instance. */
    public Agent() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        active = ACTIVE_DEFAULT;
        lastRun = LASTRUN_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the Agent.
     */
    protected void cloneObject(Agent clone) {
        super.cloneObject(clone);
        clone.setType(this.getType());
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getActive() != null)) {
            clone.setActive(this.getActive().cloneObject());
        }
        if ((this.getExecutor() != null)) {
            clone.setExecutor(this.getExecutor().cloneObject());
        }
        if ((this.getLastRun() != null)) {
            clone.setLastRun(this.getLastRun().cloneObject());
        }
        if ((this.getNextRun() != null)) {
            clone.setNextRun(this.getNextRun().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(TYPE,
                PropertyDescriptorSupport.createEnumeration(TYPE, AgentType.class, 0, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, Name.class, 1, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(ACTIVE,
                PropertyDescriptorSupport.createBasetype(ACTIVE, Flag.class, 2, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(EXECUTOR, PropertyDescriptorSupport.createBasetype(EXECUTOR, FullQualifiedClassName.class, 3,
                PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(LASTRUN,
                PropertyDescriptorSupport.createBasetype(LASTRUN, Timestamp.class, 4, PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(NEXTRUN,
                PropertyDescriptorSupport.createBasetype(NEXTRUN, Timestamp.class, 5, PROPERTY_CONSTRAINTS[5], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(Agent.getPropertyDescriptor(TYPE), this.getType(), null));
        properties.add(super.createProperty(Agent.getPropertyDescriptor(NAME), this.name, null));
        properties.add(super.createProperty(Agent.getPropertyDescriptor(ACTIVE), this.active, null));
        properties.add(super.createProperty(Agent.getPropertyDescriptor(EXECUTOR), this.executor, null));
        properties.add(super.createProperty(Agent.getPropertyDescriptor(LASTRUN), this.lastRun, null));
        properties.add(super.createProperty(Agent.getPropertyDescriptor(NEXTRUN), this.nextRun, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TYPE) && (property.getType() == AgentType.class))) {
            this.setType(((AgentType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAME) && (property.getType() == Name.class))) {
            this.setName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ACTIVE) && (property.getType() == Flag.class))) {
            this.setActive(((Flag) property.getInstance()));
            return true;
        } else if ((property.getName().equals(EXECUTOR) && (property.getType() == FullQualifiedClassName.class))) {
            this.setExecutor(((FullQualifiedClassName) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LASTRUN) && (property.getType() == Timestamp.class))) {
            this.setLastRun(((Timestamp) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NEXTRUN) && (property.getType() == Timestamp.class))) {
            this.setNextRun(((Timestamp) property.getInstance()));
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
        final Agent other = ((Agent) obj);
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.active == null)) {
            if ((other.active != null))
                return false;
        } else if ((!this.active.equals(other.active)))
            return false;
        if ((this.executor == null)) {
            if ((other.executor != null))
                return false;
        } else if ((!this.executor.equals(other.executor)))
            return false;
        if ((this.lastRun == null)) {
            if ((other.lastRun != null))
                return false;
        } else if ((!this.lastRun.equals(other.lastRun)))
            return false;
        if ((this.nextRun == null)) {
            if ((other.nextRun != null))
                return false;
        } else if ((!this.nextRun.equals(other.nextRun)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.active == null) ? 0 : this.active.hashCode()));
        result = ((PRIME * result) + ((this.executor == null) ? 0 : this.executor.hashCode()));
        result = ((PRIME * result) + ((this.lastRun == null) ? 0 : this.lastRun.hashCode()));
        result = ((PRIME * result) + ((this.nextRun == null) ? 0 : this.nextRun.hashCode()));
        return result;
    }

    @Override
    public abstract Agent cloneObject();

    /**
     * Type of the agent.
     *
     * @return the AgentType.
     */
    public AgentType getType() {
        return this.type;
    }

    /**
     * Type of the agent.
     *
     * @param type the AgentType.
     */
    public void setType(AgentType type) {
        this.type = type;
    }

    /**
     * Type of the agent.
     *
     * @param type the String.
     */
    public void setType(String type) {
        if ((type == null)) {
            this.type = null;
        } else {
            this.type = AgentType.valueOf(type);
        }
    }

    /**
     * The agent name.
     *
     * @return the Name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * The agent name.
     *
     * @param name the Name.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * The agent name.
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
     * Whether the agent is active or not.
     *
     * @return the Flag.
     */
    public Flag getActive() {
        return this.active;
    }

    /**
     * Whether the agent is active or not.
     *
     * @param active the Flag.
     */
    public void setActive(Flag active) {
        this.active = active;
    }

    /**
     * Whether the agent is active or not.
     *
     * @param active the Boolean.
     */
    public void setActive(Boolean active) {
        if ((this.active == null)) {
            if ((active == null)) {
                return;
            }
            this.active = new Flag();
        }
        this.active.setValue(active);
    }

    /**
     * The agent executor.
     *
     * @return the FullQualifiedClassName.
     */
    public FullQualifiedClassName getExecutor() {
        return this.executor;
    }

    /**
     * The agent executor.
     *
     * @param executor the FullQualifiedClassName.
     */
    public void setExecutor(FullQualifiedClassName executor) {
        this.executor = executor;
    }

    /**
     * The agent executor.
     *
     * @param executor the String.
     */
    public void setExecutor(String executor) {
        if ((this.executor == null)) {
            if ((executor == null)) {
                return;
            }
            this.executor = new FullQualifiedClassName();
        }
        this.executor.setValue(executor);
    }

    /**
     * The timestamp of the last agent run.
     *
     * @return the Timestamp.
     */
    public Timestamp getLastRun() {
        return this.lastRun;
    }

    /**
     * The timestamp of the last agent run.
     *
     * @param lastRun the Timestamp.
     */
    public void setLastRun(Timestamp lastRun) {
        this.lastRun = lastRun;
    }

    /**
     * The timestamp of the last agent run.
     *
     * @param lastRun the Long.
     */
    public void setLastRun(Long lastRun) {
        if ((this.lastRun == null)) {
            if ((lastRun == null)) {
                return;
            }
            this.lastRun = new Timestamp();
        }
        this.lastRun.setValue(lastRun);
    }

    /**
     * The timestamp of the next agent run.
     *
     * @return the Timestamp.
     */
    public Timestamp getNextRun() {
        return this.nextRun;
    }

    /**
     * The timestamp of the next agent run.
     *
     * @param nextRun the Timestamp.
     */
    public void setNextRun(Timestamp nextRun) {
        this.nextRun = nextRun;
    }

    /**
     * The timestamp of the next agent run.
     *
     * @param nextRun the Long.
     */
    public void setNextRun(Long nextRun) {
        if ((this.nextRun == null)) {
            if ((nextRun == null)) {
                return;
            }
            this.nextRun = new Timestamp();
        }
        this.nextRun.setValue(nextRun);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(Agent.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(Agent.class).getAllProperties();
    }
}
