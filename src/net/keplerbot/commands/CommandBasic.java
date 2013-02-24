package net.keplerbot.commands;

import net.keplerbot.KeplerBot;
import net.keplerbot.api.ICommand;
import net.keplerbot.managers.CommandManager;
import net.keplerbot.managers.ModeratorManager;
import net.keplerbot.utils.StringUtils;

public class CommandBasic implements ICommand{

	public String command;
	
	public String message;

	public String[] multipleMessages;
	
	public String usage;
	
	public String useSelector;
	
	public CommandBasic(String command, String message, String usage, String useselector) {
		this.command = command;
		this.message = message;
		this.usage = usage;
		this.useSelector = useselector;
	}
	
	public CommandBasic(String command, String multiplemessages[], String usage, String useselector) {
		this.command = command;
		this.multipleMessages = multiplemessages;
		this.usage = usage;
		this.useSelector = useselector;
	}
	
	@Override
	public String getCommand() {
		return this.command;
	}

	@Override
	public String getCommandUsage() {
		if(this.usage != null) {
			return this.usage;
		}
		return "Do " + this.command + " to get: " + this.message;
	}

	@Override
	public boolean canUseCommand(String sender, String channel) {
		if(useSelector.equals("@a")){
			return true;
		}else if(useSelector.equals("@m")){
			if(ModeratorManager.getInstance(channel).isModerator(sender)) {
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	@Override
	public boolean handleCommand(KeplerBot bot, String channel, String sender, String[] args) {
		if(args.length == 0) {
			if(multipleMessages == null) {
				bot.sendMessage(message);
			}else{
				for(String multimessage : multipleMessages) {
					bot.sendMessage(multimessage);
				}
			}
			return true;
		}else{
			if(multipleMessages == null && ModeratorManager.getInstance(channel).isModerator(sender)) {
				message = StringUtils.joinString(args);
				CommandManager.getInstance(channel).storeCommands();
				bot.sendMessage("Command Updated");
				return true;
				}else{
					return false;
			}
		}
	}

	@Override
	public void onMessage(KeplerBot bot, String channel, String sender, String message) {}
}
