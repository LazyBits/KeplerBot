package net.keplergaming.keplerbot.filters;

import java.util.HashMap;
import java.util.Map;

import net.keplergaming.keplerbot.KeplerBot;
import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.exception.BotException;
import net.keplergaming.keplerbot.logger.StreamLogger;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;

public class FilterManager extends ListenerAdapter<KeplerBot>{
	
	private Map<String, Filter> filterMap;
	private StreamLogger logger;
	private KeplerBotWrapper wrapper;
	
	public FilterManager(KeplerBotWrapper wrapper) {
		this.logger = wrapper.getStreamLogger();
		this.wrapper = wrapper;
		filterMap = new HashMap<String, Filter>();
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
		return (Filter) filterMap.get(filter);
	}

	@Override
	public void onMessage(MessageEvent<KeplerBot> event) {
		for (Filter filter : filterMap.values()) {
			if (!filter.isDisabled()) {
				if (filter.shouldUserBeFiltered(wrapper.getPermissionsManager(), event.getUser())) {
					if (filter.shouldRemoveMessage(wrapper, event.getBot(), event.getUser(), event.getChannel(), event.getMessage())) {
						removeMessage(event);
					}
				}
			}
		}
	}

	@Override
	public void onPrivateMessage(PrivateMessageEvent<KeplerBot> event) {
		if (event.getUser().getNick().equals("jtv")) {
			for (Filter filter : filterMap.values()) {
				if (!filter.isDisabled()) {
					filter.onPrivateMessage(event.getBot(), event.getMessage());
				}
			}
		}
	}

	public void removeMessage(MessageEvent<KeplerBot> event) {
		wrapper.getBot().sendMessage(wrapper.getChannel(), "/timeout " + event.getUser().getNick() + " 5");
	}

}
