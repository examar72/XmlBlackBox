package org.xmlblackbox.test.functional.examples.v22.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.xmlblackbox.test.infrastructure.exception.RepositoryNotFound;
import org.xmlblackbox.test.infrastructure.exception.RunPluginAbnormalTermination;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.plugin.GenericRunnablePlugin;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

public class CheckVariables extends GenericRunnablePlugin  {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1914762602416121948L;
	private final static Logger log = Logger.getLogger(CheckVariables.class);

	@Override
	public void execute(MemoryData memory) throws RunPluginAbnormalTermination {

		Properties prop ;
        try {
            prop = (Properties)memory.getRepository(Repository.SET_VARIABLE);
        } catch (RepositoryNotFound ex) {
    		log.error("RepositoryNotFound ", ex );
            throw new RunPluginAbnormalTermination("RepositoryNotFound");
        }

        NameSurname johnDetails;
        
        try {
        	johnDetails = (NameSurname)memory.getRepository(Repository.RUN_PLUGIN).get("john");
        } catch (RepositoryNotFound ex) {
            throw new RunPluginAbnormalTermination("Repository Not Found");
        }
        
        Assert.assertTrue("Name not expected ", johnDetails.getName().equals("John"));
        Assert.assertTrue("Surname not expected ", johnDetails.getSurname().equals("Malone"));
        Assert.assertTrue("Address not expected ", johnDetails.getAddress().getAddress().equals("105st Evenue"));
        Assert.assertTrue("City not expected ", johnDetails.getAddress().getCity().equals("New York"));
        
        Address johnAddress;
        
        try {
        	johnAddress = (Address)memory.getRepository(Repository.SET_VARIABLE).get("johnAddress");
        } catch (RepositoryNotFound ex) {
            throw new RunPluginAbnormalTermination("Repository Not Found");
        }
        
        Assert.assertTrue("Address not expected ", johnAddress.getAddress().equals("105st Evenue"));
        Assert.assertTrue("City not expected ", johnAddress.getCity().equals("New York"));

        
        NameSurname martinDetails;
        
        try {
        	martinDetails = (NameSurname)memory.getRepository(Repository.RUN_PLUGIN).get("martin");
        } catch (RepositoryNotFound ex) {
            throw new RunPluginAbnormalTermination("Repository Not Found");
        }
        
        Assert.assertTrue("Name not expected ", martinDetails.getName().equals("Martin"));
        Assert.assertTrue("Surname not expected ", martinDetails.getSurname().equals("McDowell"));
        Assert.assertTrue("Address not expected ", martinDetails.getAddress().getAddress().equals("95st Street"));
        Assert.assertTrue("City not expected ", martinDetails.getAddress().getCity().equals("Chicago"));
 
        String martinSurname;
        try {
        	martinSurname= (String)memory.getRepository(Repository.SET_VARIABLE).get("martinSurname");
        } catch (RepositoryNotFound ex) {
            throw new RunPluginAbnormalTermination("Repository Not Found");
        }
 
        Assert.assertTrue("Martin surname not expected", martinSurname.equals("McDowell"));
        
        Address martinAddress;
        
        try {
        	martinAddress = (Address)memory.getRepository(Repository.SET_VARIABLE).get("martinAddress");
        } catch (RepositoryNotFound ex) {
            throw new RunPluginAbnormalTermination("Repository Not Found");
        }
        
        Assert.assertTrue("Address not expected ", martinAddress.getAddress().equals("95st Street"));
        Assert.assertTrue("City not expected ", martinAddress.getCity().equals("Chicago"));
        
    }

	@Override
	public List<String> getParametersName() {
		ArrayList<String> ret = new ArrayList();
		return ret;
	}

}
