package com.nattguld.data.cache;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import com.nattguld.data.cfg.Config;

/**
 * 
 * @author randqm
 *
 */

public class FileCache {
	
	/**
	 * The save directory for the cache files.
	 */
	private final File saveDir;
	
	
	/**
	 * Creates a new file cache.
	 * 
	 * @param saveDirPath The cache files save directory path.
	 */
	public FileCache(String saveDirPath) {
		this.saveDir = new File(getCacheBaseDirPath() + File.separator + saveDirPath);
		
		getSaveDir().mkdirs();
	}
	
	/**
	 * Removes the cache version of a given file.
	 * 
	 * @param f The file.
	 */
	public void remove(File f) {
		if (Objects.isNull(f) || !f.exists()) {
			return;
		}
		for (File o : getFiles()) {
			if (o.getName().equals(f.getName())) {
				o.delete();
			}
		}
	}
	
	/**
	 * Adds a new file to the cache.
	 * 
	 * @param f The file to add.
	 * 
	 * @return The cached file.
	 */
	private File add(File f) {
		File cached = new File(getSaveDir().getAbsolutePath() + File.separator + f.getName());
		
		try {
			Files.copy(f.toPath(), cached.toPath(), StandardCopyOption.REPLACE_EXISTING);
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return cached;
	}
	
	/**
	 * Retrieves the cached version of a file.
	 * 
	 * @param fileName The file name.
	 * 
	 * @return The cached file.
	 */
	public File getCachedByFileName(String fileName) {
		if (Objects.isNull(fileName) || fileName.isEmpty()) {
			return null;
		}
		File cached = new File(getSaveDir().getAbsolutePath() + File.separator + fileName);
		
		if (!cached.exists()) {
			return null;
		}
		return cached;
	}
	
	/**
	 * Retrieves the cached version of a given file and caches if it wasn't yet.
	 * 
	 * @param f The file.
	 * 
	 * @return The cached file.
	 */
	public File getCached(File f) {
		if (Objects.isNull(f)) {
			return null;
		}
		for (File o : getFiles()) {
			if (o.getName().equals(f.getName())) {
				return o;
			}
		}
		return add(f);
	}
	
	/**
	 * Clears the cache.
	 */
	public void clear() {
		for (File f : getFiles()) {
			f.delete();
		}
	}
	
	/**
	 * Retrieves the files.
	 * 
	 * @return The files.
	 */
	public File[] getFiles() {
		return getSaveDir().listFiles();
	}
	
	/**
	 * Retrieves the save directory.
	 * 
	 * @return The save directory.
	 */
	public File getSaveDir() {
		return saveDir;
	}
	
	/**
	 * Retrieves the cache base dir path.
	 * 
	 * @return The cache base dir path.
	 */
	protected String getCacheBaseDirPath() {
		return Config.getBaseDirPath() + File.separator + "cache";
	}

}
