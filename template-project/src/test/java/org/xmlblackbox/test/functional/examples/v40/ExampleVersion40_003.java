
package org.xmlblackbox.test.functional.examples.v40;

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
public class ExampleVersion40_003 extends FlowControl{
    private final static Logger log = Logger.getLogger(ExampleVersion40_003.class);

    public ExampleVersion40_003(String msg) {
        super(msg);
    }

    public static Test suite() {
        TestSuite suite= new TestSuite(ExampleVersion40_003.class);
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
   			log.info("e.getMessage() "+e.toString());
   			log.info("TestException TestExceptionTestExceptionTestExceptionTestExceptionTestExceptionTestExceptionTestExceptionTestExceptionTestException");
   			log.error("TestException ", e);
   			Assert.assertTrue("Exception Expected OK ", e.toString().equals("Simulated web navigation with exception"));
   		} catch (Exception e) {
   			log.error("Exception ", e);
   			log.debug("Exception ExceptionExceptionExceptionExceptionExceptionExceptionExceptionExceptionExceptionExceptionExceptionExceptionExceptionExceptionException");
   			fail("Exception "+e.getMessage());
   		}
   	}

}
