
package org.xmlblackbox.test.functional.examples.v15;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;
import org.xmlblackbox.test.infrastructure.FlowControl;
import org.xmlblackbox.test.infrastructure.exception.TestException;
import org.xmlblackbox.test.util.Configurator;

/**
 *
 * @author examar.xbb
 */
public class ExampleVersion15_002 extends FlowControl{
    private final static Logger log = Logger.getLogger(ExampleVersion15_002.class);

    public ExampleVersion15_002(String msg) {
        super(msg);
    }

    public static Test suite() {
        TestSuite suite= new TestSuite(ExampleVersion15_002.class);
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
            fail("The test has not thrown the expected exception");
   		} catch (TestException e) {
            log.info("Exception expected");
            assertTrue("The message received is not one expected", e.getMessage().startsWith("Connection not set up on"));
   		} catch (Exception e) {
            log.info("Exception expected");
            fail("Exception");
        }

   	}

}
