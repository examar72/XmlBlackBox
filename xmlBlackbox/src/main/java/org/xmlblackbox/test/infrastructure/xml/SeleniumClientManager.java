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

import com.thoughtworks.selenium.DefaultSelenium;
import org.apache.log4j.Logger;
import org.jdom.Element;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

/**
 *
 * @author examar
 */
public class SeleniumClientManager extends XmlStep{

    private final static Logger logger = Logger.getLogger(SeleniumClientManager.class);

	private int server_port;
	private String server_ip;
    private String domain;
    private String browserType;



	public SeleniumClientManager(Element el) throws Exception {
		super(el);
        build(el);
	}

    @Override
	public void build(Element mainClientElement) throws Exception {
		logger.info("mainClientElement "+mainClientElement);
		logger.info("mainClientElement.getAttributeValue(nome) "+mainClientElement.getAttributeValue("name"));
		
        if (mainClientElement!=null){
            this.setName(mainClientElement.getAttributeValue("name"));
            this.setDomain(mainClientElement.getAttributeValue("domain"));
            this.setIp(mainClientElement.getAttributeValue("server-ip"));
            this.setPort(new Integer(mainClientElement.getAttributeValue("server-port")).intValue());
            this.setBrowserType(mainClientElement.getAttributeValue("browser-type"));
        }
    }

    @Override
    public void execute(MemoryData session) throws Exception {
        DefaultSelenium selenium = null;
		logger.info("this.getName() "+this.getName());
		if (session.getOrCreateRepository(Repository.WEB_NAVIGATION).get(this.getName()) != null) {
            logger.info("Browser already started");
        }else{
            selenium = new DefaultSelenium(server_ip, server_port, browserType, domain);
            try{
				selenium.start();
			}catch(Exception e){
				logger.error("Exception ", e);
			}

            session.getOrCreateRepository(Repository.WEB_NAVIGATION).put(this.getName(), selenium);

        }
    }



    @Override
    public String getRepositoryName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /**
     * @return the port
     */
    public int getPort() {
        return server_port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.server_port = port;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return server_ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.server_ip = ip;
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
