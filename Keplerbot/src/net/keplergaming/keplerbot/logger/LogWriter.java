package net.keplergaming.keplerbot.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

public class LogWriter implements ILogListener {

	private BufferedWriter fileoutwrite;

	public LogWriter(String fileName) {
		FileWriter fstream;

		try {
			File file;
			if (fileName != null) {
				file = new File("./logs/" + getDate() + "_" + fileName + ".txt");
			} else {
				file = new File("./log.txt");
			}
			file.getParentFile().mkdirs();
			fstream = new FileWriter(file, true);
			fileoutwrite = new BufferedWriter(fstream);
		} catch (IOException e) {
			MainLogger.error("Failed to save log message ", e);
		}
	}

	@Override
	public boolean shouldLog(Level lvl) {
		return true;
	}

	@Override
	public void onLog(String formattedMessage, String message, Level logLevel, Throwable t) {
		try {
			fileoutwrite.write(formattedMessage);
			fileoutwrite.newLine();
			if (t != null) {
				PrintWriter printWriter = new PrintWriter(fileoutwrite);
				t.printStackTrace(printWriter);
			}
			fileoutwrite.flush();
		} catch (IOException e) {
			MainLogger.error("Failed to save log message ", e);
		}
	}

	public String getDate() {
		return format.format(new Date());
	}

	private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
}
