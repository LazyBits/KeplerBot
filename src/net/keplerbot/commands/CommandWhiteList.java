package net.keplerbot.commands;

import java.util.ArrayList;

import net.keplerbot.KeplerBot;
import net.keplerbot.api.ICommand;
import net.keplerbot.managers.ModeratorManager;
import net.keplerbot.managers.WhiteListManager;
import net.keplerbot.utils.StringUtils;

public class CommandWhiteList implements ICommand{

	@Override
	public String getCommand() {
		return "!whitelist";
	}

	@Override
	public String getCommandUsage() {
		return "do !whitelist add [word] to add that word to the whitelist, !whitelist remove [word] to remove it and !whitelist list to list all the whitelisted words. Whitelisted words can be urls, and capital words that otherwise get banned.";
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
			if(!WhiteListManager.getInstance(channel).removeWhiteListed(args[1])) {
				bot.sendMessage("Doens't contain " + args[1]);
				return true;
			}else {
				bot.sendMessage("Removed from whitelist");
				return true;
			}
		}else if(args.length == 2 && args[0].equalsIgnoreCase("add")) {
			if(!WhiteListManager.getInstance(channel).addWhiteListed(args[1])) {
				bot.sendMessage("Already exists");
				return true;
			}else{
				bot.sendMessage("Added to whitelist");
				return true;
			}
		}else if(args.length == 1 && args[0].equalsIgnoreCase("list")) {
			ArrayList<String> list = new ArrayList<String>();
			for(String whitelisted : WhiteListManager.getInstance(channel).getWhiteList()) {
				list.add(whitelisted);
			}
			bot.sendMessage("The whitelisted words are: " + StringUtils.joinString(list));
			return true;
		}else  {
			return false;
		}
	}

	@Override
	public void onMessage(KeplerBot bot, String channel, String sender, String message) {}
}
