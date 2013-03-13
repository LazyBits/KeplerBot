package net.keplergaming.keplerbot.logger;

import java.util.logging.Level;

public interface ILogListener {

	public Level getLoglevel();

	public void onLog(String formattedMessage, String message, Level logLevel, Throwable t);

}
