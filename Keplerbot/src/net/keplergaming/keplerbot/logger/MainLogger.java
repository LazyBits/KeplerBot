package net.keplergaming.keplerbot.logger;

import java.util.logging.Level;

public class MainLogger {

	private static Logger logger = new Logger("MAIN");

	static {
		logger.addListener(new LogWriter(null));
	}

	public static void info(String message) {
		info(message, null);
	}

	public static void info(String message, Throwable t) {
		logger.log(message, Level.INFO, t);
	}

	public static void warning(String message) {
		warning(message, null);
	}

	public static void warning(String message, Throwable t) {
		logger.log(message, Level.WARNING, t);
	}

	public static void fine(String message) {
		fine(message, null);
	}

	public static void fine(String message, Throwable t) {
		logger.log(message, Level.FINE, t);
	}

	public static void finer(String message) {
		finer(message, null);
	}

	public static void finer(String message, Throwable t) {
		logger.log(message, Level.FINER, t);
	}

	public static void finest(String message) {
		finest(message, null);
	}

	public static void finest(String message, Throwable t) {
		logger.log(message, Level.FINEST, t);
	}

	public static void error(String message) {
		error(message, null);
	}

	public static void error(String message, Throwable t) {
		logger.log(message, Level.SEVERE, t);
	}
}
