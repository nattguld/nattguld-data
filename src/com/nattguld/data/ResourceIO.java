package com.nattguld.data;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
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
		try (FileInputStream fis = new FileInputStream(saveFile)) {
			return getJsonReader(fis, false);
			
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
    	return null;
	}
	
	/**
	 * Parses json content and retrieves a json reader.
	 * 
	 * @param json The json.
	 * 
	 * @return The json reader.
	 */
	public static JsonReader loadJsonFromString(String json) {
		return loadJsonFromString(json, false);
	}
	
	/**
	 * Parses json content and retrieves a json reader.
	 * 
	 * @param json The json.
	 * 
	 * @param disableHtmlEscaping Whether to disable HTML escaping or not.
	 * 
	 * @return The json reader.
	 */
	public static JsonReader loadJsonFromString(String json, boolean disableHtmlEscaping) {
		if (disableHtmlEscaping) {
			json = json.replace("[\"", "[°v°");
			json = json.replace("{\"", "{°v°");
			json = json.replace("\"]", "°v°]");
			json = json.replace("\"}", "°v°}");
			json = json.replace(",\"", ",°v°");
			json = json.replace("\",", "°v°,");
			json = json.replace(":\"", ":°v°");
			json = json.replace("\":", "°v°:");
			
			json = json.replace("\"", "''");
			
			json = json.replace("°v°", "\"");
		}
		try (ByteArrayInputStream bis = new ByteArrayInputStream(json.getBytes("UTF-8"))) {
			return getJsonReader(bis, disableHtmlEscaping);
			
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Parses a json input stream and retrieves a json reader.
	 * 
	 * @param is The input stream.
	 * 
	 * @param disableHtmlEscaping Whether to disable HTML escaping or not.
	 * 
	 * @return The json reader.
	 */
	public static JsonReader getJsonReader(InputStream is, boolean disableHtmlEscaping) {
		JsonParser parser = new JsonParser();
		Gson gson = disableHtmlEscaping ? new GsonBuilder().disableHtmlEscaping().setLenient().create() 
				: new GsonBuilder().setLenient().create();
		JsonElement jsonEl = null;
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
			jsonEl = parser.parse(br);
			
			if (Objects.isNull(jsonEl)) {
    			System.err.println("Failed to parse json");
    			return null;
    		}
    		return new JsonReader(gson, jsonEl, null);
			
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
			
		} catch (IOException ex) {
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