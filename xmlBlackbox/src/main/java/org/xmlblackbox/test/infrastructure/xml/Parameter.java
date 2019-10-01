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


import org.jdom.Element;

import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

import org.apache.log4j.Logger;

public class Parameter extends XmlStep{

    private final static Logger logger = Logger.getLogger(Parameter.class);

    private String parameterName;
    private String parameterValue;
    private Object parameterObject;
    private String version;

    private static final String VERSION11 = "1.1";


	public Parameter(Element el) throws Exception {
		super(el);
		build(el);
	}

	public void build(Element element) throws Exception{
        setParameterName(element.getAttributeValue("name"));
        setParameterValue(element.getAttributeValue("value"));
        setParameterObject(element.getAttributeValue("object"));
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

    /**
     * @return the parameterName
     */
    public String getParameterName() {
        return parameterName;
    }

    /**
     * @param parameterName the parameterName to set
     */
    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    /**
     * @return the parameterValue
     */
    public String getParameterValue() {
        return parameterValue;
    }

    /**
     * @param parameterValue the parameterValue to set
     */
    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    /**
     * @return the parameterObject
     */
    public Object getParameterObject() {
        return parameterObject;
    }

    /**
     * @param parameterObject the parameterObject to set
     */
    public void setParameterObject(Object parameterObject) {
        this.parameterObject = parameterObject;
    }
}
