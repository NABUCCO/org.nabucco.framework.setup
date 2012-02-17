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
package org.nabucco.framework.setup.facade.datatype.agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Duration;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.setup.facade.datatype.agent.Agent;
import org.nabucco.framework.setup.facade.datatype.agent.AgentType;

/**
 * DurationAgent<p/>A duration scheduled agent.<p/>
 *
 * @version 1
 * @author Nicolas Moser, PRODYNA AG, 2011-06-06
 */
public class DurationAgent extends Agent implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final AgentType TYPE_DEFAULT = AgentType.DURATION;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m1,1;" };

    public static final String DURATION = "duration";

    /** The duration in milliseconds. */
    private Duration duration;

    /** Constructs a new DurationAgent instance. */
    public DurationAgent() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        type = TYPE_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the DurationAgent.
     */
    protected void cloneObject(DurationAgent clone) {
        super.cloneObject(clone);
        clone.setType(this.getType());
        if ((this.getDuration() != null)) {
            clone.setDuration(this.getDuration().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(Agent.class).getPropertyMap());
        propertyMap.put(DURATION,
                PropertyDescriptorSupport.createBasetype(DURATION, Duration.class, 6, PROPERTY_CONSTRAINTS[0], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(DurationAgent.getPropertyDescriptor(DURATION), this.duration, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(DURATION) && (property.getType() == Duration.class))) {
            this.setDuration(((Duration) property.getInstance()));
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
        final DurationAgent other = ((DurationAgent) obj);
        if ((this.duration == null)) {
            if ((other.duration != null))
                return false;
        } else if ((!this.duration.equals(other.duration)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.duration == null) ? 0 : this.duration.hashCode()));
        return result;
    }

    @Override
    public DurationAgent cloneObject() {
        DurationAgent clone = new DurationAgent();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The duration in milliseconds.
     *
     * @return the Duration.
     */
    public Duration getDuration() {
        return this.duration;
    }

    /**
     * The duration in milliseconds.
     *
     * @param duration the Duration.
     */
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    /**
     * The duration in milliseconds.
     *
     * @param duration the Long.
     */
    public void setDuration(Long duration) {
        if ((this.duration == null)) {
            if ((duration == null)) {
                return;
            }
            this.duration = new Duration();
        }
        this.duration.setValue(duration);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(DurationAgent.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(DurationAgent.class).getAllProperties();
    }
}
