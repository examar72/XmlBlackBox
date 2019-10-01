package org.xmlblackbox.test.infrastructure.exception;

public class TagExceptionFault extends XmlBlackBoxException {

	public TagExceptionFault(Exception e) {
		super(e);
	}
	
	public TagExceptionFault(){
		super();
	}

	public TagExceptionFault(String arg0, Exception arg1) {
		super(arg0, arg1);
	}

	public TagExceptionFault(String arg0) {
		super(arg0);
	}
	
	 

}
