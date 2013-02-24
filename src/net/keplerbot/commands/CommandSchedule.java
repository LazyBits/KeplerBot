package net.keplerbot.commands;

import java.util.ArrayList;

import net.keplerbot.KeplerBot;
import net.keplerbot.api.ICommand;
import net.keplerbot.commands.schedules.Schedule;
import net.keplerbot.log.KeplerLogger;
import net.keplerbot.managers.ModeratorManager;
import net.keplerbot.managers.ScheduleManager;
import net.keplerbot.utils.StringUtils;

public class CommandSchedule implements ICommand{

	@Override
	public String getCommand() {
		return "!schedule";
	}

	@Override
	public String getCommandUsage() {
		return "do !schedule [id] [time in min] [message] to schedule an automatic message. do !schedule clear to clear the entire schedule. do !schedule remove [id] to remove that schedule. do !schedule list to list all the schedules";
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
		if(args.length > 1) {
			if(args[0].equalsIgnoreCase("remove")) {
				int id;
				try{
					id = Integer.valueOf(args[1]);
		        }
				catch(NumberFormatException e)
		        {
					KeplerLogger.getInstance(channel).warn("Unable to convert to integer", e);
					bot.sendMessage(args[0] + " cannot be converted to a number.");
		        	return true;
		        }
				ScheduleManager.getInstance(channel).removeSchedule(id);
				bot.sendMessage("schedule has been removed");
				return true;
			}else{
				int time;
				int id;
				try{
					time = Integer.valueOf(args[1]);
					id = Integer.valueOf(args[0]);
		        }
				catch(NumberFormatException e)
		        {
					KeplerLogger.getInstance(channel).warn("Unable to convert to integer", e);
					bot.sendMessage(args[0] + " or " + args[1] + " cannot be converted to a number.");
		        	return true;
		        }
				
				if (!ScheduleManager.getInstance(channel).addSchedule(new Schedule(StringUtils.joinString(StringUtils.dropStrings(args, 2)), time, id, (int) System.currentTimeMillis()), true)) {
					bot.sendMessage("Schedule id already in use!");
					return true;
				}
				return true;
			}
		}else if(args.length == 1 && args[0].equalsIgnoreCase("clear")) {
			ScheduleManager.getInstance(channel).clearSchedule();
			return true;
		}else if(args.length == 1 && args[0].equalsIgnoreCase("list")) {
			ArrayList<String> schedulelist = new ArrayList<String>();
			for(Schedule schedule: ScheduleManager.getInstance(channel).getSchedules()) {
				schedulelist.add(schedule.id + " : " + schedule.time  + " : " + schedule.message);
			}
			bot.sendMessage(StringUtils.joinString(schedulelist));
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public void onMessage(KeplerBot bot, String channel, String sender, String message) {}
}
