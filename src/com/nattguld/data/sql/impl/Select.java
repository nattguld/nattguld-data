package com.nattguld.data.sql.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.nattguld.data.sql.SqlOperation;
import com.nattguld.data.sql.SqlQuery;
import com.nattguld.data.sql.util.Where;

/**
 * 
 * @author randqm
 *
 */

public abstract class Select<V> extends SqlQuery<List<V>> {

	/**
	 * The columns.
	 */
	private final String[] columns;
	
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
	 * @param columns The columns.
	 * 
	 * @param wheres The where checks.
	 */
	public Select(String databaseName, String tableName, String[] columns, Where<?>[] wheres) {
		super(databaseName, tableName, SqlOperation.SELECT);
		
		this.columns = columns;
		this.wheres = wheres;
	}

	@Override
	public List<V> executeQuery(Connection conn, String tableName) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT");
		
		for (String colName : columns) {
			sb.append(" " + colName);
		}
		sb.append(" FROM " + tableName);

		if (Objects.nonNull(wheres) && wheres.length > 0) {
			sb.append(" WHERE ");
			
			for (int i = 0; i < wheres.length; i++) {
				if (i > 0) {
					sb.append(" AND ");
				}
				Where<?> where = wheres[i];
				sb.append(where.getColumnName() + " " + where.getCompareSign().getSign() + " ?");
			}
		}
		List<V> results = new ArrayList<>();
		
		try (Statement stmt = conn.createStatement();
				PreparedStatement pstmt  = conn.prepareStatement(sb.toString())) {
			
			if (Objects.nonNull(wheres) && wheres.length > 0) {
				for (int i = 0; i < wheres.length; i++) {
					bind(pstmt, i + 1, wheres[i]);
				}
			}
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					results.add(parseResultLine(rs));
				}	
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return results;
	}
	
	/**
	 * Parses a result line.
	 * 
	 * @param rs the result set.
	 * 
	 * @return The result value.
	 * 
	 * @throws SQLException
	 */
	protected abstract V parseResultLine(ResultSet rs) throws SQLException;

}
