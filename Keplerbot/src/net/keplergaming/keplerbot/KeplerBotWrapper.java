package net.keplergaming.keplerbot;

import net.keplergaming.keplerbot.commands.CommandManager;
import net.keplergaming.keplerbot.config.Configuration;
import net.keplergaming.keplerbot.filters.CapsFilter;
import net.keplergaming.keplerbot.filters.ColorFilter;
import net.keplergaming.keplerbot.filters.FilterManager;
import net.keplergaming.keplerbot.filters.LinkFilter;
import net.keplergaming.keplerbot.gui.MainFrame;
import net.keplergaming.keplerbot.gui.StreamLogPannel;
import net.keplergaming.keplerbot.logger.StreamLogger;
import net.keplergaming.keplerbot.permissions.PermissionsManager;
import net.keplergaming.keplerbot.version.Version;

import org.pircbotx.Channel;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.DisconnectEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.events.UnknownEvent;

public class KeplerBotWrapper extends ListenerAdapter<KeplerBot> implements Runnable{

	public KeplerBotWrapper(StreamLogPannel pannel, String streamer, boolean joinMessage) {
		this.streamer = streamer;
		this.pannel = pannel;
		this.displayJoinMessage = joinMessage;
	}

	@Override
	public void run() {
		config = new Configuration("./configs/config_" + streamer + ".properties");
		config.loadConfig();
		logger = new StreamLogger(streamer);
		logger.getLogger().addListener(pannel);
		bot = new KeplerBot(logger);

		bot.setVerbose(true);
		bot.setName(MainFrame.getInstance().getConfig().getString(Configuration.USERNAME[0], Configuration.USERNAME[1]));
		bot.setLogin(MainFrame.getInstance().getConfig().getString(Configuration.USERNAME[0], Configuration.USERNAME[1]));
		bot.setVersion("KeplerBot " + Version.getVersion());
		bot.setAutoReconnect(true);
		bot.setAutoReconnectChannels(true);

		permissionsManager = new PermissionsManager(this);
		commandManager = new CommandManager(this);
		filterManager = new FilterManager(this);
		filterManager.registerFilter(new LinkFilter(config));
		filterManager.registerFilter(new ColorFilter(config));
		filterManager.registerFilter(new CapsFilter(config));

		bot.getListenerManager().addListener(permissionsManager);
		bot.getListenerManager().addListener(commandManager);
		bot.getListenerManager().addListener(filterManager);
		bot.getListenerManager().addListener(this);

		try {
			bot.connect(streamer + ".jtvirc.com", 6667, MainFrame.getInstance().getConfig().getString(Configuration.PASSWORD[0], Configuration.PASSWORD[1]));

			bot.joinChannel("#" + streamer);

			if (displayJoinMessage) {
				bot.sendMessage(getChannel(), MainFrame.getInstance().getConfig().getString(Configuration.JOIN_MESSAGE[0], Configuration.JOIN_MESSAGE[1]));
			}
		} catch (Exception e) {
			logger.error("Could not connect to server", e);
		}
	}

	private boolean displayJoinMessage;
	private String streamer;
	private KeplerBot bot;
	private Configuration config;

	public KeplerBot getBot() {
		return bot;
	}

	private PermissionsManager permissionsManager;
	private FilterManager filterManager;
	private CommandManager commandManager;

	public CommandManager getCommandManager() {
		return commandManager;
	}

	public FilterManager getFilterManager() {
		return filterManager;
	}

	public PermissionsManager getPermissionsManager() {
		return permissionsManager;
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

	public String getStreamer() {
		return streamer;
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
			permissionsManager.stopThread();

			if (showMessage) {
				bot.sendMessage(getChannel(), MainFrame.getInstance().getConfig().getString(Configuration.LEAVE_MESSAGE[0], Configuration.LEAVE_MESSAGE[1]));
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
