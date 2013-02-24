package net.keplerbot.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.keplerbot.gui.MainFrame;
import net.keplerbot.utils.TimeUtils;

public class KeplerLogger{

	private Logger logger;
	
	private String stream;
	
	private static Map<String, KeplerLogger> instances = new HashMap<String, KeplerLogger>();
	
	private BufferedWriter fileoutwrite = null;
	
	public KeplerLogger(String channel) {
		if(instances.containsKey(channel)) {
			new Exception("Logger already exists").printStackTrace();
		}else{
			instances.put(channel, this);
			stream = channel.replace("#", "");
			logger = Logger.getLogger("KeplerLogger");
			logger.setLevel(Level.ALL);
			FileWriter fstream;
			
			try {
				File file = new File("./logs/log_" + stream + ".txt");
				file.getParentFile().mkdir();
				fstream = new FileWriter(file, true);
				fileoutwrite = new BufferedWriter(fstream);
			} catch(IOException e) { 
				e.printStackTrace(); 
			}
			info("-----------------------------------------------------------");
		}
	}
	
	public static KeplerLogger getInstance(String channel) {
		if (!instances.containsKey(channel)) {
			new KeplerLogger(channel);
		}
		return instances.get(channel);
	}
	
	private void doLog(String message, Throwable t) {
		String logMessage =  "[" + TimeUtils.getTime() + "] " + "[" + stream + "] " +  message;

		if(stream.equalsIgnoreCase("Main")) {
			MainFrame.log(stream, "[" + TimeUtils.getTime() + "] " +  message);
		}else{
			MainFrame.log(stream, "[" + TimeUtils.getTime() + "] " + "[" + stream + "] " +  message);
		}
        
		try {
			fileoutwrite.write(logMessage + "\r\n");
			fileoutwrite.flush();
		} catch (IOException e) { }

		if (t != null) {
			PrintWriter printWriter = new PrintWriter(fileoutwrite);
			t.printStackTrace(printWriter);
	        StringWriter sw = new StringWriter();
	        PrintWriter pw = new PrintWriter(sw);
	        t.printStackTrace(pw);
	        pw.flush();
	        StringTokenizer tokenizer = new StringTokenizer(sw.toString(), "\r\n");
	        while (tokenizer.hasMoreTokens()) {
	    		if(stream.equalsIgnoreCase("Main")) {
	    			MainFrame.log(stream, "[" + TimeUtils.getTime() + "] " +  tokenizer.nextToken());
	    		}else{
	    			MainFrame.log(stream, "[" + TimeUtils.getTime() + "] " + "[" + stream + "] " +  tokenizer.nextToken());
	    		}
	        }
		}
		
	}
	
	public void log(String message, Level level, Throwable t) {
		doLog(message, t);
		logger.log(level, message, t);
	}
	
	public void info(String message) {
		log(message, Level.INFO, null);
	}
	
	public void warn(String message) {
		log(message, Level.WARNING, null);
	}
	
	public void error(String message) {
		log(message, Level.SEVERE, null);
	}
	
	public void info(String message, Throwable t) {
		log(message, Level.INFO, t);
	}
	
	public void warn(String message, Throwable t) {
		log(message, Level.WARNING, t);
	}
	
	public void error(String message, Throwable t) {
		log(message, Level.SEVERE, t);
	}
}
