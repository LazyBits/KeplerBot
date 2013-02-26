package net.keplergaming.keplerbot.api;

import net.keplergaming.keplerbot.KeplerBot;

import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

public interface ICommand 
{
    public abstract String getCommand();
	
    public abstract String getCommandUsage();
	
    public abstract boolean canUseCommand( Channel channel, User sender );
    
    public abstract boolean handleCommand( MessageEvent event, KeplerBot bot, Channel channel, User sender, String[] args );
    
    public abstract void onMessage( KeplerBot bot, Channel channel, User sender, String message );
}
