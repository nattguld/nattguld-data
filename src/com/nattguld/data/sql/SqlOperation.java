package com.nattguld.data.sql;

/**
 * 
 * @author randqm
 *
 */

public enum SqlOperation {
	
	DROP_TABLE("DROP_TABLE"),
	CREATE_TABLE("CREATE TABLE"),
	INSERT("INSERT"),
	UPDATE("UPDATE"),
	DELETE("DELETE"),
	SELECT("SELECT");
	
	
	/**
	 * The operation name.
	 */
	private final String name;
	
	
	/**
	 * Creates a new SQL operation.
	 * 
	 * @param name The operation name.
	 */
	private SqlOperation(String name) {
		this.name = name;
	}
	
	/**
	 * Retrieves the operation name.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return getName();
	}

}
