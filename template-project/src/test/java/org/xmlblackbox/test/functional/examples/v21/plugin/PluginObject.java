package org.xmlblackbox.test.functional.examples.v21.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.xmlblackbox.test.infrastructure.exception.RepositoryNotFound;
import org.xmlblackbox.test.infrastructure.exception.RunPluginAbnormalTermination;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

public class PluginObject {

    private final static Logger log = Logger.getLogger(PluginObject.class);

	public void execute(MemoryData memory) throws RunPluginAbnormalTermination {

		Properties prop ;
        try {
            prop = (Properties)memory.getRepository(Repository.RUN_PLUGIN);
        } catch (RepositoryNotFound ex) {
    		log.error("RepositoryNotFound ", ex );
            throw new RunPluginAbnormalTermination("RepositoryNotFound");
        }

    }

	public List<String> getParametersName() {
		ArrayList<String> ret = new ArrayList();
		return ret;
	}

}
