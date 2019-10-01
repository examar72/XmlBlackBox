
package org.xmlblackbox.test.functional.examples.v20;

import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;
import org.xmlblackbox.test.infrastructure.FlowControl;
import org.xmlblackbox.test.infrastructure.exception.XmlBlackBoxException;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

/**
 *
 * @author examar.xbb
 */
public class ExampleVersion20_001 extends FlowControl{
    private final static Logger log = Logger.getLogger(ExampleVersion20_001.class);

    public ExampleVersion20_001(String msg) {
        super(msg);
    }

    public static Test suite() {
        TestSuite suite= new TestSuite(ExampleVersion20_001.class);
        return suite;
    }

    public void setUp() throws Exception {
    	super.setUp();
    }

    public void tearDown() throws Exception {
    }


    /**
     * This test passes an input variable from an indefined source
     * 
     */
    public void testExecute() {
       	try {
            log.info("testExecute");
            Properties inputProp = new Properties();
            inputProp.setProperty("example", "version20");
            MemoryData memory = execute(this.getClass(),inputProp);
            log.info("stop execute "+memory.get("example@inputProperties"));
            assertTrue("Variable example@inputProperties expected not found", ((String)memory.get("example@inputProperties")).equals("version20"));
            log.info(""+memory.get("example@fileProperties"));
            assertTrue("Variable example@fileProperties expected not found", ((String)memory.get("example@fileProperties")).equals("version20"));
   		} catch (XmlBlackBoxException e) {
            log.info("Exception expected");
            fail("Exception");
   		} catch (Exception e) {
            log.info("Exception expected");
            fail("Exception");
        }
   	}

}
