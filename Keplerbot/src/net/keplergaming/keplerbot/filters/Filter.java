package net.keplergaming.keplerbot.filters;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

public class Filter{

	public boolean isDisabled() {
		return disabled;
	}

	private boolean disabled = false;

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getFilterName() {
		return null;
	}

	public boolean shouldUserBeFiltered(PircBotX bot, User sender, Channel channel) {
		return false;
	}

	public boolean shouldRemoveMessage(PircBotX bot, User sender, Channel channel, String message) {
		return false;
	}

	public void onPrivateMessage(PircBotX bot, String message) {
	}
}
