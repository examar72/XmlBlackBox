
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
public class ExampleVersion40_001 extends FlowControl{
    private final static Logger log = Logger.getLogger(ExampleVersion40_001.class);

    public ExampleVersion40_001(String msg) {
        super(msg);
    }

    public static Test suite() {
        TestSuite suite= new TestSuite(ExampleVersion40_001.class);
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
   			log.error("Exception ", e);
   			fail("Exception "+e.getMessage());
   		} catch (Exception e) {
   			log.error("Exception ", e);
   			fail("Exception "+e.getMessage());
   		}
   	}

}
