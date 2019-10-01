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

import org.xmlblackbox.test.infrastructure.replacement.ReplaceFromXML;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

import java.io.StringReader;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

public abstract class XmlStep {

    public static Namespace uriXsd = Namespace.getNamespace("http://www.xmlblackbox.org/xsd/");
    private String fileName = null;
    private boolean toBeDoneItWhenIncluded = true;
    private boolean isIncluded = false;

    public XmlStep() {
        
    }
	
	public XmlStep(Element el) {
		super();
		this.xmlElement = el;
		this.xmlTagName = el.getName();
        if (el.getAttributeValue("do-it-if-included")!=null){
        	setToBeDoneWhenIncluded(new Boolean(el.getAttributeValue("do-it-if-included")).booleanValue());
        }
	}

	protected Element xmlElement=null;
	private String xmlTagName=null;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getXmlTagName() {
		return xmlTagName;
	}

	private final static Logger log = Logger.getLogger(XmlStep.class);
    
	public void reload(MemoryData session) throws Exception{
		log.info("[Reload variables]");
		ReplaceFromXML replacer= new ReplaceFromXML(session,"${","}");
		log.info(" Tag included in xml file "+this.getFileName());
	    log.debug("- Before -------------------------------------------");
        log.info("- xmlElement 	\n"+new XMLOutputter().outputString(xmlElement));
		log.debug(new XMLOutputter().outputString(xmlElement));
		String sReplaced=replacer.replace(new XMLOutputter().outputString(xmlElement));
		Element replaced=new SAXBuilder(false).build(new StringReader(sReplaced)).getRootElement();
		log.info(" Tag included in xml file "+this.getFileName());
		log.debug("- After --------------------------------------------");
		log.info("- replaced \n"+new XMLOutputter().outputString(replaced));
		log.debug(new XMLOutputter().outputString(replaced));
	    build(replaced);
	    log.info("[Reload variables][OK]");
	}
	
	public abstract void build(Element xmlElement) throws Exception;

    public abstract void execute(MemoryData session) throws Exception;

	public abstract String getRepositoryName();

    public void checkMandatoryField() throws Exception{
	    log.debug("XmlStep checkMandatoryField. The subclass has not implemented this method");
    }

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isToBeDoneItWhenIncluded() {
		return toBeDoneItWhenIncluded;
	}

	public void setToBeDoneWhenIncluded(boolean toBeDoneItWhenIncluded) {
	    log.info("toBeDoneItWhenIncluded "+toBeDoneItWhenIncluded);
		this.toBeDoneItWhenIncluded = toBeDoneItWhenIncluded;
	}

	public boolean isIncluded() {
		return isIncluded;
	}

	public void setIncluded(boolean isIncluded) {
	    log.info("isIncluded "+isIncluded);
		this.isIncluded = isIncluded;
	}

	public Element getXmlElement() {
		return xmlElement;
	}

//	protected HashMap parseParameters(Element parameters){
//		HashMap<String,String> params = new HashMap<String, String>();
//
//		if (parameters!=null) {
//			Iterator parametersList = parameters.getChildren("PARAMETER", uriXsd).iterator();
//			while (parametersList.hasNext()){
//				Element parameterElement = (Element) parametersList.next();
//				String pname = parameterElement.getAttributeValue("name");
//				String pvalue = parameterElement.getAttributeValue("value");
//
//				params.put(pname, pvalue);
//			}
//		}
//		return params;
//	}
	
}
