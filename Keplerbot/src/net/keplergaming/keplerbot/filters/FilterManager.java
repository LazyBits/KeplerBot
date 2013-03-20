package net.keplergaming.keplerbot.filters;

import java.util.HashMap;
import java.util.Map;

import net.keplergaming.keplerbot.KeplerBot;
import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.exception.BotException;
import net.keplergaming.keplerbot.logger.StreamLogger;

import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;

public class FilterManager extends ListenerAdapter<KeplerBot>{
	
	private Map<String, Filter> filterMap;
	private StreamLogger logger;
	private KeplerBotWrapper wrapper;
	private Map<String, Integer> strikesMap;
	
	public FilterManager(KeplerBotWrapper wrapper) {
		this.logger = wrapper.getStreamLogger();
		this.wrapper = wrapper;
		filterMap = new HashMap<String, Filter>();
		strikesMap = new HashMap<String, Integer>();
	}
	
	public void registerFilter(Filter filter) {
		if (filterMap.containsKey(filter)) {
			logger.warning("Filter already registered");
		}
		filterMap.put(filter.getFilterName(), filter);
	}

	public Filter getFilter(String filter) throws BotException {
		if (!filterMap.containsKey(filter)) {
			throw new BotException("Filter " + filter + " not found");
		}
		return filterMap.get(filter);
	}

	@Override
	public void onMessage(MessageEvent<KeplerBot> event) {
		for (Filter filter : filterMap.values()) {
			if (filter.isEnabled(wrapper.getConfig())) {
				if (filter.shouldUserBeFiltered(wrapper.getPermissionsManager(), event.getUser())) {
					if (filter.shouldRemoveMessage(wrapper, event.getBot(), event.getUser(), event.getChannel(), event.getMessage())) {
						removeMessage(filter, event.getUser());
					}
				}
			}
		}
	}

	@Override
	public void onPrivateMessage(PrivateMessageEvent<KeplerBot> event) {
		if (event.getUser().getNick().equals("jtv")) {
			for (Filter filter : filterMap.values()) {
				if (filter.isEnabled(wrapper.getConfig())) {
					filter.onPrivateMessage(event.getBot(), event.getMessage());
				}
			}
		}
	}

	public void removeMessage(Filter filter, User sender) {
		addStrike(sender);

		if (filter.isTimeOutEnabled(wrapper.getConfig(), getStrikes(sender))) {
			wrapper.getBot().sendMessage(wrapper.getChannel(), "/timeout " + sender.getNick() + " " + filter.getTimeOutValue(wrapper.getConfig(), getStrikes(sender)));
		} else {
			wrapper.getBot().sendMessage(wrapper.getChannel(), "/timeout " + sender.getNick() + " " + filter.getTimeOutValue(wrapper.getConfig(), 5));
			wrapper.getBot().sendMessage(wrapper.getChannel(), String.format(filter.getWarning(wrapper.getConfig(), getStrikes(sender)), sender.getNick()));
		}
	}

	public void addStrike(User user) {
		int strikes = 0;
		if (strikesMap.containsKey(user.getNick())) {
			strikes = strikesMap.get(user.getNick());
		}
		strikesMap.put(user.getNick(), strikes + 1);
	}

	public int getStrikes(User user) {
		return strikesMap.get(user.getNick());
	}

	public void resetStrikes(User user) {
		strikesMap.remove(user.getNick());
	}
}
