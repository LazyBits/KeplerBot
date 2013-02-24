package net.keplerbot.commands;

import net.keplerbot.KeplerBot;
import net.keplerbot.api.ICommand;
import net.keplerbot.managers.ModeratorManager;

public class CommandPermit implements ICommand{

	@Override
	public String getCommand() {
		return "!permit";
	}

	@Override
	public String getCommandUsage() {
		return "do !permit [username] to permit that user to post one link";
	}

	@Override
	public boolean canUseCommand(String sender, String channel) {
		if (ModeratorManager.getInstance(channel).isModerator(sender)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean handleCommand(KeplerBot bot, String channel, String sender, String[] args) {
		if(args.length == 1 && !args[0].isEmpty()) {
			bot.addpermit(args[0]);
			bot.sendMessage((args[0] + " is now permitted to post one link"));
			return true;
		}
		return false;
	}

	@Override
	public void onMessage(KeplerBot bot, String channel, String sender, String message) {}
}
