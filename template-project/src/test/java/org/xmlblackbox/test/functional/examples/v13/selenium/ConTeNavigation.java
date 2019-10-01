package org.xmlblackbox.test.functional.examples.v13.selenium;

import com.thoughtworks.selenium.*;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.xmlblackbox.test.infrastructure.interfaces.NewSeleniumNavigation;
import org.xmlblackbox.test.infrastructure.util.MemoryData;


public class ConTeNavigation extends SeleneseTestCase implements NewSeleniumNavigation{
	private final static Logger logger = Logger.getLogger(ConTeNavigation.class);

    @Override
	public Selenium executeNavigation(Selenium selenium, MemoryData memory, Properties inputProp) throws Exception {

        selenium.open("https://preventivo.conte.it/calculator/calculatorAuto.htm");
		selenium.waitForPageToLoad("30000");
		//selenium.waitForCondition("isTextPresent(\"e targa\")", "10000");
		selenium.select("//select[@id='page:text_1CNSC']", "label=Internet");
		selenium.click("page:situazione_1:0");
		selenium.click("page:situazione_2:0");
		selenium.type("page:targa", "ZX777ZZ");
		selenium.click("page:privacy_contr");
		selenium.click("page:buttonContinua");
		selenium.waitForPageToLoad("30000");

        logger.info("selenium.isTextPresent(\"Data di immatricolazione\") "+selenium.isTextPresent("Data di immatricolazione"));
        assertTrue("Non e' presente il testo \"Data di immatricolazione\"",
                selenium.isTextPresent("Data di immatricolazione"));

        Thread.sleep(30000);

        logger.info("");
        return selenium;
	}
}