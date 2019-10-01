
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
public class ExampleVersion19_002 extends FlowControl{
    private final static Logger log = Logger.getLogger(ExampleVersion19_002.class);

    public ExampleVersion19_002(String msg) {
        super(msg);
    }

    public static Test suite() {
        TestSuite suite= new TestSuite(ExampleVersion19_002.class);
        return suite;
    }

    public void setUp() throws Exception {
    	super.setUp();
    }

    public void tearDown() throws Exception {
    }

    /**
     * Same test of ExampleVersion015_003 but with different 
     * handle exception
     */
    public void testExecute() {
       	try {
            log.info("testExecute");
            execute(this.getClass(),Configurator.getProperties());
            log.info("stop execute");
            fail("The test has not thrown the expected exception");
		} catch (XmlBlackBoxException e) {
            assertTrue("The message received is not one expected", 
                    e.getMessage().startsWith("fileoutput not set up on"));
   		} catch (Exception e) {
            log.info("Exception expected");
            fail("Exception");
        }
   	}

}
