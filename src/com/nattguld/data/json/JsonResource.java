package com.nattguld.data.json;

import java.io.FileWriter;

import com.nattguld.data.IResource;

/**
 * 
 * @author randqm
 *
 */

public abstract class JsonResource implements IResource<JsonReader, JsonWriter> {


	@Override
	public void save() {
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
