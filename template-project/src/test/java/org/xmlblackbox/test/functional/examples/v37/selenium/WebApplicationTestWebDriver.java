package org.xmlblackbox.test.functional.examples.v37.selenium;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.xmlblackbox.test.infrastructure.interfaces.NewSeleniumNavigation;
import org.xmlblackbox.test.infrastructure.interfaces.NewSeleniumWebDriverNavigation;
import org.xmlblackbox.test.infrastructure.util.MemoryData;
import org.xmlblackbox.test.infrastructure.util.SeleniumEnvironment;

import com.thoughtworks.selenium.Selenium;


public class WebApplicationTestWebDriver implements NewSeleniumWebDriverNavigation{
	private final static Logger logger = Logger.getLogger(WebApplicationTestWebDriver.class);

    @Override
	public WebDriver executeNavigation(WebDriver driver, MemoryData memory, Properties inputProp) throws Exception {

//        //Properties prop = (Properties)memory.getRepository(Repository.PARAMETERS);
        Properties prop = inputProp;
        String webUrl = prop.getProperty("EXAMPLE_WEB_URL");
        logger.info("webUrl "+webUrl);
//        logger.info("selenium "+selenium);
//        //selenium = super.initialize(webUrl, (Properties)memory.getRepository(Repository.FILE_PROPERTIES));
//
        driver.get(webUrl);

//        if (driver.findElement(By.xpath("//h1[text()='Hello World']"))==null){
        if (driver.findElement(By.id("helloWorld"))==null){
        	        	throw new Exception("Hello world not found!!");
        }
        
    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

//
////        if(true){throw new Exception("PROVA");}
//        assertTrue("\"Hello World\" not found!", selenium.isTextPresent("Hello World"));
//
//        logger.info("EXECUTE SELENIUM WebApplicationTest");
//        return selenium;
    	return driver;
	}




}