package net.keplerbot.commands.schedules;

import net.keplerbot.Main;
import net.keplerbot.log.KeplerLogger;
import net.keplerbot.managers.ScheduleManager;

public class Scheduler implements Runnable{

	private Thread thread;
	private String channel;
		
	public Scheduler(String channel) {
		this.channel = channel;
		thread = new Thread(this);
		thread.setName("scheduler for " + channel);
		thread.start();
	}
	
	@Override
	public void run() {
		try{
		while(true) {
			long currentTime = System.currentTimeMillis();
			for(Schedule schedule : ScheduleManager.getInstance(channel).getSchedules()) {
				if(currentTime > schedule.lastTime + schedule.time * 60000) {
					Main.instance.getBot(channel.replace("#", "")).sendMessage(schedule.message);
					schedule.lastTime = currentTime;
				}
			}
		Thread.sleep(100);
		}
		}catch(Exception e) {
			KeplerLogger.getInstance(channel).error("Scheduler crashed", e);
		}
	}
}
