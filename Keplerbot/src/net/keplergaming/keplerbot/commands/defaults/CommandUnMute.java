package net.keplergaming.keplerbot.commands.defaults;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.config.Configuration;

import org.pircbotx.Channel;
import org.pircbotx.User;

public class CommandUnMute extends CommandMute{

	@Override
	public String getCommandName() {
		return "unmute";
	}

	@Override
	public void handleCommand(KeplerBotWrapper wrapper, User sender, Channel channel, String[] args) {
		if (args.length == 1) {
			switch (args[0]) {
				case "errors": 
					wrapper.muteErrors(false);
					wrapper.getConfig().setBoolean(Configuration.MUTE_ERRORS[0], false);
					wrapper.sendMessage(channel, args[0] + " unmuted");
					break;
				case "warnings": 
					wrapper.muteWarnings(false);
					wrapper.getConfig().setBoolean(Configuration.MUTE_WARNINGS[0], false);
					wrapper.sendMessage(channel, args[0] + " unmuted");
					break;
				case "all": 
					wrapper.muteAll(false);
					wrapper.getConfig().setBoolean(Configuration.MUTE_ALL[0], false);
					wrapper.sendMessage(channel, args[0] + " unmuted");
					break;
				default : 
					wrapper.sendError(channel, "Argument is not valid");
					break;
			}
		} else {
			wrapper.sendMessage(channel, getCommandUsage());
		}
	}
}
