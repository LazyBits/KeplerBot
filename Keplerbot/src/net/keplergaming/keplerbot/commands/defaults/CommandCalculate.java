package net.keplergaming.keplerbot.commands.defaults;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import net.keplergaming.keplerbot.KeplerBot;
import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.commands.ICommand;
import net.keplergaming.keplerbot.permissions.PermissionsManager;
import net.keplergaming.keplerbot.utils.StringUtils;

import org.pircbotx.Channel;
import org.pircbotx.User;

public class CommandCalculate implements ICommand {

	@Override
	public String getCommandName() {
		return "calculate";
	}

	@Override
	public String[] getCommandAliases() {
		return new String[] { "calc" };
	}

	@Override
	public boolean canSenderUseCommand(PermissionsManager permissionsManager, User user) {
		return true;
	}

	@Override
	public void handleCommand(KeplerBotWrapper wrapper, KeplerBot bot, User sender, Channel channel, String[] args) {
		if (args.length == 0) {
			bot.sendMessage(channel, getCommandUsage());
		} else {
			try {
				ScriptEngineManager mgr = new ScriptEngineManager();
				ScriptEngine engine = mgr.getEngineByName("JavaScript");
				bot.sendMessage(channel, engine.eval(StringUtils.joinString(args)).toString());
			} catch (Exception e) {
				bot.sendMessage(channel, "Unable to calculate '" + StringUtils.joinString(args) + "'");
				wrapper.getStreamLogger().warning( "Unable to calculate '" + StringUtils.joinString(args) + "'");
			}
		}
	}

	@Override
	public String getCommandUsage() {
		return "!" + getCommandName() + " [Formula]";
	}

}
