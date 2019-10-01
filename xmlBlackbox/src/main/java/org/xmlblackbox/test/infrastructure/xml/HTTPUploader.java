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


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jdom.Element;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;


import org.xmlblackbox.test.infrastructure.exception.TestException;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

/**
 *
 *
 * @author acrea
 */
public class HTTPUploader extends XmlStep{
	
	private final static Logger logger = Logger.getLogger(HTTPUploader.class);

	public static String UPLOAD_FILE = "UPLOAD_FILE";
	
	private Parameters parameters;
	private String fileNavigation= null;
	private String urlLogin= null;
	private String urlUpload= null;
	private String fileToUpload = null;
	private String fileInput = null;
	private String fileOutput = null;
	
	
	public HTTPUploader(Element el) throws Exception {
		super(el);
        build(el);
	}
	
	public void build(Element uploaderElement) throws Exception {


        Element parametersElement = uploaderElement.getChild("PARAMETERS", uriXsd);
    	logger.debug("parametersElement "+parametersElement);
    	if (parametersElement!=null){
            setParameters(new Parameters(parametersElement));
        }

//		parameters = parseParameters(uploaderElement);
		
		HTTPUploader httpUploader=this;
		logger.info("uploaderElement "+uploaderElement);
		logger.info("uploaderElement.getAttributeValue(nome) "+uploaderElement.getAttributeValue("name"));
		httpUploader.setName(uploaderElement.getAttributeValue("name"));
		
    	Element fileInput= uploaderElement.getChild("FILE-TO-UPLOAD");
    	if (fileInput!=null){
    		httpUploader.setFileToUpload(fileInput.getText());
    	}

    	Element urlApload = uploaderElement.getChild("URL-UPLOAD");
		logger.info("urlApload "+urlApload);
    	if (urlApload!=null){
    		logger.info("urlApload.getText() "+urlApload.getText());
    		httpUploader.setUrlUpload(urlApload.getText());
    	}
    	
    	Element urlLogin = uploaderElement.getChild("URL-LOGIN");
		logger.info("urlLogin "+urlLogin);
    	if (urlLogin!=null){
    		logger.info("urlLogin.getText() "+urlLogin.getText());
    		httpUploader.setUrlLogin(urlLogin.getText());
    	}


	}
	
	public void setFileNavigation(String fileNavigation) {
		this.fileNavigation= fileNavigation;
	}

	public String getFileNavigation() {
		return fileNavigation;
	}
	
