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
package org.nabucco.framework.setup.impl.service.sequence;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.date.Timestamp;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.SequenceGeneratorExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.setup.facade.datatype.sequence.SequenceGenerator;
import org.nabucco.framework.setup.facade.datatype.sequence.SequenceResetType;
import org.nabucco.framework.setup.facade.datatype.sequence.SequenceValue;
import org.nabucco.framework.setup.facade.exception.SequenceException;

/**
 * SequenceGenerator
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class SequenceIncrementor {

    private static final int DEFAULT_START = 0;

    private static final int DEFAULT_STEPS = 1;

    private static final int DEFAULT_LENGTH = 10;

    private static final boolean DEFAULT_LEADING_ZEROS = false;

    private static final boolean DEFAULT_OVERFLOW = false;

    private NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(SequenceIncrementor.class);

    /**
     * Singleton instance.
     */
    private static SequenceIncrementor instance = new SequenceIncrementor();

    /**
     * Private constructor.
     */
    private SequenceIncrementor() {
    }

    /**
     * Singleton access.
     * 
     * @return the SequenceGenerator instance.
     */
    public static SequenceIncrementor getInstance() {
        return instance;
    }

    /**
     * Generate a new sequence for the previous sequence.
     * 
     * @param sequence
     *            the previous sequence
     * 
     * @return the next sequence
     * 
     * @throws SequenceException
     *             when the sequence cannot be generated
     */
    public void incrementGenerator(SequenceGenerator generator) throws SequenceException {

        if (generator == null) {
            throw new SequenceException("Cannot create seqeuence for generator [null].");
        }

        switch (generator.getType()) {

        case SEQUENCE:
            generator.setValue(this.createSequenceValue(generator));
            break;

        case DATE:
            generator.setValue(this.createDateValue(generator));
            break;

        case UUID:
            generator.setValue(this.createUniqueValue(generator));
            break;

        default:
            break;

        }

        generator.setModificationTime(NabuccoSystem.getCurrentTimestamp());
    }

    /**
     * Create a continous sequence value.
     * 
     * @param generator
     *            the sequence generator
     * 
     * @return the cosinous sequence
     * 
     * @throws SequenceException
     *             when the continous sequence cannot be created
     */
    private SequenceValue createSequenceValue(SequenceGenerator generator) throws SequenceException {

        SequenceGeneratorExtension extension = generator.getExtension();

        String oldValue = generator.getValue() != null ? generator.getValue().getValue() : null;

        long start = this.getStart(extension);
        int steps = this.getSteps(extension);
        int length = this.getLength(extension);

        boolean leadingZeros = this.getLeadingZeros(extension);
        boolean overflow = this.getOverflow(extension);
        
        boolean isReset = oldValue == null || this.isReset(extension, generator.getModificationTime());
        
        long value;
        if (isReset) {
            value = start;
        } else {
            try {
                NumberFormat nf = new DecimalFormat();
                nf.setMinimumIntegerDigits(length);
                nf.setGroupingUsed(false);
                value = nf.parse(oldValue).longValue();
            } catch (Exception e) {
                logger.warning(e, "Cannot parse previous sequence [", oldValue, "]. Setting value to 'start'.");
                value = start;
                isReset = true;
            }
        }

        if (!isReset) {
            value = value + steps;
        }

        String valueAsString = String.valueOf(value);

        if (!overflow && valueAsString.length() > length) {
            value = start;
        }

        if (leadingZeros) {
            NumberFormat nf = new DecimalFormat();
            nf.setMinimumIntegerDigits(length);
            nf.setGroupingUsed(false);
            valueAsString = nf.format(value);
        }

        return new SequenceValue(valueAsString);
    }

    /**
     * Create a date sequence value.
     * 
     * @param generator
     *            the sequence generator
     * 
     * @return the date sequence
     * 
     * @throws SequenceException
     *             when the date sequence cannot be created
     */
    private SequenceValue createDateValue(SequenceGenerator generator) throws SequenceException {

        String datePattern = "yyyy-DDD";
        SequenceGeneratorExtension extension = generator.getExtension();

        if (extension.getPattern() != null
                && extension.getPattern().getValue() != null && extension.getPattern().getValue().getValue() != null) {
            datePattern = extension.getPattern().getValue().getValue();
        }

        Date date = new Date(NabuccoSystem.getCurrentTimeMillis());
        DateFormat dateFormat = new SimpleDateFormat(datePattern);
        String value = dateFormat.format(date);

        return new SequenceValue(value);
    }

    /**
     * Create a UUID sequence value.
     * 
     * @param generator
     *            the sequence generator
     * 
     * @param extension
     *            the sequence generator extension
     * 
     * @return the UUID sequence
     */
    private SequenceValue createUniqueValue(SequenceGenerator generator) {
        return new SequenceValue(NabuccoSystem.createUUID());
    }

    /**
     * Extract the start attribute from extension configuration.
     * 
     * @param extension
     *            the generator extension config
     * 
     * @return the start attribute
     */
    private long getStart(SequenceGeneratorExtension extension) {
        if (extension.getStart() == null) {
            return DEFAULT_START;
        }
        if (extension.getStart().getValue() == null) {
            return DEFAULT_START;
        }
        if (extension.getStart().getValue().getValue() == null) {
            return DEFAULT_START;
        }

        try {
            return Long.parseLong(extension.getStart().getValue().getValue());
        } catch (NumberFormatException nfe) {
            logger.warning(nfe, "Cannot format start number in <generator>.");
        }

        return DEFAULT_START;
    }

    /**
     * Extract the steps attribute from extension configuration.
     * 
     * @param extension
     *            the generator extension config
     * 
     * @return the steps attribute
     */
    private int getSteps(SequenceGeneratorExtension extension) {
        if (extension.getSteps() == null) {
            return DEFAULT_STEPS;
        }
        if (extension.getSteps().getValue() == null) {
            return DEFAULT_STEPS;
        }
        if (extension.getSteps().getValue().getValue() == null) {
            return DEFAULT_STEPS;
        }

        try {
            return Integer.parseInt(extension.getSteps().getValue().getValue());
        } catch (NumberFormatException nfe) {
            logger.warning(nfe, "Cannot format steps in <generator>.");
        }

        return DEFAULT_STEPS;
    }

    /**
     * Extract the length attribute from extension configuration.
     * 
     * @param extension
     *            the generator extension config
     * 
     * @return the length attribute
     */
    private int getLength(SequenceGeneratorExtension extension) {
        if (extension.getLength() == null) {
            return DEFAULT_LENGTH;
        }
        if (extension.getLength().getValue() == null) {
            return DEFAULT_LENGTH;
        }
        if (extension.getLength().getValue().getValue() == null) {
            return DEFAULT_LENGTH;
        }

        try {
            return Integer.parseInt(extension.getLength().getValue().getValue());
        } catch (NumberFormatException nfe) {
            logger.warning(nfe, "Cannot format length in <generator>.");
        }

        return DEFAULT_LENGTH;
    }

    /**
     * Extract the overflow attribute from extension configuration.
     * 
     * @param extension
     *            the generator extension config
     * 
     * @return the overflow attribute
     */
    private boolean getOverflow(SequenceGeneratorExtension extension) {
        if (extension.getOverflow() == null) {
            return DEFAULT_OVERFLOW;
        }
        if (extension.getOverflow().getValue() == null) {
            return DEFAULT_OVERFLOW;
        }
        if (extension.getOverflow().getValue().getValue() == null) {
            return DEFAULT_OVERFLOW;
        }

        return extension.getOverflow().getValue().getValue();
    }

    /**
     * Extract the leadingZeros attribute from extension configuration.
     * 
     * @param extension
     *            the generator extension config
     * 
     * @return the leadingZeros attribute
     */
    private boolean getLeadingZeros(SequenceGeneratorExtension extension) {
        if (extension.getLeadingZeros() == null) {
            return DEFAULT_LEADING_ZEROS;
        }
        if (extension.getLeadingZeros().getValue() == null) {
            return DEFAULT_LEADING_ZEROS;
        }
        if (extension.getLeadingZeros().getValue().getValue() == null) {
            return DEFAULT_LEADING_ZEROS;
        }

        return extension.getLeadingZeros().getValue().getValue();
    }

    /**
     * Extract the autoreset attribute from extension configuration.
     * 
     * @param extension
     *            the generator extension config
     * @param modificationTime
     *            the last modification time
     * 
     * @return <b>true</b> if the sequence must be reseted, <b>false</b> if not
     */
    private boolean isReset(SequenceGeneratorExtension extension, Timestamp modificationTime) {
        if (modificationTime == null) {
            return false;
        }
        if (modificationTime.getValue() == null) {
            return false;
        }
        if (extension.getAutoReset() == null) {
            return false;
        }
        if (extension.getAutoReset().getValue() == null) {
            return false;
        }
        if (extension.getAutoReset().getValue().getValue() == null) {
            return false;
        }

        SequenceResetType resetType;
        String resetValue = extension.getAutoReset().getValue().getValue();

        try {
            resetType = SequenceResetType.valueOf(resetValue);
        } catch (Exception e) {
            logger.warning(e, "Cannot parse autoreset type [", resetValue, "] from generator extension.");
            return false;
        }

        switch (resetType) {

        case YEAR: {
            return this.isBefore(modificationTime.getValue(), Calendar.YEAR);
        }

        case MONTH: {
            return this.isBefore(modificationTime.getValue(), Calendar.MONTH);
        }

        case DAY: {
            return this.isBefore(modificationTime.getValue(), Calendar.MONTH);
        }

        case HOUR: {
            return this.isBefore(modificationTime.getValue(), Calendar.HOUR_OF_DAY);
        }

        default:
            return false;
        }
    }

    /**
     * Check whether the appropriate date field of the modificationTime is before the current date.
     * 
     * @param modificationTime
     *            the last date
     * @param field
     *            the calendar field to check for
     * 
     * @return <b>true</b> if the other field is before the current field, <b>false</b> if not
     */
    private boolean isBefore(Long modificationTime, int field) {

        Calendar calendar = new GregorianCalendar();

        calendar.setTimeInMillis(modificationTime);
        int lastValue = calendar.get(field);

        calendar.setTimeInMillis(NabuccoSystem.getCurrentTimeMillis());
        int currentValue = calendar.get(field);

        return lastValue < currentValue;
    }

}
