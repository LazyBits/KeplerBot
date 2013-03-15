package net.keplergaming.keplerbot.commands;

import java.util.HashMap;
import java.util.Map;

import net.keplergaming.keplerbot.KeplerBot;
import net.keplergaming.keplerbot.exception.BotException;
import net.keplergaming.keplerbot.logger.StreamLogger;
import net.keplergaming.keplerbot.utils.StringUtils;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class CommandManager extends ListenerAdapter<KeplerBot>{
	
	private Map<String, ICommand> commandMap;
	private StreamLogger logger;
	
	public CommandManager(StreamLogger logger) {
		this.logger = logger;
		commandMap = new HashMap<String, ICommand>();
	}
	
	public void registerCommand(ICommand command) {
		if (commandMap.containsKey(command.getCommandName())) {
			logger.warning("Command " + command.getCommandName() + " already registered");
		}
		commandMap.put(command.getCommandName(), command);
		
		for (String alias : command.getCommandAliases()) {
			commandMap.put(alias, command);
		}
	}

	public ICommand getCommand(String command) throws BotException {
		if (!commandMap.containsKey(command)) {
			throw new BotException("Command " + command + " not found");
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
					throw new BotException(event.getUser().getNick() + " doesn't have permission for " + splitMessage[0]);
				}
			}
		} catch (BotException e) {
			event.getBot().sendMessage(event.getChannel(), e.getMessage());
		}
	}
}
