package net.keplergaming.keplerbot.filters;

import net.keplergaming.keplerbot.KeplerBot;
import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.config.Configuration;
import net.keplergaming.keplerbot.permissions.PermissionsManager;

import org.pircbotx.Channel;
import org.pircbotx.User;

public abstract class Filter{

	public boolean isEnabled(Configuration config) {
		return config.getBoolean("filter_" + this.getFilterName(), true);
	}

	public void setEnabled(Configuration config, boolean enabled) {
		config.setBoolean("filter_" + this.getFilterName(), true);
	}

	public abstract String getFilterName();

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

	public abstract boolean shouldRemoveMessage(KeplerBotWrapper wrapper, KeplerBot bot, User sender, Channel channel, String message);

	public void onPrivateMessage(KeplerBot bot, String message) {
	}

	public abstract String getDefaultWarning();

	public String getWarning(Configuration config, int strike) {
		return config.getString("filter_" + this.getFilterName() + "_warning" + strike , getDefaultWarning());
	}

	public void setWarning(Configuration config, int strike, String warning) {
		config.setString("filter_" + this.getFilterName() + "_warning" + strike , warning);
	}

	public int getTimeOutValue(Configuration config, int strike) {
		int timeout;

		switch (strike) {
		case 1: 
			timeout = 30;
			break;
		case 2: 
			timeout = 300;
			break;
		case 3: 
			timeout = 1800;
			break;
		default : 
			timeout = 5;
			break;
		}

		return config.getInteger("filter_" + this.getFilterName() + "_timeout" + strike , timeout);
	}

	public void setTimeOutValue(Configuration config, int strike, int value) {
		config.setInteger("filter_" + this.getFilterName() + "_timeout" + strike , value);
	}

	public boolean isTimeOutEnabled(Configuration config, int strike) {
		boolean timeout = true;

		if (strike == 1) {
			timeout = false;
		}
		return config.getBoolean("filter_" + this.getFilterName() + "_timeout_enabled" + strike , timeout);
	}

	public void setTimeOutEnabled(Configuration config, int strike, boolean enabled) {
		config.setBoolean("filter_" + this.getFilterName() + "_timeout_enabled" + strike , enabled);
	}
}
