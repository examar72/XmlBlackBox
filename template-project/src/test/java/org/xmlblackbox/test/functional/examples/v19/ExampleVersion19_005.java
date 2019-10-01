
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
public class ExampleVersion19_005 extends FlowControl{
    private final static Logger log = Logger.getLogger(ExampleVersion19_005.class);

    public ExampleVersion19_005(String msg) {
        super(msg);
    }

    public static Test suite() {
        TestSuite suite= new TestSuite(ExampleVersion19_005.class);
        return suite;
    }

    public void setUp() throws Exception {
    	super.setUp();
    }

    public void tearDown() throws Exception {
    }


    /**
     * In this case it is aspetting XmlBlackBoxException, with "Include file not found : setVariable"
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
            assertTrue("The message received is not one expected", 
            		e.getMessage().startsWith("Include file not found /IncorrectPath.xml"));
   		} catch (Exception e) {
            log.error("Exception not expected ",e );
            fail("Exception");
        }
   	}

}
