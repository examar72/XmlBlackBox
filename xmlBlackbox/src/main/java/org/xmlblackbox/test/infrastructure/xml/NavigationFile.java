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
import org.apache.log4j.Logger;


import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

/**
 * @author acrea
 */
public class NavigationFile extends XmlStep  {

	private final static Logger logger = Logger.getLogger(NavigationFile.class);

    private String navigationClassFile;
	private Parameters parameters = new Parameters();

	public NavigationFile(Element el) throws Exception {
		super(el);
        build(el);
	}

	public void build(Element mainElement) throws Exception {

		setName(mainElement.getAttributeValue("name"));

        Element parametersElement = mainElement.getChild("PARAMETERS", uriXsd);
    	logger.debug("parametersElement "+parametersElement);
    	if (parametersElement!=null){
            setParameters(new Parameters(parametersElement));
        }
				
		logger.debug("parameters "+getParameters());

        logger.info("Navigation Class "+mainElement.getAttributeValue("class"));

		if(mainElement.getAttributeValue("class")!=null)
			setNavigationClassFile(mainElement.getAttributeValue("class"));
	}

	@Override
	public String getRepositoryName() {
		return Repository.WEB_NAVIGATION;
	}

    @Override
    public void execute(MemoryData session) throws Exception {
    }

    /**
     * @return the navigationClassFile
     */
    public String getNavigationClassFile() {
        return navigationClassFile;
    }

    /**
     * @param navigationClassFile the navigationClassFile to set
     */
    public void setNavigationClassFile(String navigationClassFile) {
        this.navigationClassFile = navigationClassFile;
    }

    /**
     * @return the parameters
     */
    public Parameters getParameters() {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

}