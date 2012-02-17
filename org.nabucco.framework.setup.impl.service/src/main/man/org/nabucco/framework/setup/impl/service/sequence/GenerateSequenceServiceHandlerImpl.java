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
package org.nabucco.framework.setup.impl.service.sequence;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionMap;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionResolver;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.SequenceConfigExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.SequenceGeneratorExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.SequenceTemplateEntryExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.SequenceTemplateExtension;
import org.nabucco.framework.base.facade.exception.persistence.OptimisticLockException;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.framework.setup.facade.datatype.sequence.SequenceGenerator;
import org.nabucco.framework.setup.facade.datatype.sequence.SequenceType;
import org.nabucco.framework.setup.facade.datatype.sequence.SequenceValue;
import org.nabucco.framework.setup.facade.exception.SequenceAlreadyInUseException;
import org.nabucco.framework.setup.facade.exception.SequenceException;
import org.nabucco.framework.setup.facade.message.sequence.SequenceRq;
import org.nabucco.framework.setup.facade.message.sequence.SequenceRs;

/**
 * GenerateSequenceServiceHandlerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class GenerateSequenceServiceHandlerImpl extends GenerateSequenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String QUERY = "select s from SequenceGenerator s where s.generatorId in (:generators)";

    private static final String PARAM_GENERATOR = "generators";

    @Override
    protected SequenceRs generateSequence(SequenceRq rq) throws SequenceException {

        String sequenceName = rq.getSequenceName().getValue();
        String locale = rq.getLocale().getValue();

        try {
            SequenceConfigExtension extension = this.findExtension(sequenceName);

            List<SequenceGenerator> generators = this.loadGenerators(extension);
            this.incrementGenerators(generators);

            try {
                this.persistGenerators(generators);
            } catch (OptimisticLockException ole) {
                super.getLogger().warning("A newer sequence has already been generated.");
                throw new SequenceAlreadyInUseException("A newer sequence has already been generated.", ole);
            }

            SequenceTemplateExtension template = this.loadTemplate(extension, locale);
            SequenceValue sequence = this.createSequence(generators, template);

            SequenceRs rs = new SequenceRs();
            rs.setSequence(sequence);
            return rs;

        } catch (SequenceException se) {
            throw se;
        } catch (Exception e) {
            throw new SequenceException("Error generating sequence [" + sequenceName + "].", e);
        }
    }

    /**
     * Find the sequence extension.
     * 
     * @param sequenceName
     *            the sequence extension id
     * 
     * @return the sequence extension
     * 
     * @throws ExtensionException
     *             when the extension cannot be resolved
     */
    private SequenceConfigExtension findExtension(String sequenceName) throws SequenceException {

        try {
            ExtensionResolver resolver = NabuccoSystem.getExtensionResolver();
            ExtensionMap extensions = resolver
                    .resolveExtensions(ExtensionPointType.ORG_NABUCCO_FRAMEWORK_SETUP_SEQUENCE);

            NabuccoExtension extension = extensions.getExtension(sequenceName);

            if (extension == null) {
                throw new SequenceException("No sequence extension configured for name [" + sequenceName + "].");
            }

            if (!(extension instanceof SequenceConfigExtension)) {
                throw new SequenceException("Extension '"
                        + sequenceName + "' is not of type Sequence SequenceConfigExtension.");
            }

            return (SequenceConfigExtension) extension;

        } catch (ExtensionException e) {
            throw new SequenceException(e);
        }
    }

    /**
     * Load the sequence from the database.
     * 
     * @param generatorId
     *            the sequence generator id.
     * 
     * @return the current database sequence
     * 
     * @throws SequenceException
     */
    private List<SequenceGenerator> loadGenerators(SequenceConfigExtension sequenceExtension) throws SequenceException {

        try {
            List<Name> generatorIds = new ArrayList<Name>();
            for (SequenceGeneratorExtension generatorExtension : sequenceExtension.getGenerators()) {
                generatorIds.add(new Name(generatorExtension.getId().getValue().getValue()));
            }

            NabuccoQuery<SequenceGenerator> query = super.getPersistenceManager().createQuery(QUERY);
            query.setParameter(PARAM_GENERATOR, generatorIds);

            List<SequenceGenerator> resultList = query.getResultList();
            List<SequenceGenerator> generators = new ArrayList<SequenceGenerator>();

            for (SequenceGeneratorExtension generatorExtension : sequenceExtension.getGenerators()) {
                generators.add(getGenerator(resultList, generatorExtension));
            }

            return generators;

        } catch (PersistenceException pe) {
            throw new SequenceException("Error searching for generators in sequence ["
                    + sequenceExtension.getIdentifier() + "].", pe);
        }
    }

    /**
     * Find the persistent generator for the configured extension.
     * 
     * @param generators
     *            the sequence generators
     * @param extension
     *            the configured extension
     * 
     * @return the persistent generator, or null
     * 
     * @throws SequenceException
     *             when the generator cannot be resolved
     */
    private SequenceGenerator getGenerator(List<SequenceGenerator> generators, SequenceGeneratorExtension extension)
            throws SequenceException {

        for (SequenceGenerator generator : generators) {
            if (generator.getGeneratorId().getValue().equals(extension.getId().getValue().getValue())) {
                generator.setExtension(extension);
                return generator;
            }
        }

        if (extension.getType() == null
                || extension.getType().getValue() == null || extension.getType().getValue().getValue() == null) {
            throw new SequenceException("No sequence type defined in <generator> extension.");
        }

        SequenceGenerator generator = new SequenceGenerator();
        generator.setExtension(extension);
        generator.setGeneratorId(extension.getId().getValue().getValue());
        generator.setType(SequenceType.valueOf(extension.getType().getValue().getValue()));

        return generator;
    }

    /**
     * Increment the sequence value depending on the extension configuration.
     * 
     * @param generators
     *            the sequence to increment
     * 
     * @return the incremented sequence
     * 
     * @throws SequenceException
     *             when the sequence cannot be incremented
     */
    private void incrementGenerators(List<SequenceGenerator> generators) throws SequenceException {
        for (SequenceGenerator generator : generators) {
            SequenceIncrementor.getInstance().incrementGenerator(generator);
        }
    }

    /**
     * Persist the new sequence generators to the database.
     * 
     * @param generators
     *            the sequence generators to persist.
     * 
     * @return the persisted sequence generators
     * 
     * @throws SequenceException
     *             when the sequence cannot be persisted
     * @throws OptimisticLockException
     *             when a newer sequence has already been generated
     */
    private void persistGenerators(List<SequenceGenerator> generators) throws SequenceException,
            OptimisticLockException {

        try {
            for (SequenceGenerator generator : generators) {

                switch (generator.getDatatypeState()) {

                case CONSTRUCTED:
                    generator.setDatatypeState(DatatypeState.INITIALIZED);
                    break;

                case PERSISTENT:
                    generator.setDatatypeState(DatatypeState.MODIFIED);
                    break;
                }

                super.getPersistenceManager().persist(generator);
            }

            super.getPersistenceManager().flush();

        } catch (OptimisticLockException ole) {
            throw ole;
        } catch (PersistenceException pe) {
            throw new SequenceException("Error persisting sequence to database.", pe);
        }
    }

    /**
     * Create the sequence.
     * 
     * @param generators
     *            the sequence generators
     * @param extension
     *            the extension
     * 
     * @return the template extension
     * 
     * @throws SequenceException
     *             when the template cannot be found
     */
    private SequenceTemplateExtension loadTemplate(SequenceConfigExtension extension, String locale)
            throws SequenceException {
        for (SequenceTemplateExtension templateExtension : extension.getTemplates()) {
            if (templateExtension.getLocale().getValue().getValue().equals(locale)) {
                return templateExtension;
            }
        }

        throw new SequenceException("No template defined for the locale '" + locale + "'.");
    }

    /**
     * Append the template entries to the final sequence.
     * 
     * @param generators
     *            the sequence generators
     * @param template
     *            the template extension
     * 
     * @return the sequence
     */
    private SequenceValue createSequence(List<SequenceGenerator> generators, SequenceTemplateExtension template) {

        StringBuilder sequence = new StringBuilder();

        for (SequenceTemplateEntryExtension entry : template.getEntries()) {

            if (this.isGeneratorEntry(entry)) {

                String configuredGeneratorId = entry.getGeneratorId().getValue().getValue();

                for (SequenceGenerator generator : generators) {
                    String generatorId = generator.getGeneratorId().getValue();
                    if (generatorId.equalsIgnoreCase(configuredGeneratorId)) {
                        sequence.append(generator.getValue().getValue());
                    }
                }

            } else if (this.isTextEntry(entry)) {
                sequence.append(entry.getValue().getValue().getValue());
            }
        }

        return new SequenceValue(sequence.toString());
    }

    /**
     * Check whether the configured entry is of type <generator>.
     * 
     * @param entry
     *            the entry to check
     * 
     * @return <b>true</b> if the entry is of type generator, <b>false</b> if not
     */
    private boolean isGeneratorEntry(SequenceTemplateEntryExtension entry) {
        return entry.getGeneratorId() != null
                && entry.getGeneratorId().getValue() != null && entry.getGeneratorId().getValue().getValue() != null;
    }

    /**
     * Check whether the configured entry is of type <fixed-text>.
     * 
     * @param entry
     *            the entry to check
     * 
     * @return <b>true</b> if the entry is of type fixed-text, <b>false</b> if not
     */
    private boolean isTextEntry(SequenceTemplateEntryExtension entry) {
        return entry.getValue() != null && entry.getValue().getValue() != null && entry.getValue().getValue() != null;
    }
}
