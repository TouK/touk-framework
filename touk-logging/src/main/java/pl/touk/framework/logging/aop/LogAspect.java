/*
 * Copyright (C) 2009 TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package pl.touk.framework.logging.aop;

import org.apache.commons.logging.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.userdetails.UserDetails;
import pl.touk.framework.logging.logGetters.PointcutLogGetterInterface;
import pl.touk.framework.logging.messages.BusinessMessage;
import pl.touk.security.context.SecurityContextInterface;

/**
 * Aspect (in AOP sense) responsible for logging.
 * Whenever you want to log anything, use this aspect. Do not use logging (log4j, commons.logging) directly.
 *
 * @author witek wolejszo
 * @author <a href="mailto:jnb@touk.pl">Jakub Nabrdalik</a>.
 * TODO: log security context information if available
 */
@Aspect
public class LogAspect {

    protected SecurityContextInterface securityContext;
    protected PointcutLogGetterInterface logGetter;

    /**
     * Category under witch a logger is going to log business messages
     */
    protected String businessLogCategory = "businessLog";

    public LogAspect() {
	}

    /**
     * Logs entrance to annotated method.
     * @param joinPoint JoinPoint automatically filled by aspectj
     */
	@Before(value = "@annotation(pl.touk.framework.logging.aop.LogMethodEntranceInfo)")
	public void logMethodEntranceInfo(JoinPoint joinPoint) {
		Log log = getLogGetter().getLog(joinPoint);

		if (log.isInfoEnabled()) {
			log.info("entering: " + joinPoint.getSignature().getName());

            String argStringValue;
			for (Object arg : joinPoint.getArgs()) {
                argStringValue = (arg == null) ? "NULL" : arg.toString();
				log.info("   w/arg: " + argStringValue);
			}
			log.info("      at: " + joinPoint.getSourceLocation().getWithinType());
		}

	}

    /**
     * Logs exit from annotated method.
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

    /**
     * Logs exit from a business method
     * Requires a logged in user
     * @param joinPoint JoinPoint automatically filled by aspectj
     */
    @After(value = "@annotation(pl.touk.framework.logging.aop.LogBusinessOperationInfo)")
    public void logBusinessOperationExitInfo(JoinPoint joinPoint) {
        this.logBusinessMessage(joinPoint, true);
    }

    /**
     * Logs a failed exit (due to an exception) from a business method
     * Requires a logged in user
     * @param joinPoint JoinPoint automatically filled by aspectj
     */
    @AfterThrowing(value = "@annotation(pl.touk.framework.logging.aop.LogBusinessOperationInfo)")
    public void logBusinessOperationErrorInfo(JoinPoint joinPoint) {
            this.logBusinessMessage(joinPoint, false);
    }


    protected void logBusinessMessage(JoinPoint joinPoint, boolean result)
    {
        Log log = getLogGetter().getLog(this.getBusinessLogCategory());

        if (log.isInfoEnabled()) {
            UserDetails user = this.getSecurityContext().getLoggedInUser();
            if(user == null) {
                throw new SecurityException("User not logged in. Logged user required for method " + joinPoint.getSignature().getName());
            }

            StringBuffer data = new StringBuffer();
            Object[] arguments =  joinPoint.getArgs();
            for (int i = 0; i < arguments.length; i++) {
                if( arguments[i] != null)
                {
                    data.append(arguments[i].toString());
                }
            }

            BusinessMessage message = new BusinessMessage(user.getUsername(), joinPoint.getSignature().getName(), data.toString(), result);
            log.info(message);
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

    protected String getBusinessLogCategory() {
        return businessLogCategory;
    }

    public void setBusinessLogCategory(String businessLogCategory) {
        this.businessLogCategory = businessLogCategory;
    }
}