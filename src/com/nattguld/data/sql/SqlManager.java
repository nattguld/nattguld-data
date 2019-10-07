package com.nattguld.data.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 
 * @author randqm
 *
 */

public class SqlManager {
	
	/**
	 * The thread lock object.
	 */
	private static final Object LOCK = new Object();
	
	/**
	 * Whether the database is ready for new connections or not.
	 */
	private static final AtomicBoolean DB_READY = new AtomicBoolean(true);
	
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
	 * The pooling data source.
	 */
    private static HikariDataSource dataSource;

	
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
		synchronized (LOCK){
		    while (!DB_READY.get()){
		    	try {
					LOCK.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    }
		}
		DB_READY.set(false);
		
		if (Objects.isNull(dataSource)) {
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl("jdbc:mysql://" + HOST + ":" + PORT + "/" + databaseName + "?useSSL=false");
	        config.setUsername(DBConfig.getConfig().getUsername());
	        config.setPassword(DBConfig.getConfig().getPassword());
	        config.addDataSourceProperty("cachePrepStmts", "true");
	        config.addDataSourceProperty("prepStmtCacheSize", "250");
	        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
	        config.setConnectionTimeout(5000);
	        config.setMaximumPoolSize(50);

	        dataSource = new HikariDataSource(config);
		}
		Connection conn = dataSource.getConnection();
		
		if (Objects.isNull(conn)) {
			System.err.println("Failed to connect to database " + databaseName + ", NULLED connection");
			connected = false;
			release();
			return null;
		}
		return conn;
	}
	
	/**
	 * Releases the database lock.
	 */
	public static void release() {
		synchronized(LOCK){
			DB_READY.set(true);
			LOCK.notifyAll();
		}
	}
	
	/**
	 * Disposes the SQL manager.
	 * 
	 * @throws SQLException 
	 */
	public static void dispose() throws SQLException {
		LOCK.notifyAll();
		
		if (Objects.nonNull(dataSource)) {
			dataSource.close();
		}
		DB_READY.set(true);
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