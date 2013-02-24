package net.keplerbot.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.keplerbot.commands.schedules.Schedule;
import net.keplerbot.commands.schedules.Scheduler;
import net.keplerbot.listeners.SchedulesListener;
import net.keplerbot.log.KeplerLogger;
import net.keplerbot.utils.StringUtils;

import org.essiembre.FileMonitor;

public class ScheduleManager {
	
	private KeplerLogger logger;
	
	private File file;
	
	private static Map<String, ScheduleManager> instances = new HashMap<String, ScheduleManager>();
	
	private ArrayList<Schedule> schedules = new ArrayList<Schedule>();
	
	public ScheduleManager(String channel) {
		logger = KeplerLogger.getInstance("Main");
		if(instances.containsKey(channel)) {
			logger.error("ScheduleManager for " + channel + " already exists!");
		}else{
			instances.put(channel, this);
			file = new File("./schedules/schedules_" + channel.replace("#", "") + ".txt");
			file.getParentFile().mkdir();
			if(file.exists()) {
				loadSchedules();
			}else{
				try {
					file.createNewFile();
				} catch (IOException e) {
					logger.error("Failed to load schedules from file", e);
				}
				if(!file.exists()) {
					logger.error("Failed to load schedules from file");
				}
			}
			new Scheduler(channel);
			try {
				FileMonitor.getInstance().addFileChangeListener(new SchedulesListener(channel), file, 500);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static ScheduleManager getInstance(String channel) {
		if (!instances.containsKey(channel)) {
			new ScheduleManager(channel);
		}
		return instances.get(channel);
	}
	
	public void loadSchedules() {
		try {
			schedules.clear();
			BufferedReader buff = new BufferedReader(new FileReader(file));
			String line = "";

			while ((line = buff.readLine()) != null) {
				String[] args = line.split(" ", -1);
				int time;
				int id;
				try{
					time = Integer.valueOf(args[1]);
					id = Integer.valueOf(args[0]);
					addSchedule(new Schedule(StringUtils.joinString(StringUtils.dropStrings(args, 2)), time, id, System.currentTimeMillis() + new Random().nextInt(90000)), false);
		        }
				catch(NumberFormatException e)
		        {
					logger.warn("Unable to convert to integer", e);
		        }
			}
			buff.close();
		} catch (Exception e) {
			logger.error("Failed to load schedules from file", e);
		}
	}
	
	public void storeSchedules() {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(file, false));
			
			for(Schedule schedule : schedules) {
				writer.println(schedule.id + " " + schedule.time + " " + schedule.message);
			}

			writer.close();
		} catch (Exception e) {
			logger.error("Failed to store schedules to file", e);
		}
	}
	
	public ArrayList<Schedule> getSchedules() {
		return schedules;
	}

	public boolean addSchedule(Schedule newschedule, boolean shouldstore) {
		for(Schedule schedule : schedules) {
			if(schedule.id == newschedule.id) {
				logger.warn("Schedule id already in use!");
				return false;
			}
		}
		schedules.add(newschedule);
		if(shouldstore){
			storeSchedules();
		}
		return true;
	}
	
	public void removeSchedule(int id) {
		for(Schedule schedule : schedules) {
			if(schedule.id == id) {
				schedules.remove(schedule);
				storeSchedules();
				break;
			}
		}
		
		logger.warn("Can't find that schedule with id " + id);
	}
	
	public void clearSchedule() {
		schedules.clear();
		storeSchedules();
	}
}
