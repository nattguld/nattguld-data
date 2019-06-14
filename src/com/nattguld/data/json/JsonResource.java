package com.nattguld.data.json;

import java.io.File;
import java.io.FileWriter;

import com.nattguld.data.Resource;

/**
 * 
 * @author randqm
 *
 */

public abstract class JsonResource extends Resource<JsonReader, JsonWriter> {


	/**
	 * Creates a new json resource.
	 */
	public JsonResource() {
		super(null);
	}
	
	/**
	 * Creates a new json resource.
	 * 
	 * @param reader The resource reader.
	 */
	public JsonResource(JsonReader reader) {
		super(reader);
	}

	@Override
	public void save() {
		File saveDir = new File(getSaveDirPath());

		if (!saveDir.exists()) {
			saveDir.mkdirs();
		}
		JsonWriter writer = new JsonWriter();
		
		try {
			write(writer);
		
			try (FileWriter fileWriter = new FileWriter(getSavePath())) {
				fileWriter.write(writer.getGson().toJson(writer.getObject()));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
