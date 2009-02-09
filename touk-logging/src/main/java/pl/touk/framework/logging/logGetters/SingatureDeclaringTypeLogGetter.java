/*
 * Copyright (c) 2009 TouK
 * All rights reserved
 */
package pl.touk.framework.logging.logGetters;

import org.apache.commons.logging.Log;
import org.aspectj.lang.JoinPoint;

/**
 * LogGetter that returns a Log object for an advised method
 * @author <a href="mailto:jnb@touk.pl">Jakub Nabrdalik</a>.
 */
public class SingatureDeclaringTypeLogGetter implements PointcutLogGetterInterface {

    /**
     * Gets a Log for the category created from advised method class full name (with package)
     * @param joinPoint
     * @return
     */
    public Log getLog(JoinPoint joinPoint) {
        return org.apache.commons.logging.LogFactory.getLog(joinPoint.getSignature().getDeclaringType());
    }

    /**
     * Gets a Log for the category given as a String
     * @param category Name of the category for Log object
     * @return
     */
    public Log getLog(String category) {
        return org.apache.commons.logging.LogFactory.getLog(category);
    }
}