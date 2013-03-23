package net.keplergaming.keplerbot.commands.defaults;

import java.util.ArrayList;

import org.pircbotx.Channel;
import org.pircbotx.User;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.commands.ICommand;
import net.keplergaming.keplerbot.config.Configuration;
import net.keplergaming.keplerbot.permissions.PermissionsManager;
import net.keplergaming.keplerbot.utils.StringUtils;

public class CommandNews implements ICommand, Runnable {

	public CommandNews(KeplerBotWrapper wrapper) {
		this.wrapper = wrapper;

		newsConfig = new Configuration("./news/news_" + wrapper.getStreamer() + ".properties");
		newsConfig.loadConfig();
		delay = newsConfig.getInteger("delay", 300);
		on = newsConfig.getBoolean("on", true);
		newsConfig.saveConfig();
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
	public boolean canSenderUseCommand(PermissionsManager permissionsManager, User user) {
		return permissionsManager.isDeveloper(user.getNick()) || permissionsManager.isModerator(user.getNick()) || permissionsManager.isStreamer(user.getNick());
	}

	@Override
	public void handleCommand(KeplerBotWrapper wrapper, User sender, Channel channel, String[] args) {
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("on")) {
				on = true;
				save();
				wrapper.sendMessage(channel, "News turned on");
			} else if (args[0].equalsIgnoreCase("off")) {
				on = false;
				save();
				wrapper.sendMessage(channel, "News turned off");
			} else if (args[0].equalsIgnoreCase("clear")) {
				newsList.clear();
				save();
				wrapper.sendMessage(channel, "News cleared");
			} else {
				wrapper.sendMessage(channel, getCommandUsage());
			}
		} else if (args.length == 2 && args[0].equalsIgnoreCase("delay")) {
			try {
				delay = Integer.parseInt(args[1]);
				save();
				wrapper.sendMessage(channel, "News delay set to " + delay + " seconds");
			} catch (Exception e) {
				wrapper.sendMessage(channel, e.getMessage());
				wrapper.getStreamLogger().warning("Failed to execute command " + getCommandName(), e);
			}
		} else if (args.length >= 2 && args[0].equalsIgnoreCase("add")) {
			newsList.add(StringUtils.joinString(StringUtils.dropFirstString(args)));
			save();
			wrapper.sendMessage(channel, "News added");
		} else {
			wrapper.sendMessage(channel, getCommandUsage());
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

	public void save() {
		newsConfig.setInteger("delay", delay);
		newsConfig.setBoolean("on", on);

		for (int i = 0; i < newsList.size(); i++) {
			newsConfig.setString("news_" + i, newsList.get(i));
		}
		newsConfig.saveConfig();
	}

	public void load() {
		newsList = new ArrayList<String>();
		for (Object key : newsConfig.getProperties().keySet()) {
			if (((String)key).startsWith("news_")) {
				newsList.add(newsConfig.getString((String) key, "This is a default message"));
			}
		}
	}

	private Configuration newsConfig;
	private int newsIndex;
	private ArrayList<String> newsList;
	private Thread newsThread;
	private boolean on;
	private int delay;
	private KeplerBotWrapper wrapper;
}
