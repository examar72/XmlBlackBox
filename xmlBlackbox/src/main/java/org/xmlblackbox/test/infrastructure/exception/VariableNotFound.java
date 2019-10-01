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

package org.xmlblackbox.test.infrastructure.exception;

import java.util.Hashtable;

import org.apache.log4j.Logger;

public class VariableNotFound extends XmlBlackBoxException {

    private Exception containedException = null;
    protected final static Logger log = Logger.getLogger(VariableNotFound.class);


    public VariableNotFound(String key,Hashtable repository) {
        super("Variable not found : "+key);
        log.error("---------------------------------------------------------");
        log.error("[!] Variable not found : " + key);
        log.error("[!] Repository contains : " + repository);
        log.error("---------------------------------------------------------");
    	this.errorMessage = "Variable not found : "+key;
    }

    /**
     * 
     */

    public VariableNotFound() {
        super();
    }

    /**
     * 
     * @param e
     */

    public VariableNotFound(Exception e) {
      containedException = e;
    }

    /**
     * 
     * @param ex
     * @param errorMessage
     */

    public VariableNotFound(Exception ex, String errorMessage) {
      containedException = ex;
      this.errorMessage = errorMessage;
    }

    /**
     * 
     * @param errorMessage
     * @param tagName
     */

    public VariableNotFound(String errorMessage, String tagName) {
      this.errorMessage = errorMessage+tagName;
    }

   /**
    * 
    * @param errorMessage
    */

   public VariableNotFound(String errorMessage) {

     this.errorMessage = "Variable not found : "+errorMessage;
   }


    /**
     * 
     */


    public Exception getContainedException() {
      return containedException;
    }


    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}

