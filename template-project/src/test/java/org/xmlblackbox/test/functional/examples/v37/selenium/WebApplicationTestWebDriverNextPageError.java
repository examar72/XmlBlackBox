package org.xmlblackbox.test.functional.examples.v37.selenium;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.xmlblackbox.test.infrastructure.interfaces.NewSeleniumWebDriverNavigation;
import org.xmlblackbox.test.infrastructure.util.MemoryData;


public class WebApplicationTestWebDriverNextPageError implements NewSeleniumWebDriverNavigation{
	private final static Logger logger = Logger.getLogger(WebApplicationTestWebDriverNextPageError.class);

    @Override
	public WebDriver executeNavigation(WebDriver driver, MemoryData memory, Properties inputProp) throws Exception {

		
        try{
        	driver.findElement(By.linkText("next page"));
        }catch(NoSuchElementException nsee){
        	logger.error("Throw NoSuchElementException as expected");
        }
        
    	return driver;
	}




}