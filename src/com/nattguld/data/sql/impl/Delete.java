package com.nattguld.data.sql.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.nattguld.data.sql.SqlOperation;
import com.nattguld.data.sql.SqlQuery;
import com.nattguld.data.sql.util.Where;

/**
 * 
 * @author randqm
 *
 */

public class Delete extends SqlQuery<Void> {
	
	/**
	 * The where checks.
	 */
	private final Where<?>[] wheres;
	
	
	/**
	 * Creates a new SQL query.
	 * 
	 * @param databaseName The database name.
	 * 
	 * @param tableName The table name.
	 * 
	 * @param wheres The where checks.
	 */
	public Delete(String databaseName, String tableName, Where<?>[] wheres) {
		super(databaseName, tableName, SqlOperation.DELETE);
		
		this.wheres = wheres;
	}

	@Override
	public Void executeQuery(Connection conn, String tableName) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM " + tableName + " WHERE ");
		
		for (int i = 0; i < wheres.length; i++) {
			if (i != 0) {
				sb.append(" AND ");
			}
			sb.append(wheres[i].getColumnName() + " = ?");
		}
		try (Statement stmt = conn.createStatement();
				PreparedStatement pstmt  = conn.prepareStatement(sb.toString())) {

			for (int i = 0; i < wheres.length; i++) {
				bind(pstmt, i + 1, wheres[i]);
			}
			pstmt.executeUpdate();
				
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
