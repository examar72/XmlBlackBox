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

import org.apache.log4j.Logger;

public class RepositoryNotFound extends XmlBlackBoxException {

    private Exception containedException = null;
    protected final static Logger log = Logger.getLogger(RepositoryNotFound.class);

    /**
     * 
     */

    public RepositoryNotFound() {
        super();
    }

    /**
     * 
     * @param ex
     * @param errorMessage
     */

    public RepositoryNotFound(Exception ex, String errorMessage) {
      containedException = ex;
      this.errorMessage = errorMessage;
    }

    /**
     * 
     * @param errorMessage
     * @param tagName
     */

    public RepositoryNotFound(String errorMessage, String tagName) {
      this.errorMessage = errorMessage+tagName;
    }

   /**
    * 
    * @param errorMessage
    */

   public RepositoryNotFound(String errorMessage) {

     this.errorMessage = "Repository not found : "+errorMessage;
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

