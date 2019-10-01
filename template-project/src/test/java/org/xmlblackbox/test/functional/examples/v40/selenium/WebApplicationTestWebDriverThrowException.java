package org.xmlblackbox.test.functional.examples.v40.selenium;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.xmlblackbox.test.infrastructure.exception.TestException;
import org.xmlblackbox.test.infrastructure.interfaces.NewSeleniumWebDriverNavigation;
import org.xmlblackbox.test.infrastructure.util.MemoryData;


public class WebApplicationTestWebDriverThrowException implements NewSeleniumWebDriverNavigation{
	private final static Logger logger = Logger.getLogger(WebApplicationTestWebDriverThrowException.class);

    @Override
	public WebDriver executeNavigation(WebDriver driver, MemoryData memory, Properties inputProp) throws Exception {


    	if(true){
    		throw new TestException("Simulated web navigation with exception");
    	}
        
    	return driver;
	}




}