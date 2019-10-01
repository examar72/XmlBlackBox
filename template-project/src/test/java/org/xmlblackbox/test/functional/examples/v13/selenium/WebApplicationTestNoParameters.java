package org.xmlblackbox.test.functional.examples.v13.selenium;

import com.thoughtworks.selenium.*;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.xmlblackbox.test.infrastructure.interfaces.NewSeleniumNavigation;
import org.xmlblackbox.test.infrastructure.util.MemoryData;


public class WebApplicationTestNoParameters extends SeleneseTestCase implements NewSeleniumNavigation{
	private final static Logger logger = Logger.getLogger(WebApplicationTestNoParameters.class);

    @Override
	public Selenium executeNavigation(Selenium selenium, MemoryData memory, Properties inputProp) throws Exception {

        Properties prop = inputProp;

        logger.info("Parameters size "+prop.keySet().size());
        assertTrue("Parameters should be size (0), found ("+prop.keySet().size()+")", prop.keySet().size()==0);

        return selenium;
	}
}