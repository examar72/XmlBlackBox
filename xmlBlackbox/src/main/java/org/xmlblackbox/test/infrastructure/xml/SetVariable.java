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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.xmlblackbox.test.infrastructure.exception.InvalidVariableAnnotation;
import org.xmlblackbox.test.infrastructure.exception.RepositoryNotFound;
import org.xmlblackbox.test.infrastructure.exception.SetVariableException;
import org.xmlblackbox.test.infrastructure.exception.TestException;
import org.xmlblackbox.test.infrastructure.exception.VariableNotFound;
import org.xmlblackbox.test.infrastructure.interfaces.Repository;
import org.xmlblackbox.test.infrastructure.util.MemoryData;

/**
 *
 * @author Crea
 */
public class SetVariable extends XmlStep{


	private final static Logger log = Logger.getLogger(SetVariable.class);

    private List  setList = new Vector();

	public SetVariable(Element setVarElement) throws Exception {
		super(setVarElement);
		build(setVarElement);
	}
	
	public void build(Element setVarElement) throws Exception {
		log.debug("setVarElement.getAttributeValue(nome) "+setVarElement.getAttributeValue("name"));
		setList = new Vector();
        log.debug("SET-VARIABLE build element "+new XMLOutputter().outputString(setVarElement));

    	Iterator<Element> setIterator = setVarElement.getChildren("SET", uriXsd).iterator();
        log.debug("setIterator "+setVarElement.getChildren("SET", uriXsd).size());

        if (!setIterator.hasNext()){
            throw new TestException("SET tag not found");
        }

    	while(setIterator.hasNext()){
            Element setVariable = setIterator.next();
            if (setVariable!=null){
                Set set = new Set();
                set.setNome(setVariable.getAttributeValue("name"));
                set.setValue(setVariable.getAttributeValue("value"));
                set.setQueryBean(setVariable.getAttributeValue("bean"));
                set.setType(setVariable.getAttributeValue("type"));
                set.setFileName(setVariable.getAttributeValue("filename"));
                set.setNamespace(setVariable.getAttributeValue("namespace"));
                set.setXPath(setVariable.getAttributeValue("xpath"));
                set.setQuery(setVariable.getAttributeValue("query"));
                set.setConnection(setVariable.getAttributeValue("connection"));
                set.setRepository(setVariable.getAttributeValue("repository"));

                getSetList().add(set);
            }
        }
	}
	
	@Override
	public String getRepositoryName() {
		return Repository.SET_VARIABLE;
	}

    /**
     * @return the setList
     */
    public List getSetList() {
        return setList;
    }

    /**
     * @param setList the setList to set
     */
    public void setSetList(List setList) {
        this.setList = setList;
    }

    public static  void setVariableFromXml(Set set, MemoryData session, String repository) throws Exception {


        Properties prop = (Properties)session.getOrCreateRepository(repository);
        XmlObject[] xmlObject = null;
        XmlObject xobj = null;
        try {
            InputStream is = new FileInputStream(set.getFileName());
            xobj = XmlObject.Factory.parse(is);
            xmlObject = xobj.selectPath(set.getNamespace()+set.getXPath());

            if (xmlObject.length == 0){
                String error= "XmlObject not found searching by xpath query \""+set.getNamespace()+set.getXPath()+"\". Check the namespace.";
                throw new SetVariableException(error);
            }


        }
        catch (XmlException e) {
            log.error("XmlException ", e);
            e.printStackTrace();
            throw new TestException();
        }
        catch (IOException e) {
            log.error("IOException ", e);
            e.printStackTrace();
            throw new TestException();
        }

        String value = xmlObject[0].newCursor().getTextValue();
        log.debug("set.getNome() "+set.getNome());
        log.debug("value "+value);

        prop.setProperty(set.getNome(), value);


    }

