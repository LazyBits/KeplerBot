package net.keplergaming.keplerbot.logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;

public class LoggingOutputStream extends ByteArrayOutputStream {

	private String lineSeparator;

	public LoggingOutputStream() {
		super();
		lineSeparator = System.getProperty("line.separator");
	}

	public void flush() throws IOException {

		String record;
		synchronized (this) {
			super.flush();
			record = this.toString();
			super.reset();

			if (record.length() == 0 || record.equals(lineSeparator)) {
				return;
			}

			Logger.log(record, Level.ALL, null);
		}
	}
}