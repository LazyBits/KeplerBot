package net.keplerbot.listeners;

import java.io.File;

import net.keplerbot.log.KeplerLogger;
import net.keplerbot.managers.BlackListManager;

import org.essiembre.FileChangeListener;

public class BlackListListener implements FileChangeListener{

	public BlackListListener(String channel) {
		this.channel = channel;
	}
	
	private String channel;
	
	@Override
	public void fileChanged(File file) {
		KeplerLogger.getInstance("Main").info("Detected change in blacklist file");
		KeplerLogger.getInstance("Main").info("reloading blacklist file for " + channel);
		BlackListManager.getInstance(channel).loadBlackList();
	}
}
