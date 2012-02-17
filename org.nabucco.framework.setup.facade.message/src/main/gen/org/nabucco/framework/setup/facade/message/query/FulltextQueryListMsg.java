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
package org.nabucco.framework.setup.facade.message.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.framework.setup.facade.datatype.query.SetupFulltextQuery;

/**
 * FulltextQueryListMsg<p/>A message containing a list of fulltext querys.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-04-13
 */
public class FulltextQueryListMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    public static final String FULLTEXTQUERYLIST = "fulltextQueryList";

    private NabuccoList<SetupFulltextQuery> fulltextQueryList;

    /** Constructs a new FulltextQueryListMsg instance. */
    public FulltextQueryListMsg() {
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
        propertyMap.put(FULLTEXTQUERYLIST, PropertyDescriptorSupport.createCollection(FULLTEXTQUERYLIST,
                SetupFulltextQuery.class, 0, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(FulltextQueryListMsg.getPropertyDescriptor(FULLTEXTQUERYLIST),
                this.fulltextQueryList));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(FULLTEXTQUERYLIST) && (property.getType() == SetupFulltextQuery.class))) {
            this.fulltextQueryList = ((NabuccoList<SetupFulltextQuery>) property.getInstance());
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
        final FulltextQueryListMsg other = ((FulltextQueryListMsg) obj);
        if ((this.fulltextQueryList == null)) {
            if ((other.fulltextQueryList != null))
                return false;
        } else if ((!this.fulltextQueryList.equals(other.fulltextQueryList)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.fulltextQueryList == null) ? 0 : this.fulltextQueryList.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Missing description at method getFulltextQueryList.
     *
     * @return the NabuccoList<SetupFulltextQuery>.
     */
    public NabuccoList<SetupFulltextQuery> getFulltextQueryList() {
        if ((this.fulltextQueryList == null)) {
            this.fulltextQueryList = new NabuccoListImpl<SetupFulltextQuery>(NabuccoCollectionState.INITIALIZED);
        }
        return this.fulltextQueryList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(FulltextQueryListMsg.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(FulltextQueryListMsg.class).getAllProperties();
    }
}
