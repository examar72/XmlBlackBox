package org.xmlblackbox.test.functional.examples.v24.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.xmlblackbox.test.functional.examples.v13.plugin.Person;
import org.xmlblackbox.test.infrastructure.exception.RepositoryNotFound;
import org.xmlblackbox.test.infrastructure.exception.RunPluginAbnormalTermination;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.plugin.GenericRunnablePlugin;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

public class ObjectInSessionOutput extends GenericRunnablePlugin  {

    private final static Logger log = Logger.getLogger(ObjectInSessionOutput.class);

	@Override
	public void execute(MemoryData memory) throws RunPluginAbnormalTermination {

		Properties prop ;
        try {
            prop = (Properties)memory.getRepository(Repository.RUN_PLUGIN);
        } catch (RepositoryNotFound ex) {
    		log.error("RepositoryNotFound ", ex );
            throw new RunPluginAbnormalTermination("RepositoryNotFound");
        }

        Person person;
        
        try {
            person = (Person)memory.getRepository(Repository.RUN_PLUGIN).get(prop.getProperty("objectInSession"));
        } catch (RepositoryNotFound ex) {
            throw new RunPluginAbnormalTermination("Repository Not Found");
        }

        if (person==null){
            throw new RunPluginAbnormalTermination("person@runPlugin object not found in session");

        }
        log.info("person.getName() "+person.getName());
        log.info("person.getSurname() "+person.getSurname());


    }

	@Override
	public List<String> getParametersName() {
		ArrayList<String> ret = new ArrayList();
		return ret;
	}

}
