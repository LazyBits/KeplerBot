package net.keplerbot.commands;

import net.keplerbot.KeplerBot;
import net.keplerbot.api.ICommand;
import net.keplerbot.log.KeplerLogger;
import net.keplerbot.managers.ModeratorManager;

public class CommandCountDown implements ICommand{

	@Override
	public String getCommand() {
		return "!countdown";
	}

	@Override
	public String getCommandUsage() {
		return "!countdown [sec], !countdown stop";
	}

	@Override
	public boolean canUseCommand(String sender, String channel) {
		if (ModeratorManager.getInstance(channel).isModerator(sender)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean handleCommand(KeplerBot bot, String channel, String sender, String[] args) {
		if(args.length == 1) {
			if(running) {
				bot.sendMessage("There is already a countdown running");
			}else {
				int sec;
				try{
					sec = Integer.valueOf(args[0]);
		        }
				catch(NumberFormatException e)
		        {
					KeplerLogger.getInstance(channel).warn("Unable to convert to integer", e);
					bot.sendMessage(args[0] + " cannot be converted to a number.");
		        	return true;
		        }
				countdownthread = new CountDownThread(bot, sec * 1000);
				countdownthread.start();
				running = true;
				bot.sendMessage("Countdown started");
			}
			return true;
		}else if(args.length == 1 && args[0].equalsIgnoreCase("stop")) {
			if(!running) {
				bot.sendMessage("There is no countdown to stop");
				return true;
			}
			countdownthread.interrupt();
			running = false;
			return true;
		}else if(args.length == 0) {
			if(!running) {
				bot.sendMessage("There is no countdown running");
				return true;
			}
			bot.sendMessage(countdownthread.time / 1000 + "sec left to go");
			return true;
		}
		return false;
	}

	@Override
	public void onMessage(KeplerBot bot, String channel, String sender, String message) {}
	
	public boolean running = false;
	
	public CountDownThread countdownthread;
	
	class CountDownThread extends Thread {
		
	    public CountDownThread(KeplerBot bot, long time) {
	    	this.time = time;
	        this.bot = bot;
	        this.setName("CountDownThread");
	    }
	    
	    public void run() {
	        try {
	            while (time > 0) {
	                Thread.sleep(1000);
	                time = time - 1000;
	                if(time <= 5 * 1000) {
	    	            bot.sendMessage("Countdown: " + time/1000);
	                }
	            }
	            bot.sendMessage("Countdown complete");
	            running = false;
	        }
	        catch (InterruptedException e) {
	        }
	    }
	    
	    private KeplerBot bot = null;
	    public long time;
	}
}
