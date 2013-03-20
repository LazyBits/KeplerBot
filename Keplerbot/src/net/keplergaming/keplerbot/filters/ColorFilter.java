package net.keplergaming.keplerbot.filters;

import java.util.HashMap;

import net.keplergaming.keplerbot.KeplerBot;
import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.config.Configuration;

import org.pircbotx.Channel;
import org.pircbotx.User;

public class ColorFilter extends Filter{

	public ColorFilter(Configuration config) {
		this.config = config;
		userMap = new HashMap<String, String>();
	}

	@Override
	public String getFilterName() {
		return "color";
	}

	@Override
	public boolean shouldRemoveMessage(KeplerBotWrapper wrapper, KeplerBot bot, User sender, Channel channel, String message) {
		if (userMap.containsKey(sender.getNick().toLowerCase()) && !isColorAllowed(userMap.get(sender.getNick().toLowerCase()))) {
			return true;
		}
		return false;
	}

	@Override
	public void onPrivateMessage(KeplerBot bot, String message) {
		String args[] = message.split(" ");

		if (args[0].equalsIgnoreCase("USERCOLOR")) {
			userMap.put(args[1].toLowerCase(), args[2]);
		}
	}

	@Override
	public String getDefaultWarning() {
		return "%s, Please change your color";
	}

	public boolean isColorAllowed(String colorCode) {
		return config.getBoolean(colorCode, true);
	}

	private HashMap<String, String> userMap;
	private Configuration config;
}
