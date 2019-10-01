package org.xmlblackbox.test.functional.suite;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xmlblackbox.test.functional.examples.v37.ExampleVersion37_001;
import org.xmlblackbox.test.functional.examples.v37.ExampleVersion37_002;
import org.xmlblackbox.test.functional.examples.v37.ExampleVersion37_003;
import org.xmlblackbox.test.functional.examples.v38.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ExampleVersion38_001.class,
	ExampleVersion38_002.class,
	ExampleVersion38_003.class,
	ExampleVersion38_004.class,
	ExampleVersion38_005.class
	
})
public class SuiteVersion38{
}
