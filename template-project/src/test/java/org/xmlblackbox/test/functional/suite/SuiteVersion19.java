package org.xmlblackbox.test.functional.suite;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xmlblackbox.test.functional.examples.v15.ExampleVersion15_001;
import org.xmlblackbox.test.functional.examples.v15.ExampleVersion15_002;
import org.xmlblackbox.test.functional.examples.v15.ExampleVersion15_003;
import org.xmlblackbox.test.functional.examples.v19.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ExampleVersion19_001.class,
	ExampleVersion19_002.class,
	ExampleVersion19_003.class,
	ExampleVersion19_004.class,
	ExampleVersion19_005.class
	
})
public class SuiteVersion19{
}
