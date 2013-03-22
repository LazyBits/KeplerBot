package net.keplergaming.keplerbot.commands.defaults;

import org.pircbotx.Channel;
import org.pircbotx.User;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.commands.ICommand;
import net.keplergaming.keplerbot.permissions.PermissionsManager;

public class CommandAbout implements ICommand {

	@Override
	public String getCommandName() {
		return "about";
	}

	@Override
	public String[] getCommandAliases() {
		return null;
	}

	@Override
	public boolean canSenderUseCommand(PermissionsManager permissionsManager, User user) {
		return true;
	}

	@Override
	public void handleCommand(KeplerBotWrapper wrapper, User sender, Channel channel, String[] args) {
		wrapper.sendMessage(channel, "Go to http://keplergaming.github.com/KeplerBot/ for more information");
	}

	@Override
	public String getCommandUsage() {
		return "!" + getCommandName();
	}

}
