package org.xmlblackbox.test.functional.examples.v13.selenium;

import com.thoughtworks.selenium.*;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.xmlblackbox.test.infrastructure.interfaces.NewSeleniumNavigation;
import org.xmlblackbox.test.infrastructure.util.MemoryData;


public class WebApplicationTest2 extends SeleneseTestCase implements NewSeleniumNavigation{
	private final static Logger logger = Logger.getLogger(WebApplicationTest2.class);

    @Override
	public Selenium executeNavigation(Selenium selenium, MemoryData memory, Properties inputProp) throws Exception {

        //Properties prop = (Properties)memory.getRepository(Repository.PARAMETERS);
        Properties prop = inputProp;
        String webUrl = prop.getProperty("EXAMPLE_WEB_URL");
        logger.info("webUrl "+webUrl);
        logger.info("selenium "+selenium);
        //selenium = super.initialize(webUrl, (Properties)memory.getRepository(Repository.FILE_PROPERTIES));

        selenium.open(webUrl);
        selenium.waitForPageToLoad("30000");

//        if(true){throw new Exception("PROVA");}
        assertTrue("\"Hello World\" not found!", selenium.isTextPresent("Hello World"));

        logger.info("EXECUTE SELENIUM WebApplicationTest2");
        return selenium;
	}
}