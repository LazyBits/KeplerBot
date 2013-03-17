package net.keplergaming.keplerbot.permissions;

import net.keplergaming.keplerbot.KeplerBot;
import net.keplergaming.keplerbot.KeplerBotWrapper;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.PrivateMessageEvent;

public class PermissionsManager extends ListenerAdapter<KeplerBot> implements Runnable{

	public PermissionsManager(KeplerBotWrapper wrapper) {
		this.wrapper = wrapper;
		new Thread(this).start();
	}

	private KeplerBotWrapper wrapper;
	private String[] moderators;
	private boolean stop = false;

	@Override
	public void onPrivateMessage(PrivateMessageEvent<KeplerBot> event) {
		if (event.getUser().getNick().equals("jtv") && event.getMessage().startsWith("The moderators of this room are: ")) {
			moderators = event.getMessage().replace("The moderators of this room are: ", "").split(", ");
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(100000);
				if (!stop) {
					wrapper.getBot().sendMessage(wrapper.getChannel(), "/mods");
				} else {
					break;
				}
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				wrapper.getStreamLogger().warning("Permissions Manager Interrupted");
			}
		}
	}

	public boolean isModerator(String nick) {
		if (moderators != null) {
			for (String moderator : moderators) {
				if (moderator.equalsIgnoreCase(nick)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isDeveloper(String nick) {
		return nick.equalsIgnoreCase("crazyputje") || nick.equalsIgnoreCase("logomaster256") || nick.equalsIgnoreCase("spacerules");
	}

	public boolean isStreamer(String nick) {
		return nick.equalsIgnoreCase(wrapper.getStreamer());
	}

	public void stopThread() {
		stop = true;
	}
}
