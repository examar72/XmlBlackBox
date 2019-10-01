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

import java.io.File;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.jdom.Element;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;
import org.xmlblackbox.test.infrastructure.exception.RepositoryNotFound;
import org.xmlblackbox.test.infrastructure.exception.XmlBlackBoxException;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

/**
 *
 * @author examar
 */
public class SeleniumServerManager extends XmlStep{

    private final static Logger logger = Logger.getLogger(SeleniumServerManager.class);

    public static String START = "start";
    public static String STOP = "stop";

	private String port;
    private String operation;


	public SeleniumServerManager(Element el) throws Exception {
		super(el);
        build(el);
	}

    @Override
	public void build(Element seleniumServerElement) throws Exception {
		logger.info("seleniumServerElement "+seleniumServerElement);
		logger.info("seleniumServerElement.getAttributeValue(nome) "+seleniumServerElement.getAttributeValue("name"));
        logger.info("seleniumServerElement.getAttributeValue(operation) "+seleniumServerElement.getAttributeValue("operation"));

        if (seleniumServerElement!=null){
        	this.setName(seleniumServerElement.getAttributeValue("name"));
        	this.setOperation(seleniumServerElement.getAttributeValue("operation"));
            this.setPort(seleniumServerElement.getAttributeValue("port"));
        }
    }

    @Override
    public void execute(MemoryData session) throws Exception {

        SeleniumServer server = null;
        if (operation.equals(SeleniumServerManager.START)
        		&& session.existRepository(Repository.WEB_NAVIGATION) 
        		&& session.getRepository(Repository.WEB_NAVIGATION).get(this.getName()) != null){
            logger.debug("Selenium server already started");
        }else{
            if (operation.equals(SeleniumServerManager.START))
                server = startServer(session);
            else{
            	server = (SeleniumServer) session.getRepository(Repository.WEB_NAVIGATION).get("SELENIUM-SERVER-EXAMPLE-003");            	
                server.stop();
            }

            logger.debug("this.getName() "+this.getName());
            session.getOrCreateRepository(Repository.WEB_NAVIGATION).put(this.getName(), server);
        }

    }


    private SeleniumServer startServer(MemoryData session) throws XmlBlackBoxException, RepositoryNotFound {

        logger.info("Starting selenium server");

        SeleniumServer server = null;
        Properties prop = (Properties)session.getRepository(Repository.FILE_PROPERTIES);

        RemoteControlConfiguration remote = new RemoteControlConfiguration();
		remote.setPort(new Integer(port));
		if (prop.getProperty("SELENIUM_BROWSER_PROFILE_LOCATION")!=null && !prop.getProperty("SELENIUM_BROWSER_PROFILE_LOCATION").equalsIgnoreCase("")){
		    remote.setProfilesLocation(new File(prop.getProperty("SELENIUM_BROWSER_PROFILE_LOCATION")));
		    remote.setFirefoxProfileTemplate(new File(prop.getProperty("SELENIUM_BROWSER_PROFILE_LOCATION")));
		}
		
		try {
			server = new SeleniumServer(true, remote);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
            logger.error("",e);
		}
		try {
            logger.info("Starting selenium server.....");
			server.start();
		}
		catch (Exception e) {
          logger.error("", e);
          throw new XmlBlackBoxException("Exception starting SeleniumServer");
		}

        return server;

    }

    @Override
    public String getRepositoryName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /**
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @return the operation
     */
    public String getOperation() {
        return operation;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }


}
