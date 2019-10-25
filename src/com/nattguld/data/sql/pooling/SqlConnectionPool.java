package com.nattguld.data.sql.pooling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * @author randqm
 *
 */

public class SqlConnectionPool {
	
	/**
	 * The database url.
	 */
	private final String url;
	
	/**
	 * The database user username.
	 */
	private final String username;
	
	/**
	 * The database user password.
	 */
	private final String password;
	
	/**
	 * The maximum pool size.
	 */
	private final int maxPoolSize;
	
	/**
	 * The pool connections.
	 */
	private final List<Connection> connections;
	
	/**
	 * The used connections.
	 */
	private final List<Connection> usedConnections;
	
	
	/**
	 * Creates a new default connection pool.
	 * 
	 * @param url The database url.
	 * 
	 * @param username The database user username.
	 * 
	 * @param password The database user password.
	 * 
	 * @param maxPoolSize The maximum pool size.
	 */
	public SqlConnectionPool(String url, String username, String password, int maxPoolSize) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.maxPoolSize = maxPoolSize;
		this.connections = new CopyOnWriteArrayList<>();
		this.usedConnections = new CopyOnWriteArrayList<>();
	}
	
	/**
	 * Retrieves a connection to use.
	 * 
	 * @return The connection.
	 * 
	 * @throws SQLException 
	 * 
	 * @throws InterruptedException 
	 */
	public Connection getConnection() throws SQLException, InterruptedException {
		Connection connection = callIdleConnection();
		
		if (Objects.isNull(connection)) {
			if (getPoolSize() >= maxPoolSize) {
				System.out.println("All SQL connection currently in use, trying again in 10 seconds");
				Thread.sleep(10 * 1000);
				return getConnection();
			}
			connection = DriverManager.getConnection(url, username, password);
			
			if (Objects.isNull(connection)) {
				System.err.println("Failed to create a new SQL connection");
				return null;
			}
			connections.add(connection);
		}
		usedConnections.add(connection);
		return connection;
	}
	
	/**
	 * Calls an idle connection to be used.
	 * 
	 * @return The connection.
	 * 
	 * @throws SQLException 
	 */
	protected Connection callIdleConnection() throws SQLException {
		if (connections.isEmpty()) {
			return null;
		}
		Connection conn = connections.remove(connections.size() - 1);
		
		if (conn.isClosed()) {
			return callIdleConnection();
		}
		return conn;
	}
	
	/**
	 * Releases a connection.
	 * 
	 * @param conn The connection to release.
	 * 
	 * @return The pool.
	 */
	public SqlConnectionPool releaseConnection(Connection connection) {
		usedConnections.remove(connection);
        connections.add(connection);
		return this;
	}
	
	/**
	 * Retrieves the current pool size.
	 * 
	 * @return The current pool size.
	 */
	public int getPoolSize() {
		return connections.size() + usedConnections.size();
	}
	
	/**
	 * Shuts down the pool.
	 * 
	 * @return The pool.
	 * 
	 * @throws SQLException 
	 */
	public SqlConnectionPool shutdown() throws SQLException {
		for (Connection conn : usedConnections) {
			conn.close();
		}
		for (Connection conn : connections) {
			conn.close();
		}
		connections.clear();
		usedConnections.clear();
		return this;
	}

}
