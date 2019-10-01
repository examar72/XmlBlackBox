package org.xmlblackbox.test.functional.examples.v13.plugin;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.server.SeleniumServer;
import org.xmlblackbox.test.infrastructure.exception.RepositoryNotFound;
import org.xmlblackbox.test.infrastructure.exception.RunPluginAbnormalTermination;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.plugin.GenericRunnablePlugin;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

import com.thoughtworks.selenium.DefaultSelenium;

public class ModifyConfigSeleniumServer extends GenericRunnablePlugin  {

 	/**
	 * 
	 */
	private static final long serialVersionUID = -3179115840492447020L;
	private final static Logger log = Logger.getLogger(ModifyConfigSeleniumServer.class);

	@Override
	public void execute(MemoryData memory) throws RunPluginAbnormalTermination {

		Hashtable<String, Object> table ;
        try {
            table = memory.getRepository(Repository.WEB_NAVIGATION);
        } catch (RepositoryNotFound ex) {
    		log.error("RepositoryNotFound ", ex );
            throw new RunPluginAbnormalTermination("RepositoryNotFound");
        }
        
        
        DefaultSelenium seleniumClient = (DefaultSelenium)table.get("SELENIUM-CLIENT-EXAMPLE-003");
        seleniumClient.stop();
        
        SeleniumServer seleniumServer = (SeleniumServer)table.get("SELENIUM-SERVER-EXAMPLE-003");
        seleniumServer.stop();
        String browserType = null;
        try {
			browserType = (String)memory.getRepository(Repository.INPUT_PROPERTIES).get("SELENIUM_ANOTHER_BROWSER_TYPE");
		} catch (RepositoryNotFound e1) {
    		log.error("RepositoryNotFound ", e1 );
    		throw new RunPluginAbnormalTermination(e1, "RepositoryNotFound");
		}
        seleniumServer.getConfiguration().setForcedBrowserMode(browserType);
        try {
			seleniumServer.start();
		} catch (Exception e) {
    		log.error("Server starting fault", e);
		}
		
        seleniumClient.start();
	}

	@Override
	public List<String> getParametersName() {
		ArrayList<String> ret = new ArrayList();
		return ret;
	}

}
