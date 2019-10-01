package org.xmlblackbox.test.functional.examples.v13.plugin;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.derby.drda.NetworkServerControl;
import org.apache.log4j.Logger;
import org.xmlblackbox.test.infrastructure.exception.RepositoryNotFound;
import org.xmlblackbox.test.infrastructure.exception.RunPluginAbnormalTermination;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.plugin.GenericRunnablePlugin;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

public class StopDerbyDatabaseXBB extends GenericRunnablePlugin  {

    private final static Logger log = Logger.getLogger(StopDerbyDatabaseXBB.class);

	@Override
	public void execute(MemoryData memory) throws RunPluginAbnormalTermination {

		Properties prop =null;
		Properties propDB =null ;
        NetworkServerControl server = null;
        try {
            prop = (Properties)memory.getRepository(Repository.RUN_PLUGIN);
            
            if (memory.existRepository(Repository.DB_CONNECTION)){
	            propDB = (Properties)memory.getRepository(Repository.DB_CONNECTION);
	        	Connection conn = (Connection)propDB.get("DERBY");
	        	conn.close();
	    		log.info("Closed DERBY connection");
				if (((String)memory.getRepository(Repository.INPUT_PROPERTIES).get("EMBEDDED_DERBY_DATABASE")).equals("true")){
	
					server = (NetworkServerControl)prop.get("DerbyServer");
					server.shutdown();
	
	    			log.info("Shutdown DERBY server");
				}
            }

        } catch (RepositoryNotFound ex) {
    		log.error("RepositoryNotFound ", ex );
    		//If the derby server instance doesn't exist no exception has thrown 
//            throw new RunPluginAbnormalTermination("RepositoryNotFound");
        } catch (Exception e) {
    		log.error("Exception", e);
            throw new RunPluginAbnormalTermination("Exception");
        }

    }

	@Override
	public List<String> getParametersName() {
		ArrayList<String> ret = new ArrayList();
		return ret;
	}

}
