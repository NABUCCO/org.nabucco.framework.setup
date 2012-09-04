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
package org.nabucco.framework.setup.impl.service.geo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.setup.facade.message.geo.GeoImportMsg;
import org.nabucco.framework.setup.facade.message.geo.GeoLevelRq;
import org.nabucco.framework.setup.facade.message.geo.GeoLevelRs;
import org.nabucco.framework.setup.facade.message.geo.GeoRequestMsg;
import org.nabucco.framework.setup.facade.message.geo.GeoResponseMsg;
import org.nabucco.framework.setup.facade.service.geo.GeoService;

/**
 * GeoServiceImpl<p/>Service for geo information.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-05-06
 */
public class GeoServiceImpl extends ServiceSupport implements GeoService {

    private static final long serialVersionUID = 1L;

    private static final String ID = "GeoService";

    private static Map<String, String[]> ASPECTS;

    private GetGeoRelationsServiceHandler getGeoRelationsServiceHandler;

    private GetGeoRegionByLevelServiceHandler getGeoRegionByLevelServiceHandler;

    private ImportElementServiceHandler importElementServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new GeoServiceImpl instance. */
    public GeoServiceImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.getGeoRelationsServiceHandler = injector.inject(GetGeoRelationsServiceHandler.getId());
        if ((this.getGeoRelationsServiceHandler != null)) {
            this.getGeoRelationsServiceHandler.setPersistenceManager(persistenceManager);
            this.getGeoRelationsServiceHandler.setLogger(super.getLogger());
        }
        this.getGeoRegionByLevelServiceHandler = injector.inject(GetGeoRegionByLevelServiceHandler.getId());
        if ((this.getGeoRegionByLevelServiceHandler != null)) {
            this.getGeoRegionByLevelServiceHandler.setPersistenceManager(persistenceManager);
            this.getGeoRegionByLevelServiceHandler.setLogger(super.getLogger());
        }
        this.importElementServiceHandler = injector.inject(ImportElementServiceHandler.getId());
        if ((this.importElementServiceHandler != null)) {
            this.importElementServiceHandler.setPersistenceManager(persistenceManager);
            this.importElementServiceHandler.setLogger(super.getLogger());
        }
    }

    @Override
    public void preDestroy() {
        super.preDestroy();
    }

    @Override
    public String[] getAspects(String operationName) {
        if ((ASPECTS == null)) {
            ASPECTS = new HashMap<String, String[]>();
            ASPECTS.put("getGeoRelations", NO_ASPECTS);
            ASPECTS.put("getGeoRegionByLevel", NO_ASPECTS);
            ASPECTS.put("importElement", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<GeoResponseMsg> getGeoRelations(ServiceRequest<GeoRequestMsg> rq) throws SearchException {
        if ((this.getGeoRelationsServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for getGeoRelations().");
            throw new InjectionException("No service implementation configured for getGeoRelations().");
        }
        ServiceResponse<GeoResponseMsg> rs;
        this.getGeoRelationsServiceHandler.init();
        rs = this.getGeoRelationsServiceHandler.invoke(rq);
        this.getGeoRelationsServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<GeoLevelRs> getGeoRegionByLevel(ServiceRequest<GeoLevelRq> rq) throws SearchException {
        if ((this.getGeoRegionByLevelServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for getGeoRegionByLevel().");
            throw new InjectionException("No service implementation configured for getGeoRegionByLevel().");
        }
        ServiceResponse<GeoLevelRs> rs;
        this.getGeoRegionByLevelServiceHandler.init();
        rs = this.getGeoRegionByLevelServiceHandler.invoke(rq);
        this.getGeoRegionByLevelServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<GeoImportMsg> importElement(ServiceRequest<GeoImportMsg> rq) throws ServiceException {
        if ((this.importElementServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for importElement().");
            throw new InjectionException("No service implementation configured for importElement().");
        }
        ServiceResponse<GeoImportMsg> rs;
        this.importElementServiceHandler.init();
        rs = this.importElementServiceHandler.invoke(rq);
        this.importElementServiceHandler.finish();
        return rs;
    }
}
