package com.nattguld.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

import com.nattguld.data.cfg.Config;

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
	 * Retrieves a resource by it's UUID.
	 * 
	 * @param uuid The UUID.
	 * 
	 * @return The resource.
	 */
	public R getByUUID(String uuid) {
		return getResources().stream()
				.filter(r -> r.getUUID().equals(uuid))
				.findFirst().orElse(null);
	}
	
	/**
	 * Loads the resources.
	 * 
	 * @return The resource manager.
	 */
	public ResourceManager<R, I, O> load() {
		File dir = new File(Config.getBaseDirPath() + File.separator + getStorageDirName() + File.separator);
		
		if (!dir.exists()) {
			dir.mkdirs();
			return this;
		}
		for (File f : dir.listFiles()) {
			try {
				I reader = loadResource(f);
			
				if (Objects.isNull(reader)) {
					continue;
				}
				R resource = instantiateResource(reader);
				reader.close();
			
				if (Objects.isNull(resource)) {
					continue;
				}
				addRaw(resource);
				
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
	 * Retrieves the storage directory name.
	 * 
	 * @return The storage directory name.
	 */
	protected abstract String getStorageDirName();
	
	/**
	 * Retrieves whether the resource manager is empty or not.
	 * 
	 * @return The result.
	 */
	public boolean isEmpty() {
		return getResources().isEmpty();
	}
	
	/**
	 * Casts a list of UUID's to the resource instance.
	 * 
	 * @param uuids The UUID's.
	 * 
	 * @return The resources.
	 */
	public List<R> cast(List<String> uuids) {
		List<R> resources = new ArrayList<>();
		
		for (R resource : getResources()) {
			String uuid = resource.getUUID();
			
			if (Objects.isNull(uuid) || uuid.isEmpty()) {
				continue;
			}
			resources.add(resource);
		}
		return resources;
	}
	
	/**
	 * Retrieves the UUID's of a list of given resources.
	 * 
	 * @param resources The resources.
	 * 
	 * @return The UUID's.
	 */
	public List<String> getUUIDsFromResources(List<R> resources) {
		List<String> uuids = new ArrayList<>();
		
		for (R resource : resources) {
			String uuid = resource.getUUID();
			
			if (Objects.isNull(uuid) || uuid.isEmpty()) {
				continue;
			}
			uuids.add(uuid);
		}
		return uuids;
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
