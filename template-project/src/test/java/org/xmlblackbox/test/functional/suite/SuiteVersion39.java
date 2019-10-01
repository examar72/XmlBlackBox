package org.xmlblackbox.test.functional.suite;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xmlblackbox.test.functional.examples.v38.ExampleVersion38_001;
import org.xmlblackbox.test.functional.examples.v38.ExampleVersion38_002;
import org.xmlblackbox.test.functional.examples.v38.ExampleVersion38_003;
import org.xmlblackbox.test.functional.examples.v38.ExampleVersion38_004;
import org.xmlblackbox.test.functional.examples.v38.ExampleVersion38_005;
import org.xmlblackbox.test.functional.examples.v39.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ExampleVersion39_001.class
	
})
public class SuiteVersion39 {
}
