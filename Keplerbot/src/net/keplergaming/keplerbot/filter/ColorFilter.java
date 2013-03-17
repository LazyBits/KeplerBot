package net.keplergaming.keplerbot.filter;

import java.util.HashMap;

import net.keplergaming.keplerbot.config.Configuration;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

public class ColorFilter extends Filter{

	public ColorFilter(Configuration config) {
		setDisabled(config.getBoolean(Configuration.COLOR_FILTER[0], Boolean.parseBoolean(Configuration.COLOR_FILTER[1])));
		this.config = config;
		userMap = new HashMap<String, String>();
	}

	@Override
	public String getFilterName() {
		return "color";
	}

	@Override
	public boolean shouldUserBeFiltered(PircBotX bot, User sender, Channel channel) {
		return true;
	}

	@Override
	public boolean shouldRemoveMessage(PircBotX bot, User sender, Channel channel, String message) {
		if (userMap.containsKey(sender.getNick().toLowerCase()) && !isColorAllowed(userMap.get(sender.getNick().toLowerCase()))) {
			return true;
		}
		return false;
	}

	@Override
	public void onPrivateMessage(PircBotX bot, String message) {
		String args[] = message.split(" ");

		if (args[0].equalsIgnoreCase("USERCOLOR")) {
			userMap.put(args[1].toLowerCase(), args[2]);
		}
	}

	public boolean isColorAllowed(String colorCode) {
		return config.getBoolean(colorCode, true);
	}

	private HashMap<String, String> userMap;
	private Configuration config;
}
