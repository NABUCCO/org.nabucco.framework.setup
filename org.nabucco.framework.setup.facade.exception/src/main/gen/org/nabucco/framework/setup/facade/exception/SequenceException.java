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
 * SequenceException<p/>Exception for errors in sequence generation.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-01
 */
public class SequenceException extends SetupException {

    private static final long serialVersionUID = 1L;

    /** Constructs a new SequenceException instance. */
    public SequenceException() {
        super();
    }

    /**
     * Constructs a new SequenceException instance.
     *
     * @param message the String.
     */
    public SequenceException(String message) {
        super(message);
    }

    /**
     * Constructs a new SequenceException instance.
     *
     * @param cause the Throwable.
     */
    public SequenceException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new SequenceException instance.
     *
     * @param cause the Throwable.
     * @param message the String.
     */
    public SequenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
