
package org.xmlblackbox.test.functional.examples.v24;

import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;
import org.xmlblackbox.test.infrastructure.FlowControl;
import org.xmlblackbox.test.infrastructure.exception.TestException;
import org.xmlblackbox.test.util.Configurator;

/**
 *
 * @author Crea
 */
public class ExampleVersion24_003 extends FlowControl{
    private final static Logger log = Logger.getLogger(ExampleVersion24_003.class);

    public ExampleVersion24_003(String msg) {
        super(msg);
    }

    public static Test suite() {
        TestSuite suite= new TestSuite(ExampleVersion24_003.class);
        return suite;
    }

    public void setUp() throws Exception {
    	super.setUp();
    }

    public void tearDown() throws Exception {
    }

    public void testExecute() {
       	try {

            Properties fileProperties = Configurator.getProperties();
            fileProperties.put("RESET_SESSION", "false");
            log.info("testExecute");
            Configurator.configureLog4J();
            execute(this.getClass(),fileProperties);
            log.info("stop execute");
   		} catch (TestException e) {
   			log.error("Exception ", e.getContainedException());
   			fail("Exception "+e);
   		} catch (Exception e) {
   			log.error("Exception ", e);
   			fail("Exception "+e.getMessage());
   		}
   	}

}
