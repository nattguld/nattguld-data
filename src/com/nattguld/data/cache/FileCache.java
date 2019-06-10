package com.nattguld.data.cache;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

/**
 * 
 * @author randqm
 *
 */

public class FileCache {
	
	/**
	 * The cache directory.
	 */
	private final File cacheDir;
	
	
	/**
	 * Creates a new file cache.
	 * 
	 * @param cacheDirPath The cache directory path.
	 */
	public FileCache(String cacheDirPath) {
		this(new File(cacheDirPath));
	}
	
	/**
	 * Creates a new file cache.
	 * 
	 * @param cacheDir The cache directory.
	 */
	public FileCache(File cacheDir) {
		this.cacheDir = cacheDir;
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
		init();
		
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
		remove(f);

		File cached = new File(getCacheDir().getAbsolutePath() + "/" + f.getName());
		
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
	public File getCached(String fileName) {
		return getCached(new File(getCacheDir().getAbsolutePath() + "/" + fileName));
	}
	
	/**
	 * Retrieves the cached version of a given file and caches if it wasn't yet.
	 * 
	 * @param f The file.
	 * 
	 * @return The cached file.
	 */
	public File getCached(File f) {
		if (Objects.isNull(f) || !f.exists()) {
			return null;
		}
		init();
		
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
		if (getCacheDir().exists()) {
			for (File f : getFiles()) {
				f.delete();
			}
		}
	}
	
	/**
	 * Retrieves the files.
	 * 
	 * @return The files.
	 */
	public File[] getFiles() {
		return getCacheDir().listFiles();
	}
	
	/**
	 * Initializes the cache.
	 */
	private void init() {
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
	}
	
	/**
	 * Retrieves the cache directory.
	 * 
	 * @return The directory.
	 */
	public File getCacheDir() {
		init();
		
		return cacheDir;
	}

}
