package org.xmlblackbox.test.functional.suite;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xmlblackbox.test.functional.examples.v19.ExampleVersion19_001;
import org.xmlblackbox.test.functional.examples.v19.ExampleVersion19_002;
import org.xmlblackbox.test.functional.examples.v19.ExampleVersion19_003;
import org.xmlblackbox.test.functional.examples.v19.ExampleVersion19_004;
import org.xmlblackbox.test.functional.examples.v19.ExampleVersion19_005;
import org.xmlblackbox.test.functional.examples.v20.*;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	ExampleVersion20_001.class
	
})
public class SuiteVersion20 extends TestCase {

}
