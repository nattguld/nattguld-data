package com.nattguld.data.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.nattguld.data.sql.util.RowKvp;

/**
 * 
 * @author randqm
 *
 */

public abstract class SqlQuery<T> {
	
	/**
	 * The database name.
	 */
	private final String databaseName;
	
	/**
	 * The table name.
	 */
	private final String tableName;
	
	/**
	 * The SQL operation.
	 */
	private final SqlOperation operation;

	
	/**
	 * Creates a new SQL query.
	 * 
	 * @param databaseName The database name.
	 * 
	 * @param tableName The table name.
	 * 
	 * @param operation The SQL operation.
	 */
	public SqlQuery(String databaseName, String tableName, SqlOperation operation) {
		this.databaseName = databaseName;
		this.tableName = tableName;
		this.operation = operation;
	}
	
	/**
	 * Executes the operation.
	 * 
	 * @return The result.
	 */
	public T executeOperation() {
		try (Connection conn = SqlManager.getConnection(getDatabaseName())) {
			return executeQuery(conn, tableName);
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			SqlManager.release();
		}
		return null;
	}
	
	/**
	 * Executes the SQL query.
	 * 
	 * @param conn The connection.
	 * 
	 * @param tableName The name of the table.
	 * 
	 * @param operation The operation type.
	 * 
	 * @return The results.
	 */
	protected abstract T executeQuery(Connection conn, String tableName);
	
	/**
	 * Binds a row key-value pair.
	 * 
	 * @param pstmt The prepared statement.
	 * 
	 * @param paramIndex The parameter index.
	 * 
	 * @param kvp The key-value pair.
	 * 
	 * @throws SQLException 
	 */
	protected void bind(PreparedStatement pstmt, int paramIndex, RowKvp<?> kvp) throws SQLException {
		if (kvp.getValue() instanceof Byte) {
			pstmt.setByte(paramIndex, (byte)kvp.getValue());
				
		} else if (kvp.getValue() instanceof Float) {
			pstmt.setFloat(paramIndex, (float)kvp.getValue());
				
		} else if (kvp.getValue() instanceof Short) {
			pstmt.setShort(paramIndex, (short)kvp.getValue());
					
		} else if (kvp.getValue() instanceof Double) {
			pstmt.setDouble(paramIndex, (double)kvp.getValue());
				
		} else if (kvp.getValue() instanceof Integer) {
			pstmt.setInt(paramIndex, (int)kvp.getValue());
				
		} else if (kvp.getValue() instanceof Long) {
			pstmt.setLong(paramIndex, (long)kvp.getValue());
				
		} else if (kvp.getValue() instanceof String) {
			pstmt.setString(paramIndex, (String)kvp.getValue());
				
		} else if (kvp.getValue() instanceof Boolean) {
			pstmt.setBoolean(paramIndex, (boolean)kvp.getValue());
					
		} else {
			System.err.println("Unable to bind " + kvp.getColumnName() + ":" + kvp.getValue() + ", " + kvp.getValue().getClass().getSimpleName() + " not supported");
		}
	}
	
	/**
	 * Retrieves the database name.
	 * 
	 * @return The database name.
	 */
	public String getDatabaseName() {
		return databaseName;
	}
	
	/**
	 * Retrieves the table name.
	 * 
	 * @return The table name.
	 */
	public String getTableName() {
		return tableName;
	}
	
	/**
	 * Retrieves the operation.
	 * 
	 * @return The operation.
	 */
	public SqlOperation getOperation() {
		return operation;
	}

}
