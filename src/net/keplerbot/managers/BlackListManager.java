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

import net.keplerbot.listeners.BlackListListener;
import net.keplerbot.log.KeplerLogger;

import org.essiembre.FileMonitor;

public class BlackListManager {
	
	private KeplerLogger logger;
	
	private File file;
	
	private static Map<String, BlackListManager> instances = new HashMap<String, BlackListManager>();
	
	private ArrayList<String> blackList = new ArrayList<String>();
	
	public ArrayList<String> getBlackList() {
		return blackList;
	}
	
	public BlackListManager(String channel) {
		logger = KeplerLogger.getInstance("Main");
		if(instances.containsKey(channel)) {
			logger.error("BlackListManager for " + channel + " already exists!");
		}else{
			instances.put(channel, this);
			file = new File("./blacklists/blacklist_" + channel.replace("#", "") + ".txt");
			file.getParentFile().mkdir();
			if(file.exists()) {
				loadBlackList();
			}else{
				try {
					file.createNewFile();
				} catch (IOException e) {
					logger.error("Failed to load blacklist from file", e);
				}
				if(!file.exists()) {
					logger.error("Failed to load blacklist from file");
				}
			}
			try {
				FileMonitor.getInstance().addFileChangeListener(new BlackListListener(channel), file, 500);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static BlackListManager getInstance(String channel) {
		if (!instances.containsKey(channel)) {
			new BlackListManager(channel);
		}
		return instances.get(channel);
	}
	
	public void loadBlackList() {
		try {
			blackList.clear();
			BufferedReader buff = new BufferedReader(new FileReader(file));
			String line = "";

			while ((line = buff.readLine()) != null) {
				blackList.add(line);
			}
			buff.close();
		} catch (Exception e) {
			logger.error("Failed to load blacklist from file", e);
		}
	}
	
	public void storeBlackList() {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(file, false));
			
			for(String blackListed : blackList) {
				writer.println(blackListed);
			}

			writer.close();
		} catch (Exception e) {
			logger.error("Failed to store blacklist to file", e);
		}
	}
	
	public boolean addBlackListed(String word) {
		if(!blackList.contains(word)) {
			blackList.add(word);
			storeBlackList();
			return true;
		}
		return false;
	}
	
	public boolean removeBlackListed(String word) {
		if(blackList.contains(word)) {
			blackList.remove(word);
			storeBlackList();
			return true;
		}
		return false;
	}
	
	public boolean hasBlackListed(String message) {
		for(String blackListed : blackList) {
			if(message.toLowerCase().contains(blackListed.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isBlackListed(String word) {
		for(String blackListed : blackList) {
			if(word.equalsIgnoreCase(blackListed)) {
				return true;
			}
		}
		return false;
	}
}
