package net.keplergaming.keplerbot.logger;

import java.util.logging.Level;

public class StreamLogger {

	public StreamLogger(String streamer) {
		logger = new Logger(streamer.toUpperCase());
		logger.addListener(new LogWriter(streamer));
	}

	private Logger logger;

	public void info(String message) {
		info(message, null);
	}

	public void info(String message, Throwable t) {
		logger.log(message, Level.INFO, t);
	}

	public void warning(String message) {
		warning(message, null);
	}

	public void warning(String message, Throwable t) {
		logger.log(message, Level.WARNING, t);
	}

	public void fine(String message) {
		fine(message, null);
	}

	public void fine(String message, Throwable t) {
		logger.log(message, Level.FINE, t);
	}

	public void finer(String message) {
		finer(message, null);
	}

	public void finer(String message, Throwable t) {
		logger.log(message, Level.FINER, t);
	}

	public void finest(String message) {
		finest(message, null);
	}

	public void finest(String message, Throwable t) {
		logger.log(message, Level.FINEST, t);
	}

	public void error(String message) {
		error(message, null);
	}

	public void error(String message, Throwable t) {
		logger.log(message, Level.SEVERE, t);
	}

	public Logger getLogger() {
		return logger;
	}
}
