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
package org.nabucco.framework.setup.impl.service.usrvar;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.BasetypeType;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.UserVariableExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.setup.usrvar.BooleanUserVariable;
import org.nabucco.framework.base.facade.datatype.setup.usrvar.DateUserVariable;
import org.nabucco.framework.base.facade.datatype.setup.usrvar.DecimalUserVariable;
import org.nabucco.framework.base.facade.datatype.setup.usrvar.IntegerUserVariable;
import org.nabucco.framework.base.facade.datatype.setup.usrvar.StringUserVariable;
import org.nabucco.framework.base.facade.datatype.setup.usrvar.TimeUserVariable;
import org.nabucco.framework.base.facade.datatype.setup.usrvar.UserVariable;
import org.nabucco.framework.setup.facade.datatype.usrvar.SetupUserVariable;

/**
 * @author Frank Ratschinski, PRODYNA AG
 * 
 */
public class UserVariableConverter {

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            UserVariableConverter.class);

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public List<UserVariable> convertTo(List<SetupUserVariable> setupUserVarList) throws ParseException {

        List<UserVariable> resList = new ArrayList<UserVariable>();
        UserVariable var = null;
        for (SetupUserVariable userVar : setupUserVarList) {

            BasetypeType type = userVar.getType();
            switch (type) {

            case BOOLEAN: {
                BooleanUserVariable v = new BooleanUserVariable();
                v.setValue(Boolean.parseBoolean(userVar.getValue().getValue()));
                var = v;
                break;
            }
            case INTEGER: {
                IntegerUserVariable v = new IntegerUserVariable();
                v.setValue(Integer.parseInt(userVar.getValue().getValue()));
                var = v;
                break;
            }
            case DATE: {
                DateUserVariable v = new DateUserVariable();
                v.setValue(dateFormat.parse(userVar.getValue().getValue()));
                var = v;
                break;
            }
            case TIME: {
                TimeUserVariable v = new TimeUserVariable();
                v.setValue(timeFormat.parse(userVar.getValue().getValue()));
                var = v;
                break;
            }
            case STRING: {
                StringUserVariable v = new StringUserVariable();
                v.setValue(userVar.getValue().getValue());
                var = v;
                break;
            }
            case DECIMAL: {
                DecimalUserVariable v = new DecimalUserVariable();
                v.setValue(new BigDecimal(userVar.getValue().getValue()));
                var = v;
                break;
            }

            default:
                throw new IllegalStateException("Basetype is not supported " + type);

            } // end switch

            var.setName(userVar.getName());
            var.setDatatypeState(DatatypeState.INITIALIZED);
            resList.add(var);

        } // end for

        return resList;
    }

    public List<SetupUserVariable> convertFrom(List<UserVariable> userVarList) {

        List<SetupUserVariable> resultList = new ArrayList<SetupUserVariable>();

        for (UserVariable uv : userVarList) {

            SetupUserVariable var = new SetupUserVariable();
            var.setDatatypeState(DatatypeState.INITIALIZED);
            var.setName(uv.getName());

            if (uv instanceof BooleanUserVariable) {
                var.setType(BasetypeType.BOOLEAN);
                BooleanUserVariable v = (BooleanUserVariable) uv;
                var.setValue(v.getValue().getValueAsString());
            } else if (uv instanceof DateUserVariable) {
                var.setType(BasetypeType.DATE);
                DateUserVariable v = (DateUserVariable) uv;
                var.setValue(dateFormat.format(v.getValue().getValue()));
            } else if (uv instanceof TimeUserVariable) {
                var.setType(BasetypeType.TIME);
                TimeUserVariable v = (TimeUserVariable) uv;
                var.setValue(timeFormat.format(v.getValue().getValue()));
            } else if (uv instanceof IntegerUserVariable) {
                var.setType(BasetypeType.INTEGER);
                IntegerUserVariable v = (IntegerUserVariable) uv;
                var.setValue(v.getValue().getValueAsString());
            } else if (uv instanceof StringUserVariable) {
                var.setType(BasetypeType.STRING);
                StringUserVariable v = (StringUserVariable) uv;
                var.setValue(v.getValue().getValueAsString());
            } else if (uv instanceof DecimalUserVariable) {
                var.setType(BasetypeType.DECIMAL);
                DecimalUserVariable v = (DecimalUserVariable) uv;
                var.setValue(v.getValue().getValue().toPlainString());
            } else {
                logger.error("Unsupported type, cannot convert UserVariable class=[", uv.getClass().getName(), "]");
            }

            resultList.add(var);

        }

        return resultList;
    }

    public List<UserVariable> convertDefault(List<UserVariableExtension> defaultList) throws ParseException {

        List<UserVariable> svList = new ArrayList<UserVariable>();

        UserVariable var = null;

        for (UserVariableExtension uve : defaultList) {

            String sType = uve.getType().getValue().getValue();
            BasetypeType type = BasetypeType.valueOf(sType);
            String defaultValue = uve.getDefaultValue().getValue().getValue();
            String name = uve.getId().getValue().getValue();

            switch (type) {
            case BOOLEAN: {
                BooleanUserVariable v = new BooleanUserVariable();
                v.setValue(Boolean.parseBoolean(defaultValue));
                var = v;
                break;
            }
            case INTEGER: {
                IntegerUserVariable v = new IntegerUserVariable();
                v.setValue(Integer.parseInt(defaultValue));
                var = v;
                break;
            }
            case DATE: {
                DateUserVariable v = new DateUserVariable();
                v.setValue(dateFormat.parse(defaultValue));
                var = v;
                break;
            }
            case TIME: {
                TimeUserVariable v = new TimeUserVariable();
                v.setValue(timeFormat.parse(defaultValue));
                var = v;
                break;
            }
            case STRING: {
                StringUserVariable v = new StringUserVariable();
                v.setValue(defaultValue);
                var = v;
                break;
            }
            case DECIMAL: {
                DecimalUserVariable v = new DecimalUserVariable();
                v.setValue(new BigDecimal(defaultValue));
                var = v;
                break;
            }

            default:
                throw new IllegalStateException("Basetype is not supported " + type);

            } // end switch

            var.setDatatypeState(DatatypeState.INITIALIZED);
            var.setName(name);

            svList.add(var);

        } // end for loop

        return svList;

    }
}
