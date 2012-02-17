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
import java.util.List;

import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.setup.sysvar.SystemVariable;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.setup.sysvar.SystemVariableMsg;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.setup.facade.datatype.sysvar.SetupSystemVariable;
import org.nabucco.framework.setup.facade.exception.SysVarException;

/**
 * @author Frank Ratschinski, PRODYNA AG
 * 
 */
public class LoadSystemVariablesServiceHandlerImpl extends LoadSystemVariablesServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected SystemVariableMsg loadSystemVariables(EmptyServiceMessage msg) throws SearchException {

        try {
            PersistenceManager pm = getPersistenceManager();

            NabuccoQuery<SetupSystemVariable> query = pm
                    .createQuery("FROM SetupSystemVariable  v WHERE v.owner = :owner ORDER BY v.name, v.effectiveDate");
            Owner owner = getContext().getSubject().getOwner();
            query.setParameter("owner", owner);
            List<SetupSystemVariable> res = query.getResultList();
            SystemVariableMsg resultMsg = new SystemVariableMsg();
            SysVarConverter converter = new SysVarConverter();
            resultMsg.getSystemVariableList().addAll(converter.convertTo(res));

            SystemVariableSchema schema = SystemVariableSchema.getInstance();

            List<SystemVariable> defaultList = schema.fillDefaults(resultMsg);
            resultMsg.getSystemVariableList().addAll(defaultList);

            return resultMsg;

        } catch (PersistenceException e) {
            throw new SearchException("Cannot load system variables", e);
        } catch (ParseException pe) {
            throw new SearchException("Unable to convert stored system variables", pe);
        } catch (SysVarException se) {
            throw new SearchException("Cannot set default values", se);
        }

    }

}
