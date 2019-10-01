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

package org.xmlblackbox.test.infrastructure.xml;



import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.ITable;
import org.jdom.Element;
import org.apache.log4j.Logger;
import org.xmlblackbox.test.infrastructure.exception.InvalidVariableAnnotation;
import org.xmlblackbox.test.infrastructure.exception.RepositoryNotFound;


import org.xmlblackbox.test.infrastructure.exception.TestException;
import org.xmlblackbox.test.infrastructure.exception.VariableNotFound;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

import java.sql.Connection;
import org.dbunit.database.DatabaseConnection;
/**
 *
 * @author acrea
 */
public class WaitTask extends XmlStep{
	
	private final static Logger logger = Logger.getLogger(WaitTask.class);
	
	private boolean waitingTerminated=false;
	private String waitingQuery = null;
	private String waitingConnection = null;
	private String waitingTimeout = "0";
	
	public WaitTask(Element el) throws Exception {
		super(el);
        build(el);
	}
	
	public void build(Element waitElement) throws Exception {
    	
		WaitTask waitTask=this;
		logger.debug("waitElement.getAttributeValue(nome) "+waitElement.getAttributeValue("name"));
		waitTask.setName(waitElement.getAttributeValue("name"));
        waitTask.setWaitingConnection(waitElement.getAttributeValue("connection"));
		
    	Element waitTerminated = waitElement.getChild("WAIT", uriXsd);
    	logger.debug("waitTerminated "+waitTerminated);
    	if (waitTerminated!=null){
    		waitTask.setWaitingTerminated(true);
    		waitTask.setWaitingQuery(waitTerminated.getAttributeValue("query"));
    		
        	if (waitTerminated.getAttributeValue("timeout")!=null){
    			waitTask.setWaitingTimeout(waitTerminated.getAttributeValue("timeout"));
    		}

    	}
    	
	}
	
	public String getWaitingQuery() {
		return waitingQuery;
	}

	public void setWaitingQuery(String waitingQuery) {
		this.waitingQuery = waitingQuery;
	}

	public String getWaitingTimeout() {
		return waitingTimeout;
	} 

	public void setWaitingTimeout(String waitingTimeout) {
		this.waitingTimeout = waitingTimeout;
	}
	
	public boolean isWaitingTerminated() {
		return waitingTerminated;
	}

	public void setWaitingTerminated(boolean waitTerminated) {
		this.waitingTerminated = waitTerminated;
	}

	@Override
	public String getRepositoryName() {
		return Repository.WAIT_TASK;
	}
	
    public void execute(MemoryData session) throws Exception {
		if (isWaitingTerminated()){
	    	boolean waitExit = false;
	    	int index = 0;
	    	int timeout = new Integer(getWaitingTimeout());
    		logger.debug("connecting to "+getConnection());

            IDatabaseConnection conn;
            if (getConnection().indexOf("@")==-1){
                conn = new DatabaseConnection((Connection)session.getRepository(Repository.DB_CONNECTION).get(getConnection()));
            }else{
                conn = new DatabaseConnection((Connection)session.get(getConnection()));

            }

	    	while(!waitExit && (index<timeout)){
	    		String query = getWaitingQuery();
	    		logger.debug("query "+query);
	    		ITable waiTaskItable = null;
	    		try{
	    			waiTaskItable = org.xmlblackbox.test.infrastructure.util.ITableUtil.getITable(conn,
	    				"WAITTASK", query);
	    		}catch(Exception e){
	    			logger.error("Exception ", e);
	    			throw new TestException("Exception during query execution "+getWaitingQuery()+" for WAIT-TASK");
	    			
	    		}
	    		
	    		logger.debug("waiTaskItable.getRowCount() "+waiTaskItable.getRowCount());
	    		if (waiTaskItable.getRowCount()>=1){
		    		logger.debug("Found one record according to the query executed");
	    			waitExit = true;
	    		}else{
	    			try {
	    	    		logger.info(index+" Sleep one second before checking whether the query return results");
						Thread.sleep(1000);
						index++;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		}
	    		logger.debug("waitExit "+waitExit);
	    		logger.debug("(waitTask.getWaitingTimeout()) "+(getWaitingTimeout()));
	    		logger.debug("(index<waitTask.getWaitingTimeout()) "+(index<timeout));
	    		
	    	}
	    	
		}
	}
	
    /**
     * @return the waitingConnection
     */
    public String getConnection() {
        return waitingConnection;
    }

    /**
     * @param waitingConnection the waitingConnection to set
     */
    public void setWaitingConnection(String waitingConnection) {
        this.waitingConnection = waitingConnection;
    }

}