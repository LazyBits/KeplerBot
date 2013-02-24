package net.keplerbot.commands;

import net.keplerbot.KeplerBot;
import net.keplerbot.api.ICommand;
import net.keplerbot.utils.TimeUtils;

public class CommandTime implements ICommand{

	@Override
	public String getCommand() {
		return "!time";
	}

	@Override
	public String getCommandUsage() {
		return "Return the current time (atomic time if possible)";
	}

	@Override
	public boolean canUseCommand(String sender, String channel) {
		return true;
	}

	@Override
	public boolean handleCommand(KeplerBot bot, String channel, String sender, String[] args) {
		if(args.length == 0) {
			bot.sendMessage(TimeUtils.getAtomicTime());
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public void onMessage(KeplerBot bot, String channel, String sender, String message) {}
}
