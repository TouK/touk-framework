/*
 * Copyright (c) (2005 - 2009) TouK sp. z o.o. s.k.a.
 * All rights reserved
 */
package pl.touk.framework.delay;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks that the invocation of a method should be postponed (delayed) by given amount of miliseconds.
 * @author Jakub Nabrdalik 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Delay {
    long value() default 0;
    
}
