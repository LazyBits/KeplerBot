package net.keplerbot.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.keplerbot.Main;
import net.keplerbot.config.KeplerConfig;
import net.keplerbot.listeners.InputListener;
import net.keplerbot.log.KeplerLogger;

public class MainFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;

	private static KeplerLogger logger = KeplerLogger.getInstance("Main");
	
	public static MainFrame instance;
	
	private JPanel panel = new JPanel(new BorderLayout());
		
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	
	private static Map<String, LogPannel> logPannels = new HashMap<String, LogPannel>();	

	public MainFrame() {
		instance = this;
		
		setFont(new Font("Courier New", Font.PLAIN, 14));
		setResizable(false);
		setTitle("KeplerBot");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		setBounds(100, 100, 900, 500);
		panel.setBounds(0, 0, 900, 500);
		tabbedPane.setBounds(0, 0, 900, 400);
		tabbedPane.setBackground(new Color(60, 60, 60));

		panel.add(tabbedPane);
		setContentPane(panel);
		
		LogPannel logpannel = new LogPannel();
		logpannel.setName("Main");
		
		tabbedPane.add("Main", logpannel);
		
		logPannels.put("Main", logpannel);
        
		URL url = ClassLoader.getSystemResource("net/keplerbot/gui/icon.png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(url);
		setIconImage(img);
		
        final JTextField input = new JTextField();
        input.setBackground(new Color(90, 90, 90));
        final InputListener inputListener = new InputListener(logpannel.getName(), input);
        input.addActionListener(inputListener);
        this.add(input, "South");
        
		logger.info("Starting up KeplerBot MainFrame");
		logger.info("Java version: "+System.getProperty("java.version"));
		logger.info("Java vendor: "+System.getProperty("java.vendor"));
		logger.info("Java home: "+System.getProperty("java.home"));
		logger.info("Java specification: " + System.getProperty("java.vm.specification.name") + " version: " + System.getProperty("java.vm.specification.version") + " by " + System.getProperty("java.vm.specification.vendor"));
		logger.info("Java vm: "+System.getProperty("java.vm.name") + " version: " + System.getProperty("java.vm.version") + " by " + System.getProperty("java.vm.vendor"));
		logger.info("OS: "+System.getProperty("os.arch") + " " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
		logger.info("Working directory: " + System.getProperty("user.dir"));
		logger.info("Max Memory: " + Runtime.getRuntime().maxMemory() / 1024L / 1024L + " MB");
		
	 	tabbedPane.addChangeListener(new ChangeListener() {
	 		@Override
	 		public void stateChanged(ChangeEvent event){
	 			inputListener.setTab(tabbedPane.getSelectedComponent().getName());
	 		}
	 	});
	 	
		WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(null, "Are You Sure You Want To Close This Application?", "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
    				Main.instance.broadcast(KeplerConfig.getInstance().closeMessage);
                   System.exit(0);
                }
            }
        };
        addWindowListener(exitListener);
	}
	
	public static Map<String, LogPannel> getLogPannels() {
		return logPannels;
	}

	public static void log(String pannel, String log) {
		if (!logPannels.containsKey(pannel)) {
			return;
		}
		
		logPannels.get(pannel).addLogString(log);
	}
	
	public static JTabbedPane getTabbedPane() {
		return instance.tabbedPane;
	}

}
