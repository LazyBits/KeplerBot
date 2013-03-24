package net.keplergaming.keplerbot.commands.defaults;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.commands.ICommand;
import net.keplergaming.keplerbot.permissions.PermissionsManager;

public class CommandTime implements ICommand {

	@Override
	public String getCommandName() {
		return "time";
	}

	@Override
	public String[] getCommandAliases() {
		return new String[] { "date" };
	}

	@Override
	public boolean canSenderUseCommand(PermissionsManager permissionsManager, String user) {
		return true;
	}

	@Override
	public void handleCommand(KeplerBotWrapper wrapper, String sender, String[] args) {
		if (args.length == 1) {
			try {
				format.setTimeZone(TimeZone.getTimeZone(args[0]));
				wrapper.sendMessage(args[0] + ": " + format.format(new Date()));
			} catch (IllegalArgumentException e) {
				wrapper.sendError(e.getMessage());
				wrapper.getStreamLogger().warning(e.getMessage());
			}
		} else {
			format.setTimeZone(TimeZone.getDefault());
			wrapper.sendMessage("Bot time is: " + format.format(new Date()));
		}
	}

	@Override
	public String getCommandUsage() {
		return "!" + getCommandName() + " [TimeZone]";
	}

	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
