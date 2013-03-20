package net.keplergaming.keplerbot.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import net.keplergaming.keplerbot.logger.MainLogger;

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
			MainLogger.warning("Expected Integer", e);
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
			MainLogger.warning("Expected Boolean", e);
			return defaultValue;
		}
	}

	public void setBoolean(String key, boolean setValue) {
		String value = Boolean.toString(setValue);
		prop.setProperty(key, value);
	}

	public void loadConfig() {
		try {
			MainLogger.fine("Loading config file " + fileName);
			File configFile = new File(fileName);
			if (configFile.exists()) {
				InputStream in = new FileInputStream(configFile);
				prop.load(in);
				in.close();
			}
		} catch (IOException e) {
			MainLogger.error("Failed to load config file "  + fileName, e);
		}
	}

	public void saveConfig() {
		try {
			MainLogger.fine("Saving config file " + fileName);
			File configFile = new File(fileName);
			if (configFile.getParentFile() != null) {
				configFile.getParentFile().mkdirs();
			}
			OutputStream out = new FileOutputStream(configFile);
			prop.store(out, "KeplerBot");
			out.close();
		} catch (IOException e) {
			MainLogger.error("Failed to save config file " + fileName, e);
		}
	}

	public static String[] USERNAME = new String[] { "twitch_name", "" };
	public static String[] PASSWORD = new String[] { "twitch_password", "" };

	public static String[] BOT_NAME = new String[] { "bot_name", "KeplerBot" };
	public static String[] JOIN_MESSAGE = new String[] { "join_message", "%s has joined your stream" };
	public static String[] LEAVE_MESSAGE = new String[] { "leave_message", "%s has left your stream" };

	public static String[] MUTE_ALL = new String[] {"mute_all", "false"};
	public static String[] MUTE_WARNINGS = new String[] {"mute_warnings", "false"};
	public static String[] MUTE_ERRORS =  new String[] {"mute_errors", "false"};
}
