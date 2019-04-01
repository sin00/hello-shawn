package com.ericsson.li.binary2;

public class BPException extends RuntimeException {
	private static final long serialVersionUID = 7220718722496006940L;
	public static String MESSAGE_TEMPLATE = "errorCode: %s, description: %s";
	private BPErrorEnum error;

	public BPException(BPErrorEnum error) {
		super(error.getMessage());
		this.error = error;
	}

	public BPException(BPErrorEnum error, Throwable cause) {
		super(cause);
		this.error = error;
	}

	public BPErrorEnum getError() {
		return error;
	}

	public String getErrorCode() {
		return error.getCode();
	}
	
	public String getErrorMessage() {
		return error.getMessage();
	}

	@Override
	public String getMessage() {
		String errorMsg = error.getMessage();
		if (super.getMessage() != null) {
			errorMsg = errorMsg + "." + super.getMessage();
		}
		return String.format(MESSAGE_TEMPLATE, error.getCode(), errorMsg);
	}
}
