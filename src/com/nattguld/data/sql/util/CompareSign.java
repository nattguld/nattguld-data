package com.nattguld.data.sql.util;

/**
 * 
 * @author randqm
 *
 */

public enum CompareSign {
	
	EQUALS("="),
	BIGGER_THAN(">"),
	SMALLER_THAN("<"),
	LIKE("like");
	
	
	/**
	 * The sign.
	 */
	private final String sign;
	
	
	/**
	 * Creates a new compare sign.
	 * 
	 * @param sign The sign.
	 */
	private CompareSign(String sign) {
		this.sign = sign;
	}
	
	/**
	 * Retrieves the sign.
	 * 
	 * @return The sign.
	 */
	public String getSign() {
		return sign;
	}
	
	@Override
	public String toString() {
		return getSign();
	}

}
