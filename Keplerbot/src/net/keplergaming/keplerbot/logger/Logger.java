package net.keplergaming.keplerbot.logger;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class Logger {

	private static ArrayList<ILogListener> listeners;
	private static PrintStream console;

	public static boolean addListener(ILogListener listener) {
		return listeners.add(listener);
	}

	public static boolean removeListener(ILogListener listener) {
		return listeners.remove(listener);
	}

	static {
		console = System.out;
		System.setOut(new PrintStream(new LoggingOutputStream(), true));
		listeners = new ArrayList<ILogListener>();
		addListener(new LogWriter());
	}

	public static void log(String message, Level logLevel, Throwable t) {
		if (logLevel == Level.SEVERE) {
			System.err.println(logToString(message, logLevel) + throwableToString(t));
		} else {
			console.println(logToString(message, logLevel) + throwableToString(t));
		}
		
		for (ILogListener listener : listeners) {
			if(logLevel.intValue() <= listener.getLoglevel().intValue() || listener.getLoglevel().equals(Level.ALL)) {
				listener.onLog(message, logLevel, t);
			}
		}
	}

	public static void info(String message) {
		info(message, null);
	}

	public static void info(String message, Throwable t) {
		log(message, Level.INFO, t);
	}

	public static void warning(String message) {
		warning(message, null);
	}

	public static void warning(String message, Throwable t) {
		log(message, Level.WARNING, t);
	}

	public static void fine(String message) {
		fine(message, null);
	}

	public static void fine(String message, Throwable t) {
		log(message, Level.FINE, t);
	}

	public static void finer(String message) {
		finer(message, null);
	}

	public static void finer(String message, Throwable t) {
		log(message, Level.FINER, t);
	}

	public static void finest(String message) {
		finest(message, null);
	}

	public static void finest(String message, Throwable t) {
		log(message, Level.FINEST, t);
	}

	public static void error(String message) {
		error(message, null);
	}

	public static void error(String message, Throwable t) {
		log(message, Level.SEVERE, t);
	}

	public static String logToString(String message, Level logLevel) {
		if (logLevel != null) {
			return getDateString() + "[" + logLevel.toString() + "] " + message;
		}
		
		return getDateString() + message;
	}
	
	private static String throwableToString(Throwable t) {
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

	private static String getDateString() {
		return "[" + format.format(new Date()) + "]";
	}
	
	private static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
}
