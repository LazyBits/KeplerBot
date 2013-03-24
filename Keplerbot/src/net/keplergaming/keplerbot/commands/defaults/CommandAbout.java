package net.keplergaming.keplerbot.commands.defaults;

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
	public boolean canSenderUseCommand(PermissionsManager permissionsManager, String user) {
		return true;
	}

	@Override
	public void handleCommand(KeplerBotWrapper wrapper, String sender, String[] args) {
		wrapper.sendMessage("Go to http://keplergaming.github.com/KeplerBot/ for more information");
	}

	@Override
	public String getCommandUsage() {
		return "!" + getCommandName();
	}

}
