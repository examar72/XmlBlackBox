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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.dbunit.Assertion;
import org.jdom.Element;
import org.xmlblackbox.test.infrastructure.exception.XmlBlackBoxException;
import org.xmlblackbox.test.infrastructure.exception.XmlCheckException;
import org.xmlblackbox.test.infrastructure.exception.TestException;
import org.xmlblackbox.test.infrastructure.exception.XmlInsertException;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

/**
 *
 * @author examar.xbb
 */

public class CheckInsertXmlContent extends XmlStep {


	private final static Logger logger = Logger.getLogger(XmlInsertRow.class);

	private final static Logger log = Logger.getLogger(CheckInsertXmlContent.class);

    private Parameters parameters;

    private List  xmlList = new Vector();
    private List  xmlInsertList = new Vector();
    private List  xmlCheckList = new Vector();
    private String namespace;

	private String fileInput = null;
	private String fileOutput = null;
	
	public CheckInsertXmlContent(Element el) throws Exception {
		super(el);
		build(el);
	}
	
	public void build(Element checkInsertXmlElement) throws Exception {
		log.debug("name "+checkInsertXmlElement.getAttributeValue("name"));
		xmlCheckList = new Vector();
		xmlInsertList = new Vector();
		xmlList = new Vector();

        Element parametersElement = checkInsertXmlElement.getChild("PARAMETERS", uriXsd);
    	log.debug("parametersElement "+parametersElement);
    	if (parametersElement!=null){
            parameters = new Parameters(parametersElement);
        }

        setName(checkInsertXmlElement.getAttributeValue("name"));
        setNamespace(checkInsertXmlElement.getAttributeValue("namespace"));
        setFileInput(checkInsertXmlElement.getAttributeValue("fileinput"));
        setFileOutput(checkInsertXmlElement.getAttributeValue("fileoutput"));

    	Iterator<Element> xmlCheckIterator = checkInsertXmlElement.getChildren("XML-CHECK-ROW", uriXsd).iterator();
    	while(xmlCheckIterator.hasNext()){
            Element element = xmlCheckIterator.next();
            if (element!=null){
                getXmlCheckList().add(getXmlCheckRow(element));
                getXmlList().add(getXmlCheckRow(element));
            }
        }

        Iterator<Element> xmlInsertIterator = checkInsertXmlElement.getChildren("XML-INSERT-ROW", uriXsd).iterator();
    	while(xmlInsertIterator.hasNext()){
            Element element = xmlInsertIterator.next();
            if (element!=null){
                getXmlInsertList().add(getXmlInsertRow(element));
                getXmlList().add(getXmlInsertRow(element));
            }
        }

//        Iterator<Element> xmlInsertNodeIterator = checkInsertXmlElement.getChildren("XML-INSERT-NODE-ROW", uriXsd).iterator();
//    	while(xmlInsertNodeIterator.hasNext()){
//            Element element = xmlInsertNodeIterator.next();
//            if (element!=null){
//                getXmlCheckList().add(getXmlInsertNodeRow(element));
//            }
//        }
//
//        Iterator<Element> xmlRemoveNodeIterator = checkInsertXmlElement.getChildren("XML-INSERT-NODE-ROW", uriXsd).iterator();
//    	while(xmlRemoveNodeIterator.hasNext()){
//            Element element = xmlRemoveNodeIterator.next();
//            if (element!=null){
//                getXmlCheckList().add(getXmlRemoveNodeRow(element));
//            }
//        }

	}

    private XmlCheckRow getXmlCheckRow(Element element){
        XmlCheckRow xmlRow = new XmlCheckRow();
        xmlRow.setNome(element.getAttributeValue("name"));
        xmlRow.setValue(element.getAttributeValue("value"));
        xmlRow.setFileName(getFileInput());
        xmlRow.setXPath(element.getAttributeValue("xpath"));
        xmlRow.setNamespace(getNamespace());
        xmlRow.setType(XmlCheckRow.TYPE);
        return xmlRow;

    }

