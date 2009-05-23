/*
 * Copyright (C) 2009 TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package pl.touk.framework.logging.logGetters;

import org.apache.commons.logging.Log;

/**
 * Responsible for getting a Log object.
 *
 * @author <a href="mailto:jnb@touk.pl">Jakub Nabrdalik</a>.
 */
public interface LogGetterInterface {
    /**
     * Gets a Log object for given category
     * @param category Name of the category for Log object
     * @return
     */
     public Log getLog(String category);
}