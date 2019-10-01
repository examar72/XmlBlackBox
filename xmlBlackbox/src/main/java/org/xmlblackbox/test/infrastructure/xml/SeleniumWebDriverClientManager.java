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

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

/**
 *
 * @author examar
 */
public class SeleniumWebDriverClientManager extends XmlStep{

    private final static Logger logger = Logger.getLogger(SeleniumWebDriverClientManager.class);

    private String IE = "IEXPLORER";
    private String FIREFOX = "FIREFOX";
    private String CHROME = "CHROME";
    
    private String domain;
    private String browserType;



	public SeleniumWebDriverClientManager(Element el) throws Exception {
		super(el);
        build(el);
	}

    @Override
	public void build(Element webDriverClient) throws Exception {
		logger.info("webDriverClient "+webDriverClient);
		logger.info("webDriverClient.getAttributeValue(nome) "+webDriverClient.getAttributeValue("name"));
		
        if (webDriverClient!=null){
            this.setName(webDriverClient.getAttributeValue("name"));
            this.setDomain(webDriverClient.getAttributeValue("domain"));
            this.setBrowserType(webDriverClient.getAttributeValue("browser-type"));
        }
    }

    @Override
    public void execute(MemoryData session) throws Exception {
        WebDriver driver = null;
		logger.info("this.getName() "+this.getName());
		if (getBrowserType().equals(this.IE)){
			driver = new InternetExplorerDriver();
		}else if (getBrowserType().equals(this.FIREFOX)){
			driver = new FirefoxDriver();
		}else if (getBrowserType().equals(this.CHROME)){
			driver = new ChromeDriver();
		}
        session.getOrCreateRepository(Repository.WEB_NAVIGATION).put(this.getName(), driver);
    }



    @Override
    public String getRepositoryName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /**
     * @return the domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * @param domain the domain to set
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * @return the browserType
     */
    public String getBrowserType() {
        return browserType;
    }

    /**
     * @param browserType the browserType to set
     */
    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }


}
