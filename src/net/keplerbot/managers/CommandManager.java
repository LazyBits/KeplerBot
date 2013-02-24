package net.keplerbot.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.keplerbot.KeplerBot;
import net.keplerbot.api.ICommand;
import net.keplerbot.commands.CommandBasic;
import net.keplerbot.commands.CommandBlackList;
import net.keplerbot.commands.CommandCountDown;
import net.keplerbot.commands.CommandDel;
import net.keplerbot.commands.CommandHelp;
import net.keplerbot.commands.CommandJoin;
import net.keplerbot.commands.CommandLeave;
import net.keplerbot.commands.CommandModerators;
import net.keplerbot.commands.CommandNew;
import net.keplerbot.commands.CommandPermit;
import net.keplerbot.commands.CommandPoll;
import net.keplerbot.commands.CommandSchedule;
import net.keplerbot.commands.CommandTime;
import net.keplerbot.commands.CommandTimer;
import net.keplerbot.commands.CommandWhiteList;
import net.keplerbot.config.KeplerConfig;
import net.keplerbot.log.KeplerLogger;
import net.keplerbot.utils.StringUtils;

public class CommandManager {
	
	private KeplerLogger logger;
	
	private File file;
	
	private static Map<String, CommandManager> instances = new HashMap<String, CommandManager>();
	
	private String channel;
	
	public CommandManager(String channel) {
		logger = KeplerLogger.getInstance(channel);
		if(instances.containsKey(channel)) {
			logger.error("CommandManager for " + channel + " already exists!");
		}else{
			instances.put(channel, this);
			this.channel = channel;
			registerDefaultCommands();
			file = new File("./commands/commands_" + channel.replace("#", "") + ".txt");
			file.getParentFile().mkdir();
			if(file.exists()) {
				loadCommands();
			}
		}
	}
	
	public static CommandManager getInstance(String channel) {
		if (!instances.containsKey(channel)) {
			new CommandManager(channel);
		}
		return instances.get(channel);
	}
	
	/**
	 Joined List (Default + Stored)  
	 **/
	public ArrayList<ICommand> getCommands() {
		ArrayList<ICommand> commands = new ArrayList<ICommand>();
		
		for(ICommand command : defaultCommands) {
			commands.add(command);
		}
		
		for(ICommand command : storedCommands) {
			commands.add(command);
		}
		return commands;
	}
	
	public ICommand getCommand(String commandname) {
		for(ICommand command : getCommands()) {
			if(command.getCommand().equalsIgnoreCase(commandname)) {
				return command;
			}
		}
		return null;	
	}
	
	/**
	Default Commands (HardCoded)
	**/
	
	private void registerDefaultCommands() {
		registerDefaultCommand(new CommandHelp());
		registerDefaultCommand(new CommandModerators());
		registerDefaultCommand(new CommandDel());
		registerDefaultCommand(new CommandNew());
		registerDefaultCommand(new CommandPermit());
		registerDefaultCommand(new CommandTime());
		registerDefaultCommand(new CommandSchedule());
		registerDefaultCommand(new CommandWhiteList());
		registerDefaultCommand(new CommandBlackList());
		registerDefaultCommand(new CommandTimer());
		registerDefaultCommand(new CommandPoll());
		registerDefaultCommand(new CommandCountDown());
		registerDefaultCommand(new CommandBasic("!ping", "pong", "Returns 'Pong' so you can test if the bot is responding", "@a"));
		registerDefaultCommand(new CommandBasic("!about", new String[]{"KeplerBot by Crazyputje and Logomaster256", "http://nl.twitch.tv/keplerbot"}, "Useful info about the bot", "@a"));
		registerDefaultCommand(new CommandLeave());
		
		if(channel.replace("#", "").equalsIgnoreCase(KeplerConfig.getInstance().login)) {
			registerDefaultCommand(new CommandJoin());
		}
	}
	
	private ArrayList<ICommand> defaultCommands = new ArrayList<ICommand>();
	
