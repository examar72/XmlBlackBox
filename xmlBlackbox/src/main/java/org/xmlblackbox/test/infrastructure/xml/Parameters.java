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


import java.util.ArrayList;
import org.jdom.Element;

import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;

public class Parameters extends XmlStep{

    private final static Logger logger = Logger.getLogger(Parameters.class);

	private List parameterList = new ArrayList();
    private String version;

    private static final String VERSION11 = "1.1";

	public Parameters() throws Exception {
        
    }

	public Parameters(Element el) throws Exception {
		super(el);
		build(el);
	}

	public void build(Element element) throws Exception{
        if (element!=null) {
    		Iterator parametersList = element.getChildren("PARAMETER", uriXsd).iterator();
    		while (parametersList.hasNext()){
                Parameter parameter
                        = new Parameter((Element) parametersList.next());

                parameterList.add(parameter);
    		}
    	}

	}

	@Override
	public String getRepositoryName() {
		return Repository.PARAMETERS;
	}

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public void execute(MemoryData session) throws Exception {
    }

    public Properties getParametersAsProperties(){
        Properties prop = new Properties();
        for(int i=0;i<parameterList.size();i++){
            prop.setProperty(((Parameter)parameterList.get(i)).getParameterName()
                    , ((Parameter)parameterList.get(i)).getParameterValue());
        }
        return prop;
    }
}
