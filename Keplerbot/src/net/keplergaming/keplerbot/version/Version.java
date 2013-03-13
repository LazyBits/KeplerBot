package net.keplergaming.keplerbot.version;

public class Version {

	private static String major = "1";
	private static String minor = "1";
	private static String rev = "1";

	public static String getVersion() {
		return String.format("v%s.%s.%s", major, minor, rev);
	}
}