	private void registerDefaultCommand(ICommand command) {
		for(ICommand icommand : defaultCommands)
		{
			if(icommand.getCommand().equalsIgnoreCase(command.getCommand()))
			{
				logger.warn("Command already registred!", null);
				return;
			}
		}
		logger.info("Command " + command.getCommand() + " has been registred");
		defaultCommands.add(command);
	}
	
	/**
	Stored Commands (Not-HardCoded, Added in by chat)
	**/
	
	private ArrayList<CommandBasic> storedCommands = new ArrayList<CommandBasic>();

	public boolean registerCommand(CommandBasic command) {
		for(CommandBasic icommand : storedCommands)
		{
			if(icommand.getCommand().equalsIgnoreCase(command.getCommand()))
			{
				logger.warn("Command already registred!", null);
				return false;
			}
		}
		logger.info("Command " + command.getCommand() + " has been registred");
		storedCommands.add(command);
		storeCommands();
		return true;
	}
	
	public boolean removeCommand(ICommand command) {
		for(ICommand icommand : storedCommands)
		{
			if(icommand.getCommand().equalsIgnoreCase(command.getCommand()))
			{
				storedCommands.remove(icommand);
				storeCommands();
				return true;
			}
		}
		logger.warn("Unable to remove that command because it doesn't exist!", null);
		return false;
	}
	
	/**
	Saves the commands to a txt file
	**/
	 
	private void loadCommands() {
		try {
			BufferedReader buff = new BufferedReader(new FileReader(file));
			String line = "";

			while ((line = buff.readLine()) != null) {
				String[] args = line.split(" ", -1);
				registerCommand(new CommandBasic(args[0], StringUtils.joinString(StringUtils.dropStrings(args, 2)), null, args[1]));
			}
			buff.close();
		} catch (Exception e) {
			logger.error("Failed to load commands from file", e);
		}
	}
	
	public void storeCommands() {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(file, false));
			
			for(CommandBasic command : storedCommands) {
				writer.println(command.command + " "+ command.useSelector + " " + command.message);
			}

			writer.close();
		} catch (Exception e) {
			logger.error("Failed to store commands to file", e);
		}
	}
	
	/**
	runs the commands
	**/
	
	public void handleCommand(KeplerBot bot, String channel, String sender, String message) {
		String[] args = message.split(" ");
		String command = args[0];
		args = StringUtils.dropFirstString(args);
		
		for (ICommand icommand : getCommands()) {
			if(command.equalsIgnoreCase(icommand.getCommand())) {
				logger.info("Handeling " + command);
				if(icommand.canUseCommand(sender, channel)) {
					if(!needsCoolDown(message) || args.length > 0 || ModeratorManager.getInstance(channel).isModerator(sender)) {
						if(!icommand.handleCommand(bot, channel, sender, args)) {
							bot.sendMessage("Command failed :/");
						}
					}else{
						logger.info(command + " is currently under a cooldown");
						bot.sendMessage("Command under cooldown ResidentSleeper");
					}
				}else{
					logger.info(sender + " can't use " + command + "!");
					bot.sendMessage(sender + " can't use " + command + "!");
					bot.fetchModerators();
				}
				return;
			}
		}
		logger.info(command + " doesn't exist!");
	}
	
	public HashMap<String, Long> coolDowns = new HashMap<String, Long>();
	
	public boolean needsCoolDown(String command) {
		if(!KeplerConfig.getInstance().cooldownenabled) {
			return false;
		}
		
		long currentTime = System.currentTimeMillis();
		if(coolDowns.containsKey(command)) {
			long lastTime = coolDowns.get(command);
			if((currentTime > lastTime + (1000 * KeplerConfig.getInstance().cooldown)) || currentTime < lastTime) {
				coolDowns.put(command, currentTime);
				return false;
			}else{
				return true;
			}
		}else{
			coolDowns.put(command, currentTime);
			return false;
		}
	}
	
	public void onMessage(KeplerBot bot, String channel, String sender, String message) {
		for (ICommand icommand : getCommands()) {
			icommand.onMessage(bot, channel, sender, message);
		}
	}
}
