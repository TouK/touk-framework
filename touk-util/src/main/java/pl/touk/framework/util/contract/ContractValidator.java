/*
 * Copyright (c) 2009 TouK
 * All rights reserved
 */
package pl.touk.framework.util.contract;

/**
 * Uset to validate method's input/output (Design By Contract style: http://en.wikipedia.org/wiki/Design_by_contract).
 * Use as a static import and validate as in the example             
 * <code>mustBeNotNull(mustBeNotEmpty(user), mustBeNotEmpty(operation), mustBeNotEmpty(data), result)</code>
 *
 * NOTICE: Whenever you want to add a method to ContractValidator, if it is possible return the original object
 * so we can chain invocations.
 * 
 * @author <a href="mailto:jnb@touk.pl">Jakub Nabrdalik</a>.
 */
//THINK: should we throw a custom exception (ContractValidationException)? Maybe only in case we have no standard exception for the situation?
public class ContractValidator {

    /**
     * Throws IllegalArgumentException if object is null
     * @throws IllegalArgumentException
     * @param object
     * @return
     */
    public static Object mustBeNotNull(Object object)    {
        if(object == null) {
            throw new IllegalArgumentException("Parameter may not be null");
        }
        
        return object;
    }

    /**
     * Throws IllegalArgumentException if string is null or it's length is zero 
     * @throws IllegalArgumentException
     * @param string
     * @return
     */
    public static String mustBeNotEmpty(String string)    {
        if(string == null || string.length() == 0) {
            throw new IllegalArgumentException("Parameter may not be empty");
        }

        return string;
    }

    /**
     * Throws IllegalArgumentException if any object from objects is null
     * @param objects
     */
    public static void mustBeNotNull(Object... objects)    {
        for (Object object : objects) {
            mustBeNotNull(object);
        }
    }


}
