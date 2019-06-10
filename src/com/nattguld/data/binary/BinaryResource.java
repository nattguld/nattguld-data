package com.nattguld.data.binary;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.nattguld.data.IResource;

/**
 * 
 * @author randqm
 *
 */

public abstract class BinaryResource implements IResource<BinaryReader, BinaryWriter> {

	
	@Override
	public void save() {
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
