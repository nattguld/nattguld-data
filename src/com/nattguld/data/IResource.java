package com.nattguld.data;

/**
 * 
 * @author randqm
 *
 */

public interface IResource<I extends IResourceReader, O extends IResourceWriter> {
	
	
	/**
	 * Reads a resource data.
	 * 
	 * @param reader The reader.
	 */
	public void read(I reader);
	
	/**
	 * Writes the resource data.
	 * 
	 * @param writer The writer.
	 */
	public void write(O writer);
	
	/**
	 * Saves the resource.
	 */
	public void save();
	
	/**
	 * Retrieves the save path.
	 * 
	 * @return The save path.
	 */
	public String getSavePath();

}
