package com.nattguld.data.json;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

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
			
			try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getSavePath()), "UTF-8"))) {
				bw.write(writer.getGson().toJson(writer.getObject()));
			}
			/*
			try (FileWriter fileWriter = new FileWriter(getSavePath())) {
				fileWriter.write(writer.getGson().toJson(writer.getObject()));
			}
			*/
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
