package net.keplergaming.keplerbot.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import net.keplergaming.keplerbot.config.Configuration;
import net.keplergaming.keplerbot.logger.MainLogger;
import net.keplergaming.keplerbot.utils.DesktopUtils;
import net.keplergaming.keplerbot.version.Version;
import net.keplergaming.keplerbot.version.VersionChecker;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame {

	private static MainFrame Instance;
	private JFrame frmKeplerbot;

	private Configuration config;

	private ErrorPanel errorPanel = new ErrorPanel();
	private JTextField chatBox;

	private VersionChecker versionChecker;
	private Thread checkerThread;
	private JTabbedPane streamTabs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainLogger.info("Starting KeplerBot "+ Version.getVersion());

					MainLogger.info("Starting up KeplerBot MainFrame");
					MainLogger.info("Java version: " + System.getProperty("java.version"));
					MainLogger.info("Java vendor: " + System.getProperty("java.vendor"));
					MainLogger.info("Java home: " + System.getProperty("java.home"));
					MainLogger.info("Java specification: " + System.getProperty("java.vm.specification.name") + " version: " + System.getProperty("java.vm.specification.version") + " by " + System.getProperty("java.vm.specification.vendor"));
					MainLogger.info("Java vm: " + System.getProperty("java.vm.name") + " version: " + System.getProperty("java.vm.version") + " by " + System.getProperty("java.vm.vendor"));
					MainLogger.info("OS: " + System.getProperty("os.arch") + " " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
					MainLogger.info("Working directory: " + System.getProperty("user.dir"));
					MainLogger.info("Max Memory: " + Runtime.getRuntime().maxMemory() / 1024L / 1024L + " MB");

					MainLogger.fine("Setting look and feel of the gui");
					UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");

					MainLogger.fine("Creating new Mainframe");
					new MainFrame();
				} catch (Exception e) {
					MainLogger.error("Failed to load MainFrame", e);
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		Instance = this;

		config = new Configuration("keplerbot.properties");
		config.loadConfig();

		MainLogger.fine("Creating gui");
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmKeplerbot = new JFrame();
		frmKeplerbot.setResizable(false);
		frmKeplerbot.setTitle("Keplerbot");
		frmKeplerbot.setBounds(100, 100, 800, 500);
		frmKeplerbot.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MainLogger.fine("Creating threaded version checker");
		checkerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				versionChecker = new VersionChecker();

				if (versionChecker.requiresUpdate()) {
					MainLogger.info("Update found");

					int confirm = JOptionPane.showOptionDialog(null, "Would you like to update?", "Update Available", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
					if (confirm == 0) {
						versionChecker.startUpdater();
					}
				} else {
					MainLogger.info("KeplerBot is up to date");
				}
			}

		});
		checkerThread.start();

		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setFocusable(false);

		JLabel lblVersion = new JLabel(Version.getVersion());
		lblVersion.setToolTipText("Up to date\r\n");
		lblVersion.setHorizontalAlignment(SwingConstants.RIGHT);
		GroupLayout groupLayout = new GroupLayout(frmKeplerbot.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE)).addGroup(groupLayout.createSequentialGroup().addComponent(lblVersion, GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout.createSequentialGroup().addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 438, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(lblVersion, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE).addGap(4)));

		final JPanel homePanel = new JPanel();
		homePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		homePanel.setBackground(SystemColor.activeCaptionBorder);
		tabbedPane.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Home</body></html>", null, homePanel, null);

		errorPanel = new ErrorPanel();
		errorPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));

		GroupLayout gl_homePanel = new GroupLayout(homePanel);
		gl_homePanel.setHorizontalGroup(gl_homePanel.createParallelGroup(Alignment.LEADING).addGroup(gl_homePanel.createSequentialGroup().addContainerGap().addComponent(errorPanel, GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE).addContainerGap()));
		gl_homePanel.setVerticalGroup(gl_homePanel.createParallelGroup(Alignment.LEADING).addGroup(gl_homePanel.createSequentialGroup().addContainerGap().addComponent(errorPanel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE).addContainerGap(391, Short.MAX_VALUE)));
		homePanel.setLayout(gl_homePanel);

		final JPanel streamPanel = new JPanel();
		streamPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		streamPanel.setBackground(SystemColor.activeCaptionBorder);
		tabbedPane.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Streams</body></html>", null, streamPanel, null);

		JButton btnResetStream = new JButton("Reset Connection");
		btnResetStream.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (streamTabs.getSelectedComponent() != null) {
					((StreamLogPannel)streamTabs.getSelectedComponent()).resetBot();
				}
			}
		});
		btnResetStream.setFocusable(false);

		JButton btnRemoveStream = new JButton("Close Stream");
		btnRemoveStream.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (streamTabs.getSelectedComponent() != null) {
					CloseStreamDialog dialog = new CloseStreamDialog(frmKeplerbot);
					dialog.setVisible(true);
				}
			}
		});
		btnRemoveStream.setFocusable(false);

		JButton btnAddStream = new JButton("Connect to Stream");
		btnAddStream.setFocusable(false);
		btnAddStream.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final AddStreamDialog dialog = new AddStreamDialog(frmKeplerbot);
				dialog.setVisible(true);
			}
		});

		streamTabs = new JTabbedPane(JTabbedPane.TOP);
		streamTabs.setFocusable(false);
		streamTabs.setBackground(SystemColor.inactiveCaptionBorder);

		final DefaultComboBoxModel model = new DefaultComboBoxModel();
		model.addElement("Add Preset");

		JComboBox comboBoxPresets = new JComboBox();
		comboBoxPresets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (model.getSelectedItem().equals("Add Preset")) {
					String stream = JOptionPane.showInputDialog(frmKeplerbot, "Stream name", "Add Preset", JOptionPane.PLAIN_MESSAGE);
					if (stream != null && !stream.isEmpty()) {
						model.addElement(stream);
						model.setSelectedItem(stream);
					}
				}
			}
		});
		comboBoxPresets.setModel(model);

		JLabel lblPresets = new JLabel("Presents");

		chatBox = new JTextField();
		chatBox.setColumns(10);

		JButton btnAddPreset = new JButton("Connect");
		btnAddPreset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!model.getSelectedItem().equals("Add Preset")) {
					int joinMessage = JOptionPane.showConfirmDialog(frmKeplerbot, "Would you like to show the join message?");

					if (joinMessage != 2) {
						MainFrame.getInstance().addStream((String) model.getSelectedItem(), joinMessage == 0);
					}
				}
			}
		});
		btnAddPreset.setFocusable(false);

		JButton btnSend = new JButton("Send");
		btnSend.setFocusable(false);
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		GroupLayout gl_streamPanel = new GroupLayout(streamPanel);
		gl_streamPanel.setHorizontalGroup(gl_streamPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_streamPanel.createSequentialGroup().addContainerGap().addGroup(gl_streamPanel.createParallelGroup(Alignment.LEADING).addComponent(lblPresets).addComponent(btnRemoveStream, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE).addComponent(btnResetStream, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE).addComponent(btnAddStream, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE).addComponent(btnAddPreset, GroupLayout.PREFERRED_SIZE, 155, Short.MAX_VALUE).addComponent(comboBoxPresets, 0, 155, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_streamPanel.createParallelGroup(Alignment.TRAILING).addGroup(gl_streamPanel.createSequentialGroup().addComponent(chatBox, GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSend)).addComponent(streamTabs, GroupLayout.PREFERRED_SIZE, 489, GroupLayout.PREFERRED_SIZE)).addGap(7)));
		gl_streamPanel.setVerticalGroup(gl_streamPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_streamPanel.createSequentialGroup().addGroup(gl_streamPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_streamPanel.createSequentialGroup().addContainerGap().addComponent(lblPresets).addPreferredGap(ComponentPlacement.RELATED).addComponent(comboBoxPresets, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnAddPreset).addGap(72).addComponent(btnAddStream).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnRemoveStream).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnResetStream)).addComponent(streamTabs, GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_streamPanel.createParallelGroup().addComponent(btnSend).addComponent(chatBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addContainerGap()));
		streamPanel.setLayout(gl_streamPanel);

		final JPanel configPanel = new JPanel();
		configPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		configPanel.setBackground(SystemColor.activeCaptionBorder);
		tabbedPane.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Config</body></html>", null, configPanel, null);

		final JPanel loginPanel = new JPanel();
		loginPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		loginPanel.setBackground(SystemColor.activeCaptionBorder);

		final JPanel generalPanel = new JPanel();
		generalPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		generalPanel.setBackground(SystemColor.activeCaptionBorder);

		final ConfigFieldPanel botNameConfig = new ConfigFieldPanel("Bot name", Configuration.BOT_NAME[0], Configuration.BOT_NAME[1], generalPanel.getBackground());

		final ConfigFieldPanel joinMessageConfig = new ConfigFieldPanel("Join Message", Configuration.JOIN_MESSAGE[0], String.format(Configuration.JOIN_MESSAGE[1], botNameConfig.getValue()), generalPanel.getBackground());

		final ConfigFieldPanel leaveConfig = new ConfigFieldPanel("Leave Message", Configuration.LEAVE_MESSAGE[0], String.format(Configuration.LEAVE_MESSAGE[1], botNameConfig.getValue()), generalPanel.getBackground());

		GroupLayout gl_generalPanel = new GroupLayout(generalPanel);
		gl_generalPanel.setHorizontalGroup(gl_generalPanel.createParallelGroup(Alignment.LEADING).addGroup( gl_generalPanel.createSequentialGroup().addContainerGap().addGroup(gl_generalPanel.createParallelGroup(Alignment.LEADING).addComponent(botNameConfig, GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE).addComponent(joinMessageConfig, GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE).addComponent(leaveConfig, GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)).addContainerGap()));gl_generalPanel.setVerticalGroup(gl_generalPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_generalPanel.createSequentialGroup().addContainerGap().addComponent(botNameConfig, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(joinMessageConfig, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(leaveConfig, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		generalPanel.setLayout(gl_generalPanel);
		GroupLayout gl_configPanel = new GroupLayout(configPanel);gl_configPanel.setHorizontalGroup(gl_configPanel.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, gl_configPanel.createSequentialGroup().addContainerGap().addGroup(gl_configPanel.createParallelGroup(Alignment.TRAILING).addComponent(generalPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE).addComponent(loginPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)).addContainerGap()));
		gl_configPanel.setVerticalGroup(gl_configPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_configPanel.createSequentialGroup().addContainerGap().addComponent(loginPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(generalPanel, GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addContainerGap(232, Short.MAX_VALUE)));

		final ConfigFieldPanel usernameConfig = new ConfigFieldPanel("Twitch username", Configuration.USERNAME[0], Configuration.USERNAME[1], loginPanel.getBackground());
		final ConfigFieldPanel passwordConfig = new ConfigFieldPanel("Password", Configuration.PASSWORD[0], Configuration.PASSWORD[1], loginPanel.getBackground());

		GroupLayout gl_loginPanel = new GroupLayout(loginPanel);
		gl_loginPanel.setHorizontalGroup(gl_loginPanel.createParallelGroup(Alignment.TRAILING).addGroup(gl_loginPanel.createSequentialGroup().addContainerGap().addGroup(gl_loginPanel.createParallelGroup(Alignment.TRAILING).addComponent(passwordConfig, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE).addComponent(usernameConfig, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap()));gl_loginPanel.setVerticalGroup(gl_loginPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_loginPanel.createSequentialGroup().addGap(10).addComponent(usernameConfig, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(passwordConfig, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		loginPanel.setLayout(gl_loginPanel);
		configPanel.setLayout(gl_configPanel);

		configPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentHidden(ComponentEvent arg0) {
				config.setString(botNameConfig.getConfigKey(), botNameConfig.getValue());
				config.setString(joinMessageConfig.getConfigKey(), joinMessageConfig.getValue());
				config.setString(leaveConfig.getConfigKey(), leaveConfig.getValue());
				config.setString(usernameConfig.getConfigKey(), usernameConfig.getValue());
				config.setString(passwordConfig.getConfigKey(), passwordConfig.getValue());

				config.saveConfig();
			}
		});

		frmKeplerbot.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				config.setString(botNameConfig.getConfigKey(), botNameConfig.getValue());
				config.setString(joinMessageConfig.getConfigKey(), joinMessageConfig.getValue());
				config.setString(leaveConfig.getConfigKey(), leaveConfig.getValue());
				config.setString(usernameConfig.getConfigKey(), usernameConfig.getValue());
				config.setString(passwordConfig.getConfigKey(), passwordConfig.getValue());

				config.saveConfig();
			}
		});

		JPanel aboutPanel = new JPanel();
		aboutPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		aboutPanel.setBackground(SystemColor.activeCaptionBorder);
		tabbedPane.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>About</body></html>", null, aboutPanel, null);
		aboutPanel.setLayout(null);

		JTextPane aboutTextPane = new JTextPane();
		aboutTextPane.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent arg0) {
				if (HyperlinkEvent.EventType.ACTIVATED.equals(arg0.getEventType())) {
					DesktopUtils.openUrl(arg0.getURL().toString());
				}
			}
		});
		aboutTextPane.setFont(new Font("Dialog", Font.BOLD, 13));
		aboutTextPane.setBounds(9, 9, 639, 143);
		aboutTextPane.setBackground(SystemColor.activeCaptionBorder);
		aboutTextPane.setEditable(false);
		aboutTextPane.setContentType("text/html");
		aboutTextPane.setText("<center><a rel=\"license\" href=\"http://creativecommons.org/licenses/by-nc-sa/3.0/\"><img alt=\"Creative Commons License\" style=\"border-width:0\" src=\"http://i.creativecommons.org/l/by-nc-sa/3.0/88x31.png\" /></a><br /><span xmlns:dct=\"http://purl.org/dc/terms/\" href=\"http://purl.org/dc/dcmitype/Dataset\" property=\"dct:title\" rel=\"dct:type\">KeplerBot</span> by <a xmlns:cc=\"http://creativecommons.org/ns#\" href=\"http://keplergaming.com/\" property=\"cc:attributionName\" rel=\"cc:attributionURL\">Crazyputje and Logomaster256</a> is licensed under a <a rel=\"license\" href=\"http://creativecommons.org/licenses/by-nc-sa/3.0/\">Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License</a>.<br /><br />Based on a work at <a xmlns:dct=\"http://purl.org/dc/terms/\" href=\"https://code.google.com/p/pircbotx\" rel=\"dct:source\">https://code.google.com/p/pircbotx</a>.</center>");
		aboutPanel.add(aboutTextPane);

		ImagePanel keplerImage = new ImagePanel(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/net/keplergaming/keplerbot/resources/logo.png")));
		keplerImage.setBounds(297, 228, 351, 292);
		aboutPanel.add(keplerImage);
		keplerImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DesktopUtils.openUrl("www.keplergaming.com");
			}
		});

		ImagePanel githubImage = new ImagePanel(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/net/keplergaming/keplerbot/resources/github.png")));
		githubImage.setBounds(9, 291, 269, 125);
		aboutPanel.add(githubImage);
		githubImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				DesktopUtils.openUrl("https://github.com/KeplerGaming/KeplerBot");
			}
		});

		frmKeplerbot.getContentPane().setLayout(groupLayout);
		frmKeplerbot.setVisible(true);
	}

	public void addError(String key, String message) {
		MainLogger.fine("Adding error to errorPanel");
		errorPanel.addError(key, message);
	}

	public void removeError(String key) {
		MainLogger.fine("removing error from errorPanel");
		errorPanel.removeError(key);
	}

	public static MainFrame getInstance() {
		return Instance;
	}

	public Configuration getConfig() {
		return config;
	}

	public void addStream(String streamer, boolean joinMessage) {
		StreamLogPannel panel = new StreamLogPannel(config, streamer, joinMessage);
		panel.setName(streamer);

		streamTabs.add(panel);
	}

	public void closeVisibleStream(boolean showMessage) {
		StreamLogPannel panel = (StreamLogPannel)streamTabs.getSelectedComponent();
		
		if (panel != null) {
			panel.getWrapper().dispose(showMessage);
			streamTabs.remove(panel);
		}
	}
}
