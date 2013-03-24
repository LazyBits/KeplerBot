package net.keplergaming.keplerbot.commands;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.permissions.PermissionsManager;

public class BasicCommand implements ICommand {

	public BasicCommand(String name, String message, boolean modOnly) {
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
	public boolean canSenderUseCommand(PermissionsManager permissionsManager, String user) {
		if (modOnly) {
			return permissionsManager.isDeveloper(user) || permissionsManager.isModerator(user) || permissionsManager.isStreamer(user) || permissionsManager.isConsole(user);
		}
		return true;
	}

	@Override
	public void handleCommand(KeplerBotWrapper wrapper, String sender, String[] args) {
		wrapper.sendMessage(message);
	}

	@Override
	public String getCommandUsage() {
		return "!" + getCommandName();
	}

	private String name;
	private String message;
	private boolean modOnly;

	public String getMessage() {
		return message;
	}

	public boolean isModOnly() {
		return modOnly;
	}
}