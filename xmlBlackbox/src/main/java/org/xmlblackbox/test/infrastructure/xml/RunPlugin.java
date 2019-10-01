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

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.xmlblackbox.test.infrastructure.exception.TestException;
import org.xmlblackbox.test.infrastructure.exception.XmlBlackBoxException;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.plugin.GenericRunnablePlugin;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

public class RunPlugin extends XmlStep {


	private final static Logger logger = Logger.getLogger(RunPlugin.class);

    private String templateClass;
    private Parameters parameters;
    private HashMap<String, String> output;

	public Parameters getParameters() {
		return parameters;
	}
	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}
	public HashMap<String, String> getOutput() {
		return output;
	}
	public void setOutput(HashMap<String, String> output) {
		this.output = output;
	}
	public String getTemplateClass() {
		return templateClass;
	}
	public void setTemplateClass(String templateClass) {
		this.templateClass = templateClass;
	}

	public RunPlugin(Element el) throws Exception {
		super(el);
		build(el);
	}
	
    public void build(Element xmlElement) throws Exception {
		RunPlugin runPlugin = this;
		runPlugin.setTemplateClass(xmlElement.getAttributeValue("class"));
        Element parametersElement = xmlElement.getChild("PARAMETERS", uriXsd);
    	logger.debug("parametersElement "+parametersElement);
    	if (parametersElement!=null){
            parameters = new Parameters(parametersElement);
        }
	}

	@Override
	public String getRepositoryName() {
		return Repository.RUN_PLUGIN;
	}
	
    public void execute(MemoryData memory) throws TestException, Exception{

    	Properties prop = (Properties)memory.getOrCreateRepository(getRepositoryName());
    	//Immetto i parameters di input nella memoria..
        
        if (getParameters()!=null){
            logger.info("Input parameters "+getParameters().getParametersAsProperties());
            prop.putAll(getParameters().getParametersAsProperties());
        }
        Class functionClass;
        try{
	    	// Instanzio la classe..
	    	functionClass=Class.forName(getTemplateClass());
        }catch(ClassNotFoundException cnfe){
        	logger.error("ClassNotFoundException ", cnfe);
        	throw new XmlBlackBoxException("Class not found "+getTemplateClass(), cnfe);
        }
    	// Inizializzo il costruttore..
    	GenericRunnablePlugin runPluginImpl = null;
    	try{
    		runPluginImpl = (GenericRunnablePlugin) functionClass.getConstructor().newInstance();
        }catch(ClassCastException cce){
        	logger.error("ClassCastException ", cce);
        	throw new XmlBlackBoxException("Plugin class ("+getTemplateClass()+") has to implement GenericRunnablePlugin.", cce);
        }

    	// Eseguo la funzione
    	runPluginImpl.checkPrametersName(prop, runPluginImpl.getParametersName());

        runPluginImpl.execute(memory);
    }

}