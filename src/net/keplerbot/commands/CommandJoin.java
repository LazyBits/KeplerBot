package net.keplerbot.commands;

import net.keplerbot.KeplerBot;
import net.keplerbot.Main;
import net.keplerbot.api.ICommand;
import net.keplerbot.config.KeplerConfig;
import net.keplerbot.managers.ModeratorManager;

public class CommandJoin implements ICommand{

	@Override
	public String getCommand() {
		return "!join";
	}

	@Override
	public String getCommandUsage() {
		return "Makes the bot join your channel! B) (You can only make the bot join your channel and not someone elses)";

	}

	@Override
	public boolean canUseCommand(String sender, String channel) {
		if(channel.replace("#", "").equalsIgnoreCase(KeplerConfig.getInstance().login)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean handleCommand(KeplerBot bot, String channel, String sender, String[] args) {
		if(args.length == 0) {
			Main.instance.addStreamer(sender);
			bot.sendMessage("Keplerbot is now enabled on your stream, Enjoy :D");
			return true;
		}else if(ModeratorManager.getInstance(channel).isModerator(sender) && args.length == 1){
			Main.instance.addStreamer(args[0]);
			bot.sendMessage("Keplerbot is now enabled on " + args[0] + "'s stream, Enjoy :D");
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void onMessage(KeplerBot bot, String channel, String sender, String message) {}
}