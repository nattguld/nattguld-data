package com.nattguld.data.sql.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.nattguld.data.sql.SqlOperation;
import com.nattguld.data.sql.SqlQuery;

/**
 * 
 * @author randqm
 *
 */

public class DropTable extends SqlQuery<Void> {
	
	
	/**
	 * Creates a new SQL query.
	 * 
	 * @param databaseName The database name.
	 * 
	 * @param tableName The table name.
	 */
	public DropTable(String databaseName, String tableName) {
		super(databaseName, tableName, SqlOperation.DROP_TABLE);
	}

	@Override
	public Void executeQuery(Connection conn, String tableName) {
		try (Statement stmt = conn.createStatement()) {
			stmt.execute("DROP TABLE IF EXISTS " + tableName);
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
