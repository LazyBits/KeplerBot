package net.keplergaming.keplerbot.filters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.keplergaming.keplerbot.KeplerBot;
import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.config.Configuration;
import net.keplergaming.keplerbot.permissions.PermissionsManager;

import org.pircbotx.Channel;
import org.pircbotx.User;

public class LinkFilter extends Filter{

	public LinkFilter(Configuration config) {
		setDisabled(config.getBoolean(Configuration.LINK_FILTER[0], Boolean.parseBoolean(Configuration.LINK_FILTER[1])));
	}

	@Override
	public String getFilterName() {
		return "links";
	}

	@Override
	public boolean shouldUserBeFiltered(PermissionsManager permissionsManager, User sender) {
		
		return super.shouldUserBeFiltered(permissionsManager, sender);
	}

	@Override
	public boolean shouldRemoveMessage(KeplerBotWrapper wrapper, KeplerBot bot, User sender, Channel channel, String message) {
		Matcher matches = URL_PATTERN.matcher(message);
		if (matches.find()) {
			return true;
		}
		return false;
	}

	public static Pattern URL_PATTERN = Pattern.compile("^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
}
