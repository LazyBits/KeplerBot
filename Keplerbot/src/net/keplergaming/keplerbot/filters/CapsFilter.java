package net.keplergaming.keplerbot.filters;

import net.keplergaming.keplerbot.config.Configuration;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

public class CapsFilter extends Filter{

	public CapsFilter(Configuration config) {
		setDisabled(config.getBoolean(Configuration.CAPS_FILTER[0], Boolean.parseBoolean(Configuration.CAPS_FILTER[1])));
	}

	@Override
	public String getFilterName() {
		return "caps";
	}

	@Override
	public boolean shouldUserBeFiltered(PircBotX bot, User sender, Channel channel) {
		return true;
	}

	@Override
	public boolean shouldRemoveMessage(PircBotX bot, User sender, Channel channel, String message) {
		int caps = 0;
		for (int i = 0; i < message.length(); ++i) {
			if (Character.isUpperCase(message.charAt(i))) {
				caps++;
			}
		}

		if (((double)caps / (double)message.length()) > 0.5) {
			return true;
		}
		return false;
	}
}
