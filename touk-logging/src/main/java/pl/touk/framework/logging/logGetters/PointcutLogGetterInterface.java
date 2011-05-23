/*
 * Copyright (C) 2009 TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package pl.touk.framework.logging.logGetters;

import org.apache.commons.logging.Log;
import org.aspectj.lang.JoinPoint;

/**
 * Responsible for getting a Log object from a JoinPoint.
 */
public interface PointcutLogGetterInterface extends LogGetterInterface {

    /**
     * Gets a Log object for given JoinPoint
     * @param joinPoint
     * @return
     */
    Log getLog(JoinPoint joinPoint);
}