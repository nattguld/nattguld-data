package com.nattguld.data.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.nattguld.data.IResourceWriter;

/**
 * 
 * @author randqm
 *
 */

public class JsonWriter implements IResourceWriter {
    
    /**
     * The gson instance.
     */
    private final Gson gson;
    
    /**
     * The json object.
     */
    private final JsonObject object;
    
    
    /**
     * Creates a new json writer.
     */
    public JsonWriter() {
    	this.gson = new GsonBuilder().setPrettyPrinting().create();
    	this.object = new JsonObject();
    }
    
    /**
     * Writes a value.
     * 
     * @param key The key.
     * 
     * @param value The value.
     */
    public void write(String key, Object value) {
    	if (value instanceof Number) {
    		object.addProperty(key, (Number)value); 
    		
    	} else if (value instanceof String) {
    		object.addProperty(key, (String)value); 
    		
    	} else if (value instanceof Boolean) {
    		object.addProperty(key, (boolean)value); 
    		
    	} else if (value instanceof Character) {
    		object.addProperty(key, (char)value); 
    		
    	} else {
    		object.add(key, gson.toJsonTree(value));
    	}
    }
    
    /**
     * Retrieves the gson instance.
     * 
     * @return The gson instance.
     */
    public Gson getGson() {
    	return gson;
    }
    
    /**
     * Retrieves the json object.
     * 
     * @return The json object.
     */
    public JsonObject getObject() {
    	return object;
    }

}