    private XmlInsertRow getXmlInsertRow(Element element){
        XmlInsertRow xmlRow = new XmlInsertRow();
        xmlRow.setNome(element.getAttributeValue("name"));
        xmlRow.setValue(element.getAttributeValue("value"));
        xmlRow.setFileName(getFileInput());
        xmlRow.setXPath(element.getAttributeValue("xpath"));
        xmlRow.setOutputFileName(getFileOutput());
        xmlRow.setNamespace(getNamespace());
        xmlRow.setType(XmlInsertRow.TYPE);
        return xmlRow;

    }

//    private XmlInsertRemoveNodeRow getXmlInsertNodeRow(Element element){
//        XmlInsertRemoveNodeRow xmlRow = new XmlInsertRemoveNodeRow();
//        xmlRow.setNome(element.getAttributeValue("name"));
//        xmlRow.setFileName(getFileInput());
//        xmlRow.setXPath(element.getAttributeValue("xpath"));
//        xmlRow.setValue(element.getAttributeValue("value"));
//        xmlRow.setOutputFileName(getFileOutput());
//        xmlRow.setNamespace(getNamespace());
//        xmlRow.setType(XmlInsertRemoveNodeRow.TYPE_INSERT);
//        return xmlRow;
//
//    }
//
//    private XmlInsertRemoveNodeRow getXmlRemoveNodeRow(Element element){
//        XmlInsertRemoveNodeRow xmlRow = new XmlInsertRemoveNodeRow();
//        xmlRow.setNome(element.getAttributeValue("name"));
//        xmlRow.setFileName(getFileInput());
//        xmlRow.setXPath(element.getAttributeValue("xpath"));
//        xmlRow.setValue(element.getAttributeValue("value"));
//        xmlRow.setOutputFileName(getFileOutput());
//        xmlRow.setNamespace(getNamespace());
//        xmlRow.setType(XmlInsertRemoveNodeRow.TYPE_REMOVE);
//        return xmlRow;
//
//    }

	@Override
	public String getRepositoryName() {
		return Repository.SET_VARIABLE;
	}

    /**
     * @return the xmlCheckList
     */
    public List getXmlList() {
        return xmlList;
    }

    /**
     * @return the xmlCheckList
     */
    public List getXmlCheckList() {
        return xmlCheckList;
    }

        /**
     * @return the checkXmlList
     */
    public List getXmlInsertList() {
        return xmlInsertList;
    }

    /**
     * @param checkXmlList the checkXmlList to set
     */
    public void setCheckXmlList(List checkXmlList) {
        this.xmlCheckList = checkXmlList;
    }

