package net.keplerbot.commands;

import net.keplerbot.KeplerBot;
import net.keplerbot.api.ICommand;
import net.keplerbot.managers.CommandManager;
import net.keplerbot.managers.ModeratorManager;
import net.keplerbot.utils.StringUtils;

public class CommandNew implements ICommand{

	@Override
	public String getCommand() {
		return "!new";
	}

	@Override
	public String getCommandUsage() {
		return "do !new [Command] [@a|@m] [Message] to create a new command.";
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
		if(args.length > 2 && args[0].startsWith("!") && (args[1].equals("@a") || args[1].equals("@m"))) {
			if(!CommandManager.getInstance(channel).registerCommand(new CommandBasic(args[0], StringUtils.joinString(StringUtils.dropStrings(args, 2)), null, args[1]))) {
				bot.sendMessage("Command already registred!");
				return true;
			}else{
				bot.sendMessage("Created new command " + args[0]);
				return true;
			}
		}else if(args.length > 2 && args[0].startsWith("!")){
			if(!CommandManager.getInstance(channel).registerCommand(new CommandBasic(args[0], StringUtils.joinString(StringUtils.dropFirstString(args)), null, "@a"))) {
				bot.sendMessage("Command already registred!");
				return true;
			}else{
				bot.sendMessage("Created new command " + args[0]);
				return true;
			}
		}else{
			return false;
		}
	}

	@Override
	public void onMessage(KeplerBot bot, String channel, String sender, String message) {}
}
