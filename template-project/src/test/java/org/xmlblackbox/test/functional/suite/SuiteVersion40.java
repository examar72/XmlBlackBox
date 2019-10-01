package org.xmlblackbox.test.functional.suite;



import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xmlblackbox.test.functional.examples.v40.ExampleVersion40_001;
import org.xmlblackbox.test.functional.examples.v40.ExampleVersion40_002;
import org.xmlblackbox.test.functional.examples.v40.ExampleVersion40_003;
import org.xmlblackbox.test.functional.examples.v40.ExampleVersion40_004;
import org.xmlblackbox.test.functional.examples.v40.ExampleVersion40_005;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ExampleVersion40_001.class,
	ExampleVersion40_002.class,
	ExampleVersion40_003.class,
	ExampleVersion40_004.class,
	ExampleVersion40_005.class
})
public class SuiteVersion40 {
}
