package org.xmlblackbox.test.functional.examples.v13.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.xmlblackbox.test.infrastructure.exception.RepositoryNotFound;
import org.xmlblackbox.test.infrastructure.exception.RunPluginAbnormalTermination;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.plugin.GenericRunnablePlugin;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

public class CreateNewInformationObject extends GenericRunnablePlugin  {

    private final static Logger log = Logger.getLogger(CreateNewInformationObject.class);

	@Override
	public void execute(MemoryData memory) throws RunPluginAbnormalTermination {

		Properties prop ;
        try {
            prop = (Properties)memory.getRepository(Repository.RUN_PLUGIN);
        } catch (RepositoryNotFound ex) {
    		log.error("RepositoryNotFound ", ex );
            throw new RunPluginAbnormalTermination("RepositoryNotFound");
        }

        Person person = new Person();
        person.setName("Lorenz");
        person.setSurname("Manzon");
        try {
            memory.getRepository(Repository.RUN_PLUGIN).put(prop.getProperty("nameReturnObject"), person);
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
