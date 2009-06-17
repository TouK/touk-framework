/*
 * Copyright (C) 2009 TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package pl.touk.framework.logging.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import pl.touk.framework.logging.logGetters.PointcutLogGetterInterface;
import pl.touk.security.context.SecurityContextInterface;

import java.util.Collection;

/**
 * Aspect (in AOP sense) responsible for logging.
 * Whenever you want to log anything, use this aspect. Do not use logging (log4j, commons.logging) directly.
 *
 * @author witek wolejszo
 * @author <a href="mailto:jnb@touk.pl">Jakub Nabrdalik</a>.
 */
@Aspect
public class LogAspect {

    protected SecurityContextInterface securityContext;
    protected PointcutLogGetterInterface logGetter;

    public LogAspect() {
    }

    /**
     * Logs entrance to annotated method.
     *
     * @param joinPoint JoinPoint automatically filled by aspectj
     */
    @Before(value = "@annotation(pl.touk.framework.logging.aop.LogMethodEntranceInfo)")
    public void logMethodEntranceInfo(JoinPoint joinPoint) {
        Log log = getLogGetter().getLog(joinPoint);

        if (log.isInfoEnabled()) {
            log.info("entering: " + joinPoint.getSignature().getName());

            String argStringValue = "";
            for (Object arg : joinPoint.getArgs()) {
                // TODO: dekompozycja do obiektow, parametr adnotacji powinien to definiować.
                if (arg == null) {

                    argStringValue = "NULL";
                } else if (arg instanceof Collection) {

                    argStringValue = buildStringValue((Collection) arg);

                } else {
                    argStringValue = buildStringValue(arg);
                }

                log.info("   w/arg: " + argStringValue);
            }
            log.info("      at: " + joinPoint.getSourceLocation().getWithinType());
        }

    }

    protected String buildStringValue(Object arg) {
        return new ReflectionToStringBuilder(arg, ToStringStyle.MULTI_LINE_STYLE).toString();
    }

    protected String buildStringValue(Collection arg) {
        String argStringValue = arg.getClass().getSimpleName() + " [ ";

        Collection collectionToIterate = (Collection) arg;
        for (Object o : collectionToIterate) {
            argStringValue += new ReflectionToStringBuilder(o, ToStringStyle.MULTI_LINE_STYLE).toString();
        }

        argStringValue += " ] \n";
        return argStringValue;
    }

    /**
     * Logs exit from annotated method.
     *
     * @param joinPoint JoinPoint automatically filled by aspectj
     */
    @After(value = "@annotation(pl.touk.framework.logging.aop.LogMethodExitInfo)")
    public void logMethodExitInfo(JoinPoint joinPoint) {
        Log log = getLogGetter().getLog(joinPoint);

        if (log.isInfoEnabled()) {
            log.info("leaving: " + joinPoint.getSignature().getName());
            //TODO log return value
            log.info("      at: " + joinPoint.getSourceLocation().getWithinType());
        }

    }

    //setters and getters

    protected SecurityContextInterface getSecurityContext() {
        return securityContext;
    }

    public void setSecurityContext(SecurityContextInterface securityContext) {
        this.securityContext = securityContext;
    }

    protected PointcutLogGetterInterface getLogGetter() {
        return logGetter;
    }

    public void setLogGetter(PointcutLogGetterInterface logGetter) {
        this.logGetter = logGetter;
    }

}