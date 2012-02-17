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
package org.nabucco.framework.setup.facade.datatype.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.date.Timestamp;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.search.index.IndexName;
import org.nabucco.framework.base.facade.datatype.search.query.MaxResult;
import org.nabucco.framework.base.facade.datatype.security.UserId;
import org.nabucco.framework.setup.facade.datatype.query.SetupFulltextQueryRangeField;
import org.nabucco.framework.setup.facade.datatype.query.SetupFulltextQueryValueField;

/**
 * SetupFulltextQuery<p/>Entity representation of a fulltext query.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-04-13
 */
public class SetupFulltextQuery extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,12;u0,n;m1,1;", "l0,255;u0,n;m1,1;",
            "l0,255;u0,n;m1,1;", "l3,32;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "l3,255;u0,n;m1,1;", "l0,n;u0,n;m0,1;",
            "m0,n;", "m0,n;" };

    public static final String OWNER = "owner";

    public static final String NAME = "name";

    public static final String SEARCHCONTEXT = "searchContext";

    public static final String USER = "user";

    public static final String TIMESTAMP = "timestamp";

    public static final String INDEXNAME = "indexName";

    public static final String MAXRESULT = "maxResult";

    public static final String VALUEFIELDLIST = "valueFieldList";

    public static final String RANGEFIELDLIST = "rangeFieldList";

    /** The owner of the query. */
    private Owner owner;

    /** The name of the query. */
    private Name name;

    /** The search context of the query. */
    private Name searchContext;

    /** The user of the query. */
    private UserId user;

    /** The timestamp of the query. */
    private Timestamp timestamp;

    /** The name of the index used for this query. */
    private IndexName indexName;

    /** The maximum amount of results. No value is interpreted as no limit. */
    private MaxResult maxResult;

    /** The list of value fields. */
    private NabuccoList<SetupFulltextQueryValueField> valueFieldList;

    /** The list of range fields. */
    private NabuccoList<SetupFulltextQueryRangeField> rangeFieldList;

    /** Constructs a new SetupFulltextQuery instance. */
    public SetupFulltextQuery() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the SetupFulltextQuery.
     */
    protected void cloneObject(SetupFulltextQuery clone) {
        super.cloneObject(clone);
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getSearchContext() != null)) {
            clone.setSearchContext(this.getSearchContext().cloneObject());
        }
        if ((this.getUser() != null)) {
            clone.setUser(this.getUser().cloneObject());
        }
        if ((this.getTimestamp() != null)) {
            clone.setTimestamp(this.getTimestamp().cloneObject());
        }
        if ((this.getIndexName() != null)) {
            clone.setIndexName(this.getIndexName().cloneObject());
        }
        if ((this.getMaxResult() != null)) {
            clone.setMaxResult(this.getMaxResult().cloneObject());
        }
        if ((this.valueFieldList != null)) {
            clone.valueFieldList = this.valueFieldList.cloneCollection();
        }
        if ((this.rangeFieldList != null)) {
            clone.rangeFieldList = this.rangeFieldList.cloneCollection();
        }
    }

    /**
     * Getter for the ValueFieldListJPA.
     *
     * @return the List<SetupFulltextQueryValueField>.
     */
    List<SetupFulltextQueryValueField> getValueFieldListJPA() {
        if ((this.valueFieldList == null)) {
            this.valueFieldList = new NabuccoListImpl<SetupFulltextQueryValueField>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<SetupFulltextQueryValueField>) this.valueFieldList).getDelegate();
    }

    /**
     * Setter for the ValueFieldListJPA.
     *
     * @param valueFieldList the List<SetupFulltextQueryValueField>.
     */
    void setValueFieldListJPA(List<SetupFulltextQueryValueField> valueFieldList) {
        if ((this.valueFieldList == null)) {
            this.valueFieldList = new NabuccoListImpl<SetupFulltextQueryValueField>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<SetupFulltextQueryValueField>) this.valueFieldList).setDelegate(valueFieldList);
    }

    /**
     * Getter for the RangeFieldListJPA.
     *
     * @return the List<SetupFulltextQueryRangeField>.
     */
    List<SetupFulltextQueryRangeField> getRangeFieldListJPA() {
        if ((this.rangeFieldList == null)) {
            this.rangeFieldList = new NabuccoListImpl<SetupFulltextQueryRangeField>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<SetupFulltextQueryRangeField>) this.rangeFieldList).getDelegate();
    }

    /**
     * Setter for the RangeFieldListJPA.
     *
     * @param rangeFieldList the List<SetupFulltextQueryRangeField>.
     */
    void setRangeFieldListJPA(List<SetupFulltextQueryRangeField> rangeFieldList) {
        if ((this.rangeFieldList == null)) {
            this.rangeFieldList = new NabuccoListImpl<SetupFulltextQueryRangeField>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<SetupFulltextQueryRangeField>) this.rangeFieldList).setDelegate(rangeFieldList);
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
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, Name.class, 4, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(SEARCHCONTEXT,
                PropertyDescriptorSupport.createBasetype(SEARCHCONTEXT, Name.class, 5, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(USER,
                PropertyDescriptorSupport.createBasetype(USER, UserId.class, 6, PROPERTY_CONSTRAINTS[3], false));
        propertyMap
                .put(TIMESTAMP, PropertyDescriptorSupport.createBasetype(TIMESTAMP, Timestamp.class, 7,
                        PROPERTY_CONSTRAINTS[4], false));
        propertyMap
                .put(INDEXNAME, PropertyDescriptorSupport.createBasetype(INDEXNAME, IndexName.class, 8,
                        PROPERTY_CONSTRAINTS[5], false));
        propertyMap
                .put(MAXRESULT, PropertyDescriptorSupport.createBasetype(MAXRESULT, MaxResult.class, 9,
                        PROPERTY_CONSTRAINTS[6], false));
        propertyMap.put(VALUEFIELDLIST, PropertyDescriptorSupport.createCollection(VALUEFIELDLIST,
                SetupFulltextQueryValueField.class, 10, PROPERTY_CONSTRAINTS[7], false,
                PropertyAssociationType.COMPOSITION));
        propertyMap.put(RANGEFIELDLIST, PropertyDescriptorSupport.createCollection(RANGEFIELDLIST,
                SetupFulltextQueryRangeField.class, 11, PROPERTY_CONSTRAINTS[8], false,
                PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(SetupFulltextQuery.getPropertyDescriptor(OWNER), this.owner, null));
        properties.add(super.createProperty(SetupFulltextQuery.getPropertyDescriptor(NAME), this.name, null));
        properties.add(super.createProperty(SetupFulltextQuery.getPropertyDescriptor(SEARCHCONTEXT),
                this.searchContext, null));
        properties.add(super.createProperty(SetupFulltextQuery.getPropertyDescriptor(USER), this.user, null));
        properties.add(super.createProperty(SetupFulltextQuery.getPropertyDescriptor(TIMESTAMP), this.timestamp, null));
        properties.add(super.createProperty(SetupFulltextQuery.getPropertyDescriptor(INDEXNAME), this.indexName, null));
        properties.add(super.createProperty(SetupFulltextQuery.getPropertyDescriptor(MAXRESULT), this.maxResult, null));
        properties.add(super.createProperty(SetupFulltextQuery.getPropertyDescriptor(VALUEFIELDLIST),
                this.valueFieldList, null));
        properties.add(super.createProperty(SetupFulltextQuery.getPropertyDescriptor(RANGEFIELDLIST),
                this.rangeFieldList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(OWNER) && (property.getType() == Owner.class))) {
            this.setOwner(((Owner) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAME) && (property.getType() == Name.class))) {
            this.setName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SEARCHCONTEXT) && (property.getType() == Name.class))) {
            this.setSearchContext(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(USER) && (property.getType() == UserId.class))) {
            this.setUser(((UserId) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TIMESTAMP) && (property.getType() == Timestamp.class))) {
            this.setTimestamp(((Timestamp) property.getInstance()));
            return true;
        } else if ((property.getName().equals(INDEXNAME) && (property.getType() == IndexName.class))) {
            this.setIndexName(((IndexName) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MAXRESULT) && (property.getType() == MaxResult.class))) {
            this.setMaxResult(((MaxResult) property.getInstance()));
            return true;
        } else if ((property.getName().equals(VALUEFIELDLIST) && (property.getType() == SetupFulltextQueryValueField.class))) {
            this.valueFieldList = ((NabuccoList<SetupFulltextQueryValueField>) property.getInstance());
            return true;
        } else if ((property.getName().equals(RANGEFIELDLIST) && (property.getType() == SetupFulltextQueryRangeField.class))) {
            this.rangeFieldList = ((NabuccoList<SetupFulltextQueryRangeField>) property.getInstance());
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
        final SetupFulltextQuery other = ((SetupFulltextQuery) obj);
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.searchContext == null)) {
            if ((other.searchContext != null))
                return false;
        } else if ((!this.searchContext.equals(other.searchContext)))
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
        if ((this.indexName == null)) {
            if ((other.indexName != null))
                return false;
        } else if ((!this.indexName.equals(other.indexName)))
            return false;
        if ((this.maxResult == null)) {
            if ((other.maxResult != null))
                return false;
        } else if ((!this.maxResult.equals(other.maxResult)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.searchContext == null) ? 0 : this.searchContext.hashCode()));
        result = ((PRIME * result) + ((this.user == null) ? 0 : this.user.hashCode()));
        result = ((PRIME * result) + ((this.timestamp == null) ? 0 : this.timestamp.hashCode()));
        result = ((PRIME * result) + ((this.indexName == null) ? 0 : this.indexName.hashCode()));
        result = ((PRIME * result) + ((this.maxResult == null) ? 0 : this.maxResult.hashCode()));
        return result;
    }

    @Override
    public SetupFulltextQuery cloneObject() {
        SetupFulltextQuery clone = new SetupFulltextQuery();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The owner of the query.
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * The owner of the query.
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * The owner of the query.
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
     * The name of the query.
     *
     * @return the Name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * The name of the query.
     *
     * @param name the Name.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * The name of the query.
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
     * The search context of the query.
     *
     * @return the Name.
     */
    public Name getSearchContext() {
        return this.searchContext;
    }

    /**
     * The search context of the query.
     *
     * @param searchContext the Name.
     */
    public void setSearchContext(Name searchContext) {
        this.searchContext = searchContext;
    }

    /**
     * The search context of the query.
     *
     * @param searchContext the String.
     */
    public void setSearchContext(String searchContext) {
        if ((this.searchContext == null)) {
            if ((searchContext == null)) {
                return;
            }
            this.searchContext = new Name();
        }
        this.searchContext.setValue(searchContext);
    }

    /**
     * The user of the query.
     *
     * @return the UserId.
     */
    public UserId getUser() {
        return this.user;
    }

    /**
     * The user of the query.
     *
     * @param user the UserId.
     */
    public void setUser(UserId user) {
        this.user = user;
    }

    /**
     * The user of the query.
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
     * The timestamp of the query.
     *
     * @return the Timestamp.
     */
    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    /**
     * The timestamp of the query.
     *
     * @param timestamp the Timestamp.
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * The timestamp of the query.
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
     * The name of the index used for this query.
     *
     * @return the IndexName.
     */
    public IndexName getIndexName() {
        return this.indexName;
    }

    /**
     * The name of the index used for this query.
     *
     * @param indexName the IndexName.
     */
    public void setIndexName(IndexName indexName) {
        this.indexName = indexName;
    }

    /**
     * The name of the index used for this query.
     *
     * @param indexName the String.
     */
    public void setIndexName(String indexName) {
        if ((this.indexName == null)) {
            if ((indexName == null)) {
                return;
            }
            this.indexName = new IndexName();
        }
        this.indexName.setValue(indexName);
    }

    /**
     * The maximum amount of results. No value is interpreted as no limit.
     *
     * @return the MaxResult.
     */
    public MaxResult getMaxResult() {
        return this.maxResult;
    }

    /**
     * The maximum amount of results. No value is interpreted as no limit.
     *
     * @param maxResult the MaxResult.
     */
    public void setMaxResult(MaxResult maxResult) {
        this.maxResult = maxResult;
    }

    /**
     * The maximum amount of results. No value is interpreted as no limit.
     *
     * @param maxResult the Integer.
     */
    public void setMaxResult(Integer maxResult) {
        if ((this.maxResult == null)) {
            if ((maxResult == null)) {
                return;
            }
            this.maxResult = new MaxResult();
        }
        this.maxResult.setValue(maxResult);
    }

    /**
     * The list of value fields.
     *
     * @return the NabuccoList<SetupFulltextQueryValueField>.
     */
    public NabuccoList<SetupFulltextQueryValueField> getValueFieldList() {
        if ((this.valueFieldList == null)) {
            this.valueFieldList = new NabuccoListImpl<SetupFulltextQueryValueField>(NabuccoCollectionState.INITIALIZED);
        }
        return this.valueFieldList;
    }

    /**
     * The list of range fields.
     *
     * @return the NabuccoList<SetupFulltextQueryRangeField>.
     */
    public NabuccoList<SetupFulltextQueryRangeField> getRangeFieldList() {
        if ((this.rangeFieldList == null)) {
            this.rangeFieldList = new NabuccoListImpl<SetupFulltextQueryRangeField>(NabuccoCollectionState.INITIALIZED);
        }
        return this.rangeFieldList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(SetupFulltextQuery.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SetupFulltextQuery.class).getAllProperties();
    }
}
