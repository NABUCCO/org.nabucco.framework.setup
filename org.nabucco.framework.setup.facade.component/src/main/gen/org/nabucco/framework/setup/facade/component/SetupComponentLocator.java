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
package org.nabucco.framework.setup.facade.component;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.component.locator.ComponentLocator;
import org.nabucco.framework.base.facade.component.locator.ComponentLocatorSupport;

/**
 * Locator for SetupComponent.
 *
 * @author NABUCCO Generator, PRODYNA AG
 */
public class SetupComponentLocator extends ComponentLocatorSupport<SetupComponent> implements
        ComponentLocator<SetupComponent> {

    private static SetupComponentLocator instance;

    /**
     * Constructs a new SetupComponentLocator instance.
     *
     * @param component the Class<SetupComponent>.
     * @param jndiName the String.
     */
    private SetupComponentLocator(String jndiName, Class<SetupComponent> component) {
        super(jndiName, component);
    }

    @Override
    public SetupComponent getComponent() throws ConnectionException {
        SetupComponent component = super.getComponent();
        if ((component instanceof SetupComponentLocal)) {
            return new SetupComponentLocalProxy(((SetupComponentLocal) component));
        }
        return component;
    }

    /**
     * Getter for the Instance.
     *
     * @return the SetupComponentLocator.
     */
    public static SetupComponentLocator getInstance() {
        if ((instance == null)) {
            instance = new SetupComponentLocator(SetupComponent.JNDI_NAME, SetupComponent.class);
        }
        return instance;
    }
}
