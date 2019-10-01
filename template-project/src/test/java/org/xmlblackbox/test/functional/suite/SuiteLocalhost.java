package org.xmlblackbox.test.functional.suite;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xmlblackbox.test.functional.examples.v13.*;
import org.xmlblackbox.test.functional.examples.v37.ExampleVersion37_001;
import org.xmlblackbox.test.functional.examples.v37.ExampleVersion37_002;
import org.xmlblackbox.test.functional.examples.v37.ExampleVersion37_003;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	Example001WebApp.class,
	Example002WebApp.class,
	Example003WebApp.class,
	Example004WebApp.class,
	Example005WebApp.class
})
public class SuiteLocalhost{
}
