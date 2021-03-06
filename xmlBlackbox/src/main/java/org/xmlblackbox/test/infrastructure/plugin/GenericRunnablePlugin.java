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

package org.xmlblackbox.test.infrastructure.plugin;

import org.xmlblackbox.test.infrastructure.exception.RunPluginAbnormalTermination;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;



public abstract class GenericRunnablePlugin implements Serializable {

	public abstract List<String> getParametersName();

    public abstract void execute(MemoryData memory) throws RunPluginAbnormalTermination ;

    
	public void checkPrametersName(Properties prop,List parametersRequired) throws RunPluginAbnormalTermination{
		
		if (parametersRequired == null)
			throw new RunPluginAbnormalTermination("The function couldn't be started, no mandatory parameters defined. " +
                    "Verify: getParametersName() in the class implementation.");
		
		Iterator<String> it = parametersRequired.iterator();
		while (it.hasNext()){
			String param = it.next();
			if (prop.getProperty(param) == null)
				throw new RunPluginAbnormalTermination("The function couldn't be started. " +
                        "No mandatory parameters defined: " + param);
		}
	}
}
