package com.nattguld.data.json.util;

import com.google.gson.JsonElement;

/**
 * 
 * @author randqm
 *
 */

public interface JsonConvertable {
	
	
	/**
	 * Converts the object to a json element.
	 * 
	 * @return The json element.
	 */
	public JsonElement toJsonElement();

}
