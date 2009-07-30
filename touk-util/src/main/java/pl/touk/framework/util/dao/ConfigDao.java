/*
 * Copyright (C) 2009 TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package pl.touk.framework.util.dao;

/**
 * Configuration dao.
 *
 * @author Mateusz Lipczyński
 */
public interface ConfigDao {

    /**
     * Sets a property.
     *
     * @param key    property name
     * @param value  property value
     */
    void setProperty(String key, String value);

    /**
     * Gets a property value.
     *
     * @param key   property name
     * @return      property value
     */
    String getProperty(String key);
}