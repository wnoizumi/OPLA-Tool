package br.otimizes.oplatool.architecture.papyrus.touml;

import org.apache.log4j.Logger;

/**
 * Document
 *
 * @author edipofederle<edipofederle @ gmail.com>
 */
public class Document {
    private static final Logger LOGGER = Logger.getLogger(Document.class);

    public static void executeTransformation(DocumentManager documentManager,
                                             Transformation transformation) {
        try {
            LOGGER.info("executeTransformation()");
            transformation.useTransformation();
        } finally {
            LOGGER.info("saveAndCopy()");
            documentManager.saveAndCopy(documentManager.getNewModelName());
        }
    }

}