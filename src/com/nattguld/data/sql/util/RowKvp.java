package com.nattguld.data.sql.util;

/**
 * 
 * @author randqm
 *
 */

public class RowKvp<V> {
	
	/**
	 * The row name.
	 */
	private final String rowName;
	
	/**
	 * The value.
	 */
	private final V value;
	
	
	/**
	 * Creates a new row key-value pair
	 * 
	 * @param rowName The row name.
	 * 
	 * @param value The value.
	 */
	public RowKvp(String rowName, V value) {
		this.rowName = rowName;
		this.value = value;
	}
	
	/**
	 * Retrieves the row name.
	 * 
	 * @return The row name.
	 */
	public String getColumnName() {
		return rowName;
	}
	
	/**
	 * Retrieves the value.
	 * 
	 * @return The value.
	 */
	public V getValue() {
		return value;
	}

}
