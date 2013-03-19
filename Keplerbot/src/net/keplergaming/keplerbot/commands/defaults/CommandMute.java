package net.keplergaming.keplerbot.commands.defaults;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.commands.ICommand;
import net.keplergaming.keplerbot.config.Configuration;
import net.keplergaming.keplerbot.permissions.PermissionsManager;

import org.pircbotx.Channel;
import org.pircbotx.User;

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
	public boolean canSenderUseCommand(PermissionsManager permissionsManager,User user) {
		return permissionsManager.isDeveloper(user.getNick()) || permissionsManager.isModerator(user.getNick()) || permissionsManager.isStreamer(user.getNick());
	}

	@Override
	public void handleCommand(KeplerBotWrapper wrapper, User sender, Channel channel, String[] args) {
		if (args.length == 1) {
			switch (args[0]) {
				case "errors": 
					wrapper.muteErrors(true);
					wrapper.getConfig().setBoolean(Configuration.MUTE_ERRORS[0], true);
					wrapper.sendMessage(channel, args[0] + " muted");
					break;
				case "warnings": 
					wrapper.muteWarnings(true);
					wrapper.getConfig().setBoolean(Configuration.MUTE_WARNINGS[0], true);
					wrapper.sendMessage(channel, args[0] + " muted");
					break;
				case "all": 
					wrapper.muteAll(true);
					wrapper.getConfig().setBoolean(Configuration.MUTE_ALL[0], true);
					wrapper.sendMessage(channel, args[0] + " muted");
					break;
				default : 
					wrapper.sendError(channel, "Argument is not valid");
					break;
			}
		} else {
			wrapper.sendMessage(channel, getCommandUsage());
		}
	}

	@Override
	public String getCommandUsage() {
		return "!" + getCommandName() + "[errors|warnings|all]";
	}

}
