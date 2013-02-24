package net.keplerbot.api;

import net.keplerbot.KeplerBot;

public interface ICommand {
	
	public abstract String getCommand();
	
	public abstract String getCommandUsage();
	
    public abstract boolean canUseCommand(String sender, String channel);
    
    public abstract boolean handleCommand(KeplerBot bot, String channel, String sender, String[] args);
    
    public abstract void onMessage(KeplerBot bot, String channel, String sender, String message);
}