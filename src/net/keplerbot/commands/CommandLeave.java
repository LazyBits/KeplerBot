package net.keplerbot.commands;

import net.keplerbot.KeplerBot;
import net.keplerbot.Main;
import net.keplerbot.api.ICommand;
import net.keplerbot.config.KeplerConfig;
import net.keplerbot.managers.ModeratorManager;

public class CommandLeave implements ICommand{

	@Override
	public String getCommand() {
		return "!leave";
	}

	@Override
	public String getCommandUsage() {
		return "Removes the bot from your stream";	
	}

	@Override
	public boolean canUseCommand(String sender, String channel) {
		if(channel.replace("#", "").equalsIgnoreCase(KeplerConfig.getInstance().login)) {
			return true;
		}else{
			return sender.equalsIgnoreCase(channel.replace("#", ""));
		}
	}

	@Override
	public boolean handleCommand(KeplerBot bot, String channel, String sender, String[] args) {
		if(args.length == 0) {
			if(Main.instance.markremoveBot(sender)) {
				bot.sendMessage("good bye, " + sender +  " :z");
				return true;
			}else{
				bot.sendMessage("There is no bot in that stream");
				return true;
			}
		}else if(ModeratorManager.getInstance(channel).isModerator(sender) && args.length == 1){
			if(Main.instance.markremoveBot(args[0])) {
				bot.sendMessage("good bye, " + args[0] +  " :z");
				return true;
			}else{
				bot.sendMessage("There is no bot in that stream");
				return true;
			}
		}else{
			return false;
		}
	}

	@Override
	public void onMessage(KeplerBot bot, String channel, String sender, String message) {}
}