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
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.setup.facade.datatype.agent.Agent;
import org.nabucco.framework.setup.facade.datatype.agent.AgentType;
import org.nabucco.framework.setup.facade.datatype.agent.CronTrigger;

/**
 * CronAgent<p/>A chronological scheduled agent.<p/>
 *
 * @version 1
 * @author Nicolas Moser, PRODYNA AG, 2011-06-06
 */
public class CronAgent extends Agent implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final AgentType TYPE_DEFAULT = AgentType.CRON;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    public static final String CRONLIST = "cronList";

    /** The list of scheduled cron jobs. */
    private NabuccoList<CronTrigger> cronList;

    /** Constructs a new CronAgent instance. */
    public CronAgent() {
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
     * @param clone the CronAgent.
     */
    protected void cloneObject(CronAgent clone) {
        super.cloneObject(clone);
        clone.setType(this.getType());
        if ((this.cronList != null)) {
            clone.cronList = this.cronList.cloneCollection();
        }
    }

    /**
     * Getter for the CronListJPA.
     *
     * @return the List<CronTrigger>.
     */
    List<CronTrigger> getCronListJPA() {
        if ((this.cronList == null)) {
            this.cronList = new NabuccoListImpl<CronTrigger>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<CronTrigger>) this.cronList).getDelegate();
    }

    /**
     * Setter for the CronListJPA.
     *
     * @param cronList the List<CronTrigger>.
     */
    void setCronListJPA(List<CronTrigger> cronList) {
        if ((this.cronList == null)) {
            this.cronList = new NabuccoListImpl<CronTrigger>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<CronTrigger>) this.cronList).setDelegate(cronList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(Agent.class).getPropertyMap());
        propertyMap.put(CRONLIST, PropertyDescriptorSupport.createCollection(CRONLIST, CronTrigger.class, 6,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(CronAgent.getPropertyDescriptor(CRONLIST), this.cronList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(CRONLIST) && (property.getType() == CronTrigger.class))) {
            this.cronList = ((NabuccoList<CronTrigger>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public CronAgent cloneObject() {
        CronAgent clone = new CronAgent();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The list of scheduled cron jobs.
     *
     * @return the NabuccoList<CronTrigger>.
     */
    public NabuccoList<CronTrigger> getCronList() {
        if ((this.cronList == null)) {
            this.cronList = new NabuccoListImpl<CronTrigger>(NabuccoCollectionState.INITIALIZED);
        }
        return this.cronList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(CronAgent.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(CronAgent.class).getAllProperties();
    }
}
