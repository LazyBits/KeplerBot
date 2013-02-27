package net.keplergaming.keplerbot.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;

public class LogWriter implements ILogListener{

	private BufferedWriter fileoutwrite;

	public LogWriter() {
		FileWriter fstream;
		
		try {
			File file = new File("./log.txt");
			file.getParentFile().mkdirs();
			fstream = new FileWriter(file, true);
			fileoutwrite = new BufferedWriter(fstream);
		} catch(IOException e) { 
			Logger.error("Failed to save log message ", e);
		}
	}

	@Override
	public Level getLoglevel() {
		return Level.ALL;
	}

	@Override
	public void onLog(String message, Level logLevel, Throwable t) {
		try {
			fileoutwrite.write(Logger.logToString(message, logLevel));
			fileoutwrite.newLine();
			if (t != null) {
				PrintWriter printWriter = new PrintWriter(fileoutwrite);
				t.printStackTrace(printWriter);
			}
			fileoutwrite.flush();
		} catch (IOException e) {
			Logger.error("Failed to save log message ", e);
		}
	}

}
