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

/**
 * SetupComponentJndiNames<p/>Setup Component<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-28
 */
public interface SetupComponentJndiNames {

    final String COMPONENT_RELATION_SERVICE_LOCAL = "nabucco/org.nabucco.framework.setup/org.nabucco.framework.setup.facade.component.ComponentRelationService/local";

    final String COMPONENT_RELATION_SERVICE_REMOTE = "nabucco/org.nabucco.framework.setup/org.nabucco.framework.setup.facade.component.ComponentRelationService/remote";

    final String QUERY_FILTER_SERVICE_LOCAL = "nabucco/org.nabucco.framework.setup/org.nabucco.framework.setup.facade.component.QueryFilterService/local";

    final String QUERY_FILTER_SERVICE_REMOTE = "nabucco/org.nabucco.framework.setup/org.nabucco.framework.setup.facade.component.QueryFilterService/remote";

    final String AGENT_SERVICE_LOCAL = "nabucco/org.nabucco.framework.setup/org.nabucco.framework.setup.facade.service.agent.AgentService/local";

    final String AGENT_SERVICE_REMOTE = "nabucco/org.nabucco.framework.setup/org.nabucco.framework.setup.facade.service.agent.AgentService/remote";

    final String FULLTEXT_QUERY_LOCAL = "nabucco/org.nabucco.framework.setup/org.nabucco.framework.setup.facade.service.query.FulltextQuery/local";

    final String FULLTEXT_QUERY_REMOTE = "nabucco/org.nabucco.framework.setup/org.nabucco.framework.setup.facade.service.query.FulltextQuery/remote";

    final String MAINTAIN_JOURNAL_LOCAL = "nabucco/org.nabucco.framework.setup/org.nabucco.framework.setup.facade.service.journal.maintain.MaintainJournal/local";

    final String MAINTAIN_JOURNAL_REMOTE = "nabucco/org.nabucco.framework.setup/org.nabucco.framework.setup.facade.service.journal.maintain.MaintainJournal/remote";

    final String SEARCH_JOURNAL_LOCAL = "nabucco/org.nabucco.framework.setup/org.nabucco.framework.setup.facade.service.journal.search.SearchJournal/local";

    final String SEARCH_JOURNAL_REMOTE = "nabucco/org.nabucco.framework.setup/org.nabucco.framework.setup.facade.service.journal.search.SearchJournal/remote";

    final String EVICT_JOURNAL_LOCAL = "nabucco/org.nabucco.framework.setup/org.nabucco.framework.setup.facade.service.journal.evict.EvictJournal/local";

    final String EVICT_JOURNAL_REMOTE = "nabucco/org.nabucco.framework.setup/org.nabucco.framework.setup.facade.service.journal.evict.EvictJournal/remote";

    final String SEQUENCE_SERVICE_LOCAL = "nabucco/org.nabucco.framework.setup/org.nabucco.framework.setup.facade.service.sequence.SequenceService/local";

    final String SEQUENCE_SERVICE_REMOTE = "nabucco/org.nabucco.framework.setup/org.nabucco.framework.setup.facade.service.sequence.SequenceService/remote";

    final String USER_VARIABLES_LOCAL = "nabucco/org.nabucco.framework.setup/org.nabucco.framework.setup.facade.service.usrvar.UserVariables/local";

    final String USER_VARIABLES_REMOTE = "nabucco/org.nabucco.framework.setup/org.nabucco.framework.setup.facade.service.usrvar.UserVariables/remote";

    final String SYSTEM_VARIABLES_LOCAL = "nabucco/org.nabucco.framework.setup/org.nabucco.framework.setup.facade.service.sysvar.SystemVariables/local";

    final String SYSTEM_VARIABLES_REMOTE = "nabucco/org.nabucco.framework.setup/org.nabucco.framework.setup.facade.service.sysvar.SystemVariables/remote";

    final String GEO_SERVICE_LOCAL = "nabucco/org.nabucco.framework.setup/org.nabucco.framework.setup.facade.service.geo.GeoService/local";

    final String GEO_SERVICE_REMOTE = "nabucco/org.nabucco.framework.setup/org.nabucco.framework.setup.facade.service.geo.GeoService/remote";
}
