package net.keplergaming.keplerbot;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class KeplerBot extends ListenerAdapter implements Listener
{
	public KeplerBot( String channel, String hostname, boolean isPassNeeded, String pass )
	{
		PircBotX bot = new PircBotX();
		
		bot.setName( "keplerbot" ); //TODO Change to config :p
		bot.setVerbose( true );
		bot.setLogin( "keplerbot" );
		try 
		{
			if( isPassNeeded )
			{
				bot.connect( hostname, 6667, pass );
			}
			else
			{
				bot.connect( hostname );
			}
		
			bot.joinChannel( channel );
		} 
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public void onEvent( Event rawevent ) throws Exception 
	{
		super.onEvent(rawevent);
		
		//TODO General event handling
	}
	
	@Override
	public void onMessage( MessageEvent event ) throws Exception 
	{
		if( event.getMessage().startsWith( "!" ) )
		{
			//TODO Command handling
		}
		//TODO MessageEvent handling
	}
}
