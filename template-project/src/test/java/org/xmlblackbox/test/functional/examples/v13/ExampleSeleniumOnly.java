
package org.xmlblackbox.test.functional.examples.v13;

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
public class ExampleSeleniumOnly extends FlowControl{
    private final static Logger log = Logger.getLogger(ExampleSeleniumOnly.class);

    public ExampleSeleniumOnly(String msg) {
        super(msg);
    }

    public static Test suite() {
    	log.info("Log TestCase Example001WebApp");
        TestSuite suite= new TestSuite(ExampleSeleniumOnly.class);
        return suite;
    }

    public void setUp() throws Exception {
    	super.setUp();
    }

    public void tearDown() throws Exception {
    }

    public void testExecute() {
       	try {
            execute(this.getClass(),Configurator.getProperties());
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
