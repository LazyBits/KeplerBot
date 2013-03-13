package net.keplergaming.keplerbot.gui;

import java.awt.Font;
import java.awt.SystemColor;
import java.util.logging.Level;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.config.Configuration;
import net.keplergaming.keplerbot.logger.ILogListener;
import net.keplergaming.keplerbot.logger.Logger;

@SuppressWarnings("serial")
public class StreamLogPannel extends JScrollPane implements ILogListener {

	private JTextArea textArea;
	private KeplerBotWrapper wrapper;
	private Configuration config;
	private String streamer;

	public StreamLogPannel(Configuration config, String streamer, boolean joinMessage) {
		textArea = new JTextArea();
		textArea.setFocusable(false);
		textArea.setTabSize(6);
		textArea.setFont(new Font("Dialog", Font.PLAIN, 13));
		textArea.setEditable(false);
		textArea.setBackground(SystemColor.text);
		add(textArea);
		setViewportView(textArea);

		this.config = config;
		this.streamer = streamer;
		wrapper = new KeplerBotWrapper(this, config, streamer, joinMessage);
	}

	@Override
	public Level getLoglevel() {
		return Level.ALL;
	}

	@Override
	public void onLog(String formattedMessage, String message, Level logLevel, Throwable t) {
		textArea.append(formattedMessage + "\n");
		if (t != null) {
			textArea.append(Logger.throwableToString(t) + "\n");
		}

		textArea.selectAll();
	}

	public KeplerBotWrapper getWrapper() {
		return wrapper;
	}

	public void resetBot() {
		wrapper.dispose(false);
		
		wrapper = new KeplerBotWrapper(this, config, streamer, false);
	}
}