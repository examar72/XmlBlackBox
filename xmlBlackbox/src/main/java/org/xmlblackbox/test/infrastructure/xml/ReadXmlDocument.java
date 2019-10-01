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


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlOptions;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.xmlblackbox.test.infrastructure.exception.IncludeFileNotFound;
import org.xmlblackbox.test.infrastructure.exception.IncludeLoopException;
import org.xmlblackbox.test.infrastructure.exception.XmlBlackBoxException;
import org.xmlblackbox.test.infrastructure.exception.XmlValidationFault;
import org.xmlblackbox.xsd.TESTDocument;


public class ReadXmlDocument {
	
	private final static Logger log = Logger.getLogger(ReadXmlDocument.class);
	
	private List listaCompleta = new Vector();
	private List listaDBCheck = new Vector();
	private List listaHttpClient = new Vector();
	private List listaCheck = new Vector();
	private List listaWsClient = new Vector();
	private List listaSetVariable = new Vector();
	private List listaExecuteQuery = new Vector();
	private List listaCheckXmlContent = new Vector();
	private List listaDbConnection= new Vector();
	private List listaPlugin = new Vector();
	private List listaWaitTask = new Vector();
	private List listaSeleniumServer = new Vector();
	private List listaSeleniumClient = new Vector();
	private List listaSeleniumNavigation = new Vector();
	private List listaValidateXml = new Vector();

	private int includeLevelCounter = 1;

	private String nomeTest = null;
	

	public ReadXmlDocument(String navigationFile) throws Exception{
        InputStream iSValidate = this.getClass().getResourceAsStream(navigationFile);
        //navigationFile.mark(1000000);
        validateXml(iSValidate, navigationFile);
        //navigationFile.reset();
		InputStream iS = this.getClass().getResourceAsStream(navigationFile);
        loadFile(iS, navigationFile);
	}

    private void validateXml(InputStream iSValidate, String fileName) throws XmlValidationFault{
            TESTDocument testDoc = null;
			try{
                testDoc = TESTDocument.Factory.parse(iSValidate);
            }catch(Throwable e){
                log.error("e "+e);
                log.error("THROWABLE ", e);
                throw new XmlValidationFault("");
            }
			ArrayList validationErrors = new ArrayList();
			XmlOptions voptions = new XmlOptions();
			voptions.setErrorListener(validationErrors);
			boolean valid = testDoc.validate(voptions);
			if(valid)
			{
				log.info("Xml valid ("+fileName+")");
			}
			else
			{
				log.info(fileName+" is not a valid xml file");
				Iterator itr = validationErrors.iterator();
				String errors = "";
				while(itr.hasNext())
				{
					String next = itr.next().toString();
					errors = errors +next;

					log.error(next);
				}
				throw new XmlValidationFault("Invalid file :"+errors);
			}

    }

	private void loadFile(InputStream navigationFile, String fileName) throws Exception{
		loadFile(navigationFile, fileName, false);
	}

