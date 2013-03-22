package net.keplergaming.keplerbot.commands;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.permissions.PermissionsManager;

import org.pircbotx.Channel;
import org.pircbotx.User;

public class CounterCommand implements ICommand {

	public CounterCommand(String name) {
		this.name = name;
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
		return permissionsManager.isDeveloper(user.getNick()) || permissionsManager.isModerator(user.getNick()) || permissionsManager.isStreamer(user.getNick());
	}

	@Override
	public void handleCommand(KeplerBotWrapper wrapper, User sender, Channel channel, String[] args) {
		if (args.length == 0) {
			wrapper.sendMessage(channel, getCommandName() + ": " + count);
		} else if (args.length == 2) {
			try {
				if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("+")) {
					count = count + Integer.parseInt(args[1]);
					wrapper.sendMessage(channel, getCommandName() + ": " + count);
				} else if (args[0].equalsIgnoreCase("extract")  || args[0].equalsIgnoreCase("-")) {
					count = count - Integer.parseInt(args[1]);
					wrapper.sendMessage(channel, getCommandName() + ": " + count);
				} else if (args[0].equalsIgnoreCase("equals") || args[0].equalsIgnoreCase("=")) {
					count = Integer.parseInt(args[1]);
					wrapper.sendMessage(channel, getCommandName() + ": " + count);
				} else {
					wrapper.sendMessage(channel, getCommandUsage());
				}
			} catch (Exception e) {
				wrapper.sendMessage(channel, e.getMessage());
				wrapper.getStreamLogger().warning("Failed to execute command " + getCommandName(), e);
			}
		} else {
			wrapper.sendMessage(channel, getCommandUsage());
		}
	}

	@Override
	public String getCommandUsage() {
		return "!" + getCommandName() + " [add|extract|equals] [count]";
	}

	private String name;
	private int count;

	public String getName() {
		return name;
	}
}
