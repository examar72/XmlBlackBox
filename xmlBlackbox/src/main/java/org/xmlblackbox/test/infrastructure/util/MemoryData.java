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

package org.xmlblackbox.test.infrastructure.util;

import org.xmlblackbox.test.infrastructure.exception.InvalidVariableAnnotation;
import org.xmlblackbox.test.infrastructure.exception.RepositoryNotFound;
import org.xmlblackbox.test.infrastructure.exception.VariableNotFound;

import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.log4j.Logger;




public class MemoryData {

	private final static Logger log = Logger.getLogger(MemoryData.class);
	private Hashtable<String, Hashtable> memory=new Hashtable();

	public Object getOld(String key) throws RepositoryNotFound, InvalidVariableAnnotation, VariableNotFound{
		String repoName = getRepositoryName(key);
		String varName  = getVariableName(key);
		
		Hashtable repo = getRepository(repoName);
		Object var = repo.get(varName);
		
		if (var == null){
			throw new VariableNotFound(key, repo);
		}

		log.info("- get : " + key + " = " + var);
		return var;
	}

    public Object get(String key) throws RepositoryNotFound, InvalidVariableAnnotation, VariableNotFound{
		String depth =  getDeepPath(key);
		String repoName = getRepositoryName(key);
		String varName  = getVariableName(key);

		Hashtable repo = getRepository(repoName);
		Object var = repo.get(varName);

		if (var == null){
			throw new VariableNotFound(key, repo);
		}

		// The deep of object.
		if (!depth.equals("")){
			var = getRecursiveObject(var,depth);
		}

		return var;
	}


	public void set(String key,Object value) throws InvalidVariableAnnotation{
		checkVariableAnnotation(key);
		String repoName = getRepositoryName(key);
		String varName  = getVariableName(key);

		this.set(repoName,varName,value);
	}
	
	public void set(String repoName, String varName,Object value) throws InvalidVariableAnnotation{
		Hashtable repo;
		try {
			repo = getRepository(repoName);
		} catch (RepositoryNotFound e) {
			repo = createRepository(repoName);
		}
		log.info("- set : " + varName + "@" + repoName + " = " + value);
		repo.put(varName, value);
	}

	public void overrideRepository(String repoName,Properties props) {
		log.debug("- overrideRepository : " + repoName + " = " + props);
		memory.put(repoName, props);
	}

    public void addToRepository(String repoName,Properties props) {
		log.debug("- overrideRepository : " + repoName + " = " + props);
        Properties crrentProp = (Properties)memory.get(repoName);
		crrentProp.putAll(props);
	}

	private Properties createRepository(String repoName){
		log.debug("- createRepository : " + repoName);
		Properties newRepo = new Properties();
		memory.put(repoName, newRepo);
		return newRepo;
	}

	public Properties Hashtable2Properties(Hashtable input){
		log.debug("- Hash to Properties : " + input );
		Properties prop = new Properties();
		prop.putAll(input);
		return prop;
	}

	private void checkVariableAnnotation(String key) throws InvalidVariableAnnotation{
		if (!key.matches(".+@.+")){
			throw new InvalidVariableAnnotation();
		}
	}

	private String[] splitKey(String key) throws InvalidVariableAnnotation{
		checkVariableAnnotation(key);
		String[] keySplitted=key.split("@");
		return keySplitted;
	}
	
	public Hashtable getRepository(String repoName) throws RepositoryNotFound{
		Hashtable repo = memory.get(repoName);
		if (repo == null){
            throw new RepositoryNotFound(repoName);
		}

		log.debug("- get repository : " + repoName + " = " + repo);
		return repo;
	}

    public boolean existRepository(String repoName) throws RepositoryNotFound{
		Hashtable repo = memory.get(repoName);
		if (repo == null){
			return false;
		}else{
			return true;
        }

	}


    public boolean existVariable(String key) throws RepositoryNotFound, InvalidVariableAnnotation{


        String repoName = getRepositoryName(key);
		if (!existRepository(repoName)){
            return false;
        }
        String varName  = getVariableName(key);
		Hashtable repo = getRepository(repoName);
		Object var = repo.get(varName);
		if (var == null){
			return false;
		}

        return true;

	}

	
	public Hashtable getOrCreateRepository(String repoName){
		Hashtable repo;
		try {
			repo = getRepository(repoName);
		} catch (RepositoryNotFound e) {
			repo = createRepository(repoName);
		}
		return repo;
	}

	private String getRepositoryName(String key) throws InvalidVariableAnnotation{
		return splitKey(key)[1].split(":")[0];
	}

	public String getVariableName(String key) throws InvalidVariableAnnotation{
		return splitKey(key)[0];
	}

	public Map getMemory2Map(){
		log.debug("- get memory : " + memory);
		return memory;
	}
	
	public void debugMemory(){
		log.warn("---------------------------------------");
		log.warn("------------- DEBUG MEMORY ------------");
		log.warn("---------------------------------------");

		log.warn("***************************************");
		log.warn("memory "+memory);

		log.warn("***************************************");
	}

    public void resetSession(){
        memory=new Hashtable();
    }

    private String getDeepPath(String key) throws InvalidVariableAnnotation{
		checkVariableAnnotation(key);
		String[] keySplitted=key.split(":");
		if (keySplitted.length > 1){
			return keySplitted[1];
		}

		return "";
	}

	private Object getRecursiveObject(Object obj,String properties) throws VariableNotFound {

		String[] navigate = properties.split("\\.");
		BeanUtilsBean bu = new BeanUtilsBean();
		Object toreturn = obj;
		try {
			for (int i = 0 ; i<navigate.length; i++)
				toreturn = bu.getPropertyUtils().getProperty(toreturn, navigate[i]);
		} catch (IllegalAccessException e) {
			throw new VariableNotFound(e);
		} catch (InvocationTargetException e) {
			throw new VariableNotFound(e);
		} catch (NoSuchMethodException e) {
			throw new VariableNotFound(e);
		}

		return toreturn;
	}

}
