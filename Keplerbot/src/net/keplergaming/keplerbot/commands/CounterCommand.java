package net.keplergaming.keplerbot.commands;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.permissions.PermissionsManager;

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
	public boolean canSenderUseCommand(PermissionsManager permissionsManager, String user) {
		return permissionsManager.isDeveloper(user) || permissionsManager.isModerator(user) || permissionsManager.isStreamer(user) || permissionsManager.isConsole(user);
	}

	@Override
	public void handleCommand(KeplerBotWrapper wrapper, String sender, String[] args) {
		if (args.length == 0) {
			wrapper.sendMessage(getCommandName() + ": " + count);
		} else if (args.length == 2) {
			try {
				if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("+")) {
					count = count + Integer.parseInt(args[1]);
					wrapper.sendMessage(getCommandName() + ": " + count);
				} else if (args[0].equalsIgnoreCase("extract")  || args[0].equalsIgnoreCase("-")) {
					count = count - Integer.parseInt(args[1]);
					wrapper.sendMessage(getCommandName() + ": " + count);
				} else if (args[0].equalsIgnoreCase("equals") || args[0].equalsIgnoreCase("=")) {
					count = Integer.parseInt(args[1]);
					wrapper.sendMessage(getCommandName() + ": " + count);
				} else {
					wrapper.sendMessage(getCommandUsage());
				}
			} catch (Exception e) {
				wrapper.sendError(e.getMessage());
			}
		} else {
			wrapper.sendMessage(getCommandUsage());
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
