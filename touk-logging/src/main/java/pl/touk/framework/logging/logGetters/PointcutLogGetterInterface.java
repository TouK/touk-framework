/*
 * Copyright (C) 2009 TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package pl.touk.framework.logging.logGetters;

import org.apache.commons.logging.Log;
import org.aspectj.lang.JoinPoint;

/**
 * Responsible for getting a Log object from a JoinPoint.
 *
 * @author <a href="mailto:jnb@touk.pl">Jakub Nabrdalik</a>.
 */
public interface PointcutLogGetterInterface extends LogGetterInterface {
    /**
     * Gets a Log object for given JoinPoint
     * @param joinPoint
     * @return
     */
    public Log getLog(JoinPoint joinPoint);
}