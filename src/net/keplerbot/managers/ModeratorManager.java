package net.keplerbot.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.keplerbot.log.KeplerLogger;

public class ModeratorManager {

	private KeplerLogger logger;

	private String streamer;
	
	private static Map<String, ModeratorManager> instances = new HashMap<String, ModeratorManager>();
	
	private ArrayList<String> Moderators = new ArrayList<String>();
	
	public ModeratorManager(String channel) {
		logger = KeplerLogger.getInstance(channel);
		if(instances.containsKey(channel)) {
			logger.error("ModeratorManager for " + channel + " already exists!");
		}else{
			instances.put(channel, this);
			streamer = channel.replace("#", "");
		}
	}
	
	public static ModeratorManager getInstance(String channel) {
		if (!instances.containsKey(channel)) {
			new ModeratorManager(channel);
		}
		return instances.get(channel);
	}
	
	public void setModerators(String[] newmoderators) {
		Moderators.clear();
		Moderators.add("Console");
		Moderators.add(streamer);
		Moderators.add("crazyputje");
		//Moderators.add("logomaster256");
		
		for(String moderator : newmoderators) {
			if(!Moderators.contains(moderator)) {
				Moderators.add(moderator);
			}
		}
	}
	
	public boolean isModerator(String username) {
		return Moderators.contains(username);
	}
	
	public ArrayList<String> getModerators() {
		return Moderators;
	}
}
