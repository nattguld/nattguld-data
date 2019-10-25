package com.nattguld.data.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

import com.nattguld.data.sql.pooling.SqlConnectionPool;

/**
 * 
 * @author randqm
 *
 */

public class SqlManager {
	
	/**
	 * The host.
	 */
	private static final String HOST = "185.63.255.68";
	
	/**
	 * The port.
	 */
	private static final int PORT = 3306;
	
	/**
	 * Retrieves whether we should be connected or not.
	 */
	private static boolean connected;
	
	/**
	 * The connection pool.
	 */
	private static SqlConnectionPool connectionPool;

	
	/**
	 * Retrieves whether the database is running or not.
	 * 
	 * @return The result.
	 */
	public static boolean testConnection() {
		if (!DBConfig.getConfig().isSetup()) {
			connected = false;
			return false;
		}
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/?useSSL=false"
				, DBConfig.getConfig().getUsername(), DBConfig.getConfig().getPassword())) {
			connected = true;
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			connected = false;
			return false;
		}
	}

	/**
	 * Retrieves a database connection.
	 * 
	 * @param databaseName The database name.
	 * 
	 * @return The connection. 
	 * 
	 * @throws SQLException
	 */
	public static Connection getConnection(String databaseName) throws SQLException {
		if (!DBConfig.getConfig().isSetup()) {
			connected = false;
			return null;
		}
		if (Objects.isNull(connectionPool)) {
			connectionPool = new SqlConnectionPool("jdbc:mysql://" + HOST + ":" + PORT + "/" + databaseName + "?useSSL=false"
					, DBConfig.getConfig().getUsername(), DBConfig.getConfig().getPassword(), 25);
		}
		try {
			Connection conn = connectionPool.getConnection();
			
			if (Objects.isNull(conn)) {
				System.err.println("Failed to connect to database " + databaseName + ", NULLED connection");
				connected = false;
				return null;
			}
			return conn;
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Restarts the sql manager.
	 */
	public static void restart() {
		if (Objects.nonNull(connectionPool)) {
			try {
				connectionPool.shutdown();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Releases a connection.
	 * 
	 * @param conn The connection.
	 */
	public static void releaseConnection(Connection conn) {
		connectionPool.releaseConnection(conn);
	}
	
	/**
	 * Retrieves the current connection count.
	 * 
	 * @return The connection count.
	 */
	public static int getConnectionCount() {
		return Objects.isNull(connectionPool) ? 0 : connectionPool.getPoolSize();
	}
	
	/**
	 * Retrieves whether we should be connected or not.
	 * 
	 * @return The result.
	 */
	public static boolean isConnected() {
		return connected;
	}

}