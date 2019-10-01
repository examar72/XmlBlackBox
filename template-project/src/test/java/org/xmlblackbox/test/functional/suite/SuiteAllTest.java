package org.xmlblackbox.test.functional.suite;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xmlblackbox.test.functional.examples.v13.Example001WebApp;
import org.xmlblackbox.test.functional.examples.v13.Example002WebApp;
import org.xmlblackbox.test.functional.examples.v13.Example003WebApp;
import org.xmlblackbox.test.functional.examples.v13.Example004WebApp;
import org.xmlblackbox.test.functional.examples.v13.Example005WebApp;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	SuiteVersion13.class,
	SuiteVersion15.class,
	SuiteVersion19.class,
	SuiteVersion20.class,
	SuiteVersion21.class,
	SuiteVersion22.class,
	SuiteVersion24.class,
	SuiteVersion37.class,
	SuiteVersion38.class,
	SuiteVersion39.class,
	SuiteVersion40.class
})
public class SuiteAllTest {
}
