package net.keplergaming.keplerbot;

import org.pircbotx.Channel;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.DisconnectEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.events.UnknownEvent;

import net.keplergaming.keplerbot.commands.CommandManager;
import net.keplergaming.keplerbot.config.Configuration;
import net.keplergaming.keplerbot.gui.StreamLogPannel;
import net.keplergaming.keplerbot.logger.StreamLogger;
import net.keplergaming.keplerbot.version.Version;

public class KeplerBotWrapper extends ListenerAdapter<KeplerBot> {

	public KeplerBotWrapper(StreamLogPannel pannel, Configuration config, String streamer, boolean joinMessage) {
		this.streamer = streamer;
		this.pannel = pannel;
		this.config = config;
		logger = new StreamLogger(streamer);
		logger.getLogger().addListener(pannel);
		bot = new KeplerBot(logger);

		bot.setVerbose(true);
		bot.setName(config.getString(Configuration.USERNAME[0], Configuration.USERNAME[1]));
		bot.setLogin(config.getString(Configuration.USERNAME[0], Configuration.USERNAME[1]));
		bot.setVersion("KeplerBot " + Version.getVersion());
		bot.setAutoReconnect(true);
		bot.setAutoReconnectChannels(true);

		commandManager = new CommandManager();
		bot.getListenerManager().addListener(commandManager);
		bot.getListenerManager().addListener(this);

		try {
			bot.connect(streamer + ".jtvirc.com", 6667, config.getString(Configuration.PASSWORD[0], Configuration.PASSWORD[1]));

			bot.joinChannel("#" + streamer);

			if (joinMessage) {
				bot.sendMessage(getChannel(), config.getString(Configuration.JOIN_MESSAGE[0], Configuration.JOIN_MESSAGE[1]));
			}
		} catch (Exception e) {
			logger.error("Could not connect to server", e);
		}
	}

	private String streamer;
	private KeplerBot bot;
	private Configuration config;

	public KeplerBot getBot() {
		return bot;
	}

	private CommandManager commandManager;

	public CommandManager getCommandManager() {
		return commandManager;
	}

	private StreamLogger logger;

	public StreamLogger getStreamLogger() {
		return logger;
	}

	private StreamLogPannel pannel;

	public StreamLogPannel getPannel() {
		return pannel;
	}

	public Channel getChannel() {
		return bot.getChannel("#" + streamer);
	}

	public Configuration getConfig() {
		return config;
	}

	@Override
	public void onMessage(MessageEvent<KeplerBot> event) {
		logger.info(event.getUser().getNick() + " " + event.getMessage());
	}

	@Override
	public void onPrivateMessage(PrivateMessageEvent<KeplerBot> event) {
		logger.info(event.getUser().getNick() + " " + event.getMessage());

		if (event.getUser().getNick().equals("jtv") && event.getMessage().equalsIgnoreCase("Login failed.")) {
			dispose(false);
		}
	}

	@Override
	public void onUnknown(UnknownEvent<KeplerBot> event) {
		logger.info(event.getLine());
	}

	public void dispose(boolean showMessage) {
		try {
			disconnectFlag = true;

			if (showMessage) {
				bot.sendMessage(getChannel(), config.getString(Configuration.LEAVE_MESSAGE[0], Configuration.LEAVE_MESSAGE[1]));
			}

			bot.setAutoReconnect(false);
			bot.disconnect();
		} catch (Exception e) {
		}
	}

	public void onDisconnect(DisconnectEvent<KeplerBot> event) throws Exception {
		if (disconnectFlag) {
			disconnectFlag = false;
			bot.shutdown(true);
		}
	}

	public boolean disconnectFlag = false;
}
