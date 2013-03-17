package net.keplergaming.keplerbot.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class Logger {

	public Logger(String loggerName) {
		listeners = new ArrayList<ILogListener>();
		this.loggerName = loggerName;
	}

	private String loggerName;
	private ArrayList<ILogListener> listeners;

	public boolean addListener(ILogListener listener) {
		return listeners.add(listener);
	}

	public boolean removeListener(ILogListener listener) {
		return listeners.remove(listener);
	}

	public void log(String message, Level logLevel, Throwable t) {
		if (logLevel == Level.SEVERE) {
			System.err.println(logToString(message, logLevel) + throwableToString(t));
		} else {
			System.out.println(logToString(message, logLevel) + throwableToString(t));
		}

		for (ILogListener listener : listeners) {
			if (listener.shouldLog(logLevel)) {
				listener.onLog(logToString(message, logLevel), message, logLevel, t);
			}
		}
	}

	public String logToString(String message, Level logLevel) {
		if (logLevel != null) {
			return "[" + loggerName + "]" + getDateString() + "[" + logLevel.toString() + "] " + message;
		}

		return getDateString() + message;
	}

	public static String throwableToString(Throwable t) {
		if (t == null) {
			return "";
		}

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		pw.flush();
		StringTokenizer tokenizer = new StringTokenizer(sw.toString(), "\r\n");
		StringBuilder builder = new StringBuilder();

		while (tokenizer.hasMoreTokens()) {
			builder.append("\n" + tokenizer.nextToken());
		}

		return builder.toString();
	}

	public String getDateString() {
		return "[" + format.format(new Date()) + "]";
	}

	private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
}
