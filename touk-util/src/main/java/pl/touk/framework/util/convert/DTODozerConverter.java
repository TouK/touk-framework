/*
 * Copyright (C) 2009 TouK sp. z o.o. s.k.a.
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
 * @author <a href="mailto:utr@touk.pl">Ula Trzaskowska </a>
 */
public class DTODozerConverter implements DTOConverter {

    private Mapper beanMapper = new DozerBeanMapper();
    
    /**
     * Constructor for DTOConverter, setting beanMapper.
     *
     * @param beanMapper Dozer bean mapper to set
     */
    public DTODozerConverter(Mapper beanMapper) {
        this.beanMapper = beanMapper;
    }

    /**
     * Converts source domain object into desired destination DTO class instance.
     *
     * @param sourceObject - domain class to convert
     * @param destinationClass - desired class name
     *
     * @return instance of destinationClass, mapped with source object properties.
     */
    public <FROM, TO> TO convert(FROM sourceObject, java.lang.Class<TO> destinationClass) {
        if(sourceObject == null) {
            return null;
        } else {
            return beanMapper.map(sourceObject, destinationClass);
        }
    }

    /**
     * Converts domain objects collection into a collection of desired DTO objects.
     *
     * @param sourceObjects - domain class collection
     * @param destinationClass - desired class name, instances of which will be in the returning collection
     * @return a collection of DTO objects
     */
    @SuppressWarnings("unchecked")
    public <FROM extends Collection<?>, TO> List<TO> convert(FROM sourceObjectsCollection, java.lang.Class<TO> destinationClass) {
        if(sourceObjectsCollection == null) {
            return null;
        } else {
            List<TO> returnedList = new ArrayList<TO>();

            for (Object sourceObject : sourceObjectsCollection) {
                TO destinationDTO = beanMapper.map(sourceObject, destinationClass);
                returnedList.add(destinationDTO);
            }

            return returnedList;
        }
    }

    /**
     * Maps source object into destination object, without creating new instance.
     *
     * @param sourceObject
     * @param destinationObject
     */
    public <FROM, TO> TO convert(FROM sourceObject, TO destinationObject) {
        if (sourceObject == null) {
            return null;
        } else {
        	beanMapper.map(sourceObject, destinationObject);
        	return destinationObject;
        }
    }

    /**
     * Sets {@link DozerBeanMapper}.
     *
     * @param beanMapper
     */
    public void setBeanMapper(Mapper beanMapper) {
        this.beanMapper = beanMapper;
    }
    
}
