package org.xmlblackbox.test.infrastructure.exception;

public class NavigationException extends XmlBlackBoxException {

	public NavigationException(Exception e){
		super(e);
	}

	public NavigationException(String string) {
		super(string);
	}


}
