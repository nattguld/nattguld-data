package com.nattguld.data.sql.util;

/**
 * 
 * @author randqm
 *
 */

public class Where<V> extends RowKvp<V> {
	
	/**
	 * The compare sign.
	 */
	private final CompareSign compareSign;
	
	
	/**
	 * Creates a new where check.
	 * 
	 * @param rowName The row name.
	 * 
	 * @param compareSign The compare sign.
	 * 
	 * @param value The value.
	 */
	public Where(String rowName, CompareSign compareSign, V value) {
		super(rowName, value);
		
		this.compareSign = compareSign;
	}
	
	/**
	 * Retrieves the compare sign.
	 * 
	 * @return The compare sign.
	 */
	public CompareSign getCompareSign() {
		return compareSign;
	}

}
