package com.nattguld.data.sql.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import com.nattguld.data.sql.SqlOperation;
import com.nattguld.data.sql.SqlQuery;
import com.nattguld.data.sql.util.RowKvp;
import com.nattguld.data.sql.util.Where;

/**
 * 
 * @author randqm
 *
 */

public class Update extends SqlQuery<Void> {

	/**
	 * The row key-value pairs.
	 */
	private final RowKvp<?>[] rowKvps;
	
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
	 * @param rowKvps The row key-value pairs.
	 * 
	 * @param wheres The where checks.
	 */
	public Update(String databaseName, String tableName, RowKvp<?>[] rowKvps, Where<?>[] wheres) {
		super(databaseName, tableName, SqlOperation.UPDATE);
		
		this.rowKvps = rowKvps;
		this.wheres = wheres;
	}

	@Override
	public Void executeQuery(Connection conn, String tableName) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE " + tableName + " SET ");
		
		for (int i = 0; i < rowKvps.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(rowKvps[i].getColumnName() + " = ?");
		}
		if (Objects.nonNull(wheres) && wheres.length > 0) {
			sb.append(" WHERE ");
			
			for (int i = 0; i < wheres.length; i++) {
				if (i != 0) {
					sb.append(" AND ");
				}
				sb.append(wheres[i].getColumnName() + " = ?");
			}
		}
		try (Statement stmt = conn.createStatement();
				PreparedStatement pstmt  = conn.prepareStatement(sb.toString())) {
			
			int paramIndex = 1;
			
			for (int i = 0; i < rowKvps.length; i++) {
				bind(pstmt, paramIndex++, rowKvps[i]);
			}
			if (Objects.nonNull(wheres) && wheres.length > 0) {
				for (int i = 0; i < wheres.length; i++) {
					bind(pstmt, paramIndex++, wheres[i]);
				}
			}
			pstmt.executeUpdate();
				
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
