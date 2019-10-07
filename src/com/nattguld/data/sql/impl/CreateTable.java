package com.nattguld.data.sql.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.nattguld.data.sql.SqlOperation;
import com.nattguld.data.sql.SqlQuery;
import com.nattguld.data.sql.util.SqlTable;

/**
 * 
 * @author randqm
 *
 */

public class CreateTable extends SqlQuery<Boolean> {

	/**
	 * The table.
	 */
	private final SqlTable table;
	
	
	/**
	 * Creates a new SQL query.
	 * 
	 * @param databaseName The database name.
	 * 
	 * @param tableName The table name.
	 * 
	 * @param table The table.
	 */
	public CreateTable(String databaseName, SqlTable table) {
		super(databaseName, table.getName(), SqlOperation.CREATE_TABLE);
		
		this.table = table;
	}

	@Override
	public Boolean executeQuery(Connection conn, String tableName) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE IF NOT EXISTS " + tableName + " (");
		
		sb.append(System.lineSeparator());
		sb.append("    id INTEGER PRIMARY KEY AUTOINCREMENT");
		
		for (String column : table.getColumns()) {
			sb.append("," + System.lineSeparator());
			sb.append("    " + column);
		}
		sb.append(System.lineSeparator() + ");");
		
		try (Statement stmt = conn.createStatement()) {
			stmt.execute(sb.toString());
			return true;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
