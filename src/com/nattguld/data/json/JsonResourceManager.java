package com.nattguld.data.json;

import java.io.File;

import com.nattguld.data.ResourceIO;
import com.nattguld.data.ResourceManager;

/**
 * 
 * @author randqm
 *
 */

public abstract class JsonResourceManager<R extends JsonResource> extends ResourceManager<R, JsonReader, JsonWriter> {

	
	@Override
	protected JsonReader loadResource(File f) {
		return ResourceIO.loadJsonResource(f);
	}

	@Override
	protected abstract R instantiateResource(JsonReader reader);

}
