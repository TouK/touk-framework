package touk;

import javax.servlet.ServletContext;

import java.io.*;

import java.net.URL;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import org.apache.log4j.xml.DOMConfigurator;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 *
 * Enable commons logger driven log4j appender logging in WLS 10
 *
 * Add this to the web.xml of the application.
 *
 * <pre>
 * &lt;context-param&gt;
 *           &lt;param-name&gt;Log4JDOMConfiguation&lt;/param-name&gt;
 *           &lt;param-value&gt;classpath:/resource-location/log4j.xml&lt;/param-value&gt;
 *   &lt;/context-param&gt;
 *
 *   &lt;listener&gt;
 *       &lt;listener-class&gt;WebLogicLoggingConfiguration&lt;/listener-class&gt;
 *   &lt;/listener&gt;
 * </pre>
 *
 * *Note* param value is a URL that understands classpath: resource locators
 *
 * And put the following jars in base_domain/lib
 *
 * com.bea.core.apache.commons.logging_1.1.0.jar
 * com.bea.core.weblogic.commons.logging_1.0.0.0.jar
 * log4j-1.2.15.jar
 * wllog4j.jar
 *
 * Add this to your pom.xml of the application
 *
 * <pre>
 *       &lt;dependency&gt;
 *           &lt;groupId&gt;com.bea.core&lt;/groupId&gt;
 *           &lt;artifactId&gt;wllog4j&lt;/artifactId&gt;
 *           &lt;version&gt;1.0&lt;/version&gt;
 *           &lt;scope&gt;provided&lt;/scope&gt;
 *       &lt;/dependency&gt;
 *       &lt;dependency&gt;
 *           &lt;groupId&gt;com.bea.core&lt;/groupId&gt;
 *           &lt;artifactId&gt;wls-api&lt;/artifactId&gt;
 *           &lt;version&gt;1.0&lt;/version&gt;
 *           &lt;scope&gt;provided&lt;/scope&gt;
 *       &lt;/dependency&gt;
 * </pre>
 *
 * @see{http://e-docs.bea.com/wls/docs100/logging/config_logs.html}
 * 
 */
public class WebLogicLoggingConfiguration implements ServletContextListener{

    public static final String log4j_dtd = "org/apache/log4j/xml/log4j.dtd";
    private ServletContext context;

    public void configureLog4JLogging() {
        try {
            Logger logger = weblogic.logging.log4j.Log4jLoggingHelper.getLog4jServerLogger();
            
            String configPath = context.getInitParameter("Log4JDOMConfiguation");

            InputStream in = null;

            if(configPath.startsWith("classpath:")) {

                URL resourceURL = WebLogicLoggingConfiguration.class.getClassLoader().getResource(configPath.substring(10));

                if (null == resourceURL){
                    System.out.println("Log4J configuration resource NOT found: " + configPath);
                } else {
                    System.out.println("Log4J configuration resource found: " + resourceURL);
                }

                in = WebLogicLoggingConfiguration.class.getClassLoader().getResourceAsStream(configPath.substring(10));
            } else {
                URL url = new URL(configPath);
                if(!new File(url.getFile()).exists()) {
                    System.out.println("Log4J configuration URL file NOT found: " + configPath);
                }
                
                in = url.openStream();
            }
            
            if (null==in){
                System.out.println("Log4J configuration file NOT found: " + configPath);
                return ;
            } else {
                System.out.println("Log4J configuration file found: " + configPath);
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            DocumentBuilder builder = factory.newDocumentBuilder();

            builder.setEntityResolver(new EntityResolver() {
                    public InputSource resolveEntity (String publicId, String systemId)
                    {
                        if ("-//Apache Software Foundation//DTD Log4j Configuration 1.2//EN".equals(publicId)) {
                            InputStream in = DOMConfigurator.class.getClassLoader().getResourceAsStream(log4j_dtd);

                            if (null==in){
                                System.out.println("Log4J dtd file not found: " + log4j_dtd);
                                return null;
                            } else {
                                System.out.println("Log4J dtd file found: " + log4j_dtd);
                            }

                            return new InputSource(in);
                        } else {
                            System.out.println("Sorry I only know about <!DOCTYPE log4j:configuration PUBLIC '-//Apache Software Foundation//DTD Log4j Configuration 1.2//EN' 'log4j.dtd'>"); 
                        }
                        return null;
                    }
                    });

            Document document = builder.parse(in);

            DOMConfigurator.configure(document.getDocumentElement());

        } catch(Exception x) {
            x.printStackTrace(System.out);
        } catch(NoClassDefFoundError x) {
            //not running with weblogic classloader
            x.printStackTrace(System.out);
        }
    }

    public void testLog4J(){
        Logger logger = Logger.getLogger("pl.touk");
        //logger.addAppender(new org.apache.log4j.ConsoleAppender()); // The Appender is configured using either the log4j props file or other custom mechanism.
        logger.info("***************** LOG 4J INFO TEST ******************");
        logger.debug("***************** LOG 4J DEBUG TEST ******************");
     
        /*
         Logger dbLogger = Logger.getLogger("businessLog");
        dbLogger.info(new BusinessMessage("me", "doing it", "something", false));
         */
    }

    public void testLogCommons() {
        try {
            Log clog = LogFactory.getFactory().getInstance("pl.touk");
            Log dblog = LogFactory.getFactory().getInstance("businessLog");
            
            // Log String objects
            clog.info("%%%%%%%%%%%%%%%%%%% COMMONS INFO TEST %%%%%%%%%%%%%%%%%");
            clog.warn("%%%%%%%%%%%%%%%%%%% COMMONS WARN TEST %%%%%%%%%%%%%%%%%");
            
            //dblog.warn(new BusinessMessage("me", "doing it", "commons", false));

        } catch(Exception x) {
            x.printStackTrace(System.out);
        }
    }

    /*
    @LogMethodEntranceInfo
    @LogMethodExitInfo
    @LogBusinessOperationInfo
    public void testAspectLogging(){
    }
    */

    public void contextInitialized(ServletContextEvent event) {
        try{
            System.out.println("Configuring logging for weblogic " + WebLogicLoggingConfiguration.class.getName());
            this.context = event.getServletContext();
            configureLog4JLogging();
            testLog4J() ;
            testLogCommons() ;
            //testAspectLogging();
        }catch(Exception x){
            x.printStackTrace(System.out);
        }
    }

    public void contextDestroyed(ServletContextEvent event) {
        //Do we need to Unconfigure the logging - ? NO!
    }
}
