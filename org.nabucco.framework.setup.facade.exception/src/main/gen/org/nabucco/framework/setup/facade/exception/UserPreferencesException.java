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
package org.nabucco.framework.setup.facade.exception;

import org.nabucco.framework.setup.facade.exception.SetupException;

/**
 * UserPreferencesException<p/>Exception for the facade of the userpreferenes.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-03-15
 */
public class UserPreferencesException extends SetupException {

    private static final long serialVersionUID = 1L;

    /** Constructs a new UserPreferencesException instance. */
    public UserPreferencesException() {
        super();
    }

    /**
     * Constructs a new UserPreferencesException instance.
     *
     * @param message the String.
     */
    public UserPreferencesException(String message) {
        super(message);
    }

    /**
     * Constructs a new UserPreferencesException instance.
     *
     * @param cause the Throwable.
     */
    public UserPreferencesException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new UserPreferencesException instance.
     *
     * @param cause the Throwable.
     * @param message the String.
     */
    public UserPreferencesException(String message, Throwable cause) {
        super(message, cause);
    }
}
