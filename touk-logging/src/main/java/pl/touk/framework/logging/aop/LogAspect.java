/*
 * Copyright (c) 2009 TouK
 * All rights reserved
 */
package pl.touk.framework.logging.aop;

import org.apache.commons.logging.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import pl.touk.framework.logging.logGetters.PointcutLogGetterInterface;
import pl.touk.framework.logging.messages.BusinessMessage;
import pl.touk.wonderfulsecurity.beans.WsecUser;
import pl.touk.wonderfulsecurity.core.IServerSecurityContext;

/**
 * Aspect (in AOP sense) responsible for logging.
 * Whenever you want to log anything, use this aspect. Do not use logging (log4j, commons.logging) directly.
 *
 * @author witek wolejszo
 * @author <a href="mailto:jnb@touk.pl">Jakub Nabrdalik</a>.
 */
//TODO: log security context information if available
@Aspect
public class LogAspect {
    protected IServerSecurityContext serverSecurityContext;
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
	@Before(value = "@annotation(pl.touk.ewka.core.logging.aop.LogMethodEntranceInfo)")
	public void logMethodEntranceInfo(JoinPoint joinPoint) {
		Log log = getLogGetter().getLog(joinPoint);

		if (log.isInfoEnabled()) {
			log.info("entering: " + joinPoint.getSignature().getName());
			for (Object arg : joinPoint.getArgs()) {
				log.info("   w/arg: " + arg.toString());
			}
			log.info("      at: " + joinPoint.getSourceLocation().getWithinType());
		}

	}

    /**
     * Logs exit from annotated method.
     * @param joinPoint JoinPoint automatically filled by aspectj
     */
	@After(value = "@annotation(pl.touk.ewka.core.logging.aop.LogMethodExitInfo)")
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
    @After(value = "@annotation(pl.touk.ewka.core.logging.aop.LogBusinessOperationInfo)")
    public void logBusinessOperationExitInfo(JoinPoint joinPoint) {
        this.logBusinessMessage(joinPoint, true);
    }

    /**
     * Logs a failed exit (due to an exception) from a business method
     * Requires a logged in user
     * @param joinPoint JoinPoint automatically filled by aspectj
     */
    @AfterThrowing(value = "@annotation(pl.touk.ewka.core.logging.aop.LogBusinessOperationInfo)")
    public void logBusinessOperationErrorInfo(JoinPoint joinPoint) {
            this.logBusinessMessage(joinPoint, false);
    }


    protected void logBusinessMessage(JoinPoint joinPoint, boolean result)
    {
        Log log = getLogGetter().getLog(this.getBusinessLogCategory());

        if (log.isInfoEnabled()) {
            WsecUser user = this.getServerSecurityContext().getLoggedInUser();
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

            BusinessMessage message = new BusinessMessage(user.getFullName(), joinPoint.getSignature().getName(), data.toString(), result);
            log.info(message);
        }
    }

    //setters and getters

    protected IServerSecurityContext getServerSecurityContext() {
        return serverSecurityContext;
    }

    public void setServerSecurityContext(IServerSecurityContext serverSecurityContext) {
        this.serverSecurityContext = serverSecurityContext;
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