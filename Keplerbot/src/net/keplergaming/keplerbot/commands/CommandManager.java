package net.keplergaming.keplerbot.commands;

import java.util.HashMap;
import java.util.Map;

import net.keplergaming.keplerbot.KeplerBot;
import net.keplergaming.keplerbot.commands.exceptions.CommandNoPermissionsException;
import net.keplergaming.keplerbot.commands.exceptions.CommandNotFoundException;
import net.keplergaming.keplerbot.logger.MainLogger;
import net.keplergaming.keplerbot.utils.StringUtils;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class CommandManager extends ListenerAdapter<KeplerBot>{
	
	public Map<String, ICommand> commandMap;
	
	public CommandManager() {
		commandMap = new HashMap<String, ICommand>();
	}
	
	public void registerCommand(ICommand command) {
		commandMap.put(command.getCommandName(), command);
		
		for (String alias : command.getCommandAliases()) {
			commandMap.put(alias, command);
		}
	}

	public ICommand getCommand(String command) throws CommandNotFoundException {
		if (!commandMap.containsKey(command)) {
			throw new CommandNotFoundException(command);
		}
		return commandMap.get(command);
	}

	@Override
	public void onMessage(MessageEvent<KeplerBot> event) {
		try {
			if (event.getMessage().startsWith("!")) {
				String[] splitMessage = event.getMessage().split(" ");
				ICommand command = getCommand(splitMessage[0].substring(1));
				
				if (command.canSenderUseCommand(event.getBot(), event.getUser(), event.getChannel())) {
					command.handleCommand(event.getBot(), event.getUser(), event.getChannel(), StringUtils.dropFirstString(splitMessage));
				} else {
					throw new CommandNoPermissionsException(event.getUser(), command);
				}
			}
		} catch (CommandNotFoundException e) {
			MainLogger.warning("Command not found", e);
		} catch (CommandNoPermissionsException e) {
			MainLogger.warning("No permissions", e);
		}
	}
}
