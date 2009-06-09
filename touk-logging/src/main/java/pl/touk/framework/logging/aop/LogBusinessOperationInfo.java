/*
 * Copyright (C) 2009 TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package pl.touk.framework.logging.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Marks that a method invocation should be logged as a business operation.
 * @author <a href="mailto:jnb@touk.pl">Jakub Nabrdalik</a>.
 */
@Target(ElementType.METHOD)
public @interface LogBusinessOperationInfo {
}