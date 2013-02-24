package net.keplerbot.commands;

import net.keplerbot.KeplerBot;
import net.keplerbot.api.ICommand;
import net.keplerbot.managers.CommandManager;
import net.keplerbot.managers.ModeratorManager;

public class CommandDel implements ICommand{

	@Override
	public String getCommand() {
		return "!del";
	}

	@Override
	public String getCommandUsage() {
		return "do !del [command] to delete that command. (you cannot delete default commands)";
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
		if(args.length == 1 && args[0].startsWith("!")) {
			ICommand command = CommandManager.getInstance(channel).getCommand(args[0]);
			if(CommandManager.getInstance(channel).removeCommand(command)) {
				bot.sendMessage("Command removed " + args[0]);
				return true;
			}else{
				bot.sendMessage("Command not found");
				return true;
			}

		}else{
			return false;
		}
	}

	@Override
	public void onMessage(KeplerBot bot, String channel, String sender, String message) {}
}
