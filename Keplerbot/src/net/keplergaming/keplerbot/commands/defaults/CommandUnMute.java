package net.keplergaming.keplerbot.commands.defaults;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.config.ConfigConstants;

public class CommandUnMute extends CommandMute {

	@Override
	public String getCommandName() {
		return "unmute";
	}

	@Override
	public void handleCommand(KeplerBotWrapper wrapper, String sender, String[] args) {
		if (args.length == 1) {
			switch (args[0]) {
				case "errors": 
					wrapper.muteErrors(false);
					wrapper.getConfig().setBoolean(ConfigConstants.MUTE_ERRORS.getKey(), false);
					wrapper.sendMessage(args[0] + " unmuted");
					break;
				case "warnings": 
					wrapper.muteWarnings(false);
					wrapper.getConfig().setBoolean(ConfigConstants.MUTE_WARNINGS.getKey(), false);
					wrapper.sendMessage(args[0] + " unmuted");
					break;
				case "all": 
					wrapper.muteAll(false);
					wrapper.getConfig().setBoolean(ConfigConstants.MUTE_ALL.getKey(), false);
					wrapper.sendMessage(args[0] + " unmuted");
					break;
				default : 
					wrapper.sendError("Argument is not valid");
					break;
			}
		} else {
			wrapper.sendMessage(getCommandUsage());
		}
	}
}
