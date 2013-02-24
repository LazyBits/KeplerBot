package net.keplerbot.commands;

import java.util.ArrayList;

import net.keplerbot.KeplerBot;
import net.keplerbot.api.ICommand;
import net.keplerbot.managers.CommandManager;
import net.keplerbot.utils.StringUtils;

public class CommandHelp implements ICommand{

	@Override
	public String getCommand() {
		return "!help";
	}

	@Override
	public String getCommandUsage() {
		return "do !help for all commands and do !help [command] to know how that command works";
	}

	@Override
	public boolean canUseCommand(String sender, String channel) {
		return true;
	}

	@Override
	public boolean handleCommand(KeplerBot bot, String channel, String sender, String[] args) {
		if(args.length == 0) {
			ArrayList<String> commandList = new ArrayList<String>();
			
			for(ICommand icommand :  CommandManager.getInstance(channel).getCommands()) {
				if(icommand.canUseCommand(sender, channel)) {
					commandList.add(icommand.getCommand());
				}
			}
			
			bot.sendMessage("Your commands: " + StringUtils.joinString(commandList));
			return true;
		}else if(args.length == 1) {
			for(ICommand icommand :  CommandManager.getInstance(channel).getCommands()) {
				if(icommand.getCommand().equalsIgnoreCase(args[0])) {
					bot.sendMessage(icommand.getCommand() + " : " + icommand.getCommandUsage());
					return true;
				}
			}
			return false;
		}else{
			return false;
		}

	}

	@Override
	public void onMessage(KeplerBot bot, String channel, String sender, String message) {}
}
