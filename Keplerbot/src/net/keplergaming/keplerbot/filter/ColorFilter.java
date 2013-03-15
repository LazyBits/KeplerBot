package net.keplergaming.keplerbot.filter;

import java.util.HashMap;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

public class ColorFilter extends Filter{

	public ColorFilter() {
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
		return colorCode.equalsIgnoreCase("#00FF7F");
	}

	private HashMap<String, String> userMap;
}
