package net.keplergaming.keplerbot;

import net.keplergaming.keplerbot.logger.StreamLogger;

import org.pircbotx.PircBotX;

public class KeplerBot extends PircBotX {

	public KeplerBot(StreamLogger logger) {
		this.logger = logger;
	}

	@Override
	public void log(String line) {
		if (line.startsWith("*** ")) {
			logger.info(line.replace("*** ", ""));
		} else if (line.startsWith(">>>PRIVMSG #")){
			logger.info(line.replaceAll(">>>PRIVMSG #.* :", "<" + getName() + "> "));
		}

		logger.fine(line);
	}

	@Override
	public void logException(Throwable t) {
		logger.error("Exception in the inner bot", t);
	}

	private StreamLogger logger;
}
