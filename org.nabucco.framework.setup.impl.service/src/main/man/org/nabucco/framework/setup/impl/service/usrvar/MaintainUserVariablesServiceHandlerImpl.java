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

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.datatype.security.UserId;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.message.setup.usrvar.UserVariableMsg;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.framework.setup.facade.datatype.usrvar.SetupUserVariable;
import org.nabucco.framework.setup.facade.exception.SysVarException;

/**
 * @author Frank Ratschinski, PRODYNA AG
 * 
 */
public class MaintainUserVariablesServiceHandlerImpl extends MaintainUserVariablesServiceHandler {

    private static final String QUERY = "DELETE FROM SetupUserVariable v WHERE v.owner = :owner AND v.user = :user";

    private static final long serialVersionUID = 1L;

    @Override
    protected UserVariableMsg maintainUserVariables(UserVariableMsg msg) throws MaintainException {

        Subject subject = getContext().getSubject();
        Owner owner = subject.getOwner();
        UserId user = subject.getUserId();

        try {
            UserVariableSchema schema = UserVariableSchema.getInstance();
            schema.check(msg);

            NabuccoQuery<?> remove = getPersistenceManager().createQuery(QUERY);
            remove.setParameter(SetupUserVariable.OWNER, owner);
            remove.setParameter(SetupUserVariable.USER, user);
            remove.executeUpdate();

            UserVariableConverter converter = new UserVariableConverter();

            List<SetupUserVariable> setupUVList = converter.convertFrom(msg.getUserVariableList());

            for (SetupUserVariable v : setupUVList) {
                v.setOwner(owner);
                v.setUser(user);
            }

            getPersistenceManager().persist(setupUVList);

        } catch (SysVarException se) {
            throw new MaintainException("Unsupported user variable found", se);
        } catch (PersistenceException pe) {
            throw new MaintainException("Cannot save user variables", pe);
        }
        return null;
    }

}
