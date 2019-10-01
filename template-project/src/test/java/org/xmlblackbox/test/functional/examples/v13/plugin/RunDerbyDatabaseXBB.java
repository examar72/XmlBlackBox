package org.xmlblackbox.test.functional.examples.v13.plugin;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.derby.drda.NetworkServerControl;
import org.apache.log4j.Logger;
import org.xmlblackbox.test.infrastructure.exception.RepositoryNotFound;
import org.xmlblackbox.test.infrastructure.exception.RunPluginAbnormalTermination;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.plugin.GenericRunnablePlugin;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

public class RunDerbyDatabaseXBB extends GenericRunnablePlugin  {

    private final static Logger log = Logger.getLogger(RunDerbyDatabaseXBB.class);

	@Override
	public void execute(MemoryData memory) throws RunPluginAbnormalTermination {

		Properties prop ;
        try {
            prop = (Properties)memory.getRepository(Repository.RUN_PLUGIN);
        } catch (RepositoryNotFound ex) {
    		log.error("RepositoryNotFound ", ex );
            throw new RunPluginAbnormalTermination("RepositoryNotFound");
        }

    	try {
			if (((String)memory.getRepository(Repository.INPUT_PROPERTIES).get("EMBEDDED_DERBY_DATABASE")).equals("true")){

				String derbyPath = (String)memory.getRepository(Repository.INPUT_PROPERTIES).get("DERBY_PATH");

				log.info("starting derby server....");
				System.setProperty("derby.system.home", derbyPath);
				System.setProperty("derby.drda.portNumber", "1528");
				System.setProperty("derby.drda.host", "localhost");
				NetworkServerControl server =
					new NetworkServerControl(InetAddress.getByName("localhost"),1527, "xmlblackbox", "xmlblackbox");
				server.start(new PrintWriter(System.out));
				try {
					memory.getRepository(Repository.RUN_PLUGIN).put("DerbyServer", server);
				} catch (RepositoryNotFound ex) {
					throw new RunPluginAbnormalTermination("Repository Not Found");
				}

	//    	    log.info("server.getSysinfo() "+server.getSysinfo());
	//    	    log.info("server.getRuntimeInfo() "+server.getRuntimeInfo());
				log.info("Derby server started");
				try {Thread.sleep(2000);} catch (Exception ignored) {}
			}
		} catch (Exception e) {
			log.error("exception ", e);
			throw new RunPluginAbnormalTermination("Unable to load Derby server");
		}

    }

	@Override
	public List<String> getParametersName() {
		ArrayList<String> ret = new ArrayList();
		return ret;
	}

}
