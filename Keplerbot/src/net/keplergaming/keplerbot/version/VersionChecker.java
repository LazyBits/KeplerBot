package net.keplergaming.keplerbot.version;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.keplergaming.keplerbot.logger.MainLogger;

public class VersionChecker {

	public VersionChecker() {
		deleteUpdater();
		checkVersion();
	}

	private String latest, link;

	private void deleteUpdater() {
		try {
			Thread.sleep(1000);

			File file = new File("./updater.jar");
			if (file.exists()) {
				MainLogger.fine("Detected updater.jar, Attempting to delete it");
				if (!file.delete()) {
					MainLogger.warning("Failed to delete updater.jar");
				} else {
					MainLogger.fine("Deleted updater.jar");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean requiresUpdate() {
		return !Version.getVersion().equalsIgnoreCase(latest) && latest != null;
	}

	private void checkVersion() {
		try {
			MainLogger.fine("Fetching remote version file");
			URL url = new URL("https://dl.dropbox.com/u/36116005/keplerbot/version.xml");
			InputStream input = url.openStream();
			Scanner in = new Scanner(input);

			while (in.hasNext()) {
				String s = in.nextLine();

				if (s.contains("<latest>")) {
					Pattern p = Pattern.compile("\\<latest\\>(.*?)\\</latest\\>");
					Matcher m = p.matcher(s);
					m.find();
					MainLogger.fine("Latest is " + m.group(1));
					latest = m.group(1);
				}

				if (s.toLowerCase().contains("<link>")) {
					Pattern p = Pattern.compile("\\<link\\>(.*?)\\</link\\>");
					Matcher m = p.matcher(s);
					m.find();
					link = m.group(1);
				}
			}
			in.close();
			input.close();
		} catch (IOException e) {
			MainLogger.warning("Failed to connect to remote version.xml", e);
		}
	}

	public void startUpdater() {
		try {
			MainLogger.info("Starting update process");
			File jar = new File("./updater.jar");
			if (!jar.exists()) {
				MainLogger.fine("Extracting updater.jar");
				URL url = ClassLoader.getSystemResource("net/keplergaming/keplerbot/resources/updater.jar");
				FileOutputStream output = new FileOutputStream(jar);
				InputStream input = url.openStream();
				byte[] buffer = new byte[4096];
				int bytesRead = input.read(buffer);
				while (bytesRead != -1) {
					output.write(buffer, 0, bytesRead);
					bytesRead = input.read(buffer);
				}
				output.close();
				input.close();

			}

			MainLogger.fine("Running updater");
			Runtime.getRuntime().exec("java -jar ./updater.jar " + " " + link + " Keplerbot.jar");
			System.exit(1);
		} catch (IOException e) {
			MainLogger.warning("Failed to start updater.jar", e);
		}
	}
}
