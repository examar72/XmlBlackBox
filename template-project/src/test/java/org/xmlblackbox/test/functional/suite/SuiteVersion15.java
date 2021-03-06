package org.xmlblackbox.test.functional.suite;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xmlblackbox.test.functional.examples.v13.Example001WebApp;
import org.xmlblackbox.test.functional.examples.v13.Example002WebApp;
import org.xmlblackbox.test.functional.examples.v13.Example003WebApp;
import org.xmlblackbox.test.functional.examples.v13.Example004WebApp;
import org.xmlblackbox.test.functional.examples.v13.Example005WebApp;
import org.xmlblackbox.test.functional.examples.v15.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ExampleVersion15_001.class,
	ExampleVersion15_002.class,
	ExampleVersion15_003.class
})
public class SuiteVersion15{
}
