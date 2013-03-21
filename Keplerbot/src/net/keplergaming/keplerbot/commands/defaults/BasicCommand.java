package net.keplergaming.keplerbot.commands.defaults;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.commands.ICommand;
import net.keplergaming.keplerbot.permissions.PermissionsManager;

import org.pircbotx.Channel;
import org.pircbotx.User;

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