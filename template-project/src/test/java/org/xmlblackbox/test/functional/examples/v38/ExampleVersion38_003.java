
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
public class ExampleVersion38_003 extends FlowControl{
    private final static Logger log = Logger.getLogger(ExampleVersion38_003.class);

    public ExampleVersion38_003(String msg) {
        super(msg);
    }

    public static Test suite() {
    	log.info("Log TestCase ExampleVersion38_003");
        TestSuite suite= new TestSuite(ExampleVersion38_003.class);
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
            assertTrue("The message received is not one expected", e.getMessage().contains("row count (table=XMLBLACKBOX.HOUSE) expected:<1> but was:<0>"));
   			assertTrue(true);
   		} catch (Exception e) {
   			log.error("Exception ", e);
   			fail("Exception "+e.getMessage());
   		}
       	finally{
       		try{
       			Properties fileProperties = Configurator.getProperties();
	       		fileProperties.put("RESET_SESSION", "false");
	       		execute("/org/xmlblackbox/test/xml/StopDerbyDatabase.xml",fileProperties);
	       	} catch (TestException e) {
	   			log.error("Exception ", e.getContainedException());
	   			fail("Exception "+e);
	   		} catch (Exception e) {
	   			e.printStackTrace();
	   			log.error("Exception ", e);
	   			fail("Exception "+e.getMessage());
	   		}
       	}
   	}

}