	private void loadFile(InputStream navigationFile, String fileName, boolean isIncluded) throws Exception{
        SAXBuilder saxBuilder = new SAXBuilder();
        //saxBuilder.setValidation(false);

        Document documentJdom = null;

        log.debug("navigationFile "+navigationFile);
        try {
            documentJdom = saxBuilder.build(navigationFile);
	    } catch (JDOMException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
            e.printStackTrace();
        }
	    
	    Element element = documentJdom.getRootElement();	    
	    Iterator elementList = element.getChildren().iterator();
	
	    setNomeTest(element.getAttributeValue("name"));
    
	    log.debug("[Parsing xml elements]");
	    while (elementList.hasNext()){
	    	Element element2 = (Element) elementList.next();
	    	log.debug("[Element : " + element2 + " Name : " + element2.getName());
            log.debug("element2 "+new XMLOutputter().outputString(element2));
        	try {
		    	if ("CHECK-DB".equals(element2.getName())) {
	            	CheckDatabase dbCheck = new CheckDatabase(element2);
		            listaDBCheck.add(dbCheck);
		            dbCheck.setIncluded(isIncluded);
		            getListaCompleta().add(dbCheck);
		            dbCheck.setFileName(fileName);
		        } else if ("VALIDATE-XML".equals(element2.getName())) {
		        	XmlValidate xmlValidate= new XmlValidate(element2);
	            	listaValidateXml.add(xmlValidate);
		            xmlValidate.setIncluded(isIncluded);
		            getListaCompleta().add(xmlValidate);
		            xmlValidate.setFileName(fileName);
		        } else if ("SELENIUM".equals(element2.getName())) {
		        	ClientWeb httpClient= new ClientWeb(element2, ClientWeb.SELENIUM);
	            	getListaSeleniumNavigation().add(httpClient);
		            httpClient.setIncluded(isIncluded);
		            getListaCompleta().add(httpClient);
		            httpClient.setFileName(fileName);
		        } else if ("HTTPTESTER".equals(element2.getName())) {
		        	ClientWeb httpClient= new ClientWeb(element2, ClientWeb.HTTPTESTER);
	            	listaHttpClient.add(httpClient);
		            httpClient.setIncluded(isIncluded);
		            getListaCompleta().add(httpClient);
		            httpClient.setFileName(fileName);
		        } else if ("UPLOADER".equals(element2.getName())) {
		        	HTTPUploader httpUploader= new HTTPUploader(element2);
	            	listaHttpClient.add(httpUploader);
	            	httpUploader.setIncluded(isIncluded);
		            getListaCompleta().add(httpUploader);
		            httpUploader.setFileName(fileName);
		        } else if ("WEB-SERVICE-CLIENT".equals(element2.getName())) {
		        	WebServiceClient wsClient = new WebServiceClient(element2);
		        	listaWsClient.add(wsClient);
		        	wsClient.setIncluded(isIncluded);
		        	getListaCompleta().add(wsClient);
		            wsClient.setFileName(fileName);
		        } else if ("SET-VARIABLE".equals(element2.getName())) {
		        	SetVariable setVariable = new SetVariable(element2);
		        	listaSetVariable.add(setVariable);
		        	setVariable.setIncluded(isIncluded);
		        	getListaCompleta().add(setVariable);
		            setVariable.setFileName(fileName);
		        } else if ("RUN-PLUGIN".equals(element2.getName())) {
		        	RunPlugin runPlugin = new RunPlugin(element2);
		        	listaPlugin.add(runPlugin);
		        	runPlugin.setIncluded(isIncluded);
		        	getListaCompleta().add(runPlugin);	        	
		            runPlugin.setFileName(fileName);
		        } else if ("EXECUTE-QUERY".equals(element2.getName())) {
		        	RunQuery executeQuery = new RunQuery(element2);
		        	listaExecuteQuery.add(executeQuery);
		        	executeQuery.setIncluded(isIncluded);
		        	getListaCompleta().add(executeQuery);
		            executeQuery.setFileName(fileName);
		        } else if ("XML-CONTENT".equals(element2.getName())) {
                    CheckInsertXmlContent checkXmlContent = new CheckInsertXmlContent(element2);
                    listaCheckXmlContent.add(checkXmlContent);
                    checkXmlContent.setIncluded(isIncluded);
		        	getListaCompleta().add(checkXmlContent);
		            checkXmlContent.setFileName(fileName);
		        } else if ("DB-CONNECTIONS".equals(element2.getName())) {
                    XmlDbConnections xmlDbConnections = new XmlDbConnections(element2);
                    listaDbConnection.add(xmlDbConnections);
                    getListaCompleta().add(xmlDbConnections);
		            xmlDbConnections.setFileName(fileName);
		        } else if ("WAIT-TASK".equals(element2.getName())) {
                    WaitTask waitTask = new WaitTask(element2);
                    listaWaitTask.add(waitTask);
                    waitTask.setIncluded(isIncluded);
		        	getListaCompleta().add(waitTask);
		            waitTask.setFileName(fileName);
    	        } else if ("SELENIUM-SERVER".equals(element2.getName())) {
                    SeleniumServerManager seleniumServer  = new SeleniumServerManager(element2);
                    listaSeleniumServer.add(seleniumServer);
                    seleniumServer.setIncluded(isIncluded);
		        	getListaCompleta().add(seleniumServer);
		            seleniumServer.setFileName(fileName);
    	        } else if ("SELENIUM-CLIENT".equals(element2.getName())) {
                    SeleniumClientManager seleniumClient  = new SeleniumClientManager(element2);
                    listaSeleniumClient.add(seleniumClient);
                    seleniumClient.setIncluded(isIncluded);
                    getListaCompleta().add(seleniumClient);
		            seleniumClient.setFileName(fileName);
    	        } else if ("SELENIUM-WEB-DRIVER-CLIENT".equals(element2.getName())) {
                    SeleniumClientWebDriver seleniumClientWebDriver  = new SeleniumClientWebDriver(element2);
                    listaSeleniumClient.add(seleniumClientWebDriver);
                    seleniumClientWebDriver.setIncluded(isIncluded);
                    getListaCompleta().add(seleniumClientWebDriver);
                    seleniumClientWebDriver.setFileName(fileName);
                } else if ("INCLUDE-FILE".equals(element2.getName())) {
                	includeLevelCounter++;
                    String includeFileName = element2.getAttributeValue("filename");
                    
                    log.info("includeLevelCounter "+includeLevelCounter);
                    
                    log.debug("filename "+includeFileName);
                    if (includeLevelCounter>100){
                    	throw new IncludeLoopException("Include file depth is greater than 100. Perhaps there is an endless loop.");
                    }
                    
                    InputStream isValidate = this.getClass().getResourceAsStream(includeFileName);
                    if (isValidate==null){
                    	log.error("Include file ("+includeFileName+") not found");
                    	throw new IncludeFileNotFound("Include file not found "+includeFileName);
                    }

                    try{
                    	validateXml(isValidate, includeFileName);
                    }catch(XmlValidationFault xvf){
                    	log.error("The include file "+includeFileName+" is invalid", xvf);
                    	throw xvf;
                    }
                    InputStream includeFile = this.getClass().getResourceAsStream(includeFileName);
                    if (includeFile==null){
                    	throw new IncludeFileNotFound("Include file not found "+includeFileName);
                    }
                    this.loadFile(includeFile, includeFileName, true);
                }else{
            		log.error("Exception. The element " + element2.getName()+" does not exist");
                }
        	} catch (XmlBlackBoxException e) {
        		log.fatal("[!] Error during object build " + new XMLOutputter().outputString(element2));
        		throw e;
        	} catch (Exception e) {
        		log.fatal("[!] Error during object build " + new XMLOutputter().outputString(element2));
        		throw e;
			}
	   }
	   log.info("[Parsing xml elements]");
		
	}

