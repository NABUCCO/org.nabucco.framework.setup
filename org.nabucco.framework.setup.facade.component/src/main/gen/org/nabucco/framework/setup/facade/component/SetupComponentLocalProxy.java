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
package org.nabucco.framework.setup.facade.component;

import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;
import org.nabucco.framework.base.facade.service.queryfilter.QueryFilterService;
import org.nabucco.framework.setup.facade.component.SetupComponent;
import org.nabucco.framework.setup.facade.service.agent.AgentService;
import org.nabucco.framework.setup.facade.service.geo.GeoService;
import org.nabucco.framework.setup.facade.service.journal.evict.EvictJournal;
import org.nabucco.framework.setup.facade.service.journal.maintain.MaintainJournal;
import org.nabucco.framework.setup.facade.service.journal.search.SearchJournal;
import org.nabucco.framework.setup.facade.service.query.FulltextQuery;
import org.nabucco.framework.setup.facade.service.sequence.SequenceService;
import org.nabucco.framework.setup.facade.service.sysvar.SystemVariables;
import org.nabucco.framework.setup.facade.service.usrvar.UserVariables;

/**
 * SetupComponentLocalProxy<p/>Setup Component<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-28
 */
public class SetupComponentLocalProxy implements SetupComponent {

    private static final long serialVersionUID = 1L;

    private final SetupComponentLocal delegate;

    /**
     * Constructs a new SetupComponentLocalProxy instance.
     *
     * @param delegate the SetupComponentLocal.
     */
    public SetupComponentLocalProxy(SetupComponentLocal delegate) {
        super();
        if ((delegate == null)) {
            throw new IllegalArgumentException("Cannot create local proxy for component [null].");
        }
        this.delegate = delegate;
    }

    @Override
    public String getId() {
        return this.delegate.getId();
    }

    @Override
    public String getName() {
        return this.delegate.getName();
    }

    @Override
    public String getJndiName() {
        return this.delegate.getJndiName();
    }

    @Override
    public ComponentRelationService getComponentRelationService() throws ServiceException {
        return this.delegate.getComponentRelationServiceLocal();
    }

    @Override
    public QueryFilterService getQueryFilterService() throws ServiceException {
        return this.delegate.getQueryFilterServiceLocal();
    }

    @Override
    public String toString() {
        return this.delegate.toString();
    }

    @Override
    public AgentService getAgentService() throws ServiceException {
        return this.delegate.getAgentServiceLocal();
    }

    @Override
    public FulltextQuery getFulltextQuery() throws ServiceException {
        return this.delegate.getFulltextQueryLocal();
    }

    @Override
    public MaintainJournal getMaintainJournal() throws ServiceException {
        return this.delegate.getMaintainJournalLocal();
    }

    @Override
    public SearchJournal getSearchJournal() throws ServiceException {
        return this.delegate.getSearchJournalLocal();
    }

    @Override
    public EvictJournal getEvictJournal() throws ServiceException {
        return this.delegate.getEvictJournalLocal();
    }

    @Override
    public SequenceService getSequenceService() throws ServiceException {
        return this.delegate.getSequenceServiceLocal();
    }

    @Override
    public UserVariables getUserVariables() throws ServiceException {
        return this.delegate.getUserVariablesLocal();
    }

    @Override
    public SystemVariables getSystemVariables() throws ServiceException {
        return this.delegate.getSystemVariablesLocal();
    }

    @Override
    public GeoService getGeoService() throws ServiceException {
        return this.delegate.getGeoServiceLocal();
    }
}
