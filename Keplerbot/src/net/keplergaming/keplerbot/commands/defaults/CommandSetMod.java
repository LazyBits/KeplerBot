package net.keplergaming.keplerbot.commands.defaults;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.commands.BasicCommand;
import net.keplergaming.keplerbot.commands.ICommand;
import net.keplergaming.keplerbot.exception.BotException;
import net.keplergaming.keplerbot.utils.StringUtils;

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
	public void handleCommand(KeplerBotWrapper wrapper, String sender, String[] args) throws BotException {
		if (args.length >= 2) {
			String commandName = args[0];
			if (commandName.startsWith("!")) {
				commandName = commandName.substring(1);
			}
			ICommand newCommand = new BasicCommand(commandName, StringUtils.joinString(StringUtils.dropFirstString(args)), true);
			wrapper.getCommandManager().registerCommand(newCommand);
			wrapper.getCommandManager().saveCommand(newCommand);
			wrapper.sendMessage("Command !" + commandName + " set");
		} else {
			wrapper.sendMessage(getCommandUsage());
		}
	}
}
