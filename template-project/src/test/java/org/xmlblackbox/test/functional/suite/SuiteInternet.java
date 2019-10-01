package org.xmlblackbox.test.functional.suite;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.xmlblackbox.test.functional.examples.v13.*;

public class SuiteInternet extends TestCase {

	  public SuiteInternet(String name) {
		    super(name);
		  }

      public static Test suite() {
		  
		  TestSuite suite = new TestSuite("SuiteInternet");

          suite.addTest(ExampleConte.suite());
          suite.addTest(DifferentDomains.suite());
          return suite;
      }

}
