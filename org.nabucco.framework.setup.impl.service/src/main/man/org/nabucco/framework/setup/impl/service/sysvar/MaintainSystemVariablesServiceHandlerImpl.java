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

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.message.setup.sysvar.SystemVariableMsg;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.setup.facade.datatype.sysvar.SetupSystemVariable;
import org.nabucco.framework.setup.facade.exception.SysVarException;

/**
 * @author Frank Ratschinski, PRODYNA AG
 * 
 */
public class MaintainSystemVariablesServiceHandlerImpl extends MaintainSystemVariablesServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected SystemVariableMsg maintainSystemVariables(SystemVariableMsg msg) throws MaintainException {
        try {

            Owner owner = getContext().getSubject().getOwner();
            SystemVariableSchema schema = SystemVariableSchema.getInstance();

            schema.check(msg);

            PersistenceManager pm = getPersistenceManager();

            NabuccoQuery<?> query = pm.createQuery("DELETE FROM SetupSystemVariable v WHERE v.owner = :owner");
            query.setParameter("owner", owner);
            query.executeUpdate();

            SysVarConverter converter = new SysVarConverter();

            List<SetupSystemVariable> newSysVarList = converter.convertFrom(msg.getSystemVariableList());

            for (SetupSystemVariable sv : newSysVarList) {
                sv.setOwner(owner);
            }

            pm.persist(newSysVarList);

        } catch (PersistenceException e) {
            throw new MaintainException("Cannot update system variables", e);
        } catch (SysVarException se) {
            throw new MaintainException("Unsuppported system variable id", se);
        }

        return new SystemVariableMsg();

    }

}
