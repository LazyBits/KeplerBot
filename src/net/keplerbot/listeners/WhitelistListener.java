package net.keplerbot.listeners;

import java.io.File;

import net.keplerbot.log.KeplerLogger;
import net.keplerbot.managers.WhiteListManager;

import org.essiembre.FileChangeListener;

public class WhitelistListener implements FileChangeListener{

	public WhitelistListener(String channel) {
		this.channel = channel;
	}
	
	private String channel;
	
	@Override
	public void fileChanged(File file) {
		KeplerLogger.getInstance("Main").info("Detected change in whitelist file");
		KeplerLogger.getInstance("Main").info("reloading whitelist file for " + channel);
		WhiteListManager.getInstance(channel).loadWhiteList();
	}
}