	public void setListaCompleta(List listaCompleta) {
		this.listaCompleta = listaCompleta;
	}

	public List getListaCompleta() {
		return listaCompleta;
	}

	public void setNomeTest(String nomeTest) {
		this.nomeTest = nomeTest;
	}

	public String getNomeTest() {
		return nomeTest;
	}

	public List getListaCheck() {
		return listaCheck;
	}

	public void setListaCheck(List listaCheck) {
		this.listaCheck = listaCheck;
	}

	public List getListaDBCheck() {
		return listaDBCheck;
	}

	public void setListaDBCheck(List listaDBCheck) {
		this.listaDBCheck = listaDBCheck;
	}

	public List getListaHttpClient() {
		return listaHttpClient;
	}

	public void setListaHttpClient(List listaHttpClient) {
		this.listaHttpClient = listaHttpClient;
	}

	public List getListaWsClient() {
		return listaWsClient;
	}

	public void setListaWsClient(List listaWsClient) {
		this.listaWsClient = listaWsClient;
	}

    /**
     * @return the listaSeleniumServer
     */
    public List getListaSeleniumServer() {
        return listaSeleniumServer;
    }

    /**
     * @param listaSeleniumServer the listaSeleniumServer to set
     */
    public void setListaSeleniumServer(List listaSeleniumServer) {
        this.listaSeleniumServer = listaSeleniumServer;
    }

    /**
     * @return the listaSeleniumClient
     */
    public List getListaSeleniumClient() {
        return listaSeleniumClient;
    }

    /**
     * @param listaSeleniumClient the listaSeleniumClient to set
     */
    public void setListaSeleniumClient(List listaSeleniumClient) {
        this.listaSeleniumClient = listaSeleniumClient;
    }

    /**
     * @return the listaSeleniumNavigation
     */
    public List getListaSeleniumNavigation() {
        return listaSeleniumNavigation;
    }

    /**
     * @param listaSeleniumNavigation the listaSeleniumNavigation to set
     */
    public void setListaSeleniumNavigation(List listaSeleniumNavigation) {
        this.listaSeleniumNavigation = listaSeleniumNavigation;
    }

		
}

/*
 * $Id: $
 * $Log:$
 */
