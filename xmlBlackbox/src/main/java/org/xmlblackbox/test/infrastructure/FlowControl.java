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

package org.xmlblackbox.test.infrastructure;


import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;
import org.dbunit.dataset.DataSetException;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.openqa.selenium.server.SeleniumServer;
import org.xmlblackbox.test.infrastructure.exception.TestException;
import org.xmlblackbox.test.infrastructure.exception.XmlBlackBoxException;
import org.xmlblackbox.test.infrastructure.exception.XmlValidationFault;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.util.Configurator;
import org.xmlblackbox.test.infrastructure.util.DefaultSeleniumEnv;
import org.xmlblackbox.test.infrastructure.util.MemoryData;
import org.xmlblackbox.test.infrastructure.xml.ClientWeb;
import org.xmlblackbox.test.infrastructure.xml.ReadXmlDocument;
import org.xmlblackbox.test.infrastructure.xml.XmlStep;

import com.thoughtworks.selenium.DefaultSelenium;



/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company:xmlblackbox
 * </p>
 *
 * @author Examar.xbb
 *
 */

public class FlowControl extends TestCase {

    private final static Logger log = Logger.getLogger(FlowControl.class);

    private static MemoryData memory = new MemoryData();

	private String nomeTestCase = null;

	public FlowControl(String msg) {
		super(msg);
	}

	public static Test suite() {
		log.debug("Log FlowControl");
		TestSuite suite = new TestSuite(FlowControl.class);
		return suite;
	}

	public void loadPropsInDomain(Properties testProp) {
		memory.overrideRepository(Repository.FILE_PROPERTIES, testProp);
	}

	private void replacingVariableXml(Object obj,MemoryData memoryData) throws Exception{

		try {
			if(XmlStep.class.isAssignableFrom(obj.getClass()) ){

				XmlStep el=(XmlStep)obj;
				log.info("[-] Reloading variables tag : " + el.getXmlTagName() + " class : " + obj.getClass().getSimpleName());
				el.reload(memoryData);

			}
		} catch (XmlBlackBoxException e) {
			log.error("[!] Reloading variables failed", e);
			throw e;
		} catch (Exception e) {
			log.error("[!] Reloading variables failed", e);
			throw e;
		}
	}


    public MemoryData execute(Class testClass, Properties prop)	throws TestException, Exception {

        log.debug("execute (testClass, prop)");
    	String fileConfigTest = "." + testClass.getCanonicalName();
    	fileConfigTest = fileConfigTest.replaceAll("\\.", "/");
    	fileConfigTest = fileConfigTest + ".xml";

        return execute(fileConfigTest, prop);

    }



