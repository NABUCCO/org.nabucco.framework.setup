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
package org.nabucco.framework.setup.facade.datatype.agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.Number;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * CronTrigger<p/>A chronological trigger.<p/>
 *
 * @version 1
 * @author Nicolas Moser, PRODYNA AG, 2011-06-06
 */
public class CronTrigger extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m0,1;", "l0,n;u0,n;m0,1;", "l0,n;u0,n;m0,1;",
            "l0,n;u0,n;m0,1;", "l0,n;u0,n;m0,1;", "l0,n;u0,n;m0,1;" };

    public static final String YEAR = "year";

    public static final String WEEKDAY = "weekday";

    public static final String MONTH = "month";

    public static final String DAY = "day";

    public static final String HOUR = "hour";

    public static final String MINUTE = "minute";

    /** The year. */
    private Number year;

    /** The day of the week (1-7, sunday = 1). */
    private Number weekday;

    /** The month (1-12). */
    private Number month;

    /** The day of the month (1-31). */
    private Number day;

    /** The hour of the day (0-23). */
    private Number hour;

    /** The minute of the hour (0-59). */
    private Number minute;

    /** Constructs a new CronTrigger instance. */
    public CronTrigger() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the CronTrigger.
     */
    protected void cloneObject(CronTrigger clone) {
        super.cloneObject(clone);
        if ((this.getYear() != null)) {
            clone.setYear(this.getYear().cloneObject());
        }
        if ((this.getWeekday() != null)) {
            clone.setWeekday(this.getWeekday().cloneObject());
        }
        if ((this.getMonth() != null)) {
            clone.setMonth(this.getMonth().cloneObject());
        }
        if ((this.getDay() != null)) {
            clone.setDay(this.getDay().cloneObject());
        }
        if ((this.getHour() != null)) {
            clone.setHour(this.getHour().cloneObject());
        }
        if ((this.getMinute() != null)) {
            clone.setMinute(this.getMinute().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(YEAR,
                PropertyDescriptorSupport.createBasetype(YEAR, Number.class, 0, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(WEEKDAY,
                PropertyDescriptorSupport.createBasetype(WEEKDAY, Number.class, 1, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(MONTH,
                PropertyDescriptorSupport.createBasetype(MONTH, Number.class, 2, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(DAY,
                PropertyDescriptorSupport.createBasetype(DAY, Number.class, 3, PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(HOUR,
                PropertyDescriptorSupport.createBasetype(HOUR, Number.class, 4, PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(MINUTE,
                PropertyDescriptorSupport.createBasetype(MINUTE, Number.class, 5, PROPERTY_CONSTRAINTS[5], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(CronTrigger.getPropertyDescriptor(YEAR), this.year, null));
        properties.add(super.createProperty(CronTrigger.getPropertyDescriptor(WEEKDAY), this.weekday, null));
        properties.add(super.createProperty(CronTrigger.getPropertyDescriptor(MONTH), this.month, null));
        properties.add(super.createProperty(CronTrigger.getPropertyDescriptor(DAY), this.day, null));
        properties.add(super.createProperty(CronTrigger.getPropertyDescriptor(HOUR), this.hour, null));
        properties.add(super.createProperty(CronTrigger.getPropertyDescriptor(MINUTE), this.minute, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(YEAR) && (property.getType() == Number.class))) {
            this.setYear(((Number) property.getInstance()));
            return true;
        } else if ((property.getName().equals(WEEKDAY) && (property.getType() == Number.class))) {
            this.setWeekday(((Number) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MONTH) && (property.getType() == Number.class))) {
            this.setMonth(((Number) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DAY) && (property.getType() == Number.class))) {
            this.setDay(((Number) property.getInstance()));
            return true;
        } else if ((property.getName().equals(HOUR) && (property.getType() == Number.class))) {
            this.setHour(((Number) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MINUTE) && (property.getType() == Number.class))) {
            this.setMinute(((Number) property.getInstance()));
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if ((this == obj)) {
            return true;
        }
        if ((obj == null)) {
            return false;
        }
        if ((this.getClass() != obj.getClass())) {
            return false;
        }
        if ((!super.equals(obj))) {
            return false;
        }
        final CronTrigger other = ((CronTrigger) obj);
        if ((this.year == null)) {
            if ((other.year != null))
                return false;
        } else if ((!this.year.equals(other.year)))
            return false;
        if ((this.weekday == null)) {
            if ((other.weekday != null))
                return false;
        } else if ((!this.weekday.equals(other.weekday)))
            return false;
        if ((this.month == null)) {
            if ((other.month != null))
                return false;
        } else if ((!this.month.equals(other.month)))
            return false;
        if ((this.day == null)) {
            if ((other.day != null))
                return false;
        } else if ((!this.day.equals(other.day)))
            return false;
        if ((this.hour == null)) {
            if ((other.hour != null))
                return false;
        } else if ((!this.hour.equals(other.hour)))
            return false;
        if ((this.minute == null)) {
            if ((other.minute != null))
                return false;
        } else if ((!this.minute.equals(other.minute)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.year == null) ? 0 : this.year.hashCode()));
        result = ((PRIME * result) + ((this.weekday == null) ? 0 : this.weekday.hashCode()));
        result = ((PRIME * result) + ((this.month == null) ? 0 : this.month.hashCode()));
        result = ((PRIME * result) + ((this.day == null) ? 0 : this.day.hashCode()));
        result = ((PRIME * result) + ((this.hour == null) ? 0 : this.hour.hashCode()));
        result = ((PRIME * result) + ((this.minute == null) ? 0 : this.minute.hashCode()));
        return result;
    }

    @Override
    public CronTrigger cloneObject() {
        CronTrigger clone = new CronTrigger();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The year.
     *
     * @return the Number.
     */
    public Number getYear() {
        return this.year;
    }

    /**
     * The year.
     *
     * @param year the Number.
     */
    public void setYear(Number year) {
        this.year = year;
    }

    /**
     * The year.
     *
     * @param year the Integer.
     */
    public void setYear(Integer year) {
        if ((this.year == null)) {
            if ((year == null)) {
                return;
            }
            this.year = new Number();
        }
        this.year.setValue(year);
    }

    /**
     * The day of the week (1-7, sunday = 1).
     *
     * @return the Number.
     */
    public Number getWeekday() {
        return this.weekday;
    }

    /**
     * The day of the week (1-7, sunday = 1).
     *
     * @param weekday the Number.
     */
    public void setWeekday(Number weekday) {
        this.weekday = weekday;
    }

    /**
     * The day of the week (1-7, sunday = 1).
     *
     * @param weekday the Integer.
     */
    public void setWeekday(Integer weekday) {
        if ((this.weekday == null)) {
            if ((weekday == null)) {
                return;
            }
            this.weekday = new Number();
        }
        this.weekday.setValue(weekday);
    }

    /**
     * The month (1-12).
     *
     * @return the Number.
     */
    public Number getMonth() {
        return this.month;
    }

    /**
     * The month (1-12).
     *
     * @param month the Number.
     */
    public void setMonth(Number month) {
        this.month = month;
    }

    /**
     * The month (1-12).
     *
     * @param month the Integer.
     */
    public void setMonth(Integer month) {
        if ((this.month == null)) {
            if ((month == null)) {
                return;
            }
            this.month = new Number();
        }
        this.month.setValue(month);
    }

    /**
     * The day of the month (1-31).
     *
     * @return the Number.
     */
    public Number getDay() {
        return this.day;
    }

    /**
     * The day of the month (1-31).
     *
     * @param day the Number.
     */
    public void setDay(Number day) {
        this.day = day;
    }

    /**
     * The day of the month (1-31).
     *
     * @param day the Integer.
     */
    public void setDay(Integer day) {
        if ((this.day == null)) {
            if ((day == null)) {
                return;
            }
            this.day = new Number();
        }
        this.day.setValue(day);
    }

    /**
     * The hour of the day (0-23).
     *
     * @return the Number.
     */
    public Number getHour() {
        return this.hour;
    }

    /**
     * The hour of the day (0-23).
     *
     * @param hour the Number.
     */
    public void setHour(Number hour) {
        this.hour = hour;
    }

    /**
     * The hour of the day (0-23).
     *
     * @param hour the Integer.
     */
    public void setHour(Integer hour) {
        if ((this.hour == null)) {
            if ((hour == null)) {
                return;
            }
            this.hour = new Number();
        }
        this.hour.setValue(hour);
    }

    /**
     * The minute of the hour (0-59).
     *
     * @return the Number.
     */
    public Number getMinute() {
        return this.minute;
    }

    /**
     * The minute of the hour (0-59).
     *
     * @param minute the Number.
     */
    public void setMinute(Number minute) {
        this.minute = minute;
    }

    /**
     * The minute of the hour (0-59).
     *
     * @param minute the Integer.
     */
    public void setMinute(Integer minute) {
        if ((this.minute == null)) {
            if ((minute == null)) {
                return;
            }
            this.minute = new Number();
        }
        this.minute.setValue(minute);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(CronTrigger.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(CronTrigger.class).getAllProperties();
    }
}
