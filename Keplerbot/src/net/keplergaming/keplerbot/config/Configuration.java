package net.keplergaming.keplerbot.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import net.keplergaming.keplerbot.logger.Logger;

public class Configuration {

	public Configuration(String fileName) {
		prop = new Properties();
		this.fileName = fileName;
	}

	private String fileName;
	private Properties prop;

	public int getInteger(String key, int defaultValue) {
		String value = Integer.toString(defaultValue);
		if (!prop.containsKey(key)) {
			setInteger(key, defaultValue);
			return defaultValue;
		}
		
		try {
			return Integer.valueOf(prop.getProperty(key, value));
		} catch (Exception e) {
			Logger.warning("Expected Integer", e);
			return defaultValue;
		}
	}

	public void setInteger(String key, int setValue) {
		String value = Integer.toString(setValue);
		prop.setProperty(key, value);
	}

	public String getString(String key, String defaultValue) {
		if (!prop.containsKey(key)) {
			setString(key, defaultValue);
			return defaultValue;
		}
		return prop.getProperty(key, defaultValue);
	}

	public void setString(String key, String setValue) {
		prop.setProperty(key, setValue);
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		String value = Boolean.toString(defaultValue);
		if (!prop.containsKey(key)) {
			setBoolean(key, defaultValue);
			return defaultValue;
		}
		
		try {
			return Boolean.valueOf(prop.getProperty(key, value));
		} catch (Exception e) {
			Logger.warning("Expected Boolean", e);
			return defaultValue;
		}
	}

	public void setBoolean(String key, boolean setValue) {
		String value = Boolean.toString(setValue);
		prop.setProperty(key, value);
	}

	public void loadConfig() {
	    try {
	    	Logger.info("Loading config file");
	    	File configFile = new File(fileName);
	    	if (configFile.exists()) {
		    	InputStream in = new FileInputStream(configFile);
				prop.load(in);
	    	}
		} catch (IOException e) {
			Logger.error("Failed to load config file", e);
		}
	}

	public void saveConfig() {
	    try {
	    	Logger.info("Saving config file");
	    	File configFile = new File(fileName);
	    	OutputStream out = new FileOutputStream(configFile);
			prop.store(out, "KeplerBot");
		} catch (IOException e) {
			Logger.error("Failed to save config file", e);
		}
	}
}
