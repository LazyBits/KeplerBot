package net.keplergaming.keplerbot.filters;

import net.keplergaming.keplerbot.KeplerBot;
import net.keplergaming.keplerbot.KeplerBotWrapper;

import org.pircbotx.Channel;
import org.pircbotx.User;

public class CapsFilter extends Filter{

	@Override
	public String getFilterName() {
		return "caps";
	}

	@Override
	public boolean shouldRemoveMessage(KeplerBotWrapper wrapper, KeplerBot bot, User sender, Channel channel, String message) {
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

	@Override
	public String getDefaultWarning() {
		return "%s, Don't shout!";
	}
}
