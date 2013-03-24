package net.keplergaming.keplerbot.commands;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.exception.BotException;
import net.keplergaming.keplerbot.permissions.PermissionsManager;

public interface ICommand{

	public String getCommandName();

	public String[] getCommandAliases();

	public boolean canSenderUseCommand(PermissionsManager permissionsManager, String sender);

	public void handleCommand(KeplerBotWrapper wrapper, String sender, String[] args) throws BotException;

	public String getCommandUsage();
}
