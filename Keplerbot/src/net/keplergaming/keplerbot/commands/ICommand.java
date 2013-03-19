package net.keplergaming.keplerbot.commands;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.permissions.PermissionsManager;

import org.pircbotx.Channel;
import org.pircbotx.User;

public interface ICommand{

	public String getCommandName();

	public String[] getCommandAliases();

	public boolean canSenderUseCommand(PermissionsManager permissionsManager, User user);

	public void handleCommand(KeplerBotWrapper wrapper, User sender, Channel channel, String[] args);

	public String getCommandUsage();
}
