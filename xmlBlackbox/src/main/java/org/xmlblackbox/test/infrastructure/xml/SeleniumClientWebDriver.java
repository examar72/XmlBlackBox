/**
 *
 * This file is part of XmlBlackBox.
 *
 * XmlBlackBox is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * XmlBlackBox is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with XmlBlackBox.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.xmlblackbox.test.infrastructure.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.xmlblackbox.test.infrastructure.exception.TestException;
import org.xmlblackbox.test.infrastructure.exception.VariableNotFound;
import org.xmlblackbox.test.infrastructure.exception.XmlBlackBoxException;
import org.xmlblackbox.test.infrastructure.interfaces.NewSeleniumNavigation;
import org.xmlblackbox.test.infrastructure.interfaces.NewSeleniumWebDriverNavigation;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
/**
 * @author examar
 */
public class SeleniumClientWebDriver extends XmlStep  {

	private final static Logger logger = Logger.getLogger(SeleniumClientWebDriver.class);

	public static String browser_type= "";
	public static String browser_session= null;

	public static String FIREFOX = "FIREFOX";
	public static String IEXPLORER = "IEXPLORER";
	public static String CHROME = "CHROME";
	public static String HTMLUNIT= "HTMLUNIT";
	public static String PHANTOMJS= "PHANTOMJS";
	

	private Parameters parameters;
	private List<NavigationFile> navigationFileList= new ArrayList();

	public SeleniumClientWebDriver(Element el) throws Exception {
		super(el);
        build(el);
	}

	public void build(Element mainElement) throws Exception {

		logger.debug("clientElement "+mainElement);
		logger.debug("clientElement.getAttributeValue(nome) "+mainElement.getAttributeValue("name"));
		this.setName(mainElement.getAttributeValue("name"));
		this.setBrowserType(mainElement.getAttributeValue("browser-type"));
		if (mainElement.getAttributeValue("browser-session")==null||mainElement.getAttributeValue("browser-session").equals("")){
			this.setBrowserSession(defaultSeleniumClient);
		}else{
			this.setBrowserSession(mainElement.getAttributeValue("browser-session")+"@webNavigation");
		}
		
        if (mainElement.getAttributeValue("do-it-if-included")!=null){
    		logger.debug("mainElement.getAttributeValue(\"do-it-if-included\")) "+mainElement.getAttributeValue("do-it-if-included"));
        	setToBeDoneWhenIncluded(new Boolean(mainElement.getAttributeValue("do-it-if-included")).booleanValue());
        }


        navigationFileList.clear();
        Iterator navigationFileIter = mainElement.getChildren("NAVIGATION-FILE", uriXsd).iterator();
		logger.debug("navigationFileIter.hasNext() "+navigationFileIter.hasNext());
        while (navigationFileIter.hasNext()){
            Element navigationFileXml= (Element) navigationFileIter.next();
            NavigationFile navigationFile = new NavigationFile(navigationFileXml);
            addNavigationFile(navigationFile);
        }

	}

	public void addNavigationFile(NavigationFile navigationFile) {
		this.navigationFileList.add(navigationFile);
	}

	public NavigationFile getNavigationFile(int index) {
		return navigationFileList.get(index);
	}

    public List<NavigationFile> getNavigationFileList() {
		return navigationFileList;
	}

	public Parameters getParameters() {
		return parameters;
	}

	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public String getRepositoryName() {
		return Repository.WEB_NAVIGATION;
	}


