package net.keplergaming.keplerbot.commands;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import net.keplergaming.keplerbot.KeplerBot;
import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.config.Configuration;
import net.keplergaming.keplerbot.exception.BotException;
import net.keplergaming.keplerbot.logger.StreamLogger;
import net.keplergaming.keplerbot.utils.StringUtils;

import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class CommandManager extends ListenerAdapter<KeplerBot> implements Listener<KeplerBot>{
	
	private Map<String, ICommand> commandMap;
	private Map<String, ICommand> aliasMap;
	private StreamLogger logger;
	private KeplerBotWrapper wrapper;
	private Configuration userCommands;

	public CommandManager(KeplerBotWrapper wrapper) {
		this.logger = wrapper.getStreamLogger();
		this.wrapper = wrapper;
		commandMap = new HashMap<String, ICommand>();
		aliasMap = new HashMap<String, ICommand>();
		userCommands = new Configuration("./commands/commands_" + wrapper.getStreamer() + ".properties");
		userCommands.loadConfig();

		registerDefaultCommands();
		registerUserCommands();
	}

	public void saveCommand(ICommand command) {
		if (command instanceof BasicCommand) {
			userCommands.setString("command_" + command.getCommandName(), ((BasicCommand)command).getCommandName());
			userCommands.setString("message_" + command.getCommandName(), ((BasicCommand)command).getMessage());
			userCommands.setBoolean("modonly_" + command.getCommandName(), ((BasicCommand)command).isModOnly());
			userCommands.saveConfig();
			logger.fine("Saved command !" + command.getCommandName() + " to file.");
		}
	}

	public void registerCommand(ICommand command) throws BotException {
		if (commandMap.containsKey(command.getCommandName())) {
			throw new BotException("Command !" + command.getCommandName() + " already exists");
		}
		commandMap.put(command.getCommandName(), command);

		if (command.getCommandAliases() != null) {
			for (String alias : command.getCommandAliases()) {
				if (aliasMap.containsKey(alias)) {
					throw new BotException("Command Alias !" + alias + " already exists");
				}
				aliasMap.put(alias, command);
			}
		}

		logger.fine("Registered command " + command.getCommandName() + ".");
	}

	public void unRegisterCommand(String command) throws BotException {
		if (!commandMap.containsKey(command)) {
			throw new BotException("Command !" + command + " not found");
		}

		if (commandMap.get(command) instanceof BasicCommand) {
			userCommands.getProperties().remove("command_" + command);
			userCommands.getProperties().remove("message_" + command);
			userCommands.getProperties().remove("modonly_" + command);
			userCommands.saveConfig();
			logger.fine("Removed command !" + command + " from file.");
		}

		if (commandMap.get(command).getCommandAliases() != null) {
			for (String alias : commandMap.get(command).getCommandAliases()) {
				aliasMap.remove(alias);
			}
		}

		commandMap.remove(command);

		logger.info("Unregistered command !" + command + ".");
	}

	public ICommand getCommand(String command) throws BotException {
		if (commandMap.containsKey(command)) {
			return commandMap.get(command);
		} else if (aliasMap.containsKey(command)) {
			return aliasMap.get(command);
		} else {
			throw new BotException("Command !" + command + " not found");
		}
	}

	@Override
	public void onMessage(MessageEvent<KeplerBot> event) {
		processMessage(event.getUser().getNick(), event.getMessage());
	}

	public void processMessage(String sender, String message) {
		try {
			if (message.startsWith("!")) {
				String[] splitMessage = message.split(" ");
				ICommand command = getCommand(splitMessage[0].substring(1));
				
				if (command.canSenderUseCommand(wrapper.getPermissionsManager(), sender)) {
					command.handleCommand(wrapper, sender, StringUtils.dropFirstString(splitMessage));
				} else {
					throw new BotException(sender + " doesn't have permission for " + splitMessage[0]);
				}
			}
		} catch (BotException e) {
			wrapper.sendError(e.getMessage());
		}
	}

	private void registerDefaultCommands() {
		String pkgname = "net.keplergaming.keplerbot.commands.defaults";

		File directory = null;
		String fullPath;
		String relPath = pkgname.replace('.', '/');
		logger.fine("ClassDiscovery: Package: " + pkgname + " becomes Path:" + relPath);
		URL resource = ClassLoader.getSystemClassLoader().getResource(relPath);
		logger.fine("ClassDiscovery: Resource = " + resource);
		if (resource == null) {
			logger.error("No resource for " + relPath);
			return;
		}
		fullPath = resource.getFile();
		logger.fine("ClassDiscovery: FullPath = " + resource);

		try {
			directory = new File(resource.toURI());
		} catch (URISyntaxException e) {
			logger.error("Error when registering default commands", e);
		} catch (IllegalArgumentException e) {
			directory = null;
		}
		logger.fine("ClassDiscovery: Directory = " + directory);

		if (directory != null && directory.exists()) {
			String[] files = directory.list();
			for (int i = 0; i < files.length; i++) {
				if (files[i].endsWith(".class")) {
					String className = pkgname + '.' + files[i].substring(0, files[i].length() - 6);
					logger.fine("ClassDiscovery: className = " + className);
					try {
						if (ICommand.class.isAssignableFrom(Class.forName(className))) {
							Constructor<?> classConstructor = Class.forName(className).getDeclaredConstructors()[0];
							if (classConstructor != null && classConstructor.getGenericParameterTypes().length != 0 && classConstructor.getGenericParameterTypes()[0] == KeplerBotWrapper.class) {
								registerCommand((ICommand) classConstructor.newInstance((wrapper)));
							} else {
								registerCommand((ICommand) Class.forName(className).newInstance());
							}
						}
					} catch (Exception ex) {
						logger.error("Error when registering default commands", ex);
					} 
				}
			}
		} else {
			try {
				String jarPath = fullPath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
				JarFile jarFile = new JarFile(jarPath);
				Enumeration<JarEntry> entries = jarFile.entries();
				while (entries.hasMoreElements()) {
					JarEntry entry = entries.nextElement();
					String entryName = entry.getName();
					if (entryName.startsWith(relPath) && entryName.length() > (relPath.length() + "/".length())) {
						logger.fine("ClassDiscovery: JarEntry: " + entryName);
						String className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
						logger.fine("ClassDiscovery: className = " + className);
						try {
							if (ICommand.class.isAssignableFrom(Class.forName(className))) {
								Constructor<?> classConstructor = Class.forName(className).getDeclaredConstructors()[0];
								if (classConstructor != null && classConstructor.getGenericParameterTypes().length != 0 && classConstructor.getGenericParameterTypes()[0] == KeplerBotWrapper.class) {
									registerCommand((ICommand) classConstructor.newInstance((wrapper)));
								} else {
									registerCommand((ICommand) Class.forName(className).newInstance());
								}
							}
						} catch (Exception ex) {
							logger.error("Error when registering default commands", ex);
						}
					}
				}
				jarFile.close();
			} catch (IOException e) {
				logger.error(pkgname + " (" + directory + ") does not appear to be a valid package", e);
			}
		}
	}

	private void registerUserCommands() {
		for(Object key : userCommands.getProperties().keySet()) {
			if (((String)key).startsWith("command_")) {
				String commandName = ((String)key).substring(8);
				try {
					registerCommand(new BasicCommand(commandName, userCommands.getString("message_" + commandName, "This is the default message"), userCommands.getBoolean("modonly_" + commandName, false)));
				} catch (BotException e) {
					logger.error("Could not register user command " + commandName, e);
				}
			}
		}
	}

	public Set<String> getCommands() {
		return commandMap.keySet();
	}
}
