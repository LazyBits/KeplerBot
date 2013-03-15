package net.keplergaming.keplerbot.filter;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

public interface IFilter {

	public String getFilterName();

	public boolean shouldUserBeFiltered(PircBotX bot, User sender, Channel channel);

	public boolean shouldRemoveMessage(PircBotX bot, User sender, Channel channel, String message);

	public boolean isDisabled();
}
