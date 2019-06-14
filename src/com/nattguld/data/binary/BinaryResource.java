package com.nattguld.data.binary;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.nattguld.data.Resource;

/**
 * 
 * @author randqm
 *
 */

public abstract class BinaryResource extends Resource<BinaryReader, BinaryWriter> {

	
	/**
	 * Creates a new binary resource.
	 */
	public BinaryResource() {
		super(null);
	}
	
	/**
	 * Creates a new binary resource.
	 * 
	 * @param reader The resource reader.
	 */
	public BinaryResource(BinaryReader reader) {
		super(reader);
	}
	
	@Override
	public void save() {
		File saveDir = new File(getSaveDirName());
		
		if (!saveDir.exists()) {
			saveDir.mkdirs();
		}
		File f = new File(getSavePath());
		
		if (!f.exists()) {
			try {
				f.createNewFile();
				
			} catch (IOException ex) {
				ex.printStackTrace();
				return;
			}
		}
		try (DataOutputStream out = new DataOutputStream(new FileOutputStream(getSavePath()))) {
			write(new BinaryWriter(out));
			
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
