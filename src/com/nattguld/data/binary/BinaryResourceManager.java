package com.nattguld.data.binary;

import java.io.File;

import com.nattguld.data.ResourceIO;
import com.nattguld.data.ResourceManager;

/**
 * 
 * @author randqm
 *
 */

public abstract class BinaryResourceManager<R extends BinaryResource> extends ResourceManager<R, BinaryReader, BinaryWriter> {
	

	@Override
	protected BinaryReader loadResource(File f) {
		return ResourceIO.loadBinaryResource(f);
	}

	@Override
	protected abstract R instantiateResource(BinaryReader reader);
	
}
