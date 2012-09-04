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
 * SetupComponentLocal<p/>Setup Component<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-28
 */
public interface SetupComponentLocal extends SetupComponent {

    /**
     * Getter for the ComponentRelationServiceLocal.
     *
     * @return the ComponentRelationService.
     * @throws ServiceException
     */
    ComponentRelationService getComponentRelationServiceLocal() throws ServiceException;

    /**
     * Getter for the QueryFilterServiceLocal.
     *
     * @return the QueryFilterService.
     * @throws ServiceException
     */
    QueryFilterService getQueryFilterServiceLocal() throws ServiceException;

    /**
     * Getter for the AgentServiceLocal.
     *
     * @return the AgentService.
     * @throws ServiceException
     */
    AgentService getAgentServiceLocal() throws ServiceException;

    /**
     * Getter for the FulltextQueryLocal.
     *
     * @return the FulltextQuery.
     * @throws ServiceException
     */
    FulltextQuery getFulltextQueryLocal() throws ServiceException;

    /**
     * Getter for the MaintainJournalLocal.
     *
     * @return the MaintainJournal.
     * @throws ServiceException
     */
    MaintainJournal getMaintainJournalLocal() throws ServiceException;

    /**
     * Getter for the SearchJournalLocal.
     *
     * @return the SearchJournal.
     * @throws ServiceException
     */
    SearchJournal getSearchJournalLocal() throws ServiceException;

    /**
     * Getter for the EvictJournalLocal.
     *
     * @return the EvictJournal.
     * @throws ServiceException
     */
    EvictJournal getEvictJournalLocal() throws ServiceException;

    /**
     * Getter for the SequenceServiceLocal.
     *
     * @return the SequenceService.
     * @throws ServiceException
     */
    SequenceService getSequenceServiceLocal() throws ServiceException;

    /**
     * Getter for the UserVariablesLocal.
     *
     * @return the UserVariables.
     * @throws ServiceException
     */
    UserVariables getUserVariablesLocal() throws ServiceException;

    /**
     * Getter for the SystemVariablesLocal.
     *
     * @return the SystemVariables.
     * @throws ServiceException
     */
    SystemVariables getSystemVariablesLocal() throws ServiceException;

    /**
     * Getter for the GeoServiceLocal.
     *
     * @return the GeoService.
     * @throws ServiceException
     */
    GeoService getGeoServiceLocal() throws ServiceException;
}
