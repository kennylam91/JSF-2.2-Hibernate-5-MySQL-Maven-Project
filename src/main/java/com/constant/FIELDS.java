package com.constant;

public enum FIELDS {
	PHP("PHP"),
	JAVA("JAVA"),
	PYTHON("PYTHON");
	
	private final String field;
	
	FIELDS(String s) {
		field = s;
	}

	public String getField() {
		return field;
	}
	
}
