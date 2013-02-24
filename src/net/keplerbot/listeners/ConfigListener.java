package net.keplerbot.listeners;

import java.io.File;

import net.keplerbot.config.KeplerConfig;
import net.keplerbot.log.KeplerLogger;

import org.essiembre.FileChangeListener;

public class ConfigListener implements FileChangeListener{

	@Override
	public void fileChanged(File file) {
		KeplerLogger.getInstance("Main").info("Detected change in config file");
		KeplerLogger.getInstance("Main").info("reloading config file");
		KeplerConfig.getInstance().loadConfigs();
	}
}
