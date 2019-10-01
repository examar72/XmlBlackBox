
package org.xmlblackbox.test.functional.examples.v38;

import java.util.Properties;

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
public class ExampleVersion38_004 extends FlowControl{
    private final static Logger log = Logger.getLogger(ExampleVersion38_004.class);

    public ExampleVersion38_004(String msg) {
        super(msg);
    }

    public static Test suite() {
    	log.info("Log TestCase ExampleVersion38_004");
        TestSuite suite= new TestSuite(ExampleVersion38_004.class);
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
            assertTrue("The message received is not one expected", e.getMessage().contains("The value contains in ($this//xsd:getPerson/xsd:arg0[1]/xsd:name) of the Xml file target/ExampleVersion38_004_out.xml (John) is not one expected (Giulio)"));
   			assertTrue(true);
   		} catch (Exception e) {
   			log.error("Exception ", e);
   			fail("Exception "+e.getMessage());
   		}
   	}

}
