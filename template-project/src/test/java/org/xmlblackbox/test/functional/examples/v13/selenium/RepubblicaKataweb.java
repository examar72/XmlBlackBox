package org.xmlblackbox.test.functional.examples.v13.selenium;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.interfaces.SeleniumNavigation;
import org.xmlblackbox.test.infrastructure.util.MemoryData;
import org.xmlblackbox.test.infrastructure.util.SeleniumEnvironment;

import com.thoughtworks.selenium.Selenium;


public class RepubblicaKataweb extends SeleniumEnvironment implements SeleniumNavigation{
	private final static Logger logger = Logger.getLogger(RepubblicaKataweb.class);

    @Override
	public Selenium executeNavigation(MemoryData memory) throws Exception {

        Properties prop = (Properties)memory.getRepository(Repository.WEB_NAVIGATION);
        String webUrl = ((Properties)memory.getRepository(Repository.PARAMETERS)).getProperty("WEB_URL");
        logger.info("webUrl "+webUrl);

        super.initialize(webUrl, (Properties)memory.getRepository(Repository.FILE_PROPERTIES));

        selenium.open(webUrl);

		selenium.click("link=Motori");
		selenium.open("http://www.kataweb.it");

        
        return selenium;
	}

    @Override
    public void setSelenium(Selenium selenium) {
        super.selenium = selenium;
    }

    @Override
    public Selenium getSelenium() {
        return super.selenium;
    }
}