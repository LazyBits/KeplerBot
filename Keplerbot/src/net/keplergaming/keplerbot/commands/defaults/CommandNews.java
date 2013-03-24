package net.keplergaming.keplerbot.commands.defaults;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.commands.ICommand;
import net.keplergaming.keplerbot.config.ConfigConstants;
import net.keplergaming.keplerbot.logger.MainLogger;
import net.keplergaming.keplerbot.permissions.PermissionsManager;
import net.keplergaming.keplerbot.utils.StringUtils;

public class CommandNews implements ICommand, Runnable {

	public CommandNews(KeplerBotWrapper wrapper) {
		this.wrapper = wrapper;

		newsFile = new File("./news/news_" + wrapper.getStreamer() + ".txt");
		delay = wrapper.getConfig().getInteger(ConfigConstants.NEWS_DELAY.getKey(), (int) ConfigConstants.NEWS_DELAY.getDefaultValue());
		on = wrapper.getConfig().getBoolean(ConfigConstants.NEWS_ON.getKey(), (boolean) ConfigConstants.NEWS_ON.getDefaultValue());
		load();

		newsThread = new Thread(this);
		newsThread.start();
	}

	@Override
	public String getCommandName() {
		return "news";
	}

	@Override
	public String[] getCommandAliases() {
		return null;
	}

	@Override
	public boolean canSenderUseCommand(PermissionsManager permissionsManager, String user) {
		return permissionsManager.isDeveloper(user) || permissionsManager.isModerator(user) || permissionsManager.isStreamer(user) || permissionsManager.isConsole(user);
	}

	@Override
	public void handleCommand(KeplerBotWrapper wrapper, String sender, String[] args) {
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("on")) {
				on = true;
				wrapper.getConfig().setBoolean(ConfigConstants.NEWS_ON.getKey(), on);
				wrapper.getConfig().saveConfig();
				wrapper.sendMessage("News turned on");
			} else if (args[0].equalsIgnoreCase("off")) {
				on = false;
				wrapper.getConfig().setBoolean(ConfigConstants.NEWS_ON.getKey(), on);
				wrapper.getConfig().saveConfig();
				wrapper.sendMessage("News turned off");
			} else if (args[0].equalsIgnoreCase("clear")) {
				newsList.clear();
				save();
				wrapper.sendMessage("News cleared");
			} else {
				wrapper.sendMessage(getCommandUsage());
			}
		} else if (args.length == 2 && args[0].equalsIgnoreCase("delay")) {
			try {
				delay = Integer.parseInt(args[1]);
				wrapper.getConfig().setInteger(ConfigConstants.NEWS_DELAY.getKey(), delay);
				wrapper.getConfig().saveConfig();
				wrapper.sendMessage("News delay set to " + delay + " seconds");
			} catch (Exception e) {
				wrapper.sendError(e.getMessage());
				wrapper.getStreamLogger().warning("Failed to execute command " + getCommandName(), e);
			}
		} else if (args.length >= 2 && args[0].equalsIgnoreCase("add")) {
			newsList.add(StringUtils.joinString(StringUtils.dropFirstString(args)));
			save();
			wrapper.sendMessage("News added");
		} else {
			wrapper.sendMessage(getCommandUsage());
		}
	}

	@Override
	public String getCommandUsage() {
		return "!" + getCommandName() + " [on|off|clear|add|delay] [message|seconds]";
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(delay * 1000);

				if (on && !newsList.isEmpty()) {
					newsIndex++;
					if (newsList.size() <= newsIndex) {
						newsIndex = 0;
					}
					wrapper.getBot().sendMessage(wrapper.getChannel(), newsList.get(newsIndex));
				}
			} catch (InterruptedException e) {
				wrapper.getStreamLogger().error("News Thread stopped unexpectedly", e);
			}
		}
	}

	public void load() {
		wrapper.getStreamLogger().fine("Loading news");

		if (newsFile.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(newsFile));
				String line;

				while ((line = br.readLine()) != null) {
					newsList.add(line);
				}
				br.close();
			} catch (IOException e) {
				MainLogger.error("Failed to load presets", e);
			}
		}
	}

	public void save() {
		try {
			MainLogger.fine("Saving presets");
			PrintStream out = new PrintStream(newsFile);

			for (String news : newsList) {
				out.println(news);
			}

			out.flush();
			out.close();
		} catch (IOException e) {
			MainLogger.error("Failed to save presets", e);
		}
	}

	private File newsFile;
	private int newsIndex;
	private ArrayList<String> newsList;
	private Thread newsThread;
	private boolean on;
	private int delay;
	private KeplerBotWrapper wrapper;
}
