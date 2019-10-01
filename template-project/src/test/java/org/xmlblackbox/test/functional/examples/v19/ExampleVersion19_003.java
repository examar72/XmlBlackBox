
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
public class ExampleVersion19_003 extends FlowControl{
    private final static Logger log = Logger.getLogger(ExampleVersion19_003.class);

    public ExampleVersion19_003(String msg) {
        super(msg);
    }

    public static Test suite() {
        TestSuite suite= new TestSuite(ExampleVersion19_003.class);
        return suite;
    }

    public void setUp() throws Exception {
    	super.setUp();
    }

    public void tearDown() throws Exception {
    }


    /**
     * In this case it is aspetting XmlBlackBoxException, with "Repository not found : setVariable"
     * 
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
            assertTrue("The message received is not one expected", e.getMessage().startsWith("Repository not found : setVariable"));
   		} catch (Exception e) {
            log.info("Exception expected");
            fail("Exception");
        }
   	}

}
