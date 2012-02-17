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
import java.util.List;

import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.datatype.security.UserId;
import org.nabucco.framework.base.facade.datatype.setup.usrvar.UserVariable;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.setup.usrvar.UserVariableMsg;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.setup.facade.datatype.usrvar.SetupUserVariable;
import org.nabucco.framework.setup.facade.exception.SysVarException;

/**
 * LoadUserVariablesServiceHandlerImpl
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class LoadUserVariablesServiceHandlerImpl extends LoadUserVariablesServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String QUERY = "FROM SetupUserVariable v WHERE v.owner = :owner AND v.user = :user";

    @Override
    protected UserVariableMsg loadUserVariables(EmptyServiceMessage msg) throws SearchException {

        Subject subject = getContext().getSubject();
        Owner owner = subject.getOwner();
        UserId user = subject.getUserId();

        UserVariableMsg result = new UserVariableMsg();

        try {
            PersistenceManager pm = getPersistenceManager();
            NabuccoQuery<SetupUserVariable> query = pm.createQuery(QUERY);
            query.setParameter(SetupUserVariable.OWNER, owner);
            query.setParameter(SetupUserVariable.USER, user);

            List<SetupUserVariable> setupUserVariableList = query.getResultList();

            UserVariableConverter converter = new UserVariableConverter();

            List<UserVariable> userVariableList = converter.convertTo(setupUserVariableList);
            result.getUserVariableList().addAll(userVariableList);

            UserVariableSchema schema = UserVariableSchema.getInstance();

            List<UserVariable> resultWithDefaults = schema.fillDefaults(result);
            result.getUserVariableList().addAll(resultWithDefaults);

        } catch (PersistenceException pe) {
            throw new SearchException("Cannot load user preferences for owner=[" + owner + "] user=[" + user + "]", pe);
        } catch (ParseException e) {
            throw new SearchException("Cannot convert user variables", e);
        } catch (SysVarException se) {
            throw new SearchException("Cannot use default user variables", se);
        }

        return result;
    }

}
