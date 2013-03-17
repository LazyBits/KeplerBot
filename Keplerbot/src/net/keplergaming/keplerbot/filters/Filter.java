package net.keplergaming.keplerbot.filters;

import net.keplergaming.keplerbot.KeplerBot;
import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.permissions.PermissionsManager;

import org.pircbotx.Channel;
import org.pircbotx.User;

public class Filter{

	public boolean isDisabled() {
		return disabled;
	}

	private boolean disabled = false;

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getFilterName() {
		return null;
	}

	public boolean shouldUserBeFiltered(PermissionsManager permissionsManager, User sender) {
		if (permissionsManager.isDeveloper(sender.getNick())) {
			return false;
		} else if (permissionsManager.isModerator(sender.getNick())) {
			return false;
		} else if (permissionsManager.isStreamer(sender.getNick())) {
			return false;
		}
		return true;
	}

	public boolean shouldRemoveMessage(KeplerBotWrapper wrapper, KeplerBot bot, User sender, Channel channel, String message) {
		return false;
	}

	public void onPrivateMessage(KeplerBot bot, String message) {
	}
}
