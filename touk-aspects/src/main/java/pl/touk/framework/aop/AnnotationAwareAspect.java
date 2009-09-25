/*
 * Copyright (C) 2009 TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package pl.touk.framework.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author Witek Wolejszo
 */
public abstract class AnnotationAwareAspect {

    /**
     * Returns advised method's annotation.
     * 
     * @param joinPoint
     * @param annotationClass
     * @throws NoSuchMethodException 
     * @throws SecurityException 
     */
    protected Annotation getMethodAnnotation(ProceedingJoinPoint joinPoint, Class<? extends Annotation> annotationClass) throws SecurityException, NoSuchMethodException {
        
        Class targetClass = joinPoint.getTarget().getClass();
        
        Class[] classArray = new Class[joinPoint.getArgs().length];
        int i = 0;
        for (Object o : joinPoint.getArgs()) {
            classArray[i++] = o.getClass();
        }
        
        //look for annotation
        Method targetMethod = targetClass.getDeclaredMethod(joinPoint.getSignature().getName(), classArray);
        Annotation annotation = targetMethod.getAnnotation(annotationClass);
        
        return annotation;
    }

}
