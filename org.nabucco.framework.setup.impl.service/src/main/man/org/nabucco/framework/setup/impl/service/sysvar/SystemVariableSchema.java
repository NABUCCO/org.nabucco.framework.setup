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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionResolver;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.SysVarExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.SystemVariableExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.setup.sysvar.SystemVariable;
import org.nabucco.framework.base.facade.message.setup.sysvar.SystemVariableMsg;
import org.nabucco.framework.setup.facade.exception.SysVarException;

/**
 * @author Frank Ratschinski, PRODYNA AG
 * 
 */
public class SystemVariableSchema {

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            SystemVariableSchema.class);

    private static SystemVariableSchema instance;

    private Map<String, SystemVariableExtension> sysvarMap = new HashMap<String, SystemVariableExtension>();

    private SystemVariableSchema() {
        init();
    }

    public static synchronized SystemVariableSchema getInstance() {
        if (instance == null) {
            instance = new SystemVariableSchema();
        }
        return instance;
    }

    private void init() {

        try {
            ExtensionResolver er = new ExtensionResolver();
            SysVarExtension ext = (SysVarExtension) er.resolveExtension(
                    ExtensionPointType.ORG_NABUCCO_FRAMEWORK_SETUP_SYSVAR_SCHEMA, ExtensionResolver.DEFAULT_EXTENSION);

            for (SystemVariableExtension sv : ext.getSysVarList()) {
                String id = sv.getName().getValue().getValue();
                sysvarMap.put(id, sv);
            }

        } catch (Exception e) {
            logger.error(e, "Cannot load extension for setup system variables schema");
        }

    }

    public void check(SystemVariableMsg msg) throws SysVarException {
        for (SystemVariable sv : msg.getSystemVariableList()) {

            String id = sv.getName().getValue();
            if (!this.sysvarMap.containsKey(id)) {
                logger.error("Unsupported system variable id=[" + id + "]");
                throw new SysVarException("No system variable in schema defined for id=[" + id + "]");
            }
        }
    }

    public List<SystemVariable> fillDefaults(SystemVariableMsg msg) throws SysVarException {

        check(msg);
        Set<String> usedIdSet = new HashSet<String>();
        for (SystemVariable sv : msg.getSystemVariableList()) {
            usedIdSet.add(sv.getName().getValue());
        }

        Iterator<String> i = this.sysvarMap.keySet().iterator();
        List<SystemVariableExtension> defaultSysVarList = new ArrayList<SystemVariableExtension>();
        while (i.hasNext()) {
            String id = i.next();
            if (!usedIdSet.contains(id)) {
                SystemVariableExtension defaultVariable = this.sysvarMap.get(id);
                defaultSysVarList.add(defaultVariable);
            }
        }
        SysVarConverter converter = new SysVarConverter();
        try {
            List<SystemVariable> resultList = converter.convertDefault(defaultSysVarList);
            return resultList;
        } catch (ParseException pe) {
            throw new SysVarException(pe);
        }
    }
}
