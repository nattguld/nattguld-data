package com.nattguld.data.sql.util;

/**
 * 
 * @author randqm
 *
 */

public class SqlTable {
	
	/**
	 * The name of the table.
	 */
	private final String name;
	
	/**
	 * The table columns.
	 */
	private final String[] columns;
	
	
	/**
	 * Creates a new table.
	 * 
	 * @param name The name of the table.
	 * 
	 * @param columns The columns.
	 */
	public SqlTable(String name, String[] columns) {
		this.name = name;
		this.columns = columns;
	}
	
	/**
	 * Retrieves the name.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Retrieves the columns.
	 * 
	 * @return The columns.
	 */
	public String[] getColumns() {
		return columns;
	}

}
