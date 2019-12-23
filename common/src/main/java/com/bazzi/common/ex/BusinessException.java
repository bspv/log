package com.bazzi.common.ex;


import com.bazzi.common.generic.LogStatusCode;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -1100962070456291776L;
	private String code;
	private String message;

	public BusinessException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public BusinessException(LogStatusCode logStatusCode) {
		this.code = logStatusCode.getCode();
		this.message = logStatusCode.getMessage();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
