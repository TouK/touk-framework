/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Modified by Touk 2011
 */
package pl.touk.framework.logging.log4j;

import java.io.FileNotFoundException;
import javax.servlet.ServletContext;

/**
 * Convenience class that performs custom log4j initialization for web environments,
 * allowing for log file paths within the web application, with the option to
 * perform automatic refresh checks (for runtime changes in logging configuration).
 */
abstract class Log4jWebConfigurer {

    /** Parameter specifying the location of the log4j config file */
    public static final String CONFIG_FILENAME_PARAM = "log4jConfigFileName";
    /** Parameter specifying the system property name for absolute path to directory with config file */
    public static final String CONFIG_LOCATION_PREFIX = "log4jConfigLocationSystemProperty";
    /** Parameter specifying the refresh interval for checking the log4j config file */
    public static final String REFRESH_INTERVAL_PARAM = "log4jRefreshInterval";

    /**
     * Initialize log4j, including setting the web app root system property.
     * @param servletContext the current ServletContext
     */
    public static void initLogging(ServletContext servletContext) {

        // Only perform custom log4j initialization in case of a config file.
        String filename = servletContext.getInitParameter(CONFIG_FILENAME_PARAM);
        String prefix = servletContext.getInitParameter(CONFIG_LOCATION_PREFIX);
            
        String location = System.getProperty(prefix) + '/' + filename;
        
        if (location != null) {
            // Perform actual log4j initialization; else rely on log4j's default initialization.
            try {
                // Write log message to server log.
                servletContext.log("Initializing log4j from [" + location + "]");

                // Check whether refresh interval was specified.
                String intervalString = servletContext.getInitParameter(REFRESH_INTERVAL_PARAM);
                if (intervalString != null) {
                    // Initialize with refresh interval, i.e. with log4j's watchdog thread,
                    // checking the file in the background.
                    try {
                        long refreshInterval = Long.parseLong(intervalString);
                        Log4jConfigurer.initLogging(location, refreshInterval);
                    } catch (NumberFormatException ex) {
                        throw new IllegalArgumentException("Invalid 'log4jRefreshInterval' parameter: " + ex.getMessage());
                    }
                } else {
                    throw new IllegalArgumentException("'log4jRefreshInterval' parameter cannot be null");
                }
            } catch (FileNotFoundException ex) {
                throw new IllegalArgumentException("Invalid 'log4jConfigLocation' parameter: " + ex.getMessage());
            }
        }
    }

    /**
     * Shut down log4j, properly releasing all file locks
     * and resetting the web app root system property.
     * @param servletContext the current ServletContext
     */
    public static void shutdownLogging(ServletContext servletContext) {
        servletContext.log("Shutting down log4j");
        try {
            Log4jConfigurer.shutdownLogging();
        } finally {
        }
    }
}
