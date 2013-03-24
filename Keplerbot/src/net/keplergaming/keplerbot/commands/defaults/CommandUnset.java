package net.keplergaming.keplerbot.commands.defaults;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.commands.ICommand;
import net.keplergaming.keplerbot.exception.BotException;
import net.keplergaming.keplerbot.permissions.PermissionsManager;

public class CommandUnset implements ICommand {

	@Override
	public String getCommandName() {
		return "unset";
	}

	@Override
	public String[] getCommandAliases() {
		return new String[]{"remove"};
	}

	@Override
	public boolean canSenderUseCommand(PermissionsManager permissionsManager, String user) {
		return permissionsManager.isDeveloper(user) || permissionsManager.isModerator(user) || permissionsManager.isStreamer(user) || permissionsManager.isConsole(user);
	}

	@Override
	public void handleCommand(KeplerBotWrapper wrapper, String sender, String[] args) throws BotException {
		if (args.length == 1) {
			String commandName = args[0];
			if (commandName.startsWith("!")) {
				commandName = commandName.substring(1);
			}
			wrapper.getCommandManager().unRegisterCommand(commandName);
			wrapper.sendMessage("Command !" + commandName + " removed");
		} else {
			wrapper.sendMessage(getCommandUsage());
		}
	}

	@Override
	public String getCommandUsage() {
		return "!" + getCommandName() + "[command]";
	}

}
