package org.xmlblackbox.test.functional.examples.v21.selenium;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

import com.thoughtworks.selenium.SeleneseTestCase;
import com.thoughtworks.selenium.Selenium;


public class SeleniumNavigation extends SeleneseTestCase{
	private final static Logger logger = Logger.getLogger(SeleniumNavigation.class);

	public Selenium executeNavigation(Selenium selenium, MemoryData memory, Properties inputProp) throws Exception {

        selenium.open("https://preventivo.conte.it/calculator/calculatorAuto.htm");
		selenium.waitForPageToLoad("30000");

        logger.info("");
        return selenium;
	}
}