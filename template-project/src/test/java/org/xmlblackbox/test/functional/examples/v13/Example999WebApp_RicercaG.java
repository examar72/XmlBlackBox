/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.xmlblackbox.test.functional.examples.v13;

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
public class Example999WebApp_RicercaG extends FlowControl{
    private final static Logger log = Logger.getLogger(Example999WebApp_RicercaG.class);

    public Example999WebApp_RicercaG(String msg) {
        super(msg);
    }

    public static Test suite() {
        TestSuite suite= new TestSuite(Example999WebApp_RicercaG.class);
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
   			log.error("Eccezione", e.getContainedException());
   			fail("Eccezione "+e);
   		} catch (Exception e) {
   			log.error("Eccezione", e);
   			fail("Eccezione "+e.getMessage());
   		}
   	}

}
