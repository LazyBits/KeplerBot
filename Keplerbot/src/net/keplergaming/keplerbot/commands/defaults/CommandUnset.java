package net.keplergaming.keplerbot.commands.defaults;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.commands.ICommand;
import net.keplergaming.keplerbot.exception.BotException;
import net.keplergaming.keplerbot.permissions.PermissionsManager;

import org.pircbotx.Channel;
import org.pircbotx.User;

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
	public boolean canSenderUseCommand(PermissionsManager permissionsManager, User user) {
		return permissionsManager.isDeveloper(user.getNick()) || permissionsManager.isModerator(user.getNick()) || permissionsManager.isStreamer(user.getNick());
	}

	@Override
	public void handleCommand(KeplerBotWrapper wrapper, User sender, Channel channel, String[] args) {
		if (args.length == 1) {
			try {
				String commandName = args[0];
				if (commandName.startsWith("!")) {
					commandName = commandName.substring(1);
				}
				wrapper.getCommandManager().unRegisterCommand(commandName);
				wrapper.sendMessage(channel, "Command !" + commandName + " removed");
			} catch (BotException e) {
				wrapper.sendWarning(channel, e.getMessage());
				wrapper.getStreamLogger().warning("Failed to execute command " + getCommandName(), e);
			}
		} else {
			wrapper.sendMessage(channel, getCommandUsage());
		}
	}

	@Override
	public String getCommandUsage() {
		return "!" + getCommandName() + "[command]";
	}

}
