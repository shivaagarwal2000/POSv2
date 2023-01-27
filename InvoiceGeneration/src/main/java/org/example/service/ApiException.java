package org.example.service;

public class ApiException extends Exception {// TODO Move to commons Priority: 5

	private static final long serialVersionUID = 1L;
	
	public ApiException(String string) {
		super(string);
	}

}
