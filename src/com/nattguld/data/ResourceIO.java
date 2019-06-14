package com.nattguld.data;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nattguld.data.binary.BinaryReader;
import com.nattguld.data.json.JsonReader;

/**
 * 
 * @author randqm
 *
 */

public class ResourceIO {
	
	
	/**
	 * Loads a json resource.
	 * 
	 * @param saveFile The save file.
	 * 
	 * @return The json reader.
	 */
	public static JsonReader loadJsonResource(File saveFile) {
		if (!saveFile.exists()) {
			System.err.println(saveFile.getAbsolutePath() + " not found");
			return null;
		}
    	try {
    		JsonParser parser = new JsonParser();
    		Gson gson = new GsonBuilder().create();
    		JsonObject jsonObject = null;
    		
    		try (FileReader fileReader = new FileReader(saveFile)) {
    			jsonObject = (JsonObject) parser.parse(fileReader);
    		}
    		if (Objects.isNull(jsonObject)) {
    			System.err.println("Failed to parse json resource: " + saveFile.getAbsolutePath());
    			return null;
    		}
    		return new JsonReader(gson, jsonObject, saveFile);
    		
    	} catch (FileNotFoundException ex) {
    		ex.printStackTrace();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	return null;
	}
	
	/**
	 * Loads a binary resource.
	 * 
	 * @param saveFile The save file.
	 * 
	 * @return The binary reader.
	 */
	public static BinaryReader loadBinaryResource(File saveFile) {
		if (!saveFile.exists()) {
			return null;
		}
		try {
			return new BinaryReader(new DataInputStream(new FileInputStream(saveFile)));
			
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		return null;
	}

}