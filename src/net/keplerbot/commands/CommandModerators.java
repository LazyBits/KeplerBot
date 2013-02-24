package net.keplerbot.commands;

import net.keplerbot.KeplerBot;
import net.keplerbot.api.ICommand;

public class CommandModerators implements ICommand{

	@Override
	public String getCommand() {
		return "!moderators";
	}

	@Override
	public String getCommandUsage() {
		return "do !moderators to get all moderators of this channel";
	}

	@Override
	public boolean canUseCommand(String sender, String channel) {
		return true;
	}

	@Override
	public boolean handleCommand(KeplerBot bot, String channel, String sender, String[] args) {
		if(args.length == 0) {
			bot.sendMessage(bot.moderators);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void onMessage(KeplerBot bot, String channel, String sender, String message) {}
}
