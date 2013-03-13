package net.keplergaming.keplerbot.commands;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

public interface ICommand{

	public String getCommandName();

	public String[] getCommandAliases();

	public boolean canSenderUseCommand(PircBotX bot, User sender, Channel channel);

	public void handleCommand(PircBotX bot, User sender, Channel channel, String[] args);
}
