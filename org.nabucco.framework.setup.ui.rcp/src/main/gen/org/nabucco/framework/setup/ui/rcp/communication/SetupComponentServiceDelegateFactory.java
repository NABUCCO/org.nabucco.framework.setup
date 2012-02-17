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
package org.nabucco.framework.setup.ui.rcp.communication;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateFactorySupport;
import org.nabucco.framework.setup.facade.component.SetupComponent;
import org.nabucco.framework.setup.facade.component.SetupComponentLocator;
import org.nabucco.framework.setup.ui.rcp.communication.agent.AgentServiceDelegate;
import org.nabucco.framework.setup.ui.rcp.communication.geo.GeoServiceDelegate;
import org.nabucco.framework.setup.ui.rcp.communication.journal.evict.EvictJournalDelegate;
import org.nabucco.framework.setup.ui.rcp.communication.journal.maintain.MaintainJournalDelegate;
import org.nabucco.framework.setup.ui.rcp.communication.journal.search.SearchJournalDelegate;
import org.nabucco.framework.setup.ui.rcp.communication.query.FulltextQueryDelegate;
import org.nabucco.framework.setup.ui.rcp.communication.sequence.SequenceServiceDelegate;
import org.nabucco.framework.setup.ui.rcp.communication.sysvar.SystemVariablesDelegate;
import org.nabucco.framework.setup.ui.rcp.communication.usrvar.UserVariablesDelegate;

/**
 * ServiceDelegateFactoryTemplate<p/>Setup Component<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-28
 */
public class SetupComponentServiceDelegateFactory extends ServiceDelegateFactorySupport<SetupComponent> {

    private static SetupComponentServiceDelegateFactory instance = new SetupComponentServiceDelegateFactory();

    private AgentServiceDelegate agentServiceDelegate;

    private FulltextQueryDelegate fulltextQueryDelegate;

    private MaintainJournalDelegate maintainJournalDelegate;

    private SearchJournalDelegate searchJournalDelegate;

    private EvictJournalDelegate evictJournalDelegate;

    private SequenceServiceDelegate sequenceServiceDelegate;

    private UserVariablesDelegate userVariablesDelegate;

    private SystemVariablesDelegate systemVariablesDelegate;

    private GeoServiceDelegate geoServiceDelegate;

    /** Constructs a new SetupComponentServiceDelegateFactory instance. */
    private SetupComponentServiceDelegateFactory() {
        super(SetupComponentLocator.getInstance());
    }

    /**
     * Getter for the AgentService.
     *
     * @return the AgentServiceDelegate.
     * @throws ClientException
     */
    public AgentServiceDelegate getAgentService() throws ClientException {
        try {
            if ((this.agentServiceDelegate == null)) {
                this.agentServiceDelegate = new AgentServiceDelegate(this.getComponent().getAgentService());
            }
            return this.agentServiceDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot connect to component: SetupComponent", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: AgentService", e);
        }
    }

    /**
     * Getter for the FulltextQuery.
     *
     * @return the FulltextQueryDelegate.
     * @throws ClientException
     */
    public FulltextQueryDelegate getFulltextQuery() throws ClientException {
        try {
            if ((this.fulltextQueryDelegate == null)) {
                this.fulltextQueryDelegate = new FulltextQueryDelegate(this.getComponent().getFulltextQuery());
            }
            return this.fulltextQueryDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot connect to component: SetupComponent", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: FulltextQuery", e);
        }
    }

    /**
     * Getter for the MaintainJournal.
     *
     * @return the MaintainJournalDelegate.
     * @throws ClientException
     */
    public MaintainJournalDelegate getMaintainJournal() throws ClientException {
        try {
            if ((this.maintainJournalDelegate == null)) {
                this.maintainJournalDelegate = new MaintainJournalDelegate(this.getComponent().getMaintainJournal());
            }
            return this.maintainJournalDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot connect to component: SetupComponent", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: MaintainJournal", e);
        }
    }

    /**
     * Getter for the SearchJournal.
     *
     * @return the SearchJournalDelegate.
     * @throws ClientException
     */
    public SearchJournalDelegate getSearchJournal() throws ClientException {
        try {
            if ((this.searchJournalDelegate == null)) {
                this.searchJournalDelegate = new SearchJournalDelegate(this.getComponent().getSearchJournal());
            }
            return this.searchJournalDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot connect to component: SetupComponent", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: SearchJournal", e);
        }
    }

    /**
     * Getter for the EvictJournal.
     *
     * @return the EvictJournalDelegate.
     * @throws ClientException
     */
    public EvictJournalDelegate getEvictJournal() throws ClientException {
        try {
            if ((this.evictJournalDelegate == null)) {
                this.evictJournalDelegate = new EvictJournalDelegate(this.getComponent().getEvictJournal());
            }
            return this.evictJournalDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot connect to component: SetupComponent", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: EvictJournal", e);
        }
    }

    /**
     * Getter for the SequenceService.
     *
     * @return the SequenceServiceDelegate.
     * @throws ClientException
     */
    public SequenceServiceDelegate getSequenceService() throws ClientException {
        try {
            if ((this.sequenceServiceDelegate == null)) {
                this.sequenceServiceDelegate = new SequenceServiceDelegate(this.getComponent().getSequenceService());
            }
            return this.sequenceServiceDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot connect to component: SetupComponent", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: SequenceService", e);
        }
    }

    /**
     * Getter for the UserVariables.
     *
     * @return the UserVariablesDelegate.
     * @throws ClientException
     */
    public UserVariablesDelegate getUserVariables() throws ClientException {
        try {
            if ((this.userVariablesDelegate == null)) {
                this.userVariablesDelegate = new UserVariablesDelegate(this.getComponent().getUserVariables());
            }
            return this.userVariablesDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot connect to component: SetupComponent", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: UserVariables", e);
        }
    }

    /**
     * Getter for the SystemVariables.
     *
     * @return the SystemVariablesDelegate.
     * @throws ClientException
     */
    public SystemVariablesDelegate getSystemVariables() throws ClientException {
        try {
            if ((this.systemVariablesDelegate == null)) {
                this.systemVariablesDelegate = new SystemVariablesDelegate(this.getComponent().getSystemVariables());
            }
            return this.systemVariablesDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot connect to component: SetupComponent", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: SystemVariables", e);
        }
    }

    /**
     * Getter for the GeoService.
     *
     * @return the GeoServiceDelegate.
     * @throws ClientException
     */
    public GeoServiceDelegate getGeoService() throws ClientException {
        try {
            if ((this.geoServiceDelegate == null)) {
                this.geoServiceDelegate = new GeoServiceDelegate(this.getComponent().getGeoService());
            }
            return this.geoServiceDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot connect to component: SetupComponent", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: GeoService", e);
        }
    }

    /**
     * Getter for the Instance.
     *
     * @return the SetupComponentServiceDelegateFactory.
     */
    public static SetupComponentServiceDelegateFactory getInstance() {
        return instance;
    }
}
