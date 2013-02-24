package net.keplerbot.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import net.keplerbot.KeplerBot;
import net.keplerbot.api.ICommand;
import net.keplerbot.managers.ModeratorManager;

public class CommandPoll implements ICommand{

	@Override
	public String getCommand() {
		return "!poll";
	}

	@Override
	public String getCommandUsage() {
		return "!poll [A-B-C-D-...]. !poll stop";
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
		if(args.length > 1) {
			if(running) {
				bot.sendMessage("There is already a poll running");
				return true;
			}
			String[] pollArgs = args;
			for(String arg : pollArgs) {
				list.put(arg, new ArrayList<String>());
			}
			running = true;
			bot.sendMessage("New poll started");
			return true;
		}else if(args.length == 1 && args[0].equalsIgnoreCase("stop")) {
			if(!running) {
				bot.sendMessage("There is no poll to stop");
				return true;
			}
			double total = 0;
			for(Collection<String> templist: list.values()) {
				total = total + templist.size();
			}
			StringBuilder messagebuilder = new StringBuilder();
			if(total == 0 ){
				bot.sendMessage("no results found");
				return true;
			}
			for(String key : list.keySet()) {
				double percentage = list.get(key).size() / total;
				messagebuilder.append(key + ": " + (int) (percentage * 100) + "% ");
			}
			bot.sendMessage("results: " + messagebuilder.toString());
			running = false;
			list.clear();
			return true;
		}
		return false;
	}

	@Override
	public void onMessage(KeplerBot bot, String channel, String sender, String message) {
		if(running) {
			for(String key : list.keySet()) {
				if(key.equalsIgnoreCase(message)) {
					if(!list.get(key).contains(sender)) {
						list.get(key).add(sender);
					}
					break;
				}
			}
		}
	}
	
	private HashMap<String, ArrayList<String>> list = new HashMap<String, ArrayList<String>>();
	
	private boolean running = false;
}
