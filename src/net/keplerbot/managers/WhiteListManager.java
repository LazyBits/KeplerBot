package net.keplerbot.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.keplerbot.listeners.WhitelistListener;
import net.keplerbot.log.KeplerLogger;

import org.essiembre.FileMonitor;

public class WhiteListManager {
	
	private KeplerLogger logger;
	
	private File file;
	
	private static Map<String, WhiteListManager> instances = new HashMap<String, WhiteListManager>();
	
	private ArrayList<String> whiteList = new ArrayList<String>();
	
	public ArrayList<String> getWhiteList() {
		return whiteList;
	}

	public WhiteListManager(String channel) {
		logger = KeplerLogger.getInstance("Main");
		if(instances.containsKey(channel)) {
			logger.error("WhiteListManager for " + channel + " already exists!");
		}else{
			instances.put(channel, this);
			file = new File("./whitelists/whitelist_" + channel.replace("#", "") + ".txt");
			file.getParentFile().mkdir();
			if(file.exists()) {
				loadWhiteList();
			}else{
				try {
					file.createNewFile();
				} catch (IOException e) {
					logger.error("Failed to load whitelist from file", e);
				}
				if(!file.exists()) {
					logger.error("Failed to load whitelist from file");
				}
			}
			try {
				FileMonitor.getInstance().addFileChangeListener(new WhitelistListener(channel), file, 500);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static WhiteListManager getInstance(String channel) {
		if (!instances.containsKey(channel)) {
			new WhiteListManager(channel);
		}
		return instances.get(channel);
	}
	
	public void loadWhiteList() {
		try {
			whiteList.clear();
			BufferedReader buff = new BufferedReader(new FileReader(file));
			String line = "";

			while ((line = buff.readLine()) != null) {
				whiteList.add(line);
			}
			buff.close();
		} catch (Exception e) {
			logger.error("Failed to load whitelist from file", e);
		}
	}
	
	public void storeWhiteList() {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(file, false));
			
			for(String whiteListed : whiteList) {
				writer.println(whiteListed);
			}

			writer.close();
		} catch (Exception e) {
			logger.error("Failed to store whitelist to file", e);
		}
	}
	
	public boolean addWhiteListed(String word) {
		if(!whiteList.contains(word)) {
			whiteList.add(word);
			storeWhiteList();
			return true;
		}
		return false;
	}
	
	public boolean removeWhiteListed(String word) {
		if(whiteList.contains(word)) {
			whiteList.remove(word);
			storeWhiteList();
			return true;
		}
		return false;
	}
	
	public boolean hasWhiteListed(String message) {
		for(String whiteListed : whiteList) {
			if(message.toLowerCase().contains(whiteListed.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isWhiteListed(String word) {
		for(String whiteListed : whiteList) {
			if(word.equalsIgnoreCase(whiteListed)) {
				return true;
			}
		}
		return false;
	}
}
