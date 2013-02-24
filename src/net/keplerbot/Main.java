package net.keplerbot;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import net.keplerbot.config.KeplerConfig;
import net.keplerbot.gui.LogPannel;
import net.keplerbot.gui.LoginDialog;
import net.keplerbot.gui.MainFrame;
import net.keplerbot.gui.UpdateDialog;
import net.keplerbot.listeners.ConfigListener;
import net.keplerbot.log.KeplerLogger;
import net.keplerbot.updater.UpdateChecker;

import org.essiembre.FileMonitor;

public class Main{
	
	private KeplerLogger logger = KeplerLogger.getInstance("Main");

	private Map<String, KeplerBot> bots = new HashMap<String, KeplerBot>();
	
	public static Main instance;
	
	public static int build = 99;
	
	public UpdateChecker checker;
	
	public boolean hasgui = true;
	
	public static void main(String[] args) {
		instance = new Main();
		if(args.length > 0 && (args[0].equalsIgnoreCase("-nogui") || args[0].equalsIgnoreCase("nogui"))) {
			instance.hasgui = false;
			instance.init();
		}else{
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					Color baseColor = new Color(20, 80, 120);

					UIManager.put("control", baseColor);
					UIManager.put("text", new Color(222, 222, 222));
					UIManager.put("nimbusBase", new Color(0, 0, 0));
					UIManager.put("nimbusFocus", baseColor);
					UIManager.put("nimbusBorder", baseColor);
					UIManager.put("nimbusLightBackground", baseColor);
					UIManager.put("info", new Color(55, 55, 55));

					try {
						for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
							if ("Nimbus".equals(info.getName())) {
								UIManager.setLookAndFeel(info.getClassName());
								break;
							}
						}
					} catch (Exception e) {
						try {
							UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
						} catch (ClassNotFoundException e1) { instance.logger.warn("Exception occured",e1); 
						} catch (InstantiationException e1) { instance.logger.warn("Exception occured",e1); 
						} catch (IllegalAccessException e1) { instance.logger.warn("Exception occured",e1); 
						} catch (UnsupportedLookAndFeelException e1) { instance.logger.warn("Exception occured",e1); }
					}

					instance.checker = new UpdateChecker(build);
					if(instance.checker.shouldUpdate()) {
						new UpdateDialog().setVisible(true);
					}else{
						KeplerLogger.getInstance("Main").info("version equals the latest version, Starting Mainframe");
						MainFrame frame = new MainFrame();
						frame.setVisible(true);
						instance.checkLogin();
					}
				}
			});
		}
	}
	
	public void checkLogin() {
		if(KeplerConfig.getInstance().login.equals("/") || KeplerConfig.getInstance().pass.equals("/")) {
			if(hasgui) {
				new LoginDialog(MainFrame.instance, true).setVisible(true);
			}else{
				System.exit(1);
			}
		}else{
			init();
		}
	}
	
	public void init() {
		KeplerConfig.init();
		if(KeplerConfig.getInstance().joinOwnChannel) {
			addStreamer(KeplerConfig.getInstance().login);
		}
		try {
			FileMonitor.getInstance().addFileChangeListener(new ConfigListener(), new File("./config.xml"), 500);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void createBot(String name) {
		logger.info("Creating new bot for " + name);
		KeplerBot bot = new KeplerBot(name);
		bots.put(name, bot);
	}
	
	private void createLogPannel(String name) {
		LogPannel logpannel = new LogPannel();
		logpannel.setName(name);
		MainFrame.getTabbedPane().add(name, logpannel);
		MainFrame.getLogPannels().put(name, logpannel);
	}
	
	public void addStreamer(String name) {
		if (MainFrame.getLogPannels().containsKey(name)) {
			logger.warn(name + " already exists!");
			return;
		}
		
		createBot(name);
		if(hasgui) {
			createLogPannel(name);
		}
	}
	
	public KeplerBot getBot(String bot) {
		if(!bots.containsKey(bot)) {
			logger.warn("Can't find bot " + bot);
		}
		
		return bots.get(bot);
	}
	
	public Collection<KeplerBot> getBots() {
		return bots.values();
	}

	public boolean markremoveBot(String bot) {
		if(!bots.containsKey(bot)) {
			logger.warn("Can't find bot " + bot);
			return false;
		}
		getBot(bot).sendRawLine("PRIVMSG #" + bot + " :" + KeplerConfig.getInstance().closeMessage);
		bots.get(bot).sendRawLineViaQueue("QUIT :" + "keplerbot leaving :z");
		bots.get(bot).setDisposeMark();
		return true;
	}
	
	public void removeBot(String bot) {
		bots.get(bot).dispose();
		bots.remove(bot);
		if(hasgui) {
			removeLogPannel(bot);
		}
	}
	
	public void removeLogPannel(String pannel) {
		if(!MainFrame.getLogPannels().containsKey(pannel)) {
			logger.warn("Can't pannel " + pannel);
		}
		
		MainFrame.getTabbedPane().remove(MainFrame.getLogPannels().get(pannel));
		MainFrame.getLogPannels().remove(pannel);
		System.gc();
	}
	
    public void broadcast(String message) {
		KeplerLogger.getInstance("Main").info("Sending " + message + " to all connected streams");
		for(KeplerBot bot : getBots()) {
			bot.sendMessage(message);
		}
    }
}
