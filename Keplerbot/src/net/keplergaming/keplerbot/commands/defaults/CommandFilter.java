package net.keplergaming.keplerbot.commands.defaults;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.commands.ICommand;
import net.keplergaming.keplerbot.exception.BotException;
import net.keplergaming.keplerbot.filters.Filter;
import net.keplergaming.keplerbot.permissions.PermissionsManager;

import org.pircbotx.Channel;
import org.pircbotx.User;

public class CommandFilter implements ICommand{

	@Override
	public String getCommandName() {
		return "filter";
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
		if (args.length == 2) {
			try {
				Filter filter = wrapper.getFilterManager().getFilter(args[0]);
				if (args[1].equalsIgnoreCase("on")) {
					wrapper.sendMessage(channel, "Filter enabled");
					filter.setDisabled(false);
				} else if (args[1].equalsIgnoreCase("off")) {
					wrapper.sendMessage(channel, "Filter disabled");
					filter.setDisabled(true);
				} else {
					throw new BotException("Argument doesn't equal 'on' or 'off'");
				}
			} catch (BotException e) {
				wrapper.sendError(channel, e.getMessage());
				wrapper.getStreamLogger().warning("Failed to execute command " + getCommandName(), e);
			}
		} else {
			wrapper.sendMessage(channel, getCommandUsage());
		}
	}

	@Override
	public String getCommandUsage() {
		return "!" + getCommandName() + " [caps|color|links|censor] [on|off]";
	}

}
