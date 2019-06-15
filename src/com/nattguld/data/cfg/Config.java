package com.nattguld.data.cfg;

import java.io.File;

import com.nattguld.data.json.JsonReader;
import com.nattguld.data.json.JsonResource;

/**
 * 
 * @author randqm
 *
 */

public abstract class Config extends JsonResource {
	
	/**
	 * The base directory path where data should be saved.
	 */
	private static String baseDirPath = "./";

	
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
	protected String getSaveDirName() {
		return "";
	}
	
	/**
	 * Modifies the base path.
	 * 
	 * @param dirPath The path to the directory.
	 */
	public static void setBasePath(String dirPath) {
		baseDirPath = dirPath;
		
		new File(baseDirPath).mkdirs();
	}
	
	/**
	 * Retrieves the base directory path.
	 * 
	 * @return The base directory path.
	 */
	public static String getBaseDirPath() {
		return baseDirPath;
	}

}
