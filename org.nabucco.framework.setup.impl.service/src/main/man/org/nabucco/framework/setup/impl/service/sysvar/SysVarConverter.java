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
package org.nabucco.framework.setup.impl.service.sysvar;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.BasetypeType;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.SystemVariableExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.setup.sysvar.BooleanSystemVariable;
import org.nabucco.framework.base.facade.datatype.setup.sysvar.DateSystemVariable;
import org.nabucco.framework.base.facade.datatype.setup.sysvar.DecimalSystemVariable;
import org.nabucco.framework.base.facade.datatype.setup.sysvar.IntegerSystemVariable;
import org.nabucco.framework.base.facade.datatype.setup.sysvar.StringSystemVariable;
import org.nabucco.framework.base.facade.datatype.setup.sysvar.SystemVariable;
import org.nabucco.framework.base.facade.datatype.setup.sysvar.TimeSystemVariable;
import org.nabucco.framework.setup.facade.datatype.sysvar.SetupSystemVariable;

/**
 * SysVarConverter
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class SysVarConverter {

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(SysVarConverter.class);

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public List<SystemVariable> convertTo(List<SetupSystemVariable> setupSysVarList) throws ParseException {

        List<SystemVariable> resList = new ArrayList<SystemVariable>();
        SystemVariable var = null;
        for (SetupSystemVariable sysVar : setupSysVarList) {

            BasetypeType type = sysVar.getType();
            switch (type) {

            case BOOLEAN: {
                BooleanSystemVariable v = new BooleanSystemVariable();
                v.setValue(Boolean.parseBoolean(sysVar.getValue().getValue()));
                var = v;
                break;
            }
            case INTEGER: {
                IntegerSystemVariable v = new IntegerSystemVariable();
                v.setValue(Integer.parseInt(sysVar.getValue().getValue()));
                var = v;
                break;
            }
            case DATE: {
                DateSystemVariable v = new DateSystemVariable();
                v.setValue(dateFormat.parse(sysVar.getValue().getValue()));
                var = v;
                break;
            }
            case TIME: {
                TimeSystemVariable v = new TimeSystemVariable();
                v.setValue(timeFormat.parse(sysVar.getValue().getValue()));
                var = v;
                break;
            }
            case STRING: {
                StringSystemVariable v = new StringSystemVariable();
                v.setValue(sysVar.getValue().getValue());
                var = v;
                break;
            }
            case DECIMAL: {
                DecimalSystemVariable v = new DecimalSystemVariable();
                v.setValue(new BigDecimal(sysVar.getValue().getValue()));
                var = v;
                break;
            }

            default:
                throw new IllegalStateException("Basetype is not supported " + type);

            } // end switch

            var.setName(sysVar.getName());
            var.setEffectiveDate(sysVar.getEffectiveDate());
            var.setDatatypeState(DatatypeState.INITIALIZED);
            resList.add(var);
        } // end for

        return resList;
    }

    public List<SetupSystemVariable> convertFrom(List<SystemVariable> sysVarList) {

        List<SetupSystemVariable> resultList = new ArrayList<SetupSystemVariable>();

        for (SystemVariable sv : sysVarList) {

            SetupSystemVariable var = new SetupSystemVariable();
            var.setDatatypeState(DatatypeState.INITIALIZED);
            var.setEffectiveDate(sv.getEffectiveDate());
            var.setName(sv.getName());

            if (sv instanceof BooleanSystemVariable) {
                var.setType(BasetypeType.BOOLEAN);
                BooleanSystemVariable v = (BooleanSystemVariable) sv;
                var.setValue(v.getValue().getValueAsString());
            } else if (sv instanceof DateSystemVariable) {
                var.setType(BasetypeType.DATE);
                DateSystemVariable v = (DateSystemVariable) sv;
                var.setValue(dateFormat.format(v.getValue().getValue()));
            } else if (sv instanceof TimeSystemVariable) {
                var.setType(BasetypeType.TIME);
                TimeSystemVariable v = (TimeSystemVariable) sv;
                var.setValue(timeFormat.format(v.getValue().getValue()));
            } else if (sv instanceof IntegerSystemVariable) {
                var.setType(BasetypeType.INTEGER);
                IntegerSystemVariable v = (IntegerSystemVariable) sv;
                var.setValue(v.getValue().getValueAsString());
            } else if (sv instanceof StringSystemVariable) {
                var.setType(BasetypeType.STRING);
                StringSystemVariable v = (StringSystemVariable) sv;
                var.setValue(v.getValue().getValueAsString());
            } else if (sv instanceof DecimalSystemVariable) {
                var.setType(BasetypeType.DECIMAL);
                DecimalSystemVariable v = (DecimalSystemVariable) sv;
                var.setValue(v.getValue().getValue().toPlainString());
            } else {
                logger.error("Unsupported type, cannot convert SystemVariable class=[", sv.getClass().getName(), "]");
            }

            resultList.add(var);

        }

        return resultList;
    }

    public List<SystemVariable> convertDefault(List<SystemVariableExtension> defaultList) throws ParseException {

        List<SystemVariable> svList = new ArrayList<SystemVariable>();

        SystemVariable var = null;

        for (SystemVariableExtension sve : defaultList) {

            String sType = sve.getType().getValue().getValue();
            BasetypeType type = BasetypeType.valueOf(sType);
            String defaultValue = sve.getDefaultValue().getValue().getValue();
            String name = sve.getName().getValue().getValue();

            switch (type) {
            case BOOLEAN: {
                BooleanSystemVariable v = new BooleanSystemVariable();
                v.setValue(Boolean.parseBoolean(defaultValue));
                var = v;
                break;
            }
            case INTEGER: {
                IntegerSystemVariable v = new IntegerSystemVariable();
                v.setValue(Integer.parseInt(defaultValue));
                var = v;
                break;
            }
            case DATE: {
                DateSystemVariable v = new DateSystemVariable();
                v.setValue(dateFormat.parse(defaultValue));
                var = v;
                break;
            }
            case TIME: {
                TimeSystemVariable v = new TimeSystemVariable();
                v.setValue(timeFormat.parse(defaultValue));
                var = v;
                break;
            }
            case STRING: {
                StringSystemVariable v = new StringSystemVariable();
                v.setValue(defaultValue);
                var = v;
                break;
            }
            case DECIMAL: {
                DecimalSystemVariable v = new DecimalSystemVariable();
                v.setValue(new BigDecimal(defaultValue));
                var = v;
                break;
            }

            default:
                throw new IllegalStateException("Basetype is not supported " + type);

            } // end switch
            var.setDatatypeState(DatatypeState.INITIALIZED);
            var.setEffectiveDate(new Date(NabuccoSystem.getCurrentTimeMillis()));
            var.setName(name);

            svList.add(var);

        } // end for loop

        return svList;

    }
}
