package net.keplergaming.keplerbot.filter;

import java.util.HashMap;
import java.util.Map;

import net.keplergaming.keplerbot.KeplerBot;
import net.keplergaming.keplerbot.exception.BotException;
import net.keplergaming.keplerbot.logger.StreamLogger;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class FilterManager extends ListenerAdapter<KeplerBot>{
	
	private Map<String, IFilter> filterMap;
	private StreamLogger logger;
	
	public FilterManager(StreamLogger logger) {
		this.logger = logger;
		filterMap = new HashMap<String, IFilter>();
	}
	
	public void registerFilter(IFilter filter) {
		if (filterMap.containsKey(filter)) {
			logger.warning("Filter already registered");
		}
		filterMap.put(filter.getFilterName(), filter);
	}

	public IFilter getFilter(String filter) throws BotException {
		if (!filterMap.containsKey(filter)) {
			throw new BotException("Filter " + filter + " not found");
		}
		return filterMap.get(filter);
	}

	@Override
	public void onMessage(MessageEvent<KeplerBot> event) {
		for (IFilter filter : filterMap.values()) {
			if (!filter.isDisabled()) {
				if (filter.shouldUserBeFiltered(event.getBot(), event.getUser(), event.getChannel())) {
					if (filter.shouldRemoveMessage(event.getBot(), event.getUser(), event.getChannel(), event.getMessage())) {
						removeMessage(event);
					}
				}
			}
		}
	}

	public void disableFilter(String filter) throws BotException {
		if (getFilter(filter).isDisabled()) {
			((Filter)getFilter(filter)).setDisabled(true);
		} else {
			throw new BotException("Filter " + filter + " already disabled");
		}
	}

	public void enableFilter(String filter) throws BotException {
		if (!getFilter(filter).isDisabled()) {
			((Filter)getFilter(filter)).setDisabled(false);
		} else {
			throw new BotException("Filter " + filter + " already enabled");
		}
	}

	public void removeMessage(MessageEvent<KeplerBot> event) {
		logger.fine("test");
	}

}
