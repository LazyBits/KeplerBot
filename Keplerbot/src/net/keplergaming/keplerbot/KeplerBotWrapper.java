package net.keplergaming.keplerbot;

import net.keplergaming.keplerbot.commands.CommandManager;
import net.keplergaming.keplerbot.config.ConfigConstants;
import net.keplergaming.keplerbot.config.Configuration;
import net.keplergaming.keplerbot.filters.FilterManager;
import net.keplergaming.keplerbot.filters.defaults.CapsFilter;
import net.keplergaming.keplerbot.filters.defaults.ColorFilter;
import net.keplergaming.keplerbot.filters.defaults.LinkFilter;
import net.keplergaming.keplerbot.gui.MainFrame;
import net.keplergaming.keplerbot.gui.StreamLogPannel;
import net.keplergaming.keplerbot.logger.StreamLogger;
import net.keplergaming.keplerbot.permissions.PermissionsManager;
import net.keplergaming.keplerbot.proxy.SocksSocketFactory;
import net.keplergaming.keplerbot.version.Version;

import org.pircbotx.Channel;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.DisconnectEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.events.UnknownEvent;

public class KeplerBotWrapper extends ListenerAdapter<KeplerBot> implements Runnable, Listener<KeplerBot> {

	public KeplerBotWrapper(StreamLogPannel pannel, String streamer, boolean joinMessage) {
		this.streamer = streamer;
		this.pannel = pannel;
		this.displayJoinMessage = joinMessage;

		thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}

	@Override
	public void run() {
		config = new Configuration("./configs/config_" + streamer + ".properties");
		config.loadConfig();

		muteAll = config.getBoolean(ConfigConstants.MUTE_ALL.getKey(), (boolean) ConfigConstants.MUTE_ALL.getDefaultValue());
		muteErrors = config.getBoolean(ConfigConstants.MUTE_ERRORS.getKey(), (boolean) ConfigConstants.MUTE_ERRORS.getDefaultValue());
		muteWarnings = config.getBoolean(ConfigConstants.MUTE_WARNINGS.getKey(), (boolean) ConfigConstants.MUTE_WARNINGS.getDefaultValue());

		logger = new StreamLogger(streamer);
		logger.getLogger().addListener(pannel);
		bot = new KeplerBot(logger);

		bot.setVerbose(true);
		bot.setName(MainFrame.getInstance().getConfig().getString(ConfigConstants.USERNAME.getKey(), (String) ConfigConstants.USERNAME.getDefaultValue()));
		bot.setLogin(MainFrame.getInstance().getConfig().getString(ConfigConstants.USERNAME.getKey(), (String) ConfigConstants.USERNAME.getDefaultValue()));
		bot.setVersion("KeplerBot " + Version.VERSION);
		bot.setAutoReconnect(true);
		bot.setAutoReconnectChannels(true);

		permissionsManager = new PermissionsManager(this);
		commandManager = new CommandManager(this);
		filterManager = new FilterManager(this);
		filterManager.registerFilter(new LinkFilter());
		filterManager.registerFilter(new ColorFilter(config));
		filterManager.registerFilter(new CapsFilter());

		bot.getListenerManager().addListener(permissionsManager);
		bot.getListenerManager().addListener(commandManager);
		bot.getListenerManager().addListener(filterManager);
		bot.getListenerManager().addListener(this);

		try {
			if(MainFrame.getInstance().getConfig().getBoolean(ConfigConstants.PROXY.getKey(), (boolean) ConfigConstants.PROXY.getDefaultValue())) {
				logger.info("Using proxy: " + MainFrame.getInstance().getConfig().getString(ConfigConstants.PROXYSERVER.getKey(), (String) ConfigConstants.PROXYSERVER.getDefaultValue()));
				bot.connect(streamer + ".jtvirc.com", 6667, MainFrame.getInstance().getConfig().getString(ConfigConstants.PASSWORD.getKey(), (String) ConfigConstants.PASSWORD.getDefaultValue()), new SocksSocketFactory(MainFrame.getInstance().getConfig().getString(ConfigConstants.PROXYSERVER.getKey(), (String) ConfigConstants.PROXYSERVER.getDefaultValue())));
			} else {
				bot.connect(streamer + ".jtvirc.com", 6667, MainFrame.getInstance().getConfig().getString(ConfigConstants.PASSWORD.getKey(), (String) ConfigConstants.PASSWORD.getDefaultValue()));
			}

			bot.joinChannel("#" + streamer);

			if (displayJoinMessage) {
				bot.sendMessage(getChannel(), String.format(MainFrame.getInstance().getConfig().getString(ConfigConstants.JOIN_MESSAGE.getKey(), (String) ConfigConstants.JOIN_MESSAGE.getDefaultValue()), MainFrame.getInstance().getConfig().getString(ConfigConstants.BOT_NAME.getKey(), (String) ConfigConstants.BOT_NAME.getDefaultValue())));
			}
		} catch (Exception e) {
			logger.error("Could not connect to server", e);
		}
	}

	private boolean displayJoinMessage;
	private String streamer;
	private KeplerBot bot;
	private Configuration config;
	private Thread thread;

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

	public void sendMessage(String message) {
		if (!muteAll) {
			bot.sendMessage(getChannel(), message);
		}
	}

	public void sendError(String message) {
		if (!(muteAll || muteErrors)) {
			bot.sendMessage(getChannel(), message);
		}
	}

	public void sendWarning(String message) {
		if (!(muteAll || muteWarnings)) {
			bot.sendMessage(getChannel(), message);
		}
	}

	private boolean muteAll;
	private boolean muteErrors;
	private boolean muteWarnings;

	public void muteAll(boolean muted) {
		muteAll = muted;
		logger.fine("Mute All set to " + muted);
	}

	public void muteErrors(boolean muted) {
		muteErrors = muted;
		logger.fine("Mute Errors set to " + muted);
	}

	public void muteWarnings(boolean muted) {
		muteWarnings = muted;
		logger.fine("Mute Errors set to " + muted);
	}

	@Override
	public void onMessage(MessageEvent<KeplerBot> event) {
		logger.info("<" + event.getUser().getNick() + "> " + event.getMessage());
	}

	@Override
	public void onPrivateMessage(PrivateMessageEvent<KeplerBot> event) {
		logger.info("<" + event.getUser().getNick() + "> " + event.getMessage());

		if (event.getUser().getNick().equals("jtv") && event.getMessage().equalsIgnoreCase("Login failed.")) {
			dispose(false);
		}
	}

	@Override
	public void onUnknown(UnknownEvent<KeplerBot> event) {
		logger.fine(event.getLine());
	}

	public void dispose(boolean showMessage) {
		disconnectFlag = true;
		permissionsManager.stopThread();

		if (bot.isConnected()) {
			if (showMessage) {
				bot.sendMessage(getChannel(), String.format(MainFrame.getInstance().getConfig().getString(ConfigConstants.LEAVE_MESSAGE.getKey(), (String) ConfigConstants.LEAVE_MESSAGE.getDefaultValue()), MainFrame.getInstance().getConfig().getString(ConfigConstants.BOT_NAME.getKey(), (String) ConfigConstants.BOT_NAME.getDefaultValue())));
			}

			bot.setAutoReconnect(false);
			bot.disconnect();
		}

		thread = null;
	}

	@Override
	public void onDisconnect(DisconnectEvent<KeplerBot> event) throws Exception {
		if (disconnectFlag) {
			disconnectFlag = false;
			bot.shutdown(true);
		}
	}

	public boolean disconnectFlag = false;
}
