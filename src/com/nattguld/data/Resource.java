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
	 * Retrieves the save directory path.
	 * 
	 * @return The save directory path.
	 */
	protected abstract String getSaveDirPath();
	
	/**
	 * Retrieves the path to the save directory.
	 * 
	 * @return The save directory path.
	 */
	public String getSavePath() {
		return Config.getBaseDirPath() + File.separator + getSaveDirPath() + File.separator + getSaveFileName();
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