    public MemoryData execute(String fileConfigTest, Properties prop) throws TestException, Exception {
        int step = 1;
        Object obj = null;
		boolean testOK = false;
		int count = 1;
		while(!testOK && count<=3) {
			try {

				log.debug("execute (fileConfigTest, prop)");
				log.info("[ START TEST CASE : " + fileConfigTest.substring(0, fileConfigTest.indexOf(".")) + " ]");
				Properties xmlBlackboxProp = Configurator.getProperties("xmlBlackbox.properties");
				log.info("xmlBlackbox.properties " + xmlBlackboxProp);

				log.debug("[Starting Memory & File Properties]");
				memory.overrideRepository(Repository.FILE_PROPERTIES, xmlBlackboxProp);

				memory.addToRepository(Repository.FILE_PROPERTIES, prop);
				log.debug("[Starting Memory & File Properties][OK]");

				Hashtable input = memory.getOrCreateRepository(Repository.INPUT_PROPERTIES);
				memory.addToRepository(Repository.INPUT_PROPERTIES, prop);

				if (memory.get("RESET_SESSION@" + Repository.FILE_PROPERTIES).equals("true")) {
					memory = new MemoryData();
					memory.debugMemory();
					memory.overrideRepository(Repository.FILE_PROPERTIES, xmlBlackboxProp);
					memory.addToRepository(Repository.FILE_PROPERTIES, prop);
					memory.getOrCreateRepository(Repository.INPUT_PROPERTIES);
					memory.addToRepository(Repository.INPUT_PROPERTIES, prop);
				}
				memory.debugMemory();

				String stepConfig = System.getenv("XBB_STEP");
				log.debug("[Reading XML TestCase]");

				ReadXmlDocument readXmlDocument = null;
				try {
					readXmlDocument = new ReadXmlDocument(fileConfigTest);
				} catch (XmlValidationFault e1) {
					log.error("[!] Exception during " + fileConfigTest + " validation", e1);
					throw new XmlBlackBoxException("Exception during " + fileConfigTest + " validation", e1);
				} catch (DataSetException e1) {
					log.error("[!] Data set exception", e1);
					throw new XmlBlackBoxException("Data set exception in file " + fileConfigTest, e1);
				} catch (IOException e1) {
					log.error("[!] Unable to read test file ", e1);
					throw new XmlBlackBoxException("Unable to read test file " + fileConfigTest, e1);
				} catch (XmlBlackBoxException e1) {
					log.error("[!] XmlBlackBoxException ", e1);
					throw e1;
				} catch (Exception e1) {
					log.error("[!] Generic exception ", e1);
					throw new XmlBlackBoxException("Generic exception " + fileConfigTest, e1);
				}
				nomeTestCase = readXmlDocument.getNomeTest();
				log.info("[Preparing starting TestCase : " + nomeTestCase + "]");
				log.debug("[Reading XML TestCase][OK]");

				Iterator iterElement = readXmlDocument.getListaCompleta().iterator();

				log.debug("[TestCase number of steps  : " + readXmlDocument.getListaCompleta().size() + "]");
				while (iterElement.hasNext()) {
					log.info("[*][Starting execution TestCase : " + nomeTestCase + " Step : " + step + "]");
					obj = iterElement.next();

					log.info("obj instanceof ClientWeb " + (obj instanceof ClientWeb));
					if (obj instanceof ClientWeb) {
						log.debug("ClientWeb type " + ((ClientWeb) obj).getType());

						if (((ClientWeb) obj).getType().equalsIgnoreCase(ClientWeb.SELENIUM)) {
							if (readXmlDocument.getListaSeleniumNavigation().size() > 0
									&& readXmlDocument.getListaSeleniumServer().size() == 0
									&& !memory.existVariable(ClientWeb.defaultSeleniumServer)) {
								log.info("No SELENIUM-SERVER tag defined. " +
										"Default SeleniumServer starting with config from the properties file");
								SeleniumServer server = DefaultSeleniumEnv.startServer(((Properties) memory.getRepository(Repository.FILE_PROPERTIES)));
								memory.set(ClientWeb.defaultSeleniumServer, server);
							}

							DefaultSelenium selenium;
							if (readXmlDocument.getListaSeleniumNavigation().size() > 0
									&& readXmlDocument.getListaSeleniumClient().size() == 0
									&& !memory.existVariable(ClientWeb.defaultSeleniumClient)) {
								log.info("No SELENIUM-CLIENT tag defined. " +
										"Defasult Selenium client starting using config config from the properties file");
								selenium = DefaultSeleniumEnv.getSeleniumClient((Properties) memory.getRepository(Repository.FILE_PROPERTIES));
								memory.set(ClientWeb.defaultSeleniumClient, selenium);
							}
						}
					}
					XmlStep xmlStep = ((XmlStep) obj);
					log.info("xmlStep.isToBeDoneItWhenIncluded() " + (xmlStep.isToBeDoneItWhenIncluded()));
					log.info("!xmlStep.isIncluded() " + !xmlStep.isIncluded());
					log.info("!xmlStep.isIncluded() || (xmlStep.isIncluded() && xmlStep.isToBeDoneItWhenIncluded()) " + (!xmlStep.isIncluded() || (xmlStep.isIncluded() && xmlStep.isToBeDoneItWhenIncluded())));
					log.info("(xmlStep.isIncluded() && xmlStep.isToBeDoneItWhenIncluded()) " + ((xmlStep.isIncluded() && xmlStep.isToBeDoneItWhenIncluded())));
					if (!xmlStep.isIncluded() || (xmlStep.isIncluded() && xmlStep.isToBeDoneItWhenIncluded())) {
						log.info("[Identify type node & Replacing variable xml]");
						replacingVariableXml(obj, memory);

						log.debug("[Identify type node & Replacing variable xml][OK]");
						log.info("[Executing node] " + ((XmlStep) obj).getName());
						xmlStep.checkMandatoryField();
						xmlStep.execute(memory);
					} else {
						log.info("Step skipped!!!!!! Step '" + xmlStep.getName() + "' skipped because of do-it-if-included property to false");
						log.info("- xmlElement 	\n" + new XMLOutputter().outputString(xmlStep.getXmlElement()));
						log.debug(new XMLOutputter().outputString(xmlStep.getXmlElement()));
						log.info(" Tag included in xml file " + xmlStep.getFileName());

					}
					log.debug("[Executing node][OK]");
					step++;
					if (stepConfig != null && stepConfig.equals("" + step)) {
						log.info("Exit configuration (-DXBB_STEP=" + stepConfig + ") a step " + stepConfig);
						break;
					}
				}
				testOK = true;
				return memory;
			} catch (XmlBlackBoxException e) {
				if (memory != null) {
					memory.debugMemory();
				}
				if (obj != null) {
					log.error("TestException in step " + step + " (" + ((XmlStep) obj).getXmlTagName() + ") of FlowControl.execute() ", e);
				} else {
					log.error("Exception in FlowControl.execute() ", e);
				}

				log.error("Numero di esecuzioni "+count);
				count++;
				if (count >3) {
					throw e;
				}
			} catch (Exception e) {
				if (memory != null) {
					memory.debugMemory();
				}
				if (obj != null) {
					String currentStep = ((XmlStep) obj).getXmlTagName();
					log.error("Exception in in step " + step + " (" + currentStep + ") FlowControl.execute() ", e);
				} else {
					log.error("Exception in FlowControl.execute() ", e);
				}
				log.error("Numero di esecuzioni "+count);
				count++;
				if (count >3) {
					throw e;
				}
			} catch (Error e) {
				if (memory != null) {
					memory.debugMemory();
				}

				if (obj != null) {
					String currentStep = ((XmlStep) obj).getXmlTagName();
					log.error("Exception in in step " + step + " (" + currentStep + ") FlowControl.execute() ", e);
				} else {
					log.error("Exception in FlowControl.execute() ", e);
				}
				log.error("Numero di esecuzioni "+count);
				count++;
				if (count >3) {
					throw e;
				}
			} finally {
				Hashtable hashtableConnection = memory.getOrCreateRepository(Repository.DB_CONNECTION);
				Enumeration elements = hashtableConnection.elements();
				while (elements.hasMoreElements()) {
					Object objTmp = elements.nextElement();
					log.info("objTmp " + objTmp);
					if (objTmp instanceof Connection) {
						Connection conn = (Connection) objTmp;
						try {
							if (conn != null) {
								conn.close();
								conn = null;
							}
						} catch (Exception e) {
							log.error("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
							log.error("Exception during database connection close", e);
							log.error("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
						}
					}
				}
			}
		}
		return memory;
	}

	public MemoryData getMemory() {
		return memory;
	}
	
	public void setMemory(MemoryData memory) {
		this.memory=memory;
	}

}

