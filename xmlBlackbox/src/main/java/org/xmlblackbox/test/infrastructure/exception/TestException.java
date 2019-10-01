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

/**
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version $Id: $
 */

public class TestException extends Exception{

    private Exception containedException = null;
    private Error containedError= null;
    private String errorMessage = null;
    protected final static Logger log = Logger.getLogger(TestException.class);

    /**
     * 
    */
    public TestException() {
        super();
    }

	public TestException(Error e){
		super(e);
		this.containedError = e;
	}

	public TestException(Exception e){
		super(e);
		this.containedException = e;
	}

	public TestException(String arg0, Exception arg1) {
		super(arg0, arg1);
		this.containedException = arg1; 
	}

	public TestException(String arg0, Error arg1) {
		super(arg0, arg1);
		this.containedError= arg1; 
	}


    /**
    * Costruttore con argomenti
    */
    public TestException(Exception ex, String errorMessage) {
      this.containedException = ex;
      this.errorMessage = errorMessage;
    }


   /**
   * Costruttore con argomenti
   */
   public TestException(String errorMessage) {

     this.errorMessage = errorMessage;
   }


    /**
    * Restituisce l'eccezione passata nel costruttore
    *
    * @return containedException
    */
    public Throwable getContainedException() {
      return containedException;
    }

    /**
    * Restituisce il messaggio di errore passato nel costruttore
    *
    * @return errorMessage
    */
    public String toString()
    {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}