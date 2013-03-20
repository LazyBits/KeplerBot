package net.keplergaming.keplerbot.commands;

import java.io.File;
import java.io.IOException;
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
	
	public void registerCommand(ICommand command) throws BotException {
		if (commandMap.containsKey(command.getCommandName())) {
			throw new BotException("Command " + command.getCommandName() + " already exists");
		}
		commandMap.put(command.getCommandName(), command);

		if (command.getCommandAliases() != null) {
			for (String alias : command.getCommandAliases()) {
				commandMap.put(alias, command);
			}
		}

		logger.info("Succesfully registered command " + command.getCommandName() + ".");
	}

	public void unRegisterCommand(String command) throws BotException {
		if (!commandMap.containsKey(command)) {
			throw new BotException("Command " + command + " not found");
		}
		commandMap.remove(commandMap.get(command));
		logger.info("Succesfully unregistered command " + command + ".");
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
					command.handleCommand(wrapper, event.getUser(), event.getChannel(), StringUtils.dropFirstString(splitMessage));
				} else {
					throw new BotException(event.getUser().getNick() + " doesn't have permission for " + splitMessage[0]);
				}
			}
		} catch (BotException e) {
			event.getBot().sendMessage(event.getChannel(), e.getMessage());
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
						if (ICommand.class.isAssignableFrom(Class.forName(className)) && !Class.forName(className).isMemberClass()) {
							registerCommand((ICommand) Class.forName(className).newInstance());
						}
					} catch (Exception e) {
						logger.error("Error when registering default commands", e);
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
							if (ICommand.class.isAssignableFrom(Class.forName(className)) && !Class.forName(className).isMemberClass()) {
								registerCommand((ICommand) Class.forName(className).newInstance());
							}
						} catch (Exception e) {
							logger.error("Error when registering default commands", e);
						}
					}
				}
				jarFile.close();
			} catch (IOException e) {
				logger.error(pkgname + " (" + directory + ") does not appear to be a valid package", e);
			}
		}
	}

	public Set<String> getCommands() {
		return commandMap.keySet();
	}
}
