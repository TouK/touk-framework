/*
 * Copyright (c) 2009 TouK
 * All rights reserved
 */
package pl.touk.framework.logging.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Describes that a method should be logged as a business operation
 * @author <a href="mailto:jnb@touk.pl">Jakub Nabrdalik</a>.
 */
@Target(ElementType.METHOD)
public @interface LogBusinessOperationInfo {
}