	public Parameters getParameters() {
		return parameters;
	}

	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}

	public void setUrlUpload(String urlUpload) {
		this.urlUpload = urlUpload;
	}

	public String getUrlUpload() {
		return urlUpload;
	}
	
    /**
     * @return the fileToUpload
     */
    public String getFileToUpload() {
        return fileToUpload;
    }

    /**
     * @param fileToUpload the fileToUpload to set
     */
    public void setFileToUpload(String fileToUpload) {
        this.fileToUpload = fileToUpload;
    }
    
	public String getUrlLogin() {
		return urlLogin;
	}

	public void setUrlLogin(String urlLogin) {
		this.urlLogin = urlLogin;
	}



	@Override
	public String getRepositoryName() {
		return Repository.WEB_NAVIGATION;
	}
	
	public void uploadFile(MemoryData memory, HTTPUploader httpClient) throws TestException, HttpException, IOException  {
		int ris;
		HttpClient hClient = new HttpClient();
		
		Properties prop = (Properties)memory.getOrCreateRepository(getRepositoryName());
		logger.info("prop "+prop);
		
		/**
		 * Effettua la login
		 */
		String usernameStr = (String)httpClient.getParameters().getParametersAsProperties().get("username");
		String passwordStr = (String)httpClient.getParameters().getParametersAsProperties().get("password");

		Properties fileProperties = (Properties)memory.getOrCreateRepository(Repository.FILE_PROPERTIES);
		
		logger.info("Login al "+urlLogin);
		PostMethod postMethodLogin = new PostMethod(urlLogin);
		NameValuePair username = new NameValuePair();
		username.setName("userId");
		username.setValue(usernameStr);
		logger.info("username "+usernameStr);
		NameValuePair password = new NameValuePair();
		password.setName("password");
		password.setValue(passwordStr);
		logger.info("password "+passwordStr);

        if (usernameStr!=null){
    		postMethodLogin.addParameter(username);
        }
        if (passwordStr!=null){
            postMethodLogin.addParameter(password);
        }
		ris = hClient.executeMethod(postMethodLogin);
		logger.debug("ris Login password "+passwordStr+" "+ris);

		if (ris != HttpStatus.SC_MOVED_TEMPORARILY) {
			throw new TestException("Error during login for uploading the file");
		}
		
		XmlObject[] domandeXml = null;
		
		File fileXML = new File(httpClient.getFileToUpload());
		PostMethod postm = new PostMethod(urlUpload);
		
		logger.debug("fileXML.getName() "+fileXML.getName());
		logger.debug("fileXML "+fileXML);
		logger.debug("postm.getParams()  "+postm.getParams());
		logger.debug("httpClient.getParameters().get(\"OPERATION_UPLOAD_NAME\") "+httpClient.getParameters().getParametersAsProperties().get("OPERATION_UPLOAD_NAME"));
		logger.debug("httpClient.getParameters().get(\"OPERATION_UPLOAD_VALUE\") "+httpClient.getParameters().getParametersAsProperties().get("OPERATION_UPLOAD_VALUE"));

		try {
                Part[] parts = {
					new StringPart((String)httpClient.getParameters().getParametersAsProperties().get("OPERATION_UPLOAD_NAME"),
							(String)httpClient.getParameters().getParametersAsProperties().get("OPERATION_UPLOAD_VALUE")),
					new FilePart("file", fileXML.getName(), fileXML
							)};

			postm.setRequestEntity(new MultipartRequestEntity(parts, postm.getParams()));
			logger.debug("parts "+parts);
		} catch (FileNotFoundException e2) {
			logger.error("FileNotFoundException ", e2);
			throw new TestException(e2, "FileNotFoundException ");
		}
		
		hClient.getHttpConnectionManager().getParams().setConnectionTimeout(8000);

		try {
			ris = hClient.executeMethod(postm);
			logger.info("ris Upload password "+passwordStr+" "+ris);
			logger.debug("ris Upload password "+passwordStr+" "+ris);
			if (ris == HttpStatus.SC_OK) {
				//logger.info("Upload completo, risposta=" + postm.getResponseBodyAsString());
				
				InputStream in = postm.getResponseBodyAsStream();
			    //OutputStream out = new FileOutputStream(new File(prop.getProperty("FILE_RISPOSTA_SERVLET")));
			    OutputStream out = new FileOutputStream(new File(httpClient.getFileOutput()));
			    OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
			    InputStreamReader reader = new InputStreamReader(in, "UTF-8");
			    
			    BufferedReader bufferedReader= new BufferedReader(reader);
			    
			    // Transfer bytes from in to out
			    //byte[] buf = new byte[1024];
			    //int len;
			    String linea = null;
			    while ((linea = bufferedReader.readLine()) !=null) {
			    	writer.write(linea);
			    }
			    writer.close();
			    reader.close();
			    in.close();
			    out.close();
			    
			} else {
			    logger.error("Upload failed, response =" + HttpStatus.getStatusText(ris));
			    logger.error("Exception : Server response not correct ");
			    throw new TestException("Exception : Server response not correct ");
			}
		} catch (HttpException e) {
		    logger.error("Exception : Server response not correct ", e);
		    throw new TestException(e, "");
		} catch (IOException e) {
		    logger.error("Exception : Server response not correct ", e);
	
		    throw new TestException(e, "");
		} finally {
			postm.releaseConnection();
		}
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

    @Override
    public void execute(MemoryData session) throws Exception {
        Properties webNavigationProp = (Properties)session.getOrCreateRepository(Repository.WEB_NAVIGATION);
//        webNavigationProp.putAll(getParameters().getParametersAsProperties());
//
//        session.getOrCreateRepository(Repository.WEB_NAVIGATION).putAll(session.getRepository(Repository.FILE_PROPERTIES));
//        session.getOrCreateRepository(Repository.WEB_NAVIGATION).putAll(httpUploader.getParameters());
//
//        uploadFile(session, this);
    }
}