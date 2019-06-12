package com.nattguld.data.cfg;

import com.nattguld.data.json.JsonReader;
import com.nattguld.data.json.JsonResource;

/**
 * 
 * @author randqm
 *
 */

public abstract class Config extends JsonResource {
	
	/**
	 * The base path where configurations should be saved.
	 */
	private static transient String basePath = "./";

	
	/**
	 * Reads the config data.
	 * 
	 * @param reader The json reader.
	 */
	protected abstract void read(JsonReader reader);
	
	/**
	 * Retrieves the save file name.
	 * 
	 * @return The name.
	 */
	protected abstract String getSaveFileName();

	
	@Override
	public String getSavePath() {
		return basePath + getSaveFileName();
	}
	
	/**
	 * Modifies the base path.
	 * 
	 * @param dirPath The path to the directory.
	 */
	public static void setBasePath(String dirPath) {
		basePath = dirPath;
	}
	
	/**
	 * Retrieves the base path.
	 * 
	 * @return The base path.
	 */
	public static String getBasePath() {
		return basePath;
	}

}
