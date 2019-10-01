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

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.util.DBConnection;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

/**
 *   <br>
 *
 *
 * @author Crea
 */
public class XmlDbConnections extends XmlStep{


	private final static Logger log = Logger.getLogger(XmlDbConnections.class);

    private List  setDbConnection = new Vector();

	public XmlDbConnections(Element dbConnectionElement) throws Exception {
		super(dbConnectionElement);
		build(dbConnectionElement);
	}
	
	public void build(Element dbConnectionElement) throws Exception {
		log.debug("dbConnectionElement "+dbConnectionElement);
		log.debug("dbConnectionElement.getAttributeValue(nome) "+dbConnectionElement.getAttributeValue("name"));
		setDbConnection = new Vector();

        log.info("DB-CONNECTIONS \n"+new XMLOutputter().outputString(dbConnectionElement));

    	Iterator<Element> setIterator = dbConnectionElement.getChildren("CONNECTION", uriXsd).iterator();
		log.debug("dbConnectionElement iterator size "+dbConnectionElement.getChildren("CONNECTION", uriXsd).size());
    	while(setIterator.hasNext()){
            Element setConnection = setIterator.next();
            if (setConnection!=null){
                DbConnection connection = new DbConnection();
                connection.setName(setConnection.getAttributeValue("name"));
                connection.setDriver(setConnection.getAttributeValue("driver"));
                connection.setDbUrl(setConnection.getAttributeValue("db-url"));
                connection.setUsername(setConnection.getAttributeValue("username"));
                connection.setPassword(setConnection.getAttributeValue("password"));


                getDbConnectionList().add(connection);
            }
        }
	}
	
	@Override
	public String getRepositoryName() {
		return Repository.DB_CONNECTION;
	}

    /**
     * @return the setList
     */
    public List getDbConnectionList() {
        return setDbConnection;
    }

    /**
     * @param setDbConnecgtionList 
     */
    public void setDbConnectionList(List setDbConnection) {
        this.setDbConnection = setDbConnection;
    }

    @Override
    public void execute(MemoryData session) throws Exception {
        Iterator connectionList = getDbConnectionList().iterator();
        while (connectionList.hasNext()) {
            DbConnection connection= (DbConnection) connectionList.next();
            Connection conn = DBConnection.getConnection(
                    connection.getDriver(),
                    connection.getDbUrl(),
                    connection.getUsername(),
                    connection.getPassword()
                    );
            log.info("connection ("+connection.getName()+" ok");
            session.getOrCreateRepository(Repository.DB_CONNECTION).put(connection.getName(), conn);

        }
    }


}