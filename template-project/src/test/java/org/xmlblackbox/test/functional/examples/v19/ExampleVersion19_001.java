
package org.xmlblackbox.test.functional.examples.v19;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;
import org.xmlblackbox.test.infrastructure.FlowControl;
import org.xmlblackbox.test.infrastructure.exception.XmlBlackBoxException;
import org.xmlblackbox.test.util.Configurator;

/**
 *
 * @author examar.xbb
 */
public class ExampleVersion19_001 extends FlowControl{
    private final static Logger log = Logger.getLogger(ExampleVersion19_001.class);

    public ExampleVersion19_001(String msg) {
        super(msg);
    }

    public static Test suite() {
        TestSuite suite= new TestSuite(ExampleVersion19_001.class);
        return suite;
    }

    public void setUp() throws Exception {
    	super.setUp();
    }

    public void tearDown() throws Exception {
    }


    /**
     * Same test of ExampleVersion015_002 but with different 
     * handle exception. In this case it is aspetting XmlBlackBoxException,
     * in versione 15 is aspecting TestException that is the old exception 
     * throw by the framework
     */
    public void testExecute() {
       	try {
            log.info("testExecute");
            execute(this.getClass(),Configurator.getProperties());
            log.info("stop execute");
            fail("The test has not thrown the expected exception");
       	} catch (XmlBlackBoxException e) {
            log.info("Exception expected");
            assertTrue("The message received is not one expected", e.getMessage().startsWith("Connection not set up on"));
   		} catch (Exception e) {
            log.info("Exception expected");
            fail("Exception");
        }
   	}

}
