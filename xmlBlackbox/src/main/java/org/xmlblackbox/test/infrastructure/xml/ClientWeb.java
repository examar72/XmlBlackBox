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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.xmlblackbox.test.infrastructure.exception.TestException;
import org.xmlblackbox.test.infrastructure.exception.XmlBlackBoxException;
import org.xmlblackbox.test.infrastructure.interfaces.NewSeleniumNavigation;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
/**
 * @author acrea
 */
public class ClientWeb extends XmlStep  {

	private final static Logger logger = Logger.getLogger(ClientWeb.class);

	public static String HTTPTESTER = "HTTPTESTER";
	public static String SELENIUM = "SELENIUM";
	public static String UPLOAD_FILE = "UPLOAD_FILE";

	private Parameters parameters;
	private String outputPrefix = null;
	private List<NavigationFile> navigationFileList= new ArrayList();
	private String application = null;
	private String urlUpload= null;

	private boolean startConversation=false;
	private String waitingQuery = null;
	private String waitingTimeout= "100";
	private String waitingConnection = null;
	private String type;
    public static String defaultSeleniumClient = "SELENIUM-CLIENT@webNavigation";
    public static String defaultSeleniumServer = "SELENIUM-SERVER@webNavigation";


	int timeout = -1;

	public ClientWeb(Element el, String type) throws Exception {
		super(el);
        this.type = type;
        build(el);
	}

	public void build(Element mainElement) throws Exception {

//        Element parametersElement = mainElement.getChild("PARAMETERS", uriXsd);
//    	logger.debug("parametersElement "+parametersElement);
//    	if (parametersElement!=null){
//            parameters = new Parameters(parametersElement);
//        }
				
//		logger.debug("parameters.getParametersAsProperties() "+parameters.getParametersAsProperties());

		ClientWeb httpClient=this;
		logger.debug("clientElement "+mainElement);
		logger.debug("clientElement.getAttributeValue(nome) "+mainElement.getAttributeValue("name"));
		httpClient.setName(mainElement.getAttributeValue("name"));

        logger.debug("mainElement.getAttributeValue(\"selenium-client\") "+mainElement.getAttributeValue("selenium-client"));

        if(mainElement.getAttributeValue("selenium-client")!=null)
			httpClient.setSeleniumClient(mainElement.getAttributeValue("selenium-client"));

		if(mainElement.getAttributeValue("start")!=null)
			httpClient.setStartConversation(true);

        navigationFileList.clear();
        Iterator navigationFileIter = mainElement.getChildren("NAVIGATION-FILE", uriXsd).iterator();
		logger.debug("navigationFileIter.hasNext() "+navigationFileIter.hasNext());
        while (navigationFileIter.hasNext()){
            Element navigationFileXml= (Element) navigationFileIter.next();
            NavigationFile navigationFile = new NavigationFile(navigationFileXml);
            addNavigationFile(navigationFile);
        }

//    	Element fileNavigationXml= clientElement.getChild("NAVIGATION-FILE", uriXsd);
//
//    	if (fileNavigationXml!=null){
//    		httpClient.setFileNavigation(fileNavigationXml.getText());
//    	}

    	Element type = mainElement.getChild("TYPE", uriXsd);
    	if (type!=null){
    		httpClient.setType(type.getAttributeValue("name"));
    	}

//    	Element waitTerminated = mainElement.getChild("WAIT_TERMINATED", uriXsd);
//    	logger.info("waitTerminated "+waitTerminated);
//    	if (waitTerminated!=null){
//    		httpClient.setWaitingTerminated(true);
//    		httpClient.setWaitingQuery(waitTerminated.getAttributeValue("query"));
//
//    		logger.info("waitTerminated.getAttributeValue(\"timeout\") "+waitTerminated.getAttributeValue("timeout"));
//        	if (waitTerminated.getAttributeValue("timeout")!=null){
//    			httpClient.setWaitingTimeout(waitTerminated.getAttributeValue("timeout"));
//    		}
//            httpClient.setWaitingConnection(waitTerminated.getAttributeValue("connection"));
//
//    	}

//    	if (waitTerminated!=null){
//    		httpClient.setWaitingTerminated(true);
//    		httpClient.setWaitingTable(waitTerminated.getAttributeValue("table"));
//    		httpClient.setWaitingColumn(waitTerminated.getAttributeValue("column"));
//    		if (waitTerminated.getAttributeValue("timeout")!=null){
//    			httpClient.setWaitingTimeout(new Integer(waitTerminated.getAttributeValue("timeout")));
//    		}
//    		httpClient.setWaitingValue(waitTerminated.getText());
//
//    	}

	}

	public void setTimeout(int timeout){
		this.timeout = timeout;
	}

	public int getTimeout(){
		return timeout;
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

	public String getOutputPrefix() {
		return outputPrefix;
	}

	public void setOutputPrefix(String outputPrefix) {
		this.outputPrefix = outputPrefix;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public boolean isStartConversation() {
		return startConversation;
	}

	public void setStartConversation(boolean startConversation) {
		this.startConversation = startConversation;
	}

	public String getWaitingQuery() {
		return waitingQuery;
	}

	public void setWaitingQuery(String waitingQuery) {
		this.waitingQuery = waitingQuery;
	}

	public String getWaitingTimeout() {
		return waitingTimeout;
	}

	public void setWaitingTimeout(String waitingTimeout) {
		this.waitingTimeout = waitingTimeout;
	}

//	public boolean isWaitingTerminated() {
//		return waitingTerminated;
//	}
//
//	public void setWaitingTerminated(boolean waitTerminated) {
//		this.waitingTerminated = waitTerminated;
//	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setUrlUpload(String urlUpload) {
		this.urlUpload = urlUpload;
	}

	public String getUrlUpload() {
		return urlUpload;
	}

	@Override
	public String getRepositoryName() {
		return Repository.WEB_NAVIGATION;
	}


    public Selenium executeSelenium(MemoryData memory, Selenium selenium) throws TestException, Exception{

        Iterator<NavigationFile> iterNavigationFile = getNavigationFileList().iterator();
        NewSeleniumNavigation seleniumImpl = null;

        while(iterNavigationFile.hasNext()){

            NavigationFile currentNavigationFile = iterNavigationFile.next();

            
            
            Class seleniumClass;
            try{
    	    	// Instanzio la classe..
                seleniumClass=Class.forName(currentNavigationFile.getNavigationClassFile());
            }catch(ClassNotFoundException cnfe){
            	logger.error("ClassNotFoundException ", cnfe);
            	throw new XmlBlackBoxException("Class not found "+currentNavigationFile.getNavigationClassFile(), cnfe);
            }
            
            logger.info("seleniumClass "+seleniumClass);

        	try{
                seleniumImpl = (NewSeleniumNavigation)seleniumClass.getConstructor().newInstance();
            }catch(ClassCastException cce){
            	logger.error("ClassCastException ", cce);
            	throw new XmlBlackBoxException("Selenium class ("+currentNavigationFile.getNavigationClassFile()+") has to implement NewSeleniumNavigation.", cce);
            }
            logger.debug(" NavigationFile getParameters().getParametersAsProperties() "+currentNavigationFile.getParameters().getParametersAsProperties());

            Object seleniumSpeed = memory.getOrCreateRepository(Repository.FILE_PROPERTIES).get("SELENIUM_SPEED");
            try{
                String seleniumSpeedStr = null;
                if (seleniumSpeed!=null){
                    seleniumSpeedStr = (String)seleniumSpeed;
                    selenium.setSpeed(seleniumSpeedStr);
                }else{
                    //Speed reset to avoid to have set speed and it is mantained the same for the whole test case
                    selenium.setSpeed("0");
                }
                memory.overrideRepository(Repository.PARAMETERS, currentNavigationFile.getParameters().getParametersAsProperties());
                logger.info("Current Navigation file element	\n"+new XMLOutputter().outputString(currentNavigationFile.xmlElement));

                seleniumImpl.executeNavigation(selenium, memory, currentNavigationFile.getParameters().getParametersAsProperties());
            }catch(Exception e){

                logger.error("Exception during navigation. ", e);
                String seleniumPath = (String)memory.getOrCreateRepository(Repository.FILE_PROPERTIES).get("SELENIUM_PATH_ERROR");
                logger.info("seleniumPath : "+seleniumPath);

                if (seleniumPath==null){
                    seleniumPath = "";
                }
                if (seleniumImpl!=null && selenium!=null && selenium.getHtmlSource()!=null){

                       File path = new File(seleniumPath);
                       if (!path.exists()){
                           path.mkdirs();
                       }

                       String fileName = seleniumPath+"/"+currentNavigationFile+"_"+getName().replace(' ', '_')+"_selenium";
                       logger.info("Creazione del file di output di errore "+fileName+".html");
                       FileWriter fstream = new FileWriter(fileName+".html");
                       BufferedWriter out = new BufferedWriter(fstream);
                       out.write(selenium.getHtmlSource());
                       out.close();
                       selenium.windowMaximize();
                       selenium.captureScreenshot(fileName+".png");
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

        return selenium;
    }

//	public HttpTestCaseSimple runHttpTester(WebNavigationSession conversation, Map resultVaribles, Properties prop, ClientWeb httpClient, MemoryData memory) throws TestException{
//
//        HttpTestCaseSimple httpTestCase=
//            new HttpTestCaseSimple();
//        logger.info("httpClient.getNome() "+httpClient.getNome());
//        logger.info("httpClient.getType() "+httpClient.getType());
//        logger.info("httpClient.getApplication() "+httpClient.getApplication());
//        logger.info("httpClient.getUrlUpload() "+httpClient.getUrlUpload());
//
//        if(!httpClient.isStartConversation() && conversation!=null)
//	        httpTestCase.setWebNavigationSession(conversation);
//
//        Hashtable hParameters=new Hashtable();
//
//        Iterator propertiesSet = prop.keySet().iterator();
//        while(propertiesSet.hasNext()){
//        	String propTemp = (String)propertiesSet.next();
//        	hParameters.put(propTemp,prop.getProperty(propTemp));
//        }
//
//        logger.info("http.output.prefix "+httpClient.getOutputPrefix());
//        hParameters.put("http.output.prefix",httpClient.getOutputPrefix());
//
//        logger.info("hParameters "+hParameters);
//
//        httpTestCase.setInitialVariables(hParameters);
//        httpTestCase.addVariables(httpClient.getParameters());
//        if (resultVaribles!=null){
//        	httpTestCase.addVariables(resultVaribles);
//        }
//        httpTestCase.setNavigationFile(httpClient.getFileNavigation());
//        httpTestCase.setTestClass(ClientWeb.class);
//        try {
//			httpTestCase.testNode();
//		} catch (SystemException e) {
//			logger.error("SystemException ", e);
//			throw new TestException(e, "Eccezione");
//		} catch (JiffieException e) {
//			logger.error("JiffieException ", e);
//			throw new TestException(e, "Eccezione");
//		}
//
//
//		waitTask(memory);
//
//        return httpTestCase;
//
//	}


//public HttpTestCaseSimple executeHttpClient(ClientWeb httpClient, HttpTestCaseSimple httpTestCase, MemoryData memory) throws Exception {
//        try{
//            Properties prop = (Properties)memory.getOrCreateRepository(Repository.FILE_PROPERTIES);
//           logger.info("Executed web client type "+getType());
//           if (httpTestCase !=null){
//           	return runHttpTester(httpTestCase.getWebConversation(),
//           	httpTestCase.getResultVariables(), prop, httpClient, memory);
//           }else{
//        	   return runHttpTester(null, null, prop, httpClient,memory);
//           }
//        } catch(Exception e) {
//              logger.error(e.getMessage(), e);
//              throw new TestException("Errore durante la navigazione WEB ");
//          }
//
//	}

    /**
     * @return the waitingConnection
     */
    public String getWaitingConnection() {
        return waitingConnection;
    }

    /**
     * @param waitingConnection the waitingConnection to set
     */
    public void setWaitingConnection(String waitingConnection) {
        this.waitingConnection = waitingConnection;
    }

    @Override
    public void execute(MemoryData session) throws Exception {
        Properties webNavigationProp = (Properties)session.getOrCreateRepository(Repository.WEB_NAVIGATION);
            logger.info("webNavigationProp "+webNavigationProp);
        //webNavigationProp.putAll(getParameters().getParametersAsProperties());
//        Properties parametersProperties = new Properties();
//        logger.debug("getParameters() "+getParameters());
//        parametersProperties.putAll(getParameters().getParametersAsProperties());
//        logger.debug("parametersProperties "+parametersProperties);

//        session.overrideRepository(Repository.PARAMETERS, parametersProperties);


        if (getType().equals(ClientWeb.HTTPTESTER)){
//            httpTestCase = executeHttpClient(httpClient, httpTestCase, session);
//            webNavigationProp.putAll(httpTestCase.getResultVariables());
        }else if (getType().equals(ClientWeb.SELENIUM)){
            session.set(this.getSeleniumClient(), executeSelenium(session, (DefaultSelenium)session.get(this.getSeleniumClient())));
        }
}

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

}