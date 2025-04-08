package com.blo.sales.exceptions;

import org.springframework.http.HttpStatus;

public class BloSalesBusinessException extends Exception {

	private static final long serialVersionUID = 6181451194393182432L;
	
	private String exceptionMessage;
	
	private HttpStatus exceptionHttpStatus;
	
	public BloSalesBusinessException(String error, String errorCode) {
		super("[" + errorCode + "] " + error);
		exceptionMessage = this.getMessage();
		exceptionHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public BloSalesBusinessException(String error, String errorCode, HttpStatus httpStatus) {
		super("[" + errorCode + "] " + error);
		exceptionMessage = this.getMessage();
		exceptionHttpStatus = httpStatus;
	}
	
	public String getExceptionMessage() {
		return this.exceptionMessage;
	}
	
	public HttpStatus getExceptHttpStatus() {
		return this.exceptionHttpStatus;
	}
}
