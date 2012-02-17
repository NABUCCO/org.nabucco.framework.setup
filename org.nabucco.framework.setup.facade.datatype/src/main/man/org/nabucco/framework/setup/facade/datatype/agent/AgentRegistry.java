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
package org.nabucco.framework.setup.facade.datatype.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nabucco.framework.setup.facade.datatype.agent.Agent;

/**
 * AgentRegistry
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class AgentRegistry {

    private Map<String, Agent> agentMap = new HashMap<String, Agent>();

    /**
     * Singleton instance.
     */
    private static AgentRegistry instance;

    /**
     * Private constructor.
     */
    private AgentRegistry() {
    }

    /**
     * Singleton access.
     * 
     * @return the AgentRegistry instance.
     */
    public static synchronized AgentRegistry getInstance() {
        if (instance == null) {
            instance = new AgentRegistry();
        }
        return instance;
    }

    /**
     * Register an agent in the agent registry.
     * 
     * @param agent
     *            the agent to register
     */
    public void register(Agent agent) {
        this.agentMap.put(agent.getName().getValue(), agent);
    }

    /**
     * Retrieve the agent with the given name from the registry.
     * 
     * @param name
     *            the name of the agent
     * 
     * @return the registered agent, or null if no agent with the given name is registered
     */
    public Agent get(String name) {
        return this.agentMap.get(name);
    }

    /**
     * Retrieve all registered agents.
     * 
     * @return the list of registered agents
     */
    public List<Agent> getAll() {
        return new ArrayList<Agent>(this.agentMap.values());
    }

    /**
     * Clear the list of registered agents.
     */
    public void clear() {
        this.agentMap.clear();
    }
}
