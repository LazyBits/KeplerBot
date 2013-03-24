package net.keplergaming.keplerbot.commands.defaults;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.commands.ICommand;
import net.keplergaming.keplerbot.config.ConfigConstants;
import net.keplergaming.keplerbot.permissions.PermissionsManager;

public class CommandMute implements ICommand {

	@Override
	public String getCommandName() {
		return "mute";
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
		if (args.length == 1) {
			switch (args[0]) {
				case "errors": 
					wrapper.muteErrors(true);
					wrapper.getConfig().setBoolean(ConfigConstants.MUTE_ERRORS.getKey(), true);
					wrapper.sendMessage(args[0] + " muted");
					break;
				case "warnings": 
					wrapper.muteWarnings(true);
					wrapper.getConfig().setBoolean(ConfigConstants.MUTE_WARNINGS.getKey(), true);
					wrapper.sendMessage(args[0] + " muted");
					break;
				case "all": 
					wrapper.muteAll(true);
					wrapper.getConfig().setBoolean(ConfigConstants.MUTE_ALL.getKey(), true);
					wrapper.sendMessage(args[0] + " muted");
					break;
				default : 
					wrapper.sendError("Argument is not valid");
					break;
			}
		} else {
			wrapper.sendMessage(getCommandUsage());
		}
	}

	@Override
	public String getCommandUsage() {
		return "!" + getCommandName() + " [errors|warnings|all]";
	}
}
