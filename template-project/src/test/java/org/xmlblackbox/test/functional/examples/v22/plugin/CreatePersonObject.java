package org.xmlblackbox.test.functional.examples.v22.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.xmlblackbox.test.infrastructure.exception.RepositoryNotFound;
import org.xmlblackbox.test.infrastructure.exception.RunPluginAbnormalTermination;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.plugin.GenericRunnablePlugin;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

public class CreatePersonObject extends GenericRunnablePlugin  {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7392769507000912341L;
	private final static Logger log = Logger.getLogger(CreatePersonObject.class);

	@Override
	public void execute(MemoryData memory) throws RunPluginAbnormalTermination {

		Properties prop ;
        try {
            prop = (Properties)memory.getRepository(Repository.RUN_PLUGIN);
        } catch (RepositoryNotFound ex) {
    		log.error("RepositoryNotFound ", ex );
            throw new RunPluginAbnormalTermination("RepositoryNotFound");
        }

        NameSurname personDetails = new NameSurname();
        personDetails.setName(prop.getProperty("name"));
        personDetails.setSurname(prop.getProperty("surname"));
        Address address = new Address();
        address.setAddress(prop.getProperty("address"));
        address.setCity(prop.getProperty("city"));
        personDetails.setAddress(address);
        try {
            memory.getRepository(Repository.RUN_PLUGIN).put(prop.getProperty("nameReturnObject"), personDetails);
        } catch (RepositoryNotFound ex) {
            throw new RunPluginAbnormalTermination("Repository Not Found");
        }
    }

	@Override
	public List<String> getParametersName() {
		ArrayList<String> ret = new ArrayList();
		return ret;
	}

}
