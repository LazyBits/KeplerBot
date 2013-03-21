package net.keplergaming.keplerbot.commands.defaults;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.commands.ICommand;
import net.keplergaming.keplerbot.exception.BotException;
import net.keplergaming.keplerbot.utils.StringUtils;

import org.pircbotx.Channel;
import org.pircbotx.User;

public class CommandSetMod extends CommandSet {

	@Override
	public String getCommandName() {
		return "setmod";
	}

	@Override
	public String[] getCommandAliases() {
		return new String[]{"newmod"};
	}

	@Override
	public void handleCommand(KeplerBotWrapper wrapper, User sender, Channel channel, String[] args) {
		if (args.length >= 2) {
			try {
				String commandName = args[0];
				if (commandName.startsWith("!")) {
					commandName = commandName.substring(1);
				}
				ICommand newCommand = new BasicCommand(commandName, StringUtils.joinString(StringUtils.dropFirstString(args)), true);
				wrapper.getCommandManager().registerCommand(newCommand);
				wrapper.getCommandManager().saveCommand(newCommand);
				wrapper.sendMessage(channel, "Command !" + commandName + " set");
			} catch (BotException e) {
				wrapper.sendWarning(channel, e.getMessage());
				wrapper.getStreamLogger().warning("Failed to execute command " + getCommandName(), e);
			}
		} else {
			wrapper.sendMessage(channel, getCommandUsage());
		}
	}
}
