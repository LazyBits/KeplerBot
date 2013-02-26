package net.keplergaming.keplerbot.commands;

import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

import net.keplergaming.keplerbot.KeplerBot;
import net.keplergaming.keplerbot.api.ICommand;
import net.keplergaming.keplerbot.utils.StringUtils;

public class CommandBasic implements ICommand
{
	public String command;
	public String message;
	public String[] multipleMessages;
	public String usage;
	public String useSelector;
	
	public CommandBasic( String command, String message, String usage, String useSelector )
	{
		this.command = command;
		this.message = message;
		this.usage = usage;
		this.useSelector = useSelector;
	}
	
	public CommandBasic( String command, String multipleMessages[], String usage, String useSelector )
	{
		this.command = command;
		this.multipleMessages = multipleMessages;
		this.usage = usage;
		this.useSelector = useSelector;
	}
	
	@Override
	public String getCommand() 
	{
		return this.command;
	}

	@Override
	public String getCommandUsage()
	{
		if( this.usage != null )
		{
			return this.usage;
		}
		return "Do " + this.command + " to get: " + this.message;
	}

	@Override
	public boolean canUseCommand( Channel channel, User sender ) 
	{
		if( this.useSelector.equalsIgnoreCase( "@a" ) )
		{
			return true;
		}
		else if( this.useSelector.equalsIgnoreCase( "@m" ) && sender.isIrcop() )
		{
			return true;
		}
		
		return false;
	}

	@Override
	public boolean handleCommand( MessageEvent event, KeplerBot bot, Channel channel, User sender, String[] args )
	{
		if ( args.length == 0 )
		{
			if( this.multipleMessages == null )
			{
				event.respond( this.message );
			}
			else
			{
				for( String multiMessage : this.multipleMessages )
				{
					event.respond( multiMessage );
				}
			}
			return true;
		}
		else if( this.multipleMessages == null || sender.isIrcop() )
		{
			this.message = StringUtils.joinString( args );
			//TODO Store command to CommandManager
			event.respond( "Command Updated" );
			return true;
		}
		
		return false;
	}

	@Override
	public void onMessage( KeplerBot bot, Channel channel, User sender, String message )
	{
	}
}
