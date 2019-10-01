package org.xmlblackbox.test.functional.examples.v15.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.xmlblackbox.test.infrastructure.exception.RepositoryNotFound;
import org.xmlblackbox.test.infrastructure.exception.RunPluginAbnormalTermination;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.plugin.GenericRunnablePlugin;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

public class PluginExampleVersion15 extends GenericRunnablePlugin  {

    private final static Logger log = Logger.getLogger(PluginExampleVersion15.class);

	@Override
	public void execute(MemoryData memory) throws RunPluginAbnormalTermination {

		Properties prop ;
        try {
            prop = (Properties)memory.getRepository(Repository.RUN_PLUGIN);
            log.info("SetVariable repo "+(Properties)memory.getOrCreateRepository(Repository.SET_VARIABLE));
        } catch (RepositoryNotFound ex) {
    		log.error("RepositoryNotFound ", ex );
            throw new RunPluginAbnormalTermination("RepositoryNotFound");
        }


        if (prop.getProperty("varInRunPluginRepo") == null){
            throw new RunPluginAbnormalTermination("Variable varInRunPluginRepo not found");
        }

        if (!prop.getProperty("varInRunPluginRepo").equals("valueVarInRunPluginRepo")){
            throw new RunPluginAbnormalTermination("Variable varInRunPluginRepo does not contain expected value");
        }


    }

	@Override
	public List<String> getParametersName() {
		ArrayList<String> ret = new ArrayList();
		return ret;
	}

}
