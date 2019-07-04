package com.nattguld.data;

import java.io.File;

import com.nattguld.data.cfg.Config;

/**
 * 
 * @author randqm
 *
 */

public abstract class Resource<I extends IResourceReader, O extends IResourceWriter> {
	
	/**
	 * The resource reader.
	 */
	private final I reader;
	
	
	/**
	 * Creates a new resource.
	 */
	public Resource() {
		this(null);
	}
	
	/**
	 * Creates a new resource.
	 * 
	 * @param reader The resource reader.
	 */
	public Resource(I reader) {
		this.reader = reader;
	}
	
	/**
	 * Retrieves the UUID.
	 * 
	 * @return The UUID.
	 */
	protected abstract String getUUID();
	
	/**
	 * Writes the resource data.
	 * 
	 * @param writer The writer.
	 */
	protected abstract void write(O writer);
	
	/**
	 * Saves the resource.
	 */
	public abstract void save();
	
	/**
	 * Retrieves the save file name.
	 * 
	 * @return The save file name.
	 */
	protected abstract String getSaveFileName();
	
	/**
	 * Retrieves the save directory name.
	 * 
	 * @return The save directory name.
	 */
	protected abstract String getSaveDirName();
	
	/**
	 * Retrieves the save directory path.
	 * 
	 * @return The save directory path.
	 */
	public String getSaveDirPath() {
		return Config.getBaseDirPath() + File.separator + getSaveDirName() + File.separator;
	}
	
	/**
	 * Retrieves the path to the save directory.
	 * 
	 * @return The save directory path.
	 */
	public String getSavePath() {
		return getSaveDirPath() + getSaveFileName();
	}
	
	/**
	 * Retrieves the resource reader.
	 * 
	 * @return The resource reader.
	 */
	protected I getReader() {
		return reader;
	}

}
