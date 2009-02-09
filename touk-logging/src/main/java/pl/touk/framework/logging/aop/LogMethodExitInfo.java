/*
 * Copyright (c) 2009 TouK
 * All rights reserved
 */
package pl.touk.framework.logging.aop;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Describes that a method exit should be logged
 * @author Witek Wolejszo
 */
@Target(ElementType.METHOD)
public @interface LogMethodExitInfo {

}