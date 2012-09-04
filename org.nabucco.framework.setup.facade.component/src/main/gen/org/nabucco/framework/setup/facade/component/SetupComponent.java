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

import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
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
 * SetupComponent<p/>Setup Component<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-28
 */
public interface SetupComponent extends Component {

    final String COMPONENT_NAME = "org.nabucco.framework.setup";

    final String COMPONENT_PREFIX = "setu";

    final String JNDI_NAME = ((((JNDI_PREFIX + "/") + COMPONENT_NAME) + "/") + "org.nabucco.framework.setup.facade.component.SetupComponent");

    /**
     * Getter for the AgentService.
     *
     * @return the AgentService.
     * @throws ServiceException
     */
    AgentService getAgentService() throws ServiceException;

    /**
     * Getter for the FulltextQuery.
     *
     * @return the FulltextQuery.
     * @throws ServiceException
     */
    FulltextQuery getFulltextQuery() throws ServiceException;

    /**
     * Getter for the MaintainJournal.
     *
     * @return the MaintainJournal.
     * @throws ServiceException
     */
    MaintainJournal getMaintainJournal() throws ServiceException;

    /**
     * Getter for the SearchJournal.
     *
     * @return the SearchJournal.
     * @throws ServiceException
     */
    SearchJournal getSearchJournal() throws ServiceException;

    /**
     * Getter for the EvictJournal.
     *
     * @return the EvictJournal.
     * @throws ServiceException
     */
    EvictJournal getEvictJournal() throws ServiceException;

    /**
     * Getter for the SequenceService.
     *
     * @return the SequenceService.
     * @throws ServiceException
     */
    SequenceService getSequenceService() throws ServiceException;

    /**
     * Getter for the UserVariables.
     *
     * @return the UserVariables.
     * @throws ServiceException
     */
    UserVariables getUserVariables() throws ServiceException;

    /**
     * Getter for the SystemVariables.
     *
     * @return the SystemVariables.
     * @throws ServiceException
     */
    SystemVariables getSystemVariables() throws ServiceException;

    /**
     * Getter for the GeoService.
     *
     * @return the GeoService.
     * @throws ServiceException
     */
    GeoService getGeoService() throws ServiceException;
}
