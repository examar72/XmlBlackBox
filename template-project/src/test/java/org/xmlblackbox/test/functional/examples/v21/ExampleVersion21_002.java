
package org.xmlblackbox.test.functional.examples.v21;

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
public class ExampleVersion21_002 extends FlowControl{
    private final static Logger log = Logger.getLogger(ExampleVersion21_002.class);

    public ExampleVersion21_002(String msg) {
        super(msg);
    }

    public static Test suite() {
        TestSuite suite= new TestSuite(ExampleVersion21_002.class);
        return suite;
    }

    public void setUp() throws Exception {
    	super.setUp();
    }

    public void tearDown() throws Exception {
    }


    /**
     * 
     */
    public void testExecute() {
       	try {
            log.info("testExecute");
            execute(this.getClass(),Configurator.getProperties());
            log.info("stop execute");
            fail("The test has not thrown the expected exception");
   		} catch (XmlBlackBoxException e) {
            log.info("Exception expected");
            assertTrue("The message received is not one expected", e.getMessage().startsWith("Class not found"));
   		} catch (Exception e) {
            log.info("Exception expected");
            fail("Exception");
        }
   	}

}
