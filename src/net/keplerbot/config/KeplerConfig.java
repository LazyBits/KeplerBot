package net.keplerbot.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import net.keplerbot.log.KeplerLogger;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class KeplerConfig {

	private static KeplerConfig instance;
	private XMLConfiguration config;
	private static KeplerLogger logger = KeplerLogger.getInstance("Main");
	
	public String login;
	public String pass;
	public int cooldown;
	public boolean cooldownenabled;
	public String closeMessage;
	public String joinMessage;
	public boolean joinOwnChannel;
	public int capTimeOut;
	public int urlTimeOut;
	public int blackListTimeOut;
	public int messageDeletedTimeOut;
	public boolean urlsAllowed;
	public boolean capsAllowed;
	public boolean blackListEnabled;
	public boolean whiteListEnabled;
	public int timeOutInterval;

	public KeplerConfig() throws Exception {
		File configFile = new File("./config.xml");
		if(!configFile.exists()) {
			URL url = ClassLoader.getSystemResource("net/keplerbot/config/config.xml");
			FileOutputStream output = new FileOutputStream(configFile);
			InputStream input = url.openStream();
			byte [] buffer = new byte[4096];
			int bytesRead = input.read(buffer);
			while (bytesRead != -1) {
			    output.write(buffer, 0, bytesRead);
			    bytesRead = input.read(buffer);
			}
			output.close();
			input.close();
		}
		try{
			config = new XMLConfiguration(configFile);
		}catch(ConfigurationException e) {
			configFile.delete();
			logger.error("Failed to load configuration, please restart the bot", e);
			instance = null;
		}
	}
	
	public void loadConfigs() {
		login = getString("twitch_login", "/");
		pass = getString("twitch_pass", "/");
		cooldown = getInt("commandcooldown_sec", 10);
		cooldownenabled = getBoolean("commandcooldown_enabled", true);
		closeMessage = getString("close_message", "Bye, Bye :z");
		joinMessage = getString("join_message", "keplerbot initialized");
		joinOwnChannel = getBoolean("join_own_channel", true);
		capTimeOut = getInt("cap_timeout", 5);
		urlTimeOut = getInt("url_timeout", 5);
		blackListTimeOut = getInt("blacklist_timeout", 5);
		messageDeletedTimeOut = getInt("message_deleted_timeout", 300);
		urlsAllowed = getBoolean("urls_allowed", false);
		capsAllowed = getBoolean("caps_allowed", false);
		blackListEnabled = getBoolean("blacklist_enabled", true);
		whiteListEnabled = getBoolean("whitelist_enabled", true);
		timeOutInterval = getInt("timeout_interval", 4);
	}
	
	public static void init() {
		if(instance == null) {
			try {
				logger.info("Loading configuration");
				instance = new KeplerConfig();
				instance.loadConfigs();
				logger.info("Loaded configuration");
			} catch (Exception e) {
				logger.error("Failed to load configuration", e);
			}
		}
	}
	
	public static KeplerConfig getInstance() {
		init();
		return instance;
	}
	
	private int getInt(String key, int defaultValue) {
		logger.info("Getting " + key + " from configuration");
		int value = config.getInt(key, defaultValue);
		logger.info(key + " : " + value);
		return value;
	}
	
	private String getString(String key, String defaultValue) {
		logger.info("Getting " + key + " from configuration");
		String value = config.getString(key, defaultValue);
		logger.info(key + " : " + value);
		return value;
	}
	
	private boolean getBoolean(String key, boolean defaultValue) {
		logger.info("Getting " + key + " from configuration");
		Boolean value = config.getBoolean(key, defaultValue);
		logger.info(key + " : " + defaultValue);
		return value;
	}
	
	public void setProperty(String property, String value) {
		try{
			config.setProperty(property, value);
			config.save();
			loadConfigs();
		}catch(ConfigurationException e){
			logger.error("Error in updating the config file", e);
		}
	}
}
