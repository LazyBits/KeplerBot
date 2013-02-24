package net.keplerbot.commands.schedules;

public class Schedule {
	
	public Schedule(String message, int time, int id, long lastTime) {
		this.message = message;
		this.time = time;
		this.id = id;
		this.lastTime = lastTime;
	}

	public String message;
	
	public int time;
	
	public int id;
	
	public long lastTime;
}
