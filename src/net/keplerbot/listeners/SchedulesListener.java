package net.keplerbot.listeners;

import java.io.File;

import net.keplerbot.log.KeplerLogger;
import net.keplerbot.managers.ScheduleManager;

import org.essiembre.FileChangeListener;

public class SchedulesListener implements FileChangeListener{

	public SchedulesListener(String channel) {
		this.channel = channel;
	}
	
	private String channel;
	
	@Override
	public void fileChanged(File file) {
		KeplerLogger.getInstance("Main").info("Detected change in schedule file");
		KeplerLogger.getInstance("Main").info("reloading schedule file for " + channel);
		ScheduleManager.getInstance(channel).loadSchedules();
	}
}
