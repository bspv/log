package com.bazzi.common.ex;

public class ParameterException extends RuntimeException {
	private static final long serialVersionUID = -578826259136348403L;
	private String code;
	private String message;

	public ParameterException(String code, String message) {
		this.code = code;
		this.message = message;
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
