/*
 * Copyright (C) 2009 TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package pl.touk.framework.logging.aop;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Marks that a method exit should be logged.
 * @author Witek Wolejszo
 */
@Target(ElementType.METHOD)
@Deprecated
public @interface LogMethodExitInfo {

}