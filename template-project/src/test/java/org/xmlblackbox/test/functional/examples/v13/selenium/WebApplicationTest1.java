package org.xmlblackbox.test.functional.examples.v13.selenium;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.xmlblackbox.test.infrastructure.interfaces.NewSeleniumNavigation;
import org.xmlblackbox.test.infrastructure.util.MemoryData;
import org.xmlblackbox.test.infrastructure.util.SeleniumEnvironment;

import com.thoughtworks.selenium.Selenium;


public class WebApplicationTest1 extends SeleniumEnvironment implements NewSeleniumNavigation{
	private final static Logger logger = Logger.getLogger(WebApplicationTest1.class);

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

        logger.info("EXECUTE SELENIUM WebApplicationTest");
        return selenium;
	}


    @Override
    public void setSelenium(Selenium selenium) {
        super.selenium = selenium;
    }

}