    /**
     * @return the namespace
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * @param namespace the namespace to set
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
    
    public XmlObject getXmlObject() throws TestException{
        XmlObject xobj = null;
        try {
            InputStream is = new FileInputStream(getFileInput());
            xobj = XmlObject.Factory.parse(is);
            return xobj;
        }
        catch (XmlException e) {
            logger.error("XmlException ", e);
            e.printStackTrace();
            throw new TestException();
        }
        catch (IOException e) {
            logger.error("IOException ", e);
            e.printStackTrace();
            throw new TestException();
        }
    }
    
    public XmlObject checkValueInXml(XmlObject xobj, XmlCheckRow xmlRow, MemoryData memory) throws Exception{

        XmlObject[] xmlObject = null;

        xmlObject = xobj.selectPath(xmlRow.getNamespace()+xmlRow.getXPath());

        logger.debug("xmlObject "+xmlObject);
        if (xmlObject!=null && xmlObject.length==0){
            String error= "XmlObject not found searching by xpath query \""+xmlRow.getNamespace()+xmlRow.getXPath()+"\". Check the namespace.";
            throw new XmlCheckException(error);
        }
        logger.debug("xmlObject.length "+xmlObject.length);
        logger.debug("xmlObject[0] "+xmlObject[0]);
        logger.debug("xmlObject[0].xmlText() "+xmlObject[0].xmlText());

        String value = xmlObject[0].newCursor().getTextValue();
        logger.debug("xmlRow.getNome() "+xmlRow.getNome());
        logger.debug("value "+value);
        logger.debug("xmlRow.getValue() "+xmlRow.getValue());


        String errorMessage = xmlRow.getNome()+". The value contains in ("+xmlRow.getXPath()+") of the Xml file "
                    +xmlRow.getFileName()+" ("+value+") is not one expected ("+xmlRow.getValue()+")";

        if (!value.equals(xmlRow.getValue())){
            logger.debug(errorMessage);
        }
        try{
            Assert.assertEquals(errorMessage, xmlRow.getValue(), value);
        }catch(AssertionFailedError e){
            logger.error("errorMessage "+ errorMessage);
            logger.error("xobj "+ xobj.xmlText());
            logger.error("Exception "+e);
            throw new XmlBlackBoxException("XmlBlackBoxException "+errorMessage, e);
        }

        return xobj;

    }
    
    public XmlObject insertValueInXml(XmlObject xobj, XmlInsertRow xmlRow, MemoryData memory) throws Exception{

        XmlObject[] xmlObject = null;
        logger.debug("xmlRow.getNamespace() "+xmlRow.getNamespace());
        logger.debug("xmlRow.getXPath() "+xmlRow.getXPath());

        logger.debug("DoctypeName "+xobj.documentProperties().getDoctypeName());
        logger.debug("DoctypePublicId "+xobj.documentProperties().getDoctypePublicId());

        xmlObject = xobj.selectPath(xmlRow.getNamespace()+xmlRow.getXPath());

        if (xmlObject.length<1){
            String error= "XmlObject not found searching by xpath query \""+xmlRow.getNamespace()+xmlRow.getXPath()+"\". Check the namespace.";
            throw new XmlInsertException(error);
        }

        logger.debug("xobj "+xobj);

//        xmlObject[0].newCursor().setAttributeText(new QName("xsi:nil"), null);
        xmlObject[0].newCursor().setTextValue(xmlRow.getValue());
        logger.debug("xmlRow.getNome() "+xmlRow.getNome());
        logger.debug("inserted in xml the value "+xmlObject[0].newCursor().getTextValue());
        logger.debug("XmlObject "+xmlObject[0].xmlText());

        logger.debug("xobj "+xobj);

        return xobj;
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
        Iterator<XmlCheckRow> iter = getXmlList().iterator();
        XmlObject xobj = getXmlObject();

        while(iter.hasNext()){
            XmlRowInterface xmlRow = iter.next();
            if (xmlRow.getType().equals(XmlCheckRow.TYPE)){
                xobj = checkValueInXml(xobj, (XmlCheckRow)xmlRow, session);
            }else if (xmlRow.getType().equals(XmlInsertRow.TYPE)){
                xobj = insertValueInXml(xobj, (XmlInsertRow)xmlRow, session);
//            }else if (xmlRow.getType().equals(XmlInsertRemoveNodeRow.TYPE_INSERT)){
//                log.info("((XmlInsertRemoveNodeRow)xmlRow).getXPath() "+((XmlInsertRemoveNodeRow)xmlRow).getXPath());
//
//                xobj = insertNodeInXml(xobj, (XmlInsertRemoveNodeRow)xmlRow,
//                        session);
//                log.info("xobj.xmlText() "+xobj.xmlText());
            }
        }

        try{
            if (getFileOutput()!=null){
                log.info("File output salvato "+getFileOutput());
                XmlOptions xmlOptions = new XmlOptions();
                xmlOptions.setSavePrettyPrint();

                xobj.save(new File(getFileOutput()), xmlOptions);
            }
        }catch(IOException e){
            throw new TestException(e, "IOException");
        }
    }

    public void checkMandatoryField() throws Exception{
        if (getXmlInsertList().size()>0 && fileOutput==null){
            throw new XmlInsertException(XmlInsertException.OUTPUTFILE_NOT_SETUP, getName());
        }
    }






}
