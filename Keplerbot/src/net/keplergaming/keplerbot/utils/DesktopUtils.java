package net.keplergaming.keplerbot.utils;

import java.awt.Desktop;
import java.net.URI;

import net.keplergaming.keplerbot.logger.MainLogger;

public class DesktopUtils {

	public static String getOS() {
		return System.getProperty("os.name").toLowerCase();
	}

	public static void openUrl(String url) {
		try {
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().browse(new URI(url));
			}
		} catch (Exception e) {
			MainLogger.error("Could not open link", e);
		}
	}
}
