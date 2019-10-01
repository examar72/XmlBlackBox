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

import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.jdom.Element;
import org.xmlblackbox.test.infrastructure.exception.DbCheckException;
import org.xmlblackbox.test.infrastructure.exception.XmlBlackBoxException;
import org.xmlblackbox.test.infrastructure.util.MemoryData;
/**
 * Es. Esegue una query
 * <br>
 *     &lt;EXECUTE-QUERY nome="CambioStatoDomanda"&gt;<br>
 *        &lt;QUERY name="nome di questo tag" query="UPDATE RICHIESTA_VARIAZ SET STATO_RICHIESTA='presaInCarico' WHERE ID_DOMANDA_AGEV=${idDomandaPresaInCarico@setVariable}" type="UPDATE"/&gt;<br>
 *    &lt;/EXECUTE-QUERY&gt;<br>
 *
 * @author Crea
 */
public class RunQuery extends XmlStep{


	private final static Logger log = Logger.getLogger(RunQuery.class);
    private Parameters parameters;

    private String connection;

    private List queryList = new Vector();


	public RunQuery(Element el) throws Exception {
		super(el);
		build(el);
	}

	public void build(Element executeQueryElement) throws Exception{
		log.debug("executeQueryElement.getAttributeValue(nome) "+executeQueryElement.getAttributeValue("name"));
		log.debug("executeQueryElement.getChildren(\"QUERY\").size() "+executeQueryElement.getChildren("QUERY").size());
		queryList = new Vector();


//        Element parametersElement = executeQueryElement.getChild("PARAMETERS");
//    	if (parametersElement!=null) {
//    		Iterator parametersList = parametersElement.getChildren("PARAMETER", uriXsd).iterator();
//    		while (parametersList.hasNext()){
//    			Element parameterElement = (Element) parametersList.next();
//    			String pname = parameterElement.getAttributeValue("name");
//    			String pvalue = parameterElement.getAttributeValue("value");
//
//    			parameters.put(pname, pvalue);
//    		}
//    	}
        Element parametersElement = executeQueryElement.getChild("PARAMETERS", uriXsd);
    	log.debug("parametersElement "+parametersElement);
    	if (parametersElement!=null){
            parameters = new Parameters(parametersElement);
        }
//    	parameters = parseParameters(executeQueryElement);

    	Iterator<Element> queryIterator = executeQueryElement.getChildren("QUERY", uriXsd).iterator();
    	while(queryIterator.hasNext()){
            Element executeQuery = queryIterator.next();
			log.debug("executeQueryElement.getAttributeValue(nome) "+executeQuery.getAttributeValue("nome"));
    		log.debug("executeQueryElement.getAttributeValue(query) "+executeQuery.getAttributeValue("query"));
            log.debug("executeQueryElement.getAttributeValue(type) "+executeQuery.getAttributeValue("type"));
            log.debug("executeQueryElement.getAttributeValue(connection) "+executeQuery.getAttributeValue("connection"));

            if (executeQuery!=null){
                Query query = new Query();
                query.setNome(executeQuery.getAttributeValue("name"));
                query.setQuery(executeQuery.getAttributeValue("query"));
                query.setType(executeQuery.getAttributeValue("type"));
                query.setConnection(executeQuery.getAttributeValue("connection"));


                getQueryList().add(query);
            }
        }
	}

	@Override
	public String getRepositoryName() {
		return Repository.SET_VARIABLE;
	}

    /**
     * @return the queryList
     */
    public List getQueryList() {
        return queryList;
    }

    /**
     * @param queryList the queryList to set
     */
    public void setQueryList(List queryList) {
        this.queryList = queryList;
    }

    /**
     * @return the connection
     */
    public String getConnection() {
        return connection;
    }

    @Override
    public void execute(MemoryData session) throws Exception,XmlBlackBoxException {
        Iterator<Query> iter = getQueryList().iterator();

        while(iter.hasNext()){
            Query query = iter.next();
        	try{
	            log.info("Query Connection "+query.getConnection());
	            IDatabaseConnection conn;
	            if (query.getConnection().indexOf("@")==-1){
	                conn = new DatabaseConnection((Connection)session.getRepository(Repository.DB_CONNECTION).get(query.getConnection()));
	            }else{
	                conn = new DatabaseConnection((Connection)session.get(query.getConnection()));
	            }
	            if (conn == null ||conn.getConnection()==null){
	                throw new DbCheckException("Connection \""+query.getConnection()+"\" not found");
	            }

	            log.info("Run query "+query.getQuery()+" about query tag "+query.getNome());
	            if (query.getType().equals(Query.UPDATE) || query.getType().equals(Query.INSERT)){
	                int result = conn.getConnection().createStatement().executeUpdate(query.getQuery());
	                log.info(query.getType()+" result "+result);
	            }else{
	                boolean result = conn.getConnection().createStatement().execute(query.getQuery());
	                log.info(query.getType()+" result "+result);
	            }
	        }catch(Exception e){
	        	log.error("Exception during query execution ("+query.getQuery()+")");
	            log.error("Exception "+e.getMessage(), e);
	            throw new XmlBlackBoxException(e);
	        }catch(Error e){
	        	log.error("Exception during query execution ("+query.getQuery()+")");
	            log.error("Exception", e);
	            throw new XmlBlackBoxException(e);
	        }
	    }
    }

}
