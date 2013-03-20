package net.keplergaming.keplerbot.commands.defaults;

import java.util.ArrayList;
import java.util.Collections;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.commands.ICommand;
import net.keplergaming.keplerbot.exception.BotException;
import net.keplergaming.keplerbot.permissions.PermissionsManager;
import net.keplergaming.keplerbot.utils.StringUtils;

import org.pircbotx.Channel;
import org.pircbotx.User;

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
	public boolean canSenderUseCommand(PermissionsManager permissionsManager, User user) {
		return true;
	}

	@Override
	public void handleCommand(KeplerBotWrapper wrapper, User sender, Channel channel, String[] args) {
		if (args.length == 0) {
			ArrayList<String> commandNames = new ArrayList<String>();
			for (String command : wrapper.getCommandManager().getCommands()) {
				try {
					if (wrapper.getCommandManager().getCommand(command).canSenderUseCommand(wrapper.getPermissionsManager(), sender)) {
						commandNames.add(command);
					}
				} catch (BotException e) {
					wrapper.getStreamLogger().warning("Failed to execute command " + getCommandName(), e);
				}
			}
			Collections.sort(commandNames);
			wrapper.sendMessage(channel, "Your commands: " + StringUtils.joinString(commandNames));
		} else if (args.length == 1) {
			try {
				String commandName = args[0];
				if (commandName.startsWith("!")) {
					commandName = commandName.substring(1);
				}
				ICommand command = wrapper.getCommandManager().getCommand(commandName);
				wrapper.sendMessage(channel, command.getCommandUsage());
			} catch (BotException e) {
				wrapper.sendError(channel, e.getMessage());
				wrapper.getStreamLogger().warning("Failed to execute command " + getCommandName(), e);
			}
		}
	}

	@Override
	public String getCommandUsage() {
		return "!" + getCommandName() + " [command]";
	}
}
