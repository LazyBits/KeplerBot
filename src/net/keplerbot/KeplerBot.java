package net.keplerbot;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.keplerbot.config.KeplerConfig;
import net.keplerbot.log.KeplerLogger;
import net.keplerbot.managers.BlackListManager;
import net.keplerbot.managers.CommandManager;
import net.keplerbot.managers.ModeratorManager;
import net.keplerbot.managers.ScheduleManager;
import net.keplerbot.managers.WhiteListManager;

import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.Queue;

public class KeplerBot extends PircBot {
	
	private String theChannel;
	
	private boolean joined = false;
	
	private String streamer;
	
	private ArrayList<String> permits = new ArrayList<String>();
	
	public String moderators;
	
	public boolean disposeMark = false;
	
	public Queue timeOutQueue = new Queue();;
	
	public Thread timeOutThread;
	
	public KeplerBot(String streamer) {
		setName(KeplerConfig.getInstance().login);
		setVerbose(true);
		setLogin(KeplerConfig.getInstance().login);
		setVersion("V2.0 by crazyputje and logomaster256");
		this.streamer = streamer;
		try {
			connect(streamer + ".jtvirc.com", 6667, KeplerConfig.getInstance().pass);
			joinChannel("#" + streamer);
		} catch (Exception e) {
			KeplerLogger.getInstance("Main").error("Unable to connect to the server!", e);
			KeplerLogger.getInstance(streamer).error("Unable to connect to the server!", e);
		}
        if (timeOutThread == null) {
        	timeOutThread = new TimeOutThread(this, timeOutQueue);
        	timeOutThread.start();
        }
	}
	
	private static final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";
	
	public static boolean containsUrl(String checkmessage) {
		Pattern p = Pattern.compile(URL_REGEX);
		Matcher m = p.matcher(checkmessage);
		
		if(m.find()) {
		    return true;
		}
		
		return false;
	}
	
	public void fetchModerators() {
		sendMessage(theChannel, "/mods");
	}
	
	public void timeOut(String username, int time) {
		timeOutQueue.add("PRIVMSG " + this.getChannel() + " :" + "/timeout " + username + " "+ time);
	}
	
	public void addpermit(String username) {
		permits.add(username.toLowerCase());
	}
	
	public void sendMessage(String message) {
		sendMessage(theChannel, message);
	}
	
	public String getChannel() {
		return theChannel;
	}
	
	@Override
    public void log(String line) {
		if(theChannel != null) {
			KeplerLogger.getInstance(theChannel).info(line);
		}else{
			KeplerLogger.getInstance("Main").info(line);
		}
    }

	@Override
    public void onJoin(String channel, String sender, String login, String hostname) {
		if(!joined) {
			joined = true;
	    	theChannel = getChannels()[0];
	    	KeplerLogger.getInstance(channel).info("Successfully joined channel " + channel);
	    	CommandManager.getInstance(channel);
        	BlackListManager.getInstance(channel);
        	WhiteListManager.getInstance(channel);
	    	ScheduleManager.getInstance(channel);
	    	fetchModerators();
			sendMessage(KeplerConfig.getInstance().joinMessage);
		}
    }

	@Override
    public void onPrivateMessage(String sender, String login, String hostname, String message) {
    	if(message.contains("The moderators of this room are:")) {
    		moderators = message;
        	String[] newmoderators = message.replace("The moderators of this room are: " , "").split(", ");
        	ModeratorManager.getInstance(theChannel).setModerators(newmoderators);
    	}
    }
    
	@Override
    public void onMessage(String channel, String sender, String login, String hostname, String message) {
		CommandManager.getInstance(channel).onMessage(this, channel, sender, message);
		
		if(message.startsWith("!")) {
			CommandManager.getInstance(channel).handleCommand(this, channel, sender, message);
		}
		
		if(ModeratorManager.getInstance(channel).isModerator(sender)) {
			return;
		}
		
		if(message.trim().equals("<message deleted>")) {
			timeOut(sender, 5 * 60);
			sendMessage("Not funny - " + sender);
		}
		
		String stripped = message;
		
		if(KeplerConfig.getInstance().whiteListEnabled) {
			for(String whitelisted : WhiteListManager.getInstance(channel).getWhiteList()) {
				stripped = stripped.replace(whitelisted, "-");
			}
		}
		
		if(containsUrl(stripped) && !KeplerConfig.getInstance().urlsAllowed) {
			if(permits.contains(sender.toLowerCase())) {
				permits.remove(sender.toLowerCase());
			}else {
				timeOut(sender, KeplerConfig.getInstance().urlTimeOut);
				sendMessage("Don't post links - " + sender);
			}
		}
		
		int caps = 0;
		for (int i = 0; i < stripped.length(); ++i) {
			if (Character.isUpperCase(stripped.charAt(i))) {
				caps++;
			}
		}
		
		if(((caps > (message.length() / 2))) && message.length() > 3 && !KeplerConfig.getInstance().capsAllowed){
			timeOut(sender, KeplerConfig.getInstance().capTimeOut);
			sendMessage("No caps - " + sender);
		}
		
		if(BlackListManager.getInstance(channel).hasBlackListed(message) && KeplerConfig.getInstance().blackListEnabled) {
			timeOut(sender, KeplerConfig.getInstance().blackListTimeOut);
			sendMessage("BlackListed Word - " + sender);
		}
    }
	
	@Override
	public void onDisconnect() {
		if(disposeMark) {
			Main.instance.removeBot(streamer);
		}else{
			try {
				connect(streamer + ".jtvirc.com", 6667, KeplerConfig.getInstance().pass);
				joinChannel("#" + streamer);
			} catch (Exception e) {
				KeplerLogger.getInstance("Main").error("Unable to reconnect to " + streamer + " !", e);
				KeplerLogger.getInstance(streamer).error("Unable to reconnect to the server!", e);
			}
		}
	}
	
	public void setDisposeMark() {
		disposeMark = true;
	}
}
