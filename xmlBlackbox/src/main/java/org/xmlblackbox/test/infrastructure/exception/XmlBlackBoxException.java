package org.xmlblackbox.test.infrastructure.exception;


public class XmlBlackBoxException extends TestException {
 
    protected String errorMessage = "";

	public XmlBlackBoxException(){
		super();
	}
	
	public XmlBlackBoxException(Exception e){
		super(e);
	}

	public XmlBlackBoxException(Error e){
		super(e);
	}

	public XmlBlackBoxException(String arg0) {
		super(arg0);
		this.errorMessage = arg0;
	}
	
    public XmlBlackBoxException(String arg0, Exception e) {
		super(arg0, e);
		this.errorMessage = arg0;
	}

    public XmlBlackBoxException(String arg0, Error e) {
		super(arg0, e);
		this.errorMessage = arg0;
	}

	/**
     * 
    */
    public String toString()
    {
        return errorMessage;
    }

    /**
     * 
    */
    public String getMessage()
    {
        return errorMessage;
    }

	
}
