package org.xmlblackbox.test.functional.examples.v37.selenium;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.xmlblackbox.test.infrastructure.interfaces.NewSeleniumWebDriverNavigation;
import org.xmlblackbox.test.infrastructure.util.MemoryData;


public class WebApplicationTestWebDriverNextPage implements NewSeleniumWebDriverNavigation{
	private final static Logger logger = Logger.getLogger(WebApplicationTestWebDriverNextPage.class);

    @Override
	public WebDriver executeNavigation(WebDriver driver, MemoryData memory, Properties inputProp) throws Exception {

		driver.findElement(By.linkText("next page")).click();

        if (driver.findElement(By.id("nextPage"))==null){
        	        	throw new Exception("Hello world not found!!");
        }
        
        driver.close();
    	return driver;
	}




}