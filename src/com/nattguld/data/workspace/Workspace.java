package com.nattguld.data.workspace;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.nattguld.data.cfg.Config;

/**
 * 
 * @author randqm
 *
 */

public class Workspace implements AutoCloseable {
	
	/**
	 * The work directory.
	 */
	private final File dir;
	
	/**
	 * Creates a new workspace.
	 * 
	 * @param dir The directory.
	 */
	public Workspace() {
		this("");
	}
	
	/**
	 * Creates a new workspace.
	 * 
	 * @param dir The directory.
	 */
	public Workspace(String dir) {
		this.dir = new File(Config.getBaseDirPath() + File.separator + dir + File.separator + hashCode());
		
		getWorkDir().mkdirs();
	}
	
	/**
	 * Adds a file with a given path to the workspace.
	 * 
	 * @param path The path.
	 * 
	 * @return The file in the workspace.
	 */
	public File addToWorkspace(String path) {
		return addToWorkspace(new File(path));
	}
	
	/**
	 * Adds a file to the workspace.
	 * 
	 * @param f The file to add.
	 * 
	 * @return The file in the workspace.
	 */
	public File addToWorkspace(File f) {
		File copy = new File(getWorkPath() + File.separator + f.getName());
		
		try {
			Files.copy(f.toPath(), copy.toPath(), StandardCopyOption.REPLACE_EXISTING);
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return copy;
	}
	
	/**
	 * Retrieves the work directory.
	 * 
	 * @return The work directory.
	 */
	public File getWorkDir() {
		return dir;
	}
	
	/**
	 * Retrieves the work path.
	 * 
	 * @return The work path.
	 */
	public String getWorkPath() {
		return getWorkDir().getAbsolutePath();
	}
	
	@Override
	public void close() throws Exception {
		for (File f : getWorkDir().listFiles()) {
			f.delete();
		}
		getWorkDir().delete();
	}
	
	@Override
	public String toString() {
		return "Workspace (" + getWorkPath() + ")";
	}

}
