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

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Convenience class that features simple methods for custom log4j configuration.
 */
abstract class Log4jConfigurer {

	/** Extension that indicates a log4j XML config file: ".xml" */
	public static final String XML_FILE_EXTENSION = ".xml";


	/**
     * TODO: review comment
     * 
	 * Initialize log4j from the given location, with the given refresh interval
	 * for the config file. Assumes an XML file in case of a ".xml" file extension,
	 * and a properties file otherwise.
	 * <p>Log4j's watchdog thread will asynchronously check whether the timestamp
	 * of the config file has changed, using the given interval between checks.
	 * A refresh interval of 1000 milliseconds (one second), which allows to
	 * do on-demand log level changes with immediate effect, is not unfeasible.
	 * <p><b>WARNING:</b> Log4j's watchdog thread does not terminate until VM shutdown;
	 * in particular, it does not terminate on LogManager shutdown. Therefore, it is
	 * recommended to <i>not</i> use config file refreshing in a production J2EE
	 * environment; the watchdog thread would not stop on application shutdown there.
	 * @param location the location of the config file: either a "classpath:" location
	 * (e.g. "classpath:myLog4j.properties"), an absolute file URL
	 * (e.g. "file:C:/log4j.properties), or a plain absolute path in the file system
	 * (e.g. "C:/log4j.properties")
	 * @param refreshInterval interval between config file refresh checks, in milliseconds
	 * @throws FileNotFoundException if the location specifies an invalid file path
	 */
	public static void initLogging(String location, long refreshInterval) throws FileNotFoundException {
		File file = new File(location);
		if (!file.exists()) {
			throw new FileNotFoundException("Log4j config file [" + location + "] not found");
		}
		if (location.toLowerCase().endsWith(XML_FILE_EXTENSION)) {
			DOMConfigurator.configureAndWatch(file.getAbsolutePath(), refreshInterval);
		}
		else {
			PropertyConfigurator.configureAndWatch(file.getAbsolutePath(), refreshInterval);
		}
	}

	/**
	 * Shut down log4j, properly releasing all file locks.
	 * <p>This isn't strictly necessary, but recommended for shutting down
	 * log4j in a scenario where the host VM stays alive (for example, when
	 * shutting down an application in a J2EE environment).
	 */
	public static void shutdownLogging() {
		LogManager.shutdown();
	}

}
