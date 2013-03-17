package net.keplergaming.keplerbot.commands;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.keplergaming.keplerbot.KeplerBot;
import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.exception.BotException;
import net.keplergaming.keplerbot.logger.StreamLogger;
import net.keplergaming.keplerbot.utils.StringUtils;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class CommandManager extends ListenerAdapter<KeplerBot>{
	
	private Map<String, ICommand> commandMap;
	private StreamLogger logger;
	private KeplerBotWrapper wrapper;
	
	public CommandManager(KeplerBotWrapper wrapper) {
		this.logger = wrapper.getStreamLogger();
		this.wrapper = wrapper;
		commandMap = new HashMap<String, ICommand>();

		registerDefaultCommands();
	}
	
	public void registerCommand(ICommand command) {
		if (commandMap.containsKey(command.getCommandName())) {
			logger.warning("Command " + command.getCommandName() + " already registered");
		}
		commandMap.put(command.getCommandName(), command);

		if (command.getCommandAliases() != null) {
			for (String alias : command.getCommandAliases()) {
				commandMap.put(alias, command);
			}
		}

		logger.info("Succesfully registered command " + command.getCommandName());
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
				
				if (command.canSenderUseCommand(wrapper.getPermissionsManager(), event.getUser())) {
					command.handleCommand(wrapper, event.getBot(), event.getUser(), event.getChannel(), StringUtils.dropFirstString(splitMessage));
				} else {
					throw new BotException(event.getUser().getNick() + " doesn't have permission for " + splitMessage[0]);
				}
			}
		} catch (BotException e) {
			event.getBot().sendMessage(event.getChannel(), e.getMessage());
		}
	}

	public void registerDefaultCommands() {
		URL root = Thread.currentThread().getContextClassLoader().getResource("net/keplergaming/keplerbot/commands/defaults");

		File[] files = new File(root.getFile()).listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".class");
		    }
		});

		for (File file : files) {
		    String className = file.getName().replaceAll(".class$", "");
		    Class<?> cls;
			try {
				cls = Class.forName("net.keplergaming.keplerbot.commands.defaults." + className);
			    if (ICommand.class.isAssignableFrom(cls)) {
					registerCommand((ICommand) cls.newInstance());

			    }
			} catch (Exception e) {
				logger.warning("Error when registering default commands", e);
			}
		}
	}

	public Set<String> getCommands() {
		return commandMap.keySet();
	}
}
