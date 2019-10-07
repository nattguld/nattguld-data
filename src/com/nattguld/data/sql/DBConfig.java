package com.nattguld.data.sql;

import com.nattguld.data.cfg.Config;
import com.nattguld.data.cfg.ConfigManager;
import com.nattguld.data.json.JsonReader;
import com.nattguld.data.json.JsonWriter;

/**
 * 
 * @author randqm
 *
 */

public class DBConfig extends Config {
	
	/**
	 * The username.
	 */
	private String username = "";
	
	/**
	 * The password.
	 */
	private String password = "";
	
	/**
	 * The prefix.
	 */
	private String prefix = "";
	

	@Override
	protected void read(JsonReader reader) {
		this.username = reader.getAsString("username", "");
		this.password = reader.getAsString("password", "");
		this.prefix = reader.getAsString("prefix", "");
	}
	
	@Override
	protected void write(JsonWriter writer) {
		writer.write("username", username);
		writer.write("password", password);
		writer.write("prefix", prefix);
	}
	
	/**
	 * Modifies the prefix.
	 * 
	 * @param prefix The new prefix.
	 * 
	 * @return The config.
	 */
	public DBConfig setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}
	
	/**
	 * Retrieves the prefix.
	 * 
	 * @return The prefix.
	 */
	public String getPrefix() {
		return prefix;
	}
	
	/**
	 * Modifies the username.
	 * 
	 * @param username The new username.
	 * 
	 * @return The config.
	 */
	public DBConfig setUsername(String username) {
		this.username = username;
		return this;
	}
	
	/**
	 * Retrieves the username.
	 * 
	 * @return The username.
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Modifies the password.
	 * 
	 * @param password The new password.
	 * 
	 * @return The config.
	 */
	public DBConfig setPassword(String password) {
		this.password = password;
		return this;
	}
	
	/**
	 * Retrieves the password.
	 * 
	 * @return The password.
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Retrieves whether the config has been set up or not.
	 * 
	 * @return The result.
	 */
	public boolean isSetup() {
		return !getUsername().isEmpty() && !getPassword().isEmpty();
	}

	@Override
	protected String getSaveFileName() {
		return ".db_config";
	}
	
	/**
	 * Retrieves the config.
	 * 
	 * @return The config.
	 */
	public static DBConfig getConfig() {
		return (DBConfig)ConfigManager.getConfig(new DBConfig());
	}

}
