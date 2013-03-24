package net.keplergaming.keplerbot.commands.defaults;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.commands.CounterCommand;
import net.keplergaming.keplerbot.commands.ICommand;
import net.keplergaming.keplerbot.exception.BotException;
import net.keplergaming.keplerbot.permissions.PermissionsManager;

public class CommandCounter implements ICommand {

	@Override
	public String getCommandName() {
		return "counter";
	}

	@Override
	public String[] getCommandAliases() {
		return null;
	}

	@Override
	public boolean canSenderUseCommand(PermissionsManager permissionsManager, String user) {
		return permissionsManager.isDeveloper(user) || permissionsManager.isModerator(user) || permissionsManager.isStreamer(user) || permissionsManager.isConsole(user);
	}

	@Override
	public void handleCommand(KeplerBotWrapper wrapper, String sender, String[] args) throws BotException {
		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("set")) {
				String commandName = args[1];
				if (commandName.startsWith("!")) {
					commandName = commandName.substring(1);
				}
				ICommand newCommand = new CounterCommand(commandName);
				wrapper.getCommandManager().registerCommand(newCommand);
				wrapper.sendMessage("Counter !" + commandName + " set");
			} else {
				String commandName = args[1];
				if (commandName.startsWith("!")) {
					commandName = commandName.substring(1);
				}
				wrapper.getCommandManager().unRegisterCommand(commandName);
				wrapper.sendMessage("Counter !" + commandName + " removed");
			}
		} else {
			wrapper.sendMessage(getCommandUsage());
		}
	}

	@Override
	public String getCommandUsage() {
		return "!" + getCommandName() + " [set|unset] [command]";
	}
}
