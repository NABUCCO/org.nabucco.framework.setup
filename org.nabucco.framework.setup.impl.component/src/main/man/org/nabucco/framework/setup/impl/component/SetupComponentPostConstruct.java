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

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.component.handler.PostConstructHandler;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionMap;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionResolver;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.AgentExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.JournalExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.impl.service.timer.TimerLookupException;
import org.nabucco.framework.base.impl.service.timer.TimerService;
import org.nabucco.framework.setup.facade.component.JournalConfiguration;
import org.nabucco.framework.setup.facade.component.SetupConfiguration;
import org.nabucco.framework.setup.facade.datatype.agent.Agent;
import org.nabucco.framework.setup.facade.datatype.agent.AgentRegistry;
import org.nabucco.framework.setup.facade.exception.AgentException;

/**
 * SetupComponentPostConstruct
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class SetupComponentPostConstruct extends PostConstructHandler {

    private static final long serialVersionUID = 1L;

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            SetupComponentPostConstruct.class);

    @Override
    public void invoke() {

        try {
            this.launchAgents();
            this.configureJournal();

        } catch (ExtensionException ee) {
            logger.error(ee, "Error loading setup configuration.");
        } catch (Exception e) {
            logger.error(e, "Unexpected error during setup component startup.");
        }
    }

    /**
     * Loads and launches the configured agents.
     * 
     * @throws ExtensionException
     *             when the configuration cannot be loaded
     */
    private void launchAgents() throws ExtensionException {

        ExtensionResolver resolver = NabuccoSystem.getExtensionResolver();
        ExtensionMap extensionMap = resolver.resolveExtensions(ExtensionPointType.ORG_NABUCCO_FRAMEWORK_SETUP_AGENT);

        String[] extensionNames = extensionMap.getExtensionNames();

        for (String name : extensionNames) {
            NabuccoExtension extension = extensionMap.getExtension(name);

            if (extension instanceof AgentExtension) {
                AgentExtension agentExtension = (AgentExtension) extension;

                try {
                    Agent agent = AgentFactory.getInstance().createAgent(agentExtension);
                    AgentRegistry.getInstance().register(agent);
                } catch (AgentException ae) {
                    logger.error(ae, "Error loading agent [", agentExtension.getName(), "].");
                }

            } else {
                logger.warning("Skipping extension [", name, "] since it is not of type AgentExtension.");
            }
        }

        try {
            TimerService.getInstance().schedule(new AgentTimer());
        } catch (TimerLookupException e) {
            logger.error(e, "Error scheduling agent timer.");
        }
    }

    /**
     * Configures the jounaling entries.
     * 
     * @throws ExtensionException
     *             when the configuration cannot be loaded
     */
    private void configureJournal() throws ExtensionException {

        ExtensionResolver resolver = NabuccoSystem.getExtensionResolver();
        JournalExtension extension = (JournalExtension) resolver.resolveExtension(
                ExtensionPointType.ORG_NABUCCO_FRAMEWORK_SETUP_JOURNAL, ExtensionResolver.DEFAULT_EXTENSION);

        JournalConfiguration journalConfig = SetupConfiguration.getInstance().getJournalConfig();
        journalConfig.setMaxEntries(extension.getMaxEntries().getValue().getValue());
    }
}
