package net.keplergaming.keplerbot.commands.defaults;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.commands.ICommand;
import net.keplergaming.keplerbot.exception.BotException;
import net.keplergaming.keplerbot.filters.Filter;
import net.keplergaming.keplerbot.permissions.PermissionsManager;

public class CommandFilter implements ICommand {

	@Override
	public String getCommandName() {
		return "filter";
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
	public void handleCommand(KeplerBotWrapper wrapper, String sender, String[] args) throws BotException {
		if (args.length == 2) {
			Filter filter = wrapper.getFilterManager().getFilter(args[0]);
			if (args[1].equalsIgnoreCase("on")) {
				wrapper.sendMessage("Filter enabled");
				filter.setEnabled(wrapper.getConfig(), true);
			} else if (args[1].equalsIgnoreCase("off")) {
				wrapper.sendMessage("Filter disabled");
				filter.setEnabled(wrapper.getConfig(), false);
			} else {
				throw new BotException("Argument doesn't equal 'on' or 'off'");
			}
		} else {
			wrapper.sendMessage(getCommandUsage());
		}
	}

	@Override
	public String getCommandUsage() {
		return "!" + getCommandName() + " [caps|color|links|censor] [on|off]";
	}
}
