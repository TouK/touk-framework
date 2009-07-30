/*
 * Copyright (C) 2009 TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package pl.touk.framework.util.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * Configuration manager.
 *
 * @author <a href="mailto:mlp@touk.pl">Mateusz Lipczyński</a>
 */
@Repository
public class ConfigDaoImpl extends JdbcDaoSupport implements ConfigDao {

    public void setProperty(String key, String value) {
        this.getJdbcTemplate().update("update configuration set value = ? where key = ?", new Object[]{key, value});
    }

    public String getProperty(String key) {
        String value = (String) this.getJdbcTemplate().queryForObject("select value from configuration where key = ?", new Object[]{key}, String.class);
        return value;
    }

}