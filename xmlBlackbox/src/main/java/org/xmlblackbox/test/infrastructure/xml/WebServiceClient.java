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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.axis2.client.Stub;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.jdom.Element;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

/**
 * @author Crea
 */
public class WebServiceClient extends XmlStep {

    protected final static Logger logger = Logger.getLogger(WebServiceClient.class);
	
	private String url= null;
	private String stub= null;
	private String operation= null;

    private String fileInput = null;
	private String fileOutput = null;

    private Parameters parameters = null;
	
    private String version;

    private static final String VERSION10 = "1.0";
    private static final String VERSION11 = "1.1";
    private static final String VERSION12 = "1.2";

	public WebServiceClient(Element el) throws Exception {
		super(el);
		build(el);
	}
	
	public void build(Element clientElement) throws Exception {
		logger.debug("clientElement "+clientElement);
		logger.debug("---------------------------------");
	    
		WebServiceClient wsClient=this;
		wsClient.setName(clientElement.getAttributeValue("name"));
		logger.debug("wsClient.getNome() "+wsClient.getName());

		wsClient.setVersion(clientElement.getAttributeValue("version"));

//	    <!-- Example to make a web service call using xml prepared befor as input-->
//	    <WEB-SERVICE-CLIENT version="1.1" name="webservice">
//	        <FILE-INPUT>target/Example002WebApp_1_out.xml</FILE-INPUT>
//	        <FILE-OUTPUT>target/Example002WebApp_2_out.xml</FILE-OUTPUT>
//	        <URL>${EXAMPLE_WEB_SERVICE_URL@fileProperties}</URL>
//	        <STUB>org.xmlblackbox.test.webservice.client.NewWebServiceServiceStub</STUB>
//	        <OPERATION>getPerson</OPERATION>
//	    </WEB-SERVICE-CLIENT>

		if (getVersion().equals(VERSION10)||getVersion().equals(VERSION11)){
	    	Element fileInput = clientElement.getChild("FILE-INPUT", uriXsd);
	    	logger.debug("fileInput "+fileInput);
	    	if (fileInput!=null){
	    		wsClient.setFileInput(fileInput.getText());
	    	}
	
	    	Element urlServizioElement = clientElement.getChild("URL", uriXsd);
	
			logger.debug("urlServizioElement "+urlServizioElement);
	    	if (urlServizioElement!=null){
	    		wsClient.setUrl(urlServizioElement.getText());
	    	}
	
	    	Element stubServizioElement = clientElement.getChild("STUB", uriXsd);
			logger.debug("stubServizioElement "+stubServizioElement);
	    	if (stubServizioElement!=null){
	    		wsClient.setStub(stubServizioElement.getText());
	    	}
	    	
	    	Element operationServizioElement = clientElement.getChild("OPERATION", uriXsd);
			logger.debug("operationServizioElement "+operationServizioElement);
	    	if (operationServizioElement!=null){
	    		wsClient.setOperation(operationServizioElement.getText());
	    	}
	    	
	    	Element fileOutput = clientElement.getChild("FILE-OUTPUT", uriXsd);
	    	logger.debug("fileOutput "+fileOutput);
	    	if (fileOutput!=null){
	        	wsClient.setFileOutput(fileOutput.getText());
	    	}
	
	        Element parametersElement = clientElement.getChild("PARAMETERS", uriXsd);
	    	logger.debug("parametersElement "+parametersElement);
	    	if (parametersElement!=null){
	            parameters = new Parameters(parametersElement);
	        }
		}else if (getVersion().equals(VERSION12)){
	    	logger.info("clientElement "+clientElement);
    		wsClient.setFileInput(clientElement.getAttributeValue("input-file"));
    		wsClient.setUrl(clientElement.getAttributeValue("url"));
    		wsClient.setStub(clientElement.getAttributeValue("stub"));
    		wsClient.setOperation(clientElement.getAttributeValue("operation"));
    		wsClient.setFileOutput(clientElement.getAttributeValue("output-file"));
		}
	}

	public void setUrl(String urlServizio) {
		this.url = urlServizio;
	}

	public String getUrl(){
		return url;
	}

	public void setStub(String stubServizio) {
		this.stub = stubServizio;
	}

	public String getStub(){
		return stub;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getOperation(){
		return operation;
	}

    public Parameters getParameters() {
        return parameters;
	}

	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public String getRepositoryName() {
		return Repository.WEB_SERVICE;
	}
	
    public void execute(MemoryData session) throws Exception {
        File fileInput = new File(getFileInput());
		
        XmlObject richiesta = XmlObject.Factory.parse(fileInput);

		XmlObject response=null;
		
        Stub binding = null;
        
		logger.info("Client WS: " + getName());
		logger.info("URL: " + getUrl()+" OPERATION: " + getOperation());
		logger.info("STUB: " + getStub());
		logger.info("FILEINPUT: " + fileInput);
		
		try{
			saveRequest(richiesta);
		}catch(Exception e){
			logger.error("Eccezione ", e);
			throw e;
		}
		
        logger.debug("executeWebServiceClient ");
        try {
        	Class stubClass=Class.forName(getStub());
        	
        	Class[] parametri=new Class[1];
        	parametri[0]=String.class;
        	
        	Object[] values=new Object[1];
        	values[0]=getUrl();
			logger.debug("---- values : " +values[0]);
			logger.debug("---- WS Operation     : " +getOperation());
        	
			binding = (Stub)
				stubClass.getConstructor(parametri).newInstance(values);
			
			logger.debug("---- WS Operation     : " +getOperation());
			Class[] parametriInvocazione=new Class[1];
			
			logger.debug("richiesta.xmlText(): " +richiesta.xmlText());
			
			Class[] interfacce=richiesta.getClass().getInterfaces();
			
			for (int i=0; i < interfacce.length; i++){
				if (XmlObject.class.isAssignableFrom(interfacce[i])){
					parametriInvocazione[0]=interfacce[i];
					logger.debug("   Parametro invocazione #0 assegnato: "+parametriInvocazione[0]);
					break;
				}
			}
			logger.debug("---- WS Operation  ----------------");
			
			String timeout ="300000";
			((org.apache.axis2.client.Stub)binding)._getServiceClient().getOptions().setTimeOutInMilliSeconds(Integer.parseInt(timeout));
			((org.apache.axis2.client.Stub)binding)._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, new Integer(1*240*1000));
        	Object valoriInvocazione=richiesta;

            logger.debug("Invoke del Web Service getFileOutput() : "+getFileOutput());
        	logger.debug("Invoke del Web Service richiesta : "+richiesta.xmlText());

        	Method metodo=stubClass.getDeclaredMethod(getOperation(), parametriInvocazione);
			logger.debug("Invoke del Web Service ("+getOperation()+")");
            response=(XmlObject)metodo.invoke(binding, valoriInvocazione);
			logger.debug("After the Web Service invoke ("+getOperation()+")");
			logger.debug ("WS Resonse= [" +response.toString()+ "]");

        } catch (InvocationTargetException e) {
//        	try{
//        		logger.error("Fault", e);
//        		response=(XmlObject)e.getTargetException().getClass().getMethod("getFaultMessage", new Class[0]).invoke(e.getTargetException(), new Object[0]);
//        		saveRisposta(response);
//        		return response;
//        	}catch(NoSuchMethodException nsme){
//        		logger.error("Exception", nsme);
//        	}
        	logger.error("Exception", e);
        	throw e; 
        }
		
		saveResponse(response);

	}
	private void saveResponse(XmlObject rispostaWebService) throws IOException, XmlException{
		
		logger.debug("getFileOutput() "+ getFileOutput());
		File fileToSave = new File(getFileOutput());
		XmlOptions xmlOptions = new XmlOptions();
        xmlOptions.setSavePrettyPrint();
		rispostaWebService.save(new File(fileToSave.getParentFile()+"/"+fileToSave.getName()), xmlOptions);

	}
	
	private void saveRequest(XmlObject richiestaWebService) throws IOException, XmlException{
		
		logger.debug("getFileInput() "+ getFileInput());
		File fileToSave = new File(getFileInput());
		XmlOptions xmlOptions = new XmlOptions();
        xmlOptions.setSavePrettyPrint();
		richiestaWebService.save(new File(fileToSave.getParentFile()+"/"+fileToSave.getName()), xmlOptions);
        

	}

    /**
     * @return the fileInput
     */
    public String getFileInput() {
        return fileInput;
    }

    /**
     * @param fileInput the fileInput to set
     */
    public void setFileInput(String fileInput) {
        this.fileInput = fileInput;
    }

    /**
     * @return the fileOutput
     */
    public String getFileOutput() {
        return fileOutput;
    }

    /**
     * @param fileOutput the fileOutput to set
     */
    public void setFileOutput(String fileOutput) {
        this.fileOutput = fileOutput;
    }

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}