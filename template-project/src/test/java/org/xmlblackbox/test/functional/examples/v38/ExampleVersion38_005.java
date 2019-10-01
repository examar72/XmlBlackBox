
package org.xmlblackbox.test.functional.examples.v38;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;
import org.xmlblackbox.test.infrastructure.FlowControl;
import org.xmlblackbox.test.infrastructure.exception.TestException;
import org.xmlblackbox.test.infrastructure.exception.XmlBlackBoxException;
import org.xmlblackbox.test.util.Configurator;

/**
 *
 * @author Examar
 */
public class ExampleVersion38_005 extends FlowControl{
    private final static Logger log = Logger.getLogger(ExampleVersion38_005.class);

    public ExampleVersion38_005(String msg) {
        super(msg);
    }

    public static Test suite() {
    	log.info("Log TestCase ExampleVersion38_005");
        TestSuite suite= new TestSuite(ExampleVersion38_005.class);
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
   			fail("Exception expected");
   		} catch (XmlBlackBoxException e) {
   			log.info("e.getMessage() "+e.getMessage());
            assertTrue("The message received is not one expected", e.getContainedException().getMessage().contains("Table/View 'XMLBLACKBOX.HOUSE_WRONG' does not exist"));
   			assertTrue(true);
   		} catch (Exception e) {
   			log.error("Exception ", e);
   			fail("Exception "+e.getMessage());
   		}
   	}

}
