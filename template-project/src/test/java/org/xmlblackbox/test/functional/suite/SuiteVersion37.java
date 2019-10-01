package org.xmlblackbox.test.functional.suite;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xmlblackbox.test.functional.examples.v37.ExampleVersion37_001;
import org.xmlblackbox.test.functional.examples.v37.ExampleVersion37_002;
import org.xmlblackbox.test.functional.examples.v37.ExampleVersion37_003;
import org.xmlblackbox.test.functional.examples.v37.ExampleVersion37_004;
import org.xmlblackbox.test.functional.examples.v37.ExampleVersion37_005;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ExampleVersion37_001.class,
	ExampleVersion37_002.class,
	ExampleVersion37_003.class,
	ExampleVersion37_004.class,
	ExampleVersion37_005.class
})

public class SuiteVersion37 {

}
