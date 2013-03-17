package net.keplergaming.keplerbot.commands.defaults;

import java.util.ArrayList;
import java.util.Collections;

import org.pircbotx.Channel;
import org.pircbotx.User;

import net.keplergaming.keplerbot.KeplerBot;
import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.commands.ICommand;
import net.keplergaming.keplerbot.exception.BotException;
import net.keplergaming.keplerbot.permissions.PermissionsManager;
import net.keplergaming.keplerbot.utils.StringUtils;

public class CommandHelp implements ICommand{

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public String[] getCommandAliases() {
		return new String[]{"?"};
	}

	@Override
	public boolean canSenderUseCommand(PermissionsManager permissionsManager, User user) {
		return true;
	}

	@Override
	public void handleCommand(KeplerBotWrapper wrapper, KeplerBot bot, User sender, Channel channel, String[] args) {
		if (args.length == 0) {
			ArrayList<String> commandNames = new ArrayList<String>();
			for (String command : wrapper.getCommandManager().getCommands()) {
				commandNames.add(command);
			}
			Collections.sort(commandNames);
			bot.sendMessage(channel, StringUtils.joinString(commandNames));
		} else if (args.length == 1) {
			try {
				ICommand command = wrapper.getCommandManager().getCommand(args[0]);
				bot.sendMessage(channel, command.getCommandUsage());
			} catch (BotException e) {
				try {
					ICommand command = wrapper.getCommandManager().getCommand(args[0].substring(1));
					bot.sendMessage(channel, command.getCommandUsage());
				} catch (BotException e2) {
					bot.sendMessage(channel, e.getMessage());
					wrapper.getStreamLogger().warning("Failed to execute command " + getCommandName(), e);
				}
			}
		}
	}

	@Override
	public String getCommandUsage() {
		return "!" + getCommandName() + " [command]";
	}

}
