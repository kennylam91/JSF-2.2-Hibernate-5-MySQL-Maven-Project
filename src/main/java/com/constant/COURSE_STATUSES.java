package com.constant;

public enum COURSE_STATUSES {
	REGISTERING("REGISTERING"),
	STUDYING("STUDYING"),
	COMPLETED("COMPLETED");
	
	private final String status;
	
	private COURSE_STATUSES(String s) {
		status = s;
	}

	public String getStatus() {
		return status;
	}
	
	
}
