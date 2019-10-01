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

package org.xmlblackbox.test.infrastructure.util;


import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;

public abstract class DefaultSeleniumEnv{

    private final static Logger logger = Logger.getLogger(DefaultSeleniumEnv.class);

	private static DefaultSelenium seleniumClient;
	private static SeleniumServer server = null;

 	private void stopServer() {
		try {
			server.stop();
		}
		catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	public static DefaultSelenium getSeleniumClient(Properties prop) {

        logger.debug("SELENIUM_BASE_URL "+prop.getProperty("SELENIUM_BASE_URL"));
        logger.debug("SELENIUM_HOST_SERVER "+prop.getProperty("SELENIUM_HOST_SERVER"));
        logger.debug("SELENIUM_PORT_SERVER "+prop.getProperty("SELENIUM_PORT_SERVER"));
        logger.debug("SELENIUM_BROWSER_TYPE "+prop.getProperty("SELENIUM_BROWSER_TYPE"));

        String url = prop.getProperty("SELENIUM_BASE_URL");
        String server_ip = prop.getProperty("SELENIUM_HOST_SERVER");
        Integer server_port = new Integer(prop.getProperty("SELENIUM_PORT_SERVER"));
        String browserType= prop.getProperty("SELENIUM_BROWSER_TYPE");

        DefaultSelenium currentSelenium;

        logger.debug("server_ip "+server_ip);
        logger.debug("server_port "+server_port);
        logger.debug("browserType "+browserType);
        logger.debug("url "+url);
		if (seleniumClient == null) {
			currentSelenium = new DefaultSelenium(server_ip, server_port, browserType, url){
				public void open(String url) { commandProcessor.doCommand("open", new String[] {url,"true"});};
			};
			try{
	            logger.info("Browser "+browserType+" starting......");
				currentSelenium.start();
	            logger.info("Browser "+browserType+" started (server ip:"+server_ip+", server port:"+server_port+", browser type:"+browserType+")");
			}catch(Exception e){
				logger.error("Exception ", e);
			}
            logger.debug("selenium "+currentSelenium);
			seleniumClient = currentSelenium;
		}
        return seleniumClient;
	}

	public static SeleniumServer startServer(Properties prop) {

        RemoteControlConfiguration remote = new RemoteControlConfiguration();
        String port = prop.getProperty("SELENIUM_PORT_SERVER");
		remote.setPort(new Integer(port));

		try {
			server = new SeleniumServer(true, remote);
            logger.info("Selenium server starting......");
			server.start();
            logger.info("Selenium server started (server port "+port+")");
		}
		catch (Exception e) {
            logger.error("Exception during server starting. Maybe the Selenium server has already started. " +
            		"Port ("+port+")",e);
		}

        return server;

	}

}
