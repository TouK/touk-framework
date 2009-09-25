/*
 * Copyright (C) 2009 TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package pl.touk.framework.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author Witek Wołejszo
 */
public abstract class AnnotationAwareAspect {
	
	private final Log log = LogFactory.getLog(AnnotationAwareAspect.class);

    /**
     * Returns advised method's annotation.
     * 
     * @param joinPoint
     * @param annotationClass
     * @throws NoSuchMethodException 
     * @throws SecurityException 
     */
    protected Annotation getMethodAnnotation(ProceedingJoinPoint joinPoint, Class<? extends Annotation> annotationClass) throws SecurityException {
        Class targetClass = joinPoint.getTarget().getClass();
        Method targetMethod = ((MethodSignature) joinPoint.getSignature()).getMethod(); 
        try {
			targetMethod = targetClass.getMethod(targetMethod.getName(), targetMethod.getParameterTypes());
		} catch (NoSuchMethodException e) { }
        Annotation annotation = targetMethod.getAnnotation(annotationClass);
        return annotation;
    }

}
