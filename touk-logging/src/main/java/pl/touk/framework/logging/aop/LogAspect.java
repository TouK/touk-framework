/*
 * Copyright (C) 2009 TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package pl.touk.framework.logging.aop;

import java.util.Collection;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import pl.touk.framework.logging.logGetters.PointcutLogGetterInterface;
import pl.touk.framework.logging.logGetters.SignatureDeclaringTypeLogGetter;

import pl.touk.security.context.SecurityContextInterface;

/**
 * Aspect (in AOP sense) responsible for logging.
 * Whenever you want to log anything, use this aspect. Do not use logging (log4j, commons.logging) directly.
 *
 * @author witek wolejszo
 * @author <a href="mailto:jnb@touk.pl">Jakub Nabrdalik</a>.
 */
@Aspect
public class LogAspect {
	
	//private final Log log = LogFactory.getLog(LogAspect.class);

    protected SecurityContextInterface securityContext;
    
    //Default implementation, useful when LogAspect is not instantiated in Spring
    protected PointcutLogGetterInterface logGetter = new SignatureDeclaringTypeLogGetter();

    public LogAspect() {
    }

    /**
     * Logs entrance to annotated method.
     *
     * @param joinPoint JoinPoint automatically filled by aspectj
     */
    @Before(value = "@annotation(pl.touk.framework.logging.aop.LogMethodEntranceInfo)")
    public void logMethodEntranceInfo(JoinPoint joinPoint) {
        Log log = this.logGetter.getLog(joinPoint);

        if (log.isInfoEnabled()) {
        	
    		StringBuilder sb = new StringBuilder();
			
			sb.append("entering  ---------------------\n");
			sb.append("  method: ").append(joinPoint.getSignature().getName()).append("\n");
			sb.append("      at: ").append(joinPoint.getSourceLocation().getWithinType()).append("\n");

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

                sb.append("   w/arg: ").append(argStringValue).append("\n");
            }
            
            sb.append("         ---------------------");
			log.info(sb.toString());
        }

    }
    
    @Around(value = "@annotation(logInvocation)")
    public Object logMethodInvocation(ProceedingJoinPoint joinPoint, LogInvocation logInvocation) throws Throwable {
        
        LogLevel level = logInvocation.value();
        
        Log log = this.logGetter.getLog(joinPoint);
        StringBuilder sb = new StringBuilder();
        
        //logging entry
        if (isLevelEnabled(log, level)) {
			sb.append("entering  ---------------------\n");
			sb.append("  method: ").append(joinPoint.getSignature().getName()).append("\n");
			sb.append("      at: ").append(joinPoint.getSourceLocation().getWithinType()).append("\n");
	
	        String argStringValue = "";
	        for (Object arg : joinPoint.getArgs()) {
	            if (arg == null) {
	                argStringValue = "NULL";
	            } else if (arg instanceof Collection) {
	                argStringValue = buildStringValue((Collection) arg);
	            } else {
	                argStringValue = buildStringValue(arg);
	            }
	
	            sb.append("   w/arg: ").append(argStringValue).append("\n");
	        }        
	        sb.append("         ---------------------");
	        
	        log(level, log, sb.toString());
        }
        
        Object result = null;
        
        //invocation
        try {
        	
        	result = joinPoint.proceed();
        	
        } catch (Throwable throwable) {
        	
        	if (log.isErrorEnabled()) {
    			
    			sb = new StringBuilder();
    			sb.append("exception  ---------------------\n");
    			sb.append("   method: ").append(joinPoint.getSignature().getName()).append("\n");
    			sb.append("       at: ").append(joinPoint.getSourceLocation().getWithinType()).append("\n");
    			sb.append("  message: ").append(throwable.getMessage()).append("\n");
    			sb.append("           ---------------------");
    			
    			log.error(sb.toString(), throwable);
    		}        	
        	throw throwable;
        }
        
        //logging leaving
        if (isLevelEnabled(log, level)) {
	        sb = new StringBuilder();
			sb.append("leaving  ---------------------\n");
			sb.append(" method: ").append(joinPoint.getSignature().getName()).append("\n");
			sb.append("     at: ").append(joinPoint.getSourceLocation().getWithinType()).append("\n");
			sb.append("  value: ").append(result).append("\n");
			sb.append("         ---------------------");
			
			log(level, log, sb.toString());
        }
        
        return result;
    }

    private boolean isLevelEnabled(Log log, LogLevel level) {
    	switch (level) {
			case TRACE:
				return log.isTraceEnabled();
			case DEBUG:
				return log.isDebugEnabled();
			case INFO:
				return log.isInfoEnabled();
			case WARNING:
				return log.isInfoEnabled();
			case ERROR:
				return log.isErrorEnabled();
			case FATAL:
				return log.isFatalEnabled();
		    default:
		    	return false;
		}    
	}

	private void log(LogLevel level, Log log, String string) {
        
    	switch (level) {
    		case TRACE:
    			log.trace(string);
    			break;
    		case DEBUG:
    			log.debug(string);
    			break;
    		case INFO:
    			log.info(string);
    			break;
    		case WARNING:
    			log.warn(string);
    			break;
    		case ERROR:
    			log.error(string);
    			break;    			
    	}    
	}

	/**
     * Logs exit from annotated method.
     * @param joinPoint JoinPoint automatically filled by aspectj
     */
	@AfterReturning(value = "@annotation(pl.touk.framework.logging.aop.LogMethodExitInfo)", returning = "retValue")
	public void logMethodExitInfo(JoinPoint joinPoint, Object retValue) {
		Log log = this.logGetter.getLog(joinPoint);

		if (log.isInfoEnabled()) {
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("leaving  ---------------------\n");
			sb.append(" method: ").append(joinPoint.getSignature().getName()).append("\n");
			sb.append("     at: ").append(joinPoint.getSourceLocation().getWithinType()).append("\n");
			sb.append("  value: ").append(retValue).append("\n");
			sb.append("         ---------------------");
			
			log.info(sb.toString());
		}

	}

    /**
     * Logs method exception.
     * @param joinPoint JoinPoint automatically filled by aspectj
     */
	@AfterThrowing(value = "@annotation(pl.touk.framework.logging.aop.LogMethodExceptionError)", throwing = "throwable")
	public void logMethodExceptionError(JoinPoint joinPoint, Throwable throwable) {
		Log log = this.logGetter.getLog(joinPoint);

		if (log.isErrorEnabled()) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("exception  ---------------------\n");
			sb.append("   method: ").append(joinPoint.getSignature().getName()).append("\n");
			sb.append("       at: ").append(joinPoint.getSourceLocation().getWithinType()).append("\n");
			sb.append("  message: ").append(throwable.getMessage()).append("\n");
			sb.append("           ---------------------");
			
			log.error(sb.toString(), throwable);
		}

	}
	
    protected String buildStringValue(Object arg) {
        String packageName = arg.getClass().getPackage().getName();

        if ("java.lang".equals(packageName) || "java.math".equals(packageName)) {
            return arg.getClass().getName() + ": " + arg.toString();
        }
        
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

    //setters and getters

    public void setSecurityContext(SecurityContextInterface securityContext) {
        this.securityContext = securityContext;
    }

    public void setLogGetter(PointcutLogGetterInterface logGetter) {
        this.logGetter = logGetter;
    }

}