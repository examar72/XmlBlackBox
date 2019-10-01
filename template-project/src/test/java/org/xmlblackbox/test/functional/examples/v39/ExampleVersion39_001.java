
package org.xmlblackbox.test.functional.examples.v39;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;
import org.xmlblackbox.test.infrastructure.FlowControl;
import org.xmlblackbox.test.infrastructure.exception.TestException;
import org.xmlblackbox.test.infrastructure.util.Configurator;

/**
 *
 * @author Crea
 */
public class ExampleVersion39_001 extends FlowControl{
    private final static Logger log = Logger.getLogger(ExampleVersion39_001.class);

    public ExampleVersion39_001(String msg) {
        super(msg);
    }

    public static Test suite() {
        TestSuite suite= new TestSuite(ExampleVersion39_001.class);
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
   		} catch (TestException e) {
            assertTrue("The message received is not one expected", e.getMessage().contains("Include file depth is greater than 100. Perhaps there is an endless loop"));
   			Assert.assertTrue(true);
   		} catch (Exception e) {
   			log.error("Exception ", e);
   			fail("Exception "+e.getMessage());
   		}
   	}

}
