package net.keplergaming.keplerbot.commands.defaults;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.commands.ICommand;
import net.keplergaming.keplerbot.permissions.PermissionsManager;

public class CommandCountdown implements ICommand, Runnable {

	public CommandCountdown(KeplerBotWrapper wrapper) {
		this.wrapper = wrapper;
	}

	@Override
	public String getCommandName() {
		return "countdown";
	}

	@Override
	public String[] getCommandAliases() {
		return null;
	}

	@Override
	public boolean canSenderUseCommand(PermissionsManager permissionsManager, String user) {
		return permissionsManager.isDeveloper(user) || permissionsManager.isModerator(user) || permissionsManager.isStreamer(user) || permissionsManager.isConsole(user);
	}

	@Override
	public void handleCommand(KeplerBotWrapper wrapper, String sender, String[] args) {
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("stop")) {
				if (sec != 0) {
					sec = 0;
					countDownThread = null;
					wrapper.sendMessage("Countdown stopped");
				} else {
					wrapper.sendError("Countdown not running");
				}
			} else {
				try {
					sec = Integer.parseInt(args[0]);
					countDownThread = new Thread(this);
					countDownThread.start();
					wrapper.sendMessage("Countdown started");
				} catch (Exception e) {
					wrapper.sendError(e.getMessage());
					wrapper.getStreamLogger().warning("Failed to execute command " + getCommandName(), e);
				}
			}
		} else if (args.length == 2 && args[0].equalsIgnoreCase("start")) {
			try {
				sec = Integer.parseInt(args[1]);
				countDownThread = new Thread(this);
				countDownThread.start();
				wrapper.sendMessage("Countdown started");
			} catch (Exception e) {
				wrapper.sendError(e.getMessage());
				wrapper.getStreamLogger().warning("Failed to execute command " + getCommandName(), e);
			}
		} else {
			if (sec != 0) {
				wrapper.sendMessage("Time left, " + String.format("%d:%02d:%02d", sec / 3600, (sec % 3600) / 60, (sec % 60)));
			} else {
				wrapper.sendError("Countdown not running");
			}
		}
	}

	@Override
	public String getCommandUsage() {
		return "!" + getCommandName() + " [start|stop] [seconds]";
	}

	@Override
	public void run() {
		while (sec > 0) {
			try {
				Thread.sleep(1000);
				sec--;
				if (sec == 0) {
					wrapper.sendMessage("Countdown stopped");
				} else if (sec <= 5) {
					wrapper.sendMessage("Countdown " + sec);
				}
			} catch (InterruptedException e) {
			}
		}
	}

	private int sec;
	private KeplerBotWrapper wrapper;
	private Thread countDownThread;
}
