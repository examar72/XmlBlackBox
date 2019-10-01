package org.xmlblackbox.test.functional.suite;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xmlblackbox.test.functional.examples.v20.ExampleVersion20_001;
import org.xmlblackbox.test.functional.examples.v21.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ExampleVersion21_001.class,
	ExampleVersion21_002.class,
	ExampleVersion21_003.class,
	ExampleVersion21_004.class
	
})
public class SuiteVersion21{
}
