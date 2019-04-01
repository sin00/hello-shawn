package com.ericsson.li.binary2;

public enum BPErrorEnum {
	INVALID_EXPRESSION("BP001", "invalid expression"),
	UNSUPPORTED_TYPE("BP002", "unsupported type"),
	UNKNOWN_ERROR("BP999", "unknown error.");

	private String code;
	private String message;

	private BPErrorEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}


}
