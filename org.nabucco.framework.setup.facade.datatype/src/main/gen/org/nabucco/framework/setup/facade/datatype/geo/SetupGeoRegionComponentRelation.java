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
package org.nabucco.framework.setup.facade.datatype.geo;

import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationType;

/**
 * SetupGeoRegionComponentRelation<p/>GeoLocation for setup component.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-05-06
 */
public class SetupGeoRegionComponentRelation extends ComponentRelation<SetupGeoRegion> {

    private static final long serialVersionUID = 1L;

    /** Constructs a new SetupGeoRegionComponentRelation instance. */
    protected SetupGeoRegionComponentRelation() {
        super();
    }

    /**
     * Constructs a new SetupGeoRegionComponentRelation instance.
     *
     * @param relationType the ComponentRelationType.
     */
    public SetupGeoRegionComponentRelation(ComponentRelationType relationType) {
        super(relationType);
    }

    /**
     * Getter for the Tarthe .
     *
     * @return the SetupGeoRegion.
     */
    public SetupGeoRegion getTarget() {
        return super.getTarget();
    }

    /**
     * Setter for the Target.
     *
     * @param target the SetupGeoRegion.
     */
    public void setTarget(SetupGeoRegion target) {
        super.setTarget(target);
    }

    @Override
    public SetupGeoRegionComponentRelation cloneObject() {
        SetupGeoRegionComponentRelation clone = new SetupGeoRegionComponentRelation();
        super.cloneObject(clone);
        return clone;
    }
}
