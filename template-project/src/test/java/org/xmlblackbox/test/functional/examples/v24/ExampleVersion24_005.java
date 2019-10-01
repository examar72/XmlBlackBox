
package org.xmlblackbox.test.functional.examples.v24;

import junit.framework.Assert;
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
public class ExampleVersion24_005 extends FlowControl{
    private final static Logger log = Logger.getLogger(ExampleVersion24_005.class);

    public ExampleVersion24_005(String msg) {
        super(msg);
    }

    public static Test suite() {
        TestSuite suite= new TestSuite(ExampleVersion24_005.class);
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
   			log.error("TestException ", e.getContainedException());
            String exceptionMessage = e.getContainedException().getMessage();
            log.error("Contained exception "+exceptionMessage);
            Assert.assertTrue("Excpected exception messagge didn't find", 
            		"Unknown property 'address2' on class 'class org.xmlblackbox.test.functional.examples.v22.plugin.Address'".equals(exceptionMessage));
   		} catch (Exception e) {
   			log.error("Exception ", e);
   			fail("Exception "+e.getMessage());
   		}
   	}

}
