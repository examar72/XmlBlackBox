package org.xmlblackbox.test.functional.examples.v13.selenium;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.interfaces.SeleniumNavigation;
import org.xmlblackbox.test.infrastructure.util.MemoryData;
import org.xmlblackbox.test.infrastructure.util.SeleniumEnvironment;

import com.thoughtworks.selenium.Selenium;


public class CorriereXBB extends SeleniumEnvironment implements SeleniumNavigation{
	private final static Logger logger = Logger.getLogger(CorriereXBB.class);

    @Override
	public Selenium executeNavigation(MemoryData memory) throws Exception {

        Properties prop = (Properties)memory.getRepository(Repository.WEB_NAVIGATION);
        super.initialize("http://localhost:8080", (Properties)memory.getRepository(Repository.FILE_PROPERTIES));

        selenium.open("http://localhost:8080");
        selenium.waitForPageToLoad("30000");
        
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