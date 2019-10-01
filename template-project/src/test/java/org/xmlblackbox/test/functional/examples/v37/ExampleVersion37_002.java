
package org.xmlblackbox.test.functional.examples.v37;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;
import org.junit.Assume;
import org.junit.Ignore;
import org.xmlblackbox.test.infrastructure.FlowControl;
import org.xmlblackbox.test.infrastructure.exception.TestException;
import org.xmlblackbox.test.util.Configurator;

/**
 *
 * @author Crea
 */
public class ExampleVersion37_002 extends FlowControl{
    private final static Logger log = Logger.getLogger(ExampleVersion37_002.class);

    public ExampleVersion37_002(String msg) {
        super(msg);
    }

    public static Test suite() {
        TestSuite suite= new TestSuite(ExampleVersion37_002.class);
        return suite;
    }

    public void setUp() throws Exception {
    	super.setUp();
    }

    public void tearDown() throws Exception {
    }

    public void testExecute() {
	log.info("os.name "+System.getProperty("os.name"));
    	if (!"linux".equalsIgnoreCase(System.getProperty("os.name"))){
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

}
