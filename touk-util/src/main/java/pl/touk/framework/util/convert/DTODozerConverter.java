/*
 * Copyright (c) (2005 - )2009 TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package pl.touk.framework.util.convert;

import org.dozer.Mapper;
import org.dozer.DozerBeanMapper;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

/**
 * @author <a href="mailto:jkr@touk.pl">Jakub Kurlenda</a>
 * @author <a href="mailto:jnb@touk.pl">Jakub Nabrdalik </a>
 * 
 */

public class DTOConverter {
    private static final Mapper beanMapper = new DozerBeanMapper();

    /*
     * Converts source domain object into desired destination DTO class instance.
     *
     * @param sourceObject - domain class to convert
     * @param destination class - desired class name
     *
     * @return instance of destinationClass, mapped with source object properties.
     */

    public static <FROM, TO> TO convert(FROM sourceObject, java.lang.Class<TO> destinationClass) {
        return beanMapper.map(sourceObject, destinationClass);
    }

    /*
     * Converts domain objects collection into a collection of desired DTO objects.
     *
     * @param sourceObjects - domain class collection
     * @param destinationClass - desired class name, instances of which will be in the returning collection
     *
     * @return a collection of DTO objects
     */

    public static <FROM extends Collection, TO> List<TO> convert(FROM sourceObjects, java.lang.Class<TO> destinationClass) {
        List<TO> returnedList = new ArrayList<TO>();

        for (Object sourceObject : sourceObjects) {
            TO destinationDTO = beanMapper.map(sourceObject, destinationClass);
            returnedList.add(destinationDTO);
        }

        return returnedList;
    }

    /*
     * Maps source object into destination object, without creating new instance
     *
     * @param sourceObject
     * @param destinationObject
     *
     * @return destinationObject, mapped with source objects properties.
     */

    public static <FROM, TO> TO convert(FROM sourceObject, TO destinationObject) {
        beanMapper.map(sourceObject, destinationObject);
        return destinationObject;
    }
}
