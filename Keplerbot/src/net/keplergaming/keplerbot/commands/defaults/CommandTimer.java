package net.keplergaming.keplerbot.commands.defaults;

import org.pircbotx.Channel;
import org.pircbotx.User;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.commands.ICommand;
import net.keplergaming.keplerbot.permissions.PermissionsManager;

public class CommandTimer implements ICommand, Runnable {

	@Override
	public String getCommandName() {
		return "timer";
	}

	@Override
	public String[] getCommandAliases() {
		return null;
	}

	@Override
	public boolean canSenderUseCommand(PermissionsManager permissionsManager, User user) {
		return permissionsManager.isDeveloper(user.getNick()) || permissionsManager.isModerator(user.getNick()) || permissionsManager.isStreamer(user.getNick());
	}

	@Override
	public void handleCommand(KeplerBotWrapper wrapper, User sender, Channel channel, String[] args) {
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("stop")) {
				if (!stopped) {
					stopped = true;
					sec = 0;
					timerThread = null;
					wrapper.sendMessage(channel, "Timer stopped");
				} else {
					wrapper.sendMessage(channel, "Timer not running");
				}
			} else if (args[0].equalsIgnoreCase("start")) {
				if (stopped) {
					stopped = false;
					timerThread = new Thread(this);
					timerThread.start();
					wrapper.sendMessage(channel, "Timer started");
				}
			} else {
				wrapper.sendMessage(channel, getCommandUsage());
			}
		} else {
			if (sec != 0) {
				wrapper.sendMessage(channel, "Time running, " + String.format("%d:%02d:%02d", sec / 3600, (sec % 3600) / 60, (sec % 60)));
			} else {
				wrapper.sendMessage(channel, "Timer not running");
			}
		}
	}

	@Override
	public String getCommandUsage() {
		return "!" + getCommandName() + " [start|stop]";
	}

	@Override
	public void run() {
		while (!stopped) {
			try {
				Thread.sleep(1000);
				sec++;
			} catch (InterruptedException e) {
			}
		}
	}

	private int sec;
	private boolean stopped = false;;
	private Thread timerThread;
}
