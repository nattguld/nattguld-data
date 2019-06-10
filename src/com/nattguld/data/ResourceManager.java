package com.nattguld.data;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

/**
 * 
 * @author randqm
 *
 */

public abstract class ResourceManager<R extends Resource<I, O>, I extends IResourceReader, O extends IResourceWriter> {

	/**
	 * Holds the loaded resources.
	 */
	private final List<R> resources = new CopyOnWriteArrayList<>();

	
	/**
	 * Adds a new resource.
	 * 
	 * @param resource The resource to add.
	 */
	public void add(R resource) {
		add(resource, null);
	}
	
	/**
	 * Adds a new resource.
	 * 
	 * @param resource The resource to add.
	 * 
	 * @param predicate The predicate.
	 */
	public void add(R resource, Predicate<R> predicate) {
		if (addRaw(resource, predicate)) {
			resource.save();
		}
	}
	
	/**
	 * Adds a new resource without saving it.
	 * 
	 * @param resource The resource to add.
	 * 
	 * @return Whether the resource was added or not.
	 */
	public boolean addRaw(R resource) {
		return addRaw(resource, null);
	}
	
	/**
	 * Adds a new resource without saving it.
	 * 
	 * @param resource The resource to add.
	 * 
	 * @param predicate The predicate.
	 * 
	 * @return Whether the resource was added or not.
	 */
	public boolean addRaw(R resource, Predicate<R> predicate) {
		if (Objects.nonNull(resource) && !resources.contains(resource)) {
			if (Objects.isNull(predicate) || predicate.test(resource)) {
				resources.add(resource);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Removes a resource.
	 * 
	 * @param resource The resource to remove.
	 */
	public void remove(R resource) {
		resources.remove(resource);
		
		deleteSaveFile(resource);
	}
	
	/**
	 * Deletes the save file of a resource.
	 * 
	 * @param resource The resource.
	 */
	public void deleteSaveFile(R resource) {
		if (Objects.isNull(resource)) {
			return;
		}
		new File(resource.getSavePath()).delete();
	}
	
	/**
	 * Loads the resources.
	 * 
	 * @return The resource manager.
	 */
	public ResourceManager<R, I, O> load() {
		File dir = new File(getStorageDirPath());
		
		if (!dir.exists()) {
			dir.mkdirs();
			return this;
		}
		for (File f : dir.listFiles()) {
			try {
				I reader = loadResource(f);
			
				if (Objects.isNull(reader)) {
					reader.close();
					continue;
				}
				R resource = instantiateResource(reader);
				reader.close();
			
				if (Objects.isNull(resource)) {
					continue;
				}
				resources.add(resource);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return this;
	}
	
	/**
	 * Retrieves the resource reader for a given file.
	 * 
	 * @param f The file to get the resource reader for.
	 * 
	 * @return The resource reader.
	 */
	protected abstract I loadResource(File f);
	
	/**
	 * Loads the resource object with it's resource reader.
	 * 
	 * @param reader The resource reader.
	 * 
	 * @return The resource object.
	 */
	protected abstract R instantiateResource(I reader);
	
	/**
	 * Clears the resource container.
	 */
	public void clear() {
		getResources().clear();
	}
	
	/**
	 * Deletes all resources.
	 */
	public void deleteAllResources() {
		for (R resource : getResources()) {
			remove(resource);
		}
	}
	
	/**
	 * Retrieves the storage directory path.
	 * 
	 * @return The storage directory path.
	 */
	protected abstract String getStorageDirPath();
	
	/**
	 * Retrieves whether the resource manager is empty or not.
	 * 
	 * @return The result.
	 */
	public boolean isEmpty() {
		return getResources().isEmpty();
	}
	
	/**
	 * Retrieves the resources.
	 * 
	 * @return The resources.
	 */
	public List<R> getResources() {
		return resources;
	}

}
