package com.nattguld.data.json;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nattguld.data.IResourceReader;

/**
 * 
 * @author randqm
 *
 */

public class JsonReader implements IResourceReader {
    
    /**
     * The gson reader.
     */
    private final Gson gson;
    
    /**
     * The json object.
     */
    private final JsonObject object;
    
    /**
     * The loaded file.
     */
    private final File f;

     
    /**
     * Creates a new json loader.
     * 
     * @param gson The gson reader.
     * 
     * @param object The json object.
     * 
     * @param f The loaded file.
     */
    public JsonReader(Gson gson, JsonObject object, File f) {
    	this.gson = gson;
    	this.object = object;
    	this.f = f;
    }
    
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}
    
    /**
     * Retrieves a string value.
     * 
     * @param key The key.
     * 
     * @return The value.
     */
    public String getAsString(String key) {
    	return object.get(key).getAsString();
    }
    
    /**
     * Retrieves a string value.
     * 
     * @param key The key.
     * 
     * @param defaultValue The default value.
     * 
     * @return The value.
     */
    public String getAsString(String key, String defaultValue) {
    	if (!has(key)) {
    		return defaultValue;
    	}
    	return getAsString(key);
    }
       
    /**
     * Retrieves an integer value.
     * 
     * @param key The key.
     * 
     * @return The value.
     */
    public int getAsInt(String key) {
    	return object.get(key).getAsInt();
    }
    
    /**
     * Retrieves an integer value.
     * 
     * @param key The key.
     * 
     * @param defaultValue The default value.
     * 
     * @return The value.
     */
    public int getAsInt(String key, int defaultValue) {
    	if (!has(key)) {
    		return defaultValue;
    	}
    	return getAsInt(key);
    }
       
    /**
     * Retrieves a float value.
     * 
     * @param key The key.
     * 
     * @return The value.
     */
    public float getAsFloat(String key) {
    	return object.get(key).getAsFloat();
    }
    
    /**
     * Retrieves a float value.
     * 
     * @param key The key.
     * 
     * @param defaultValue The default value.
     * 
     * @return The value.
     */
    public float getAsFloat(String key, float defaultValue) {
    	if (!has(key)) {
    		return defaultValue;
    	}
    	return getAsFloat(key);
    }
       
    /**
     * Retrieves a double value.
     * 
     * @param key The key.
     * 
     * @return The value.
     */
    public double getAsDouble(String key) {
    	return object.get(key).getAsDouble();
    }
    
    /**
     * Retrieves a double value.
     * 
     * @param key The key.
     * 
     * @param defaultValue The default value.
     * 
     * @return The value.
     */
    public double getAsDouble(String key, double defaultValue) {
    	if (!has(key)) {
    		return defaultValue;
    	}
    	return getAsDouble(key);
    }
       
    /**
     * Retrieves a boolean value.
     * 
     * @param key The key.
     * 
     * @return The value.
     */
    public boolean getAsBoolean(String key) {
    	return object.get(key).getAsBoolean();
    }
    
    /**
     * Retrieves a boolean value.
     * 
     * @param key The key.
     * 
     * @param defaultValue The default value.
     * 
     * @return The value.
     */
    public boolean getAsBoolean(String key, boolean defaultValue) {
    	if (!has(key)) {
    		return defaultValue;
    	}
    	return getAsBoolean(key);
    }
    
    /**
     * Retrieves a long value.
     * 
     * @param key The key.
     * 
     * @return The value.
     */
    public long getAsLong(String key) {
    	return object.get(key).getAsLong();
    }
    
    /**
     * Retrieves a long value.
     * 
     * @param key The key.
     * 
     * @param defaultValue The default value.
     * 
     * @return The value.
     */
    public long getAsLong(String key, long defaultValue) {
    	if (!has(key)) {
    		return defaultValue;
    	}
    	return getAsLong(key);
    }
    
    /**
     * Retrieves an object value.
     * 
     * @param key The key.
     * 
     * @param c The class type.
     * 
     * @return The object.
     */
    public Object getAsObject(String key, Class<?> c) {
    	return gson.fromJson(object.get(key), c);
    }
    
    /**
     * Retrieves an object value.
     * 
     * @param key The key.
     * 
     * @param c The class type.
     * 
     * @param defaultValue The default value.
     * 
     * @return The value.
     */
    public Object getAsObject(String key, Class<?> c, Object defaultValue) {
    	if (!has(key)) {
    		return defaultValue;
    	}
    	return getAsObject(key, c);
    }
    
    /**
     * Retrieves a list.
     * 
     * @param key The key.
     * 
     * @param elementType The element type.
     * 
     * @param list The default list.
     * 
     * @return The list.
     */
    public <T> List<T> getAsList(String key, Type elementType, List<T> list) {
    	return (List<T>)getAsCollection(key, elementType, list);
    }
    
    /**
     * Retrieves a deque.

     * @param key The key.
     * 
     * @param elementType The element type.
     * 
     * @param deque The default deque.
     * 
     * @return The deque.
     */
    public <T> Deque<T> getAsDeque(String key, Type elementType, Deque<T> deque) {
    	return (Deque<T>)getAsCollection(key, elementType, deque);
    }
    
    /**
     * Retrieves a collection.

     * @param key The key.
     * 
     * @param elementType The element type.
     * 
     * @param collection The default collection.
     * 
     * @return The collection.
     */
    public <T> Collection<T> getAsCollection(String key, Type elementType, Collection<T> collection) {
    	if (!has(key)) {
    		return collection;
    	}
    	@SuppressWarnings("unchecked")
    	Collection<T> collectionFromJson = (Collection<T>)getGson().fromJson(getObject().get(key).getAsJsonArray(), elementType);
    	
    	if (Objects.nonNull(collectionFromJson) && !collectionFromJson.isEmpty()) {
    		collection.addAll(collectionFromJson);
    	}
    	return collection;
    }
    
    /**
     * Retrieves a map value.
     * 
     * @param key The key.
     * 
     * @param elementType The element type.
     * 
     * @param map The default map.
     * 
     * @return The map.
     */
	public <T, V> Map<T, V> getAsMap(String key, Type elementType, Map<T, V> map) {
    	if (!has(key)) {
    		return map;
    	}
    	@SuppressWarnings("unchecked")
    	Map<T, V> mapFromJson = (Map<T, V>)getGson().fromJson(getObject().get(key).getAsJsonObject(), elementType);
    	
    	if (Objects.nonNull(mapFromJson) && !mapFromJson.isEmpty()) {
    		map.putAll(mapFromJson);
    	}
    	return map;
    }
    
    /**
     * Retrieves whether a key is available or not.
     * 
     * @param key The key.
     * 
     * @return The result.
     */
    public boolean has(String key) {
    	return getObject().has(key);
    }
    
    /**
     * Retrieves the gson reader.
     * 
     * @return The reader.
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
    
    /**
     * Retrieves the loaded file.
     * 
     * @return The loaded file.
     */
    public File getLoadedFile() {
    	return f;
    }

}
