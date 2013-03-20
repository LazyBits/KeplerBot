package net.keplergaming.keplerbot.commands.defaults;

import org.pircbotx.Channel;
import org.pircbotx.User;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.commands.ICommand;
import net.keplergaming.keplerbot.exception.BotException;
import net.keplergaming.keplerbot.permissions.PermissionsManager;
import net.keplergaming.keplerbot.utils.StringUtils;

public class CommandSet implements ICommand {

	@Override
	public String getCommandName() {
		return "set";
	}

	@Override
	public String[] getCommandAliases() {
		return new String[]{"new"};
	}

	@Override
	public boolean canSenderUseCommand(PermissionsManager permissionsManager, User user) {
		return permissionsManager.isDeveloper(user.getNick()) || permissionsManager.isModerator(user.getNick()) || permissionsManager.isStreamer(user.getNick());
	}

	@Override
	public void handleCommand(KeplerBotWrapper wrapper, User sender, Channel channel, String[] args) {
		if (args.length >= 2) {
			try {
				String commandName = args[0];
				if (commandName.startsWith("!")) {
					commandName = commandName.substring(1);
				}
				wrapper.getCommandManager().registerCommand(new CommandBasic(commandName, StringUtils.joinString(StringUtils.dropFirstString(args)), false));
				wrapper.sendMessage(channel, "Command !" + commandName + " set");
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
		return "!" + getCommandName() + " [message]";
	}

	class CommandBasic implements ICommand{

		public CommandBasic(String name, String message, boolean modOnly) {
			this.name = name;
			this.message = message;
			this.modOnly = modOnly;
		}

		@Override
		public String getCommandName() {
			return name;
		}

		@Override
		public String[] getCommandAliases() {
			return null;
		}

		@Override
		public boolean canSenderUseCommand(PermissionsManager permissionsManager, User user) {
			if (modOnly) {
				return permissionsManager.isDeveloper(user.getNick()) || permissionsManager.isModerator(user.getNick()) || permissionsManager.isStreamer(user.getNick());
			}
			return true;
		}

		@Override
		public void handleCommand(KeplerBotWrapper wrapper, User sender, Channel channel, String[] args) {
			wrapper.sendMessage(channel, message);
		}

		@Override
		public String getCommandUsage() {
			return null;
		}

		private String name;
		private String message;
		private boolean modOnly;
	}
}
