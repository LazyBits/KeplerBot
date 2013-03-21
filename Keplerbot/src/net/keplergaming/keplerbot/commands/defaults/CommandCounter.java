package net.keplergaming.keplerbot.commands.defaults;

import org.pircbotx.Channel;
import org.pircbotx.User;

import net.keplergaming.keplerbot.KeplerBotWrapper;
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
	public boolean canSenderUseCommand(PermissionsManager permissionsManager, User user) {
		return permissionsManager.isDeveloper(user.getNick()) || permissionsManager.isModerator(user.getNick()) || permissionsManager.isStreamer(user.getNick());
	}

	@Override
	public void handleCommand(KeplerBotWrapper wrapper, User sender,Channel channel, String[] args) {
		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("set")) {
				try {
					String commandName = args[1];
					if (commandName.startsWith("!")) {
						commandName = commandName.substring(1);
					}
					ICommand newCommand = new CalculaterCommand(commandName);
					wrapper.getCommandManager().registerCommand(newCommand);
					wrapper.sendMessage(channel, "Counter !" + commandName + " set");
				} catch (BotException e) {
					wrapper.sendWarning(channel, e.getMessage());
					wrapper.getStreamLogger().warning("Failed to execute command " + getCommandName(), e);
				}
			} else {
				try {
					String commandName = args[1];
					if (commandName.startsWith("!")) {
						commandName = commandName.substring(1);
					}
					wrapper.getCommandManager().unRegisterCommand(commandName);
					wrapper.sendMessage(channel, "Counter !" + commandName + " removed");
				} catch (BotException e) {
					wrapper.sendWarning(channel, e.getMessage());
					wrapper.getStreamLogger().warning("Failed to execute command " + getCommandName(), e);
				}
			}
		} else {
			wrapper.sendMessage(channel, getCommandUsage());
		}
	}

	@Override
	public String getCommandUsage() {
		return "!" + getCommandName() + " [set|unset] [command]";
	}
}
