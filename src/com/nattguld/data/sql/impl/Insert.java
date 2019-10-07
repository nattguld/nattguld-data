package com.nattguld.data.sql.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.nattguld.data.sql.SqlOperation;
import com.nattguld.data.sql.SqlQuery;
import com.nattguld.data.sql.util.RowKvp;

/**
 * 
 * @author randqm
 *
 */

public class Insert extends SqlQuery<Boolean> {

	/**
	 * The row key-value pairs.
	 */
	private final RowKvp<?>[] rowKvps;
	
	
	/**
	 * Creates a new SQL query.
	 * 
	 * @param databaseName The database name.
	 * 
	 * @param tableName The table name.
	 * 
	 * @param @param rowKvps The row key-value pairs.
	 */
	public Insert(String databaseName, String tableName, RowKvp<?>[] rowKvps) {
		super(databaseName, tableName, SqlOperation.INSERT);
		
		this.rowKvps = rowKvps;
	}

	@Override
	public Boolean executeQuery(Connection conn, String tableName) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO " + tableName + "(");
		
		for (int i = 0; i < rowKvps.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(rowKvps[i].getColumnName());
		}
		sb.append(") VALUES(");
		
		for (int i = 0; i < rowKvps.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append("?");
		}
		sb.append(")");
		
		try (Statement stmt = conn.createStatement();
				PreparedStatement pstmt  = conn.prepareStatement(sb.toString())) {
			
			for (int i = 0; i < rowKvps.length; i++) {
				bind(pstmt, i + 1, rowKvps[i]);
			}
			int result = pstmt.executeUpdate();
			return result > 0;
				
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
