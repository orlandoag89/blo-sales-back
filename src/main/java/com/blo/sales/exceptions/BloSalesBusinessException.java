package com.blo.sales.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public @Getter class BloSalesBusinessException extends Exception {

	private static final long serialVersionUID = 6181451194393182432L;
	
	private String exceptionMessage;
	
	private HttpStatus exceptionHttpStatus;
	
	private String errorCode;
	
	public BloSalesBusinessException(String errorMessage, String errorCode) {
		super("[" + errorCode + "] " + errorMessage);
		exceptionMessage = errorMessage;
		exceptionHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		this.errorCode = errorCode;
	}

	public BloSalesBusinessException(String errorMessage, String errorCode, HttpStatus httpStatus) {
		super("[" + errorCode + "] " + errorMessage);
		exceptionMessage = errorMessage;
		exceptionHttpStatus = httpStatus;
		this.errorCode = errorCode;
	}
		
	public HttpStatus getExceptHttpStatus() {
		return this.exceptionHttpStatus;
	}
}