    public static void setVariableFromDb(Set currentSet, MemoryData memory, String repository)
            throws TestException, RepositoryNotFound, InvalidVariableAnnotation, VariableNotFound {
        try {

            Properties prop = (Properties)memory.getOrCreateRepository(repository);
            IDatabaseConnection conn;

            if (currentSet.getConnection().indexOf("@")==-1){
                conn = new DatabaseConnection((Connection)memory.getRepository(Repository.DB_CONNECTION).get(currentSet.getConnection()));
            }else{
                conn = new DatabaseConnection((Connection)memory.get(currentSet.getConnection()));

            }

            log.debug("query: " + currentSet.getQuery());
            log.debug("conn: " + conn);
            log.debug("currentSet: " + currentSet);
            log.debug("currentSet.getConnection(): " + currentSet.getConnection());
            log.debug("conn.getConnection(): " + conn.getConnection());
            PreparedStatement prepareStatement = conn.getConnection().prepareStatement(currentSet.getQuery());
            ResultSet resultSet= prepareStatement.executeQuery();
            if (resultSet.next()){
                log.debug("result: " + resultSet.getString(1));
                prop.setProperty(currentSet.getNome(), resultSet.getString(1));
                log.info("Value extract from DB " + resultSet.getString(1));
            } else {
                prop.setProperty(currentSet.getNome(), "");
                log.info("No results founded for set " + currentSet.getNome()+"!!!!!!!!!!");
            }
            if (resultSet.next()) {
                throw new TestException("More than one result founded for set " + currentSet.getNome());
            }
        }  catch (SQLException ex) {
            log.error("SQLException ", ex);
            throw new TestException();
        }



    }

    @Override
    public void execute(MemoryData session) throws Exception {
        Iterator<Set> iter = getSetList().iterator();
        log.debug("getSetList().size() "+getSetList().size());

        while(iter.hasNext()){
            String setRepository = null;
            Set currentSet = iter.next();

            if (currentSet.getRepository()!=null){
                setRepository = currentSet.getRepository();
            }else{
                setRepository = getRepositoryName();
            }
            log.debug("setRepository "+setRepository);
            log.debug("currentSet.getNome() "+currentSet.getNome());
            log.debug("currentSet.getValue() "+currentSet.getValue());
            log.debug("currentSet.getType() " + currentSet.getType());

            if (currentSet.getType()!=null){
                if (currentSet.getType().equals(Set.VALUE_TYPE)){
//                    if (currentSet.getValue()!=null && currentSet.getQueryBean()!=null){
//                        throw new SetVariableException("value and query-bean cannot be set together in the same SET-VARIABLE");
//                    }
                    session.set(setRepository,currentSet.getNome(),currentSet.getValue());
                }else if (currentSet.getType().equals(Set.OBJ_TYPE)){
                	if (currentSet.getQueryBean()==null){
                		throw new SetVariableException("Bean not found for "+currentSet.getNome()+" variable");
                	}
                    session.set(setRepository, 
                    		currentSet.getNome(),
                    			session.get(currentSet.getQueryBean()));
                }else if(currentSet.getType().equals(Set.XML_TYPE)){
                    SetVariable.setVariableFromXml(currentSet, session, setRepository);
                }else if(currentSet.getType().equals(Set.DB_TYPE)) {
                    SetVariable.setVariableFromDb(currentSet, session, setRepository);
                }
            }else{
                throw new TestException("The type in SET tag ("+currentSet.getNome()+") is not correctly set");
            }

        }
    }

    public void checkMandatoryField() throws Exception{
        Iterator<Set> iter = getSetList().iterator();
        log.debug("checkMandatoryField getSetList().size() "+getSetList().size());

        while(iter.hasNext()){
            Set currentSet = iter.next();
            if (currentSet.getType().equals(Set.DB_TYPE) && currentSet.getConnection()==null){
                throw new SetVariableException(SetVariableException.CONNECTION_NOT_SETUP, currentSet.getNome());
            }
        }
    }

}