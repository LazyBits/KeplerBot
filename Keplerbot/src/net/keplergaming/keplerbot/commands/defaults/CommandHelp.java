package net.keplergaming.keplerbot.commands.defaults;

import java.util.ArrayList;
import java.util.Collections;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.commands.ICommand;
import net.keplergaming.keplerbot.exception.BotException;
import net.keplergaming.keplerbot.permissions.PermissionsManager;
import net.keplergaming.keplerbot.utils.StringUtils;

public class CommandHelp implements ICommand {

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public String[] getCommandAliases() {
		return new String[]{"?"};
	}

	@Override
	public boolean canSenderUseCommand(PermissionsManager permissionsManager, String user) {
		return true;
	}

	@Override
	public void handleCommand(KeplerBotWrapper wrapper, String sender, String[] args) throws BotException {
		if (args.length == 0) {
			ArrayList<String> commandNames = new ArrayList<String>();
			for (String command : wrapper.getCommandManager().getCommands()) {
				if (wrapper.getCommandManager().getCommand(command).canSenderUseCommand(wrapper.getPermissionsManager(), sender)) {
					commandNames.add(command);
				}
			}
			Collections.sort(commandNames);
			wrapper.sendMessage("Your commands: " + StringUtils.joinString(commandNames));
		} else if (args.length == 1) {
			String commandName = args[0];
			if (commandName.startsWith("!")) {
				commandName = commandName.substring(1);
			}
			ICommand command = wrapper.getCommandManager().getCommand(commandName);
			wrapper.sendMessage(command.getCommandUsage());
		} else {
			wrapper.sendMessage(getCommandUsage());
		}
	}

	@Override
	public String getCommandUsage() {
		return "!" + getCommandName() + " [command]";
	}
}
