package net.keplerbot.commands;

import net.keplerbot.KeplerBot;
import net.keplerbot.api.ICommand;
import net.keplerbot.managers.ModeratorManager;

public class CommandTimer implements ICommand{

	@Override
	public String getCommand() {
		return "!timer";
	}

	@Override
	public String getCommandUsage() {
		return "!timer start, !timer stop";
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
		if(args.length == 1 && args[0].equalsIgnoreCase("start")) {
			if(beginTime == 0) {
				beginTime = System.currentTimeMillis();
				bot.sendMessage("Timer started");
			}else {
				bot.sendMessage("Timer already running");
			}
			return true;
		}else if(args.length == 1 && args[0].equalsIgnoreCase("stop")) {
			if(beginTime == 0) {
				bot.sendMessage("There is no Timer to stop");
				return true;
			}
			bot.sendMessage("Timer was running for " + getDifference());
			beginTime = 0;
			return true;
		}else if(args.length == 0) {
			if(beginTime == 0) {
				bot.sendMessage("There is no Timer running");
				return true;
			}
			bot.sendMessage("Timer is running for " + getDifference());
			return true;
		}
		return false;
	}

	@Override
	public void onMessage(KeplerBot bot, String channel, String sender, String message) {}
	
	private long beginTime;
	
	public String getDifference() {
		long diff = System.currentTimeMillis() - beginTime;
		long diffSeconds = diff / 1000;
		long diffMinutes = diff / (60 * 1000);
		long diffHours = diff / (60 * 60 * 1000);
		return String.format("%02d", diffHours) + ":" + String.format("%02d", diffMinutes) + ":" + String.format("%02d", diffSeconds);
	}
}
