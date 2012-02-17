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
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.UserVarExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.UserVariableExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.setup.usrvar.UserVariable;
import org.nabucco.framework.base.facade.message.setup.usrvar.UserVariableMsg;
import org.nabucco.framework.setup.facade.exception.SysVarException;

/**
 * UserVariableSchema
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class UserVariableSchema {

    private static UserVariableSchema instance;

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(UserVariableSchema.class);

    private Map<String, UserVariableExtension> userVarMap = new HashMap<String, UserVariableExtension>();

    /**
     * Creates a new {@link UserVariableSchema} instance.
     */
    private UserVariableSchema() {
        this.init();
    }

    /**
     * Getter for the singleton instance.
     * 
     * @return
     */
    public static synchronized UserVariableSchema getInstance() {
        if (instance == null) {
            instance = new UserVariableSchema();
        }
        return instance;
    }

    /**
     * Initialize the user variable schema via extensions.
     */
    private void init() {

        try {
            ExtensionResolver er = new ExtensionResolver();
            UserVarExtension ext = (UserVarExtension) er.resolveExtension(
                    ExtensionPointType.ORG_NABUCCO_FRAMEWORK_SETUP_USRVAR_SCHEMA, ExtensionResolver.DEFAULT_EXTENSION);

            for (UserVariableExtension uv : ext.getUserVarList()) {
                String id = uv.getId().getValue().getValue();
                userVarMap.put(id, uv);
            }

        } catch (Exception e) {
            logger.error(e, "Cannot load extension for setup user variables schema");
        }

    }

    public void check(UserVariableMsg msg) throws SysVarException {
        for (UserVariable uv : msg.getUserVariableList()) {

            String id = uv.getName().getValue();
            if (!this.userVarMap.containsKey(id)) {
                logger.error("Unsupported user variable id=[" + id + "]");
                throw new SysVarException("No user variable in schema defined for id=[" + id + "]");
            }
        }
    }

    public List<UserVariable> fillDefaults(UserVariableMsg msg) throws SysVarException {

        check(msg);
        Set<String> usedIdSet = new HashSet<String>();
        for (UserVariable sv : msg.getUserVariableList()) {
            usedIdSet.add(sv.getName().getValue());
        }

        Iterator<String> i = this.userVarMap.keySet().iterator();
        List<UserVariableExtension> defaultUserVarList = new ArrayList<UserVariableExtension>();
        while (i.hasNext()) {
            String id = i.next();
            if (!usedIdSet.contains(id)) {
                UserVariableExtension defaultVariable = this.userVarMap.get(id);
                defaultUserVarList.add(defaultVariable);
            }
        }
        UserVariableConverter converter = new UserVariableConverter();
        try {
            List<UserVariable> resultList = converter.convertDefault(defaultUserVarList);
            return resultList;
        } catch (ParseException pe) {
            throw new SysVarException(pe);
        }
    }
}
