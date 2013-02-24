package net.keplerbot.commands;

import java.util.ArrayList;

import net.keplerbot.KeplerBot;
import net.keplerbot.api.ICommand;
import net.keplerbot.managers.BlackListManager;
import net.keplerbot.managers.ModeratorManager;
import net.keplerbot.utils.StringUtils;

public class CommandBlackList implements ICommand{

	@Override
	public String getCommand() {
		return "!blacklist";
	}

	@Override
	public String getCommandUsage() {
		return "do !blacklist add [word] to add that word to the blacklist, !blacklist remove [word] to remove it and !blacklist list to list all the blacklisted words.";
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
		if(args.length == 2 && args[0].equalsIgnoreCase("remove")) {
			if(!BlackListManager.getInstance(channel).removeBlackListed(args[1])) {
				bot.sendMessage("Doens't contain " + args[1]);
				return true;
			}else{
				bot.sendMessage("Removed from blacklist");
				return true;
			}
		}else if(args.length == 2 && args[0].equalsIgnoreCase("add")) {
			if(!BlackListManager.getInstance(channel).addBlackListed(args[1])) {
				bot.sendMessage("Already exists");
				return true;
			}else {
				bot.sendMessage("Added to blacklist");
				return true;
			}
		}else if(args.length == 1 && args[0].equalsIgnoreCase("list")) {
			ArrayList<String> list = new ArrayList<String>();
			for(String blacklisted : BlackListManager.getInstance(channel).getBlackList()) {
				list.add(blacklisted);
			}
			bot.sendMessage("The blacklisted words are: " + StringUtils.joinString(list));
			return true;
		}else  {
			return false;
		}
	}
	
	@Override
	public void onMessage(KeplerBot bot, String channel, String sender, String message) {}
}
