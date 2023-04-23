package com.contus.searchapi.ExceptionHandler;

public class ContusException extends Exception {

	private static final long serialVersionUID = 1L;

	private String errorMessage;

	/**
	 * 
	 * @param ErrorMessage
	 */

	public ContusException(String message) {
		this.errorMessage = message;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
