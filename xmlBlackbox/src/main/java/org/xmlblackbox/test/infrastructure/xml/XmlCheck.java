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

import java.io.StringReader;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import org.xmlblackbox.test.infrastructure.replacement.ReplaceFromXML;
import org.xmlblackbox.test.infrastructure.util.MemoryData;
 

public class XmlCheck extends XmlStep implements XmlCheckList {
	
	Element xmlElement=null;

	private String name=null;
	
	private String namespace=null;
	
	private String occurrence=null;
	private List listaCheck = new Vector();

    @Override
    public void execute(MemoryData session) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
	
	public class XmlField {
		public XmlField(Element elemento){
			if(elemento.getAttributeValue("PROPERTY")!=null)
				this.setProperty(elemento.getAttributeValue("PROPERTY"));
			if(elemento.getAttributeValue("NAME")!=null)
				this.setName(elemento.getAttributeValue("NAME"));
	    	if(elemento.getAttributeValue("TIPO")!=null)
	    		this.setTipo(elemento.getAttributeValue("TIPO"));
	    	
    		this.setValue(elemento.getAttributeValue("VALUE"));
    		this.setNamespace(elemento.getAttributeValue("namespace"));
		}
		
		private String name=null;
		private String tipo=null;
		private String value=null;
		private String namespace=null;
		private String property=null;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getTipo() {
			return tipo;
		}
		public void setTipo(String tipo) {
			this.tipo = tipo;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getNamespace() {
			return namespace;
		}
		public void setNamespace(String namespace) {
			this.namespace = namespace;
		}
		public String getProperty() {
			return property;
		}
		public void setProperty(String property) {
			this.property = property;
		}
		
	}

	public class XmlDefine {
		
//  		<!-- CHECK_DEFINE NAME="PROTOCOLLO_COMUNICAZIONE" PATH="./apr4:statoModello/apr4:protocolloComunicazione"/>
//  		<CHECK_DEFINE NAME="ID_COMUNICAZIONE" USE="PROTOCOLLO_COMUNICAZIONE" FILTER="getIdComunicazione" /-->

		
		public XmlDefine(Element elemento){
	    	this.setName(elemento.getAttributeValue("NAME"));
	    	this.setNamespace(elemento.getAttributeValue("namespace"));
	    	
	    	if (elemento.getAttributeValue("PATH")!=null)
	    		this.setPath(elemento.getAttributeValue("PATH"));
    		
	    	if (elemento.getAttributeValue("USE")!=null)
	    		this.setUse(elemento.getAttributeValue("USE"));
    		
	    	if (elemento.getAttributeValue("FILTER")!=null)
	    		this.setFilter(elemento.getAttributeValue("FILTER"));

	    	if (elemento.getAttributeValue("AS_XML")!=null)
	    		this.setAsXml(elemento.getAttributeValue("AS_XML"));

	    	if (elemento.getAttributeValue("DEFAULT")!=null)
	    		this.setDefaultValue(elemento.getAttributeValue("DEFAULT"));
	    	
		}
		
		private String name=null;
		private String path=null;
		private String use=null;
		private String filter=null;
		private String asXml="false";
		private String namespace=null;
		private String defaultValue=null;
		
		public String getFilter() {
			return filter;
		}
		public void setFilter(String filter) {
			this.filter = filter;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public String getUse() {
			return use;
		}
		public void setUse(String use) {
			this.use = use;
		}
		public String getAsXml() {
			return asXml;
		}
		public void setAsXml(String asXml) {
			this.asXml = asXml;
		}
		public String getNamespace() {
			return namespace;
		}
		public void setNamespace(String namespace) {
			this.namespace = namespace;
		}
		public String getDefaultValue() {
			return defaultValue;
		}
		public void setDefaultValue(String defaultValue) {
			this.defaultValue = defaultValue;
		}
		

		
	}

	
	public XmlCheck(Element el) throws Exception {
		super(el);
		build(el);
	}
	
	
	public void build(Element elemento) throws Exception{
		
    	this.setName(elemento.getAttributeValue("name"));
    	this.setNamespace(elemento.getAttributeValue("namespace"));
    	
    	
    	if (elemento.getAttributeValue("OCCURRENCE")!=null)
    		this.setOccurrence(elemento.getAttributeValue("OCCURRENCE"));
    	
        Iterator fieldList = elemento.getChildren("CHECK_FIELD").iterator();
        
        while (fieldList.hasNext()){
        	XmlField xmlCheck = new XmlField((Element) fieldList.next());

        	listaCheck.add(xmlCheck);
        }

        Iterator nodeList = elemento.getChildren("CHECK_NODE").iterator();
        
        while (nodeList.hasNext()){
        	XmlCheck xmlCheck = new XmlCheck((Element) nodeList.next());

        	listaCheck.add(xmlCheck);
        }

        Iterator defineList = elemento.getChildren("CHECK_DEFINE").iterator();
        
        while (defineList.hasNext()){
        	XmlDefine xmlCheck = new XmlDefine((Element) defineList.next());

        	listaCheck.add(xmlCheck);
        }
    	
    	
	}

	public List getListaCheck() {
		return listaCheck;
	}

	public void setListaCheck(List listaCheck) {
		this.listaCheck = listaCheck;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOccurrence() {
		return occurrence;
	}

	public void setOccurrence(String occurrence) {
		this.occurrence = occurrence;
	}

	public String getNamespace() {
		return namespace;
	}


	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	@Override
	public String getRepositoryName() {
		return null;
	}


}
