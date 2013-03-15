package net.keplergaming.keplerbot.filter;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

public class Filter implements IFilter{

	@Override
	public boolean isDisabled() {
		return disabled;
	}

	private boolean disabled = false;

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	@Override
	public String getFilterName() {
		return null;
	}

	@Override
	public boolean shouldUserBeFiltered(PircBotX bot, User sender, Channel channel) {
		return false;
	}

	@Override
	public boolean shouldRemoveMessage(PircBotX bot, User sender, Channel channel, String message) {
		return false;
	}

	@Override
	public void onPrivateMessage(PircBotX bot, String message) {
	}
}
