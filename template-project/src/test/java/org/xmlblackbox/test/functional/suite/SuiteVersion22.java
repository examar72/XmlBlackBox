package org.xmlblackbox.test.functional.suite;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xmlblackbox.test.functional.examples.v21.ExampleVersion21_001;
import org.xmlblackbox.test.functional.examples.v21.ExampleVersion21_002;
import org.xmlblackbox.test.functional.examples.v21.ExampleVersion21_003;
import org.xmlblackbox.test.functional.examples.v21.ExampleVersion21_004;
import org.xmlblackbox.test.functional.examples.v22.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ExampleVersion22_001.class
})
public class SuiteVersion22{
}
