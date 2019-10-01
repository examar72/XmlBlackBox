package org.xmlblackbox.test.functional.examples.v13.selenium;

import com.thoughtworks.selenium.*;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.xmlblackbox.test.infrastructure.interfaces.NewSeleniumNavigation;
import org.xmlblackbox.test.infrastructure.util.MemoryData;


public class WebApplicationNavigation extends SeleneseTestCase implements NewSeleniumNavigation{
	private final static Logger logger = Logger.getLogger(WebApplicationNavigation.class);

    @Override
	public Selenium executeNavigation(Selenium selenium, MemoryData memory, Properties inputProp) throws Exception {

        //Properties prop = (Properties)memory.getRepository(Repository.PARAMETERS);
        Properties prop = inputProp;
        String webUrl = prop.getProperty("EXAMPLE_WEB_URL");
        logger.info("webUrl "+webUrl);
        logger.info("selenium "+selenium);
        //selenium = super.initialize(webUrl, (Properties)memory.getRepository(Repository.FILE_PROPERTIES));

        selenium.open(webUrl);
        selenium.waitForPageToLoad("50000");
        selenium.click("link=next page");
		//selenium.waitForPageToLoad("30000");

        logger.info("EXECUTE SELENIUM WebApplicationNavigation");
        return selenium;
	}
}