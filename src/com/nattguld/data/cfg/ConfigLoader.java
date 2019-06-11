package com.nattguld.data.cfg;

import java.io.File;
import java.util.Objects;

import com.nattguld.data.ResourceIO;
import com.nattguld.data.json.JsonReader;

/**
 * 
 * @author randqm
 *
 */

public class ConfigLoader {

	
	/**
	 * Loads & retrieves a config or reverts to the default config when unable to load the config.
	 * 
	 * @param defaultConfig The default config.
	 * 
	 * @return The loaded or default config.
	 */
	public static void load(Config config) {
		File saveFile = new File(config.getSavePath());
		
		if (!saveFile.exists()) {
			return;
		}
		JsonReader reader = ResourceIO.loadJsonResource(saveFile);
		
		if (Objects.isNull(reader)) {
			return;
		}
		config.read(reader);
	}

}
