package com.nattguld.data.cfg;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.nattguld.data.ResourceIO;
import com.nattguld.data.json.JsonReader;

/**
 * 
 * @author randqm
 *
 */

public class ConfigManager {
	
	/**
	 * The loaded configurations.
	 */
	private static List<Config> configs = new ArrayList<>();

	
	/**
	 * Retrieves a loaded config.
	 * 
	 * @param config The config.
	 * 
	 * @return The config.
	 */
	public static Config getConfig(Config config) {
		Config exists = getConfigForSaveFileName(config.getSaveFileName());
		
		if (Objects.nonNull(exists)) {
			return exists;
		}
		load(config);
		return config;
	}
	
	/**
	 * Loads & retrieves a config or reverts to the default config when unable to load the config.
	 * 
	 * @param defaultConfig The default config.
	 * 
	 * @return The loaded or default config.
	 */
	private static void load(Config config) {
		File saveFile = new File(config.getSavePath());
		
		if (!saveFile.exists()) {
			return;
		}
		JsonReader reader = ResourceIO.loadJsonResource(saveFile);
		
		if (Objects.isNull(reader)) {
			return;
		}
		config.setReader(reader).read(reader);
		configs.add(config);
	}
	
	/**
	 * Retrieves a config for it's save file name.
	 * 
	 * @param saveFileName The save file name.
	 * 
	 * @return The config.
	 */
	private static Config getConfigForSaveFileName(String saveFileName) {
		for (Config cfg : configs) {
			if (cfg.getSaveFileName().equals(saveFileName)) {
				return cfg;
			}
		}
		return null;
	}

}