    public WebDriver executeSeleniumWebDriver(MemoryData memory, WebDriver driver) throws TestException, Exception{

        Iterator<NavigationFile> iterNavigationFile = getNavigationFileList().iterator();
        NewSeleniumWebDriverNavigation seleniumWebDriverImpl = null;

        while(iterNavigationFile.hasNext()){
            NavigationFile currentNavigationFile = iterNavigationFile.next();
            
        	logger.info("isIncluded() "+isIncluded());
        	logger.info("currentNavigationFile.isIncluded() "+currentNavigationFile.isIncluded());
        	logger.info("currentNavigationFile.isToBeDoneItWhenIncluded() "+currentNavigationFile.isToBeDoneItWhenIncluded());
            if (!isIncluded() || (isIncluded() && currentNavigationFile.isToBeDoneItWhenIncluded())){
            	    
	            Class seleniumWebDriverClass;
	            try{
	    	    	// Instanzio la classe..
	                seleniumWebDriverClass=Class.forName(currentNavigationFile.getNavigationClassFile());
	            }catch(ClassNotFoundException cnfe){
	            	logger.error("ClassNotFoundException ", cnfe);
	            	throw new XmlBlackBoxException("Class not found "+currentNavigationFile.getNavigationClassFile(), cnfe);
	            }
	            
	            logger.info("seleniumWebDriverClass "+seleniumWebDriverClass);
	            logger.info("Tag included in xml file "+this.getFileName());
	            
	        	try{
	        		seleniumWebDriverImpl = (NewSeleniumWebDriverNavigation)seleniumWebDriverClass.getConstructor().newInstance();
	            }catch(ClassCastException cce){
	            	logger.error("ClassCastException ", cce);
	            	throw new XmlBlackBoxException("Selenium class ("+currentNavigationFile.getNavigationClassFile()+") has to implement NewSeleniumNavigation.", cce);
	            }
	            logger.debug(" NavigationFile getParameters().getParametersAsProperties() "+currentNavigationFile.getParameters().getParametersAsProperties());
	
	            Object seleniumSpeed = memory.getOrCreateRepository(Repository.FILE_PROPERTIES).get("SELENIUM_SPEED");
	            try{
	                memory.overrideRepository(Repository.PARAMETERS, currentNavigationFile.getParameters().getParametersAsProperties());
	                logger.info("Current Navigation file element	\n"+new XMLOutputter().outputString(currentNavigationFile.xmlElement));
	                logger.info("Current browser session "+browser_session);
	                driver = seleniumWebDriverImpl.executeNavigation(driver, memory, currentNavigationFile.getParameters().getParametersAsProperties());
	            }catch(Exception e){
	
	                logger.error("Exception during navigation. ", e);
	                String seleniumPath = (String)memory.getOrCreateRepository(Repository.FILE_PROPERTIES).get("SELENIUM_PATH_ERROR");
	                logger.info("seleniumPath : "+seleniumPath);
	
	                if (seleniumPath==null){
	                    seleniumPath = "";
	                }
	                if (driver!=null && driver.getPageSource()!=null){
	
	                   File path = new File(seleniumPath);
	                   if (!path.exists()){
	                       path.mkdirs();
	                   }
	
	                   String fileName = seleniumPath+"/"+currentNavigationFile.getClass()+"_"+getName().replace(' ', '_')+"_selenium";
	                   logger.info("Creazione del file di output di errore "+fileName+".html");
	                   FileWriter fstream = new FileWriter(fileName+".html");
	                   BufferedWriter out = new BufferedWriter(fstream);
	                   out.write(driver.getPageSource());
	                   out.close();
	                }
	
	                String timeOpenSeleniumOnError = (String)memory.getOrCreateRepository(Repository.FILE_PROPERTIES).get("TIME_OPEN_SELENIUM_ON_ERROR");
	                int wait = 10;
	                if (timeOpenSeleniumOnError!=null){
	                    wait = new Integer(timeOpenSeleniumOnError).intValue();
	                }
	               logger.error("Exception during navigation. Stop "+(wait)+" seconds to visualize Selenium window");
	               Thread.sleep((wait*1000));
	               throw e;
	            }
            }
        }

        return driver;
    }

    @Override
    public void execute(MemoryData memory) throws Exception {
        Properties webNavigationProp = (Properties)memory.getOrCreateRepository(Repository.WEB_NAVIGATION);
            logger.info("webNavigationProp "+webNavigationProp);
            WebDriver driver = null;

            try{
            	memory.get(this.getBrowserSession());
            }catch(VariableNotFound vnf){
                if (browser_type.equals(FIREFOX)){
	            	driver = new FirefoxDriver();
	            }else if (browser_type.equals(IEXPLORER)){
	            	driver = new InternetExplorerDriver();
	            }else if (browser_type.equals(CHROME)){
	            	driver = new ChromeDriver();
	            }else if (browser_type.equals(PHANTOMJS)){
	            	String phantomDriverPath= (String)memory.getOrCreateRepository(Repository.FILE_PROPERTIES).get("PHANTOMJS_DRIVER_PATH");
	                logger.info("phantomjs.binary.path "+phantomDriverPath);
	                if (phantomDriverPath==null){
	                	throw new TestException("PHANTOMJS_DRIVER_PATH not defined in properties file. Download from http://phantomjs.org/download.html and set the installation path in properties file");
	                }else{
		                File filePath = new File(phantomDriverPath);
		                if (!filePath.exists()){
		                	throw new TestException(phantomDriverPath+ " not found. Download Phantomjs from http://phantomjs.org/download.html ");
		                }
	                }
	                System.setProperty("phantomjs.binary.path", phantomDriverPath);
	            	driver = new PhantomJSDriver();
	            }else if (browser_type.equals(HTMLUNIT)){
	            	driver = new HtmlUnitDriver();
	            }

	            memory.set(this.getBrowserSession(), driver);
            }

            logger.info("this.getBrowserSession() "+this.getBrowserSession());
            logger.info("(WebDriver)memory.get(this.getBrowserSession()) "+(WebDriver)memory.get(this.getBrowserSession()));
            executeSeleniumWebDriver(memory, (WebDriver)memory.get(this.getBrowserSession()));
    }
    
    public static String defaultSeleniumClient = "SELENIUM-WEB-DRIVER-CLIENT@webNavigation";

    /**
     * @return the defaultSeleniumClient
     */
    public String getSeleniumClient() {
        return defaultSeleniumClient;
    }

    /**
     * @param defaultSeleniumClient the defaultSeleniumClient to set
     */
    public void setSeleniumClient(String defaultSeleniumClient) {
        this.defaultSeleniumClient = defaultSeleniumClient;
    }

	public static String getBrowserType() {
		return browser_type;
	}

	public static void setBrowserType(String browserType) {
		browser_type = browserType;
	}

	public static String getBrowserSession() {
		return browser_session;
	}

	public static void setBrowserSession(String browserSession) {
		browser_session = browserSession;
	}


}