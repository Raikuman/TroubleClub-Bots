package com.reliquary.crow.resources.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultConfigWriter {

	private final Logger logger = LoggerFactory.getLogger(DefaultConfigWriter.class);

	/*
	botSettingsConfig
	Writes default config for bot settings
	 */
	public void writeBotSettingsConfigFile() {

		List<String> configSettings = new ArrayList<>(Arrays.asList(
			"prefix=!",
			"presencedirectory=presence",
			"commandresourcedirectory=commandresources",
			"quotedirectory=quotes"
		));

		String directory = "config";
		String fileName = "botSettings";

		// Create config
		writeConfigFile(configSettings, createConfigFile(directory + "/" + fileName));
	}

	/*
	writePresenceSettingsConfigFile
	Writes default config for presence settings
	 */
	public void writePresenceSettingsConfigFile() {

		List<String> configSettings = new ArrayList<>(Arrays.asList(
			"yamEmoji=U+1f360",
			"yamTargetChannel=",
			"yamPostChannel=",
			"checkmarkEmoji=U+2705"
		));

		String directory = "config";
		String fileName = "presenceSettings";

		// Create config
		writeConfigFile(configSettings, createConfigFile(directory + "/" + fileName));
	}

	/*
	writeConfigFile
	Write config to given file
	 */
	private void writeConfigFile(List<String> config, File file) {

		if (file.exists()) {
			try {
			// Write to file with BufferedWriter
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			for (String configSetting : config) {
				writer.write(configSetting);
				writer.newLine();
			}

			writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (!file.exists()) {
			logger.info("Config " + file.getName() + " already created. Continuing...");
		} else
			logger.info("Error creating config " + file.getName());
	}

	/*
	createConfigFile
	Create config file
	*/
	private File createConfigFile(String fileName) {

		// Create file, check if exists
		File configFile = new File(fileName + ".cfg");

		try {
			if (configFile.createNewFile())
				logger.info("Created config " + configFile.getName() + " successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return configFile;
	}
}
