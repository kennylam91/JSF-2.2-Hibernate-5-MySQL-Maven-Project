package com.constant;

public enum AUTHORITIES {
	ADMIN_ROLE("ADMIN_ROLE"),
	STUDENT_ROLE("STUDENT_ROLE");
	
	private final String status;
	
	private AUTHORITIES(String s) {
		status = s;
	}

	public String getStatus() {
		return status;
	}

}
