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
package org.nabucco.framework.setup.facade.datatype.query;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.search.query.FulltextQuery;
import org.nabucco.framework.base.facade.datatype.search.query.FulltextQueryField;
import org.nabucco.framework.base.facade.datatype.search.query.FulltextQueryRangeField;
import org.nabucco.framework.base.facade.datatype.search.query.FulltextQueryValueField;

/**
 * Helper class for converting fulltext queries into storable queries and converting stored queries
 * into executable queries.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * 
 */
public class FulltextQueryManager {

    /** The Singleton Instance */
    private static FulltextQueryManager instance;

    /**
     * Creates a new {@link FulltextQueryManager} instance.
     */
    private FulltextQueryManager() {
    }

    /**
     * Access on the {@link FulltextQueryManager} singleton instance.
     * 
     * @return the singleton instance
     */
    public static synchronized FulltextQueryManager getInstance() {
        if (instance == null) {
            instance = new FulltextQueryManager();
        }
        return instance;
    }

    public SetupFulltextQuery createStorableQuery(FulltextQuery query, String name) {

        SetupFulltextQuery storableQuery = new SetupFulltextQuery();
        storableQuery.setDatatypeState(DatatypeState.INITIALIZED);

        storableQuery.setIndexName(query.getIndexName());
        storableQuery.setMaxResult(query.getMaxResult());
        storableQuery.setName(name);

        // will be set in maintain operation
        // storableQuery.setOwner(...);
        // storableQuery.setUser(...);

        storableQuery.setTimestamp(NabuccoSystem.getCurrentTimestamp());

        for (FulltextQueryField field : query.getFieldList()) {

            if (field instanceof FulltextQueryValueField) {
                FulltextQueryValueField valueField = (FulltextQueryValueField) field;

                SetupFulltextQueryValueField storableField = new SetupFulltextQueryValueField();
                storableField.setDatatypeState(DatatypeState.INITIALIZED);
                storableField.setFieldName(valueField.getFieldName());
                storableField.setFieldType(valueField.getFieldType());
                storableField.setFieldValue(valueField.getFieldValue());
                storableQuery.getValueFieldList().add(storableField);

            } else if (field instanceof FulltextQueryRangeField) {
                FulltextQueryRangeField valueField = (FulltextQueryRangeField) field;

                SetupFulltextQueryRangeField storableField = new SetupFulltextQueryRangeField();
                storableField.setDatatypeState(DatatypeState.INITIALIZED);
                storableField.setFieldName(valueField.getFieldName());
                storableField.setFieldType(valueField.getFieldType());
                storableField.setMinValue(valueField.getMinValue());
                storableField.setMaxValue(valueField.getMaxValue());
                storableQuery.getRangeFieldList().add(storableField);
            }
        }

        return storableQuery;
    }

    /**
     * Converts a stored fulltext query into a new query for fulltext searching.
     */
    public FulltextQuery createQueryFromStoredQuery(SetupFulltextQuery storedQuery) {
        FulltextQuery query = new FulltextQuery();
        query.setDatatypeState(DatatypeState.INITIALIZED);

        query.setIndexName(storedQuery.getIndexName());
        query.setMaxResult(storedQuery.getMaxResult());

        for (SetupFulltextQueryValueField storedField : storedQuery.getValueFieldList()) {
            FulltextQueryValueField field = new FulltextQueryValueField();
            field.setDatatypeState(DatatypeState.INITIALIZED);
            field.setFieldName(storedField.getFieldName());
            field.setFieldType(storedField.getFieldType());
            field.setFieldValue(storedField.getFieldValue());
            query.getFieldList().add(field);
        }
        
        for (SetupFulltextQueryRangeField storedField : storedQuery.getRangeFieldList()) {
            FulltextQueryRangeField field = new FulltextQueryRangeField();
            field.setDatatypeState(DatatypeState.INITIALIZED);
            field.setFieldName(storedField.getFieldName());
            field.setFieldType(storedField.getFieldType());
            field.setMinValue(storedField.getMinValue());
            field.setMaxValue(storedField.getMaxValue());
            query.getFieldList().add(field);
        }

        return query;
    }

}
