package pl.touk.framework.util.convert;

import java.util.Collection;
import java.util.List;

/**
 * Interface with operations for converting domain objects to DTOs.
 * @author  Ula Trzaskowska
 */
public interface DTOConverter {
    
    /**
     * Converts source domain object into desired destination DTO class instance.
     *
     * @param sourceObject - domain class to convert
     * @param destination class - desired class name
     *
     * @return instance of destinationClass, mapped with source object properties.
     */
    public <FROM, TO> TO convert(FROM sourceObject, java.lang.Class<TO> destinationClass);

    /**
     * Converts domain objects collection into a collection of desired DTO objects.
     *
     * @param sourceObjectsCollection - collection of objects
     * @param destinationClass - desired class name, instances of which will be in the returning collection
     *
     * @return a collection of DTO objects
     */
    public <FROM extends Collection<?>, TO> List<TO> convert(FROM sourceObjectsCollection, java.lang.Class<TO> destinationClass);

    /**
     * Maps source object into destination object, without creating new instance
     *
     * @param sourceObject
     * @param destinationObject
     *
     * @return destinationObject, mapped with source objects properties.
     */
    public <FROM, TO> TO convert(FROM sourceObject, TO destinationObject);

}
