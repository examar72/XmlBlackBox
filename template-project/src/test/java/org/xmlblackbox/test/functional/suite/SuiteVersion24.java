package org.xmlblackbox.test.functional.suite;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xmlblackbox.test.functional.examples.v22.ExampleVersion22_001;
import org.xmlblackbox.test.functional.examples.v24.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ExampleVersion24_001.class,
	 /**
     * Next two tests are ideally one. They test the property RESET_SESSION.
     * If this property is "false" the session object maintains valid all variables
     * created during tests execution.
     */
	ExampleVersion24_002.class,
	ExampleVersion24_003.class,
	ExampleVersion24_004.class,
	ExampleVersion24_005.class
})
public class SuiteVersion24{

}
