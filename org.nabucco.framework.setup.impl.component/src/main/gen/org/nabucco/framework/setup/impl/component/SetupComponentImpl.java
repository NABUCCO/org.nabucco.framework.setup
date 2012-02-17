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
package org.nabucco.framework.setup.impl.component;

import org.nabucco.framework.base.facade.component.handler.PostConstructHandler;
import org.nabucco.framework.base.facade.component.handler.PreDestroyHandler;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.facade.service.queryfilter.QueryFilterService;
import org.nabucco.framework.base.impl.component.ComponentSupport;
import org.nabucco.framework.setup.facade.component.SetupComponentLocal;
import org.nabucco.framework.setup.facade.component.SetupComponentRemote;
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
 * SetupComponentImpl<p/>Setup Component<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-28
 */
public class SetupComponentImpl extends ComponentSupport implements SetupComponentLocal, SetupComponentRemote {

    private static final long serialVersionUID = 1L;

    private static final String ID = "SetupComponent";

    /** Constructs a new SetupComponentImpl instance. */
    public SetupComponentImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PostConstructHandler handler = injector.inject(PostConstructHandler.getId());
        if ((handler == null)) {
            if (super.getLogger().isDebugEnabled()) {
                super.getLogger().debug("No post construct handler configured for \'", ID, "\'.");
            }
            return;
        }
        handler.setLocatable(this);
        handler.setLogger(super.getLogger());
        handler.invoke();
    }

    @Override
    public void preDestroy() {
        super.preDestroy();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PreDestroyHandler handler = injector.inject(PreDestroyHandler.getId());
        if ((handler == null)) {
            if (super.getLogger().isDebugEnabled()) {
                super.getLogger().debug("No pre destroy handler configured for \'", ID, "\'.");
            }
            return;
        }
        handler.setLocatable(this);
        handler.setLogger(super.getLogger());
        handler.invoke();
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getName() {
        return COMPONENT_NAME;
    }

    @Override
    public String getJndiName() {
        return JNDI_NAME;
    }

    @Override
    public ComponentRelationService getComponentRelationService() throws ServiceException {
        return super.lookup(SetupComponentJndiNames.COMPONENT_RELATION_SERVICE_REMOTE, ComponentRelationService.class);
    }

    @Override
    public ComponentRelationService getComponentRelationServiceLocal() throws ServiceException {
        return super.lookup(SetupComponentJndiNames.COMPONENT_RELATION_SERVICE_LOCAL, ComponentRelationService.class);
    }

    @Override
    public QueryFilterService getQueryFilterService() throws ServiceException {
        return super.lookup(SetupComponentJndiNames.QUERY_FILTER_SERVICE_REMOTE, QueryFilterService.class);
    }

    @Override
    public QueryFilterService getQueryFilterServiceLocal() throws ServiceException {
        return super.lookup(SetupComponentJndiNames.QUERY_FILTER_SERVICE_LOCAL, QueryFilterService.class);
    }

    @Override
    public AgentService getAgentServiceLocal() throws ServiceException {
        return super.lookup(SetupComponentJndiNames.AGENT_SERVICE_LOCAL, AgentService.class);
    }

    @Override
    public AgentService getAgentService() throws ServiceException {
        return super.lookup(SetupComponentJndiNames.AGENT_SERVICE_REMOTE, AgentService.class);
    }

    @Override
    public FulltextQuery getFulltextQueryLocal() throws ServiceException {
        return super.lookup(SetupComponentJndiNames.FULLTEXT_QUERY_LOCAL, FulltextQuery.class);
    }

    @Override
    public FulltextQuery getFulltextQuery() throws ServiceException {
        return super.lookup(SetupComponentJndiNames.FULLTEXT_QUERY_REMOTE, FulltextQuery.class);
    }

    @Override
    public MaintainJournal getMaintainJournalLocal() throws ServiceException {
        return super.lookup(SetupComponentJndiNames.MAINTAIN_JOURNAL_LOCAL, MaintainJournal.class);
    }

    @Override
    public MaintainJournal getMaintainJournal() throws ServiceException {
        return super.lookup(SetupComponentJndiNames.MAINTAIN_JOURNAL_REMOTE, MaintainJournal.class);
    }

    @Override
    public SearchJournal getSearchJournalLocal() throws ServiceException {
        return super.lookup(SetupComponentJndiNames.SEARCH_JOURNAL_LOCAL, SearchJournal.class);
    }

    @Override
    public SearchJournal getSearchJournal() throws ServiceException {
        return super.lookup(SetupComponentJndiNames.SEARCH_JOURNAL_REMOTE, SearchJournal.class);
    }

    @Override
    public EvictJournal getEvictJournalLocal() throws ServiceException {
        return super.lookup(SetupComponentJndiNames.EVICT_JOURNAL_LOCAL, EvictJournal.class);
    }

    @Override
    public EvictJournal getEvictJournal() throws ServiceException {
        return super.lookup(SetupComponentJndiNames.EVICT_JOURNAL_REMOTE, EvictJournal.class);
    }

    @Override
    public SequenceService getSequenceServiceLocal() throws ServiceException {
        return super.lookup(SetupComponentJndiNames.SEQUENCE_SERVICE_LOCAL, SequenceService.class);
    }

    @Override
    public SequenceService getSequenceService() throws ServiceException {
        return super.lookup(SetupComponentJndiNames.SEQUENCE_SERVICE_REMOTE, SequenceService.class);
    }

    @Override
    public UserVariables getUserVariablesLocal() throws ServiceException {
        return super.lookup(SetupComponentJndiNames.USER_VARIABLES_LOCAL, UserVariables.class);
    }

    @Override
    public UserVariables getUserVariables() throws ServiceException {
        return super.lookup(SetupComponentJndiNames.USER_VARIABLES_REMOTE, UserVariables.class);
    }

    @Override
    public SystemVariables getSystemVariablesLocal() throws ServiceException {
        return super.lookup(SetupComponentJndiNames.SYSTEM_VARIABLES_LOCAL, SystemVariables.class);
    }

    @Override
    public SystemVariables getSystemVariables() throws ServiceException {
        return super.lookup(SetupComponentJndiNames.SYSTEM_VARIABLES_REMOTE, SystemVariables.class);
    }

    @Override
    public GeoService getGeoServiceLocal() throws ServiceException {
        return super.lookup(SetupComponentJndiNames.GEO_SERVICE_LOCAL, GeoService.class);
    }

    @Override
    public GeoService getGeoService() throws ServiceException {
        return super.lookup(SetupComponentJndiNames.GEO_SERVICE_REMOTE, GeoService.class);
    }
}
