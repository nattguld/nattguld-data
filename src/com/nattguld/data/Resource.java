package com.nattguld.data;

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
	 * Retrieves the save path.
	 * 
	 * @return The save path.
	 */
	public abstract String getSavePath();
	
	/**
	 * Retrieves the resource reader.
	 * 
	 * @return The resource reader.
	 */
	protected I getReader() {
		return reader;
	}

}
