package net.keplergaming.keplerbot.gui;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import net.keplergaming.keplerbot.config.ConfigConstants;
import net.keplergaming.keplerbot.config.Configuration;
import net.keplergaming.keplerbot.logger.MainLogger;
import net.keplergaming.keplerbot.preset.PresetHandler;
import net.keplergaming.keplerbot.utils.DesktopUtils;
import net.keplergaming.keplerbot.version.Version;
import net.keplergaming.keplerbot.version.VersionChecker;

import com.github.rjeschke.txtmark.Processor;

public class MainFrame {

	private static MainFrame Instance;
	private JFrame frmKeplerbot;

	private Configuration config;

	private PresetHandler presetHandler;

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
			@Override
			public void run() {
				try {
					MainLogger.info("Starting KeplerBot "+ Version.VERSION);

					MainLogger.fine("Starting up KeplerBot MainFrame");
					MainLogger.fine("Java version: " + System.getProperty("java.version"));
					MainLogger.fine("Java vendor: " + System.getProperty("java.vendor"));
					MainLogger.fine("Java home: " + System.getProperty("java.home"));
					MainLogger.fine("Java specification: " + System.getProperty("java.vm.specification.name") + " version: " + System.getProperty("java.vm.specification.version") + " by " + System.getProperty("java.vm.specification.vendor"));
					MainLogger.fine("Java vm: " + System.getProperty("java.vm.name") + " version: " + System.getProperty("java.vm.version") + " by " + System.getProperty("java.vm.vendor"));
					MainLogger.fine("OS: " + System.getProperty("os.arch") + " " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
					MainLogger.fine("Working directory: " + System.getProperty("user.dir"));
					MainLogger.fine("Max Memory: " + Runtime.getRuntime().maxMemory() / 1024L / 1024L + " MB");

					MainLogger.fine("Setting look and feel of the gui");
					Properties props = new Properties();
					props.put("macStyleWindowDecoration", "on");
					props.put("linuxStyleScrollBar", "on");
					com.jtattoo.plaf.graphite.GraphiteLookAndFeel.setCurrentTheme(props);
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
		frmKeplerbot.setTitle("Keplerbot");
		frmKeplerbot.setBounds(100, 100, 900, 500);
		frmKeplerbot.setMinimumSize(frmKeplerbot.getSize());
		frmKeplerbot.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

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

		final JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.LEFT);
		tabbedPane.setFocusable(false);

		JLabel lblVersion = new JLabel(Version.VERSION);
		lblVersion.setToolTipText("Up to date\r\n");
		lblVersion.setHorizontalAlignment(SwingConstants.RIGHT);
		GroupLayout groupLayout = new GroupLayout(frmKeplerbot.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(tabbedPane).addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup().addComponent(lblVersion, GroupLayout.PREFERRED_SIZE, 856, GroupLayout.PREFERRED_SIZE).addContainerGap()))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout.createSequentialGroup().addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 438, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(lblVersion, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE).addGap(4)));

		final JPanel homePanel = new JPanel();
		homePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		homePanel.setBackground(SystemColor.activeCaptionBorder);
		tabbedPane.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Home</body></html>", null, homePanel, null);

		errorPanel = new ErrorPanel();
		errorPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));

		JTextPane dtrpnReadMe = new JTextPane();
		dtrpnReadMe.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
					DesktopUtils.openUrl(e.getURL().toString());
				}
			}
		});
		dtrpnReadMe.setFont(new Font("Dialog", Font.PLAIN, 13));
		dtrpnReadMe.setBorder(new EmptyBorder(0, 10, 0, 0));
		dtrpnReadMe.setBackground(homePanel.getBackground());
		dtrpnReadMe.setEditable(false);
		dtrpnReadMe.setFocusable(false);
		dtrpnReadMe.setContentType("text/html");
		try {
			URL url = new URL("https://raw.github.com/KeplerGaming/KeplerBot/master/README.md");
			URLConnection con = url.openConnection();
			Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
			Matcher m = p.matcher(con.getContentType());
			String charset = m.matches() ? m.group(1) : "ISO-8859-1";
			Reader r = new InputStreamReader(con.getInputStream(), charset);
			StringBuilder buf = new StringBuilder();
			while (true) {
				int ch = r.read();
				if (ch < 0)
					break;
				buf.append((char) ch);
			}
			String markdownSource = buf.toString();
			dtrpnReadMe.setText(Processor.process(markdownSource));
			dtrpnReadMe.setCaretPosition(0);
		} catch (IOException e) {
			dtrpnReadMe.setText("<html>Could not load</html>");
		}
		JScrollPane scrollPane = new JScrollPane(dtrpnReadMe);
		scrollPane.setBorder(null);

		GroupLayout gl_homePanel = new GroupLayout(homePanel);
		gl_homePanel.setHorizontalGroup(gl_homePanel.createParallelGroup(Alignment.LEADING).addGroup(gl_homePanel.createSequentialGroup().addGap(12).addComponent(errorPanel, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE).addContainerGap()).addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE));
		gl_homePanel.setVerticalGroup(gl_homePanel.createParallelGroup(Alignment.LEADING).addGroup(gl_homePanel.createSequentialGroup().addContainerGap().addComponent(errorPanel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE).addGap(5).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)));
		homePanel.setLayout(gl_homePanel);

		final JPanel streamPanel = new JPanel();
		streamPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		streamPanel.setBackground(SystemColor.activeCaptionBorder);
		tabbedPane.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Streams</body></html>", null, streamPanel, null);

		JButton btnResetStream = new JButton("Reset Connection");
		btnResetStream.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (streamTabs.getSelectedComponent() != null) {
					((StreamLogPannel)streamTabs.getSelectedComponent()).resetBot();
				}
			}
		});
		btnResetStream.setFocusable(false);

		JButton btnRemoveStream = new JButton("Close Stream");
		btnRemoveStream.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (streamTabs.getSelectedComponent() != null && streamTabs.getSelectedIndex() != 0) {
					CloseStreamDialog dialog = new CloseStreamDialog(frmKeplerbot);
					dialog.setVisible(true);
				}
			}
		});
		btnRemoveStream.setFocusable(false);

		JButton btnAddStream = new JButton("Connect to Stream");
		btnAddStream.setFocusable(false);
		btnAddStream.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final AddStreamDialog dialog = new AddStreamDialog(frmKeplerbot);
				dialog.setVisible(true);
			}
		});

		streamTabs = new JTabbedPane(SwingConstants.TOP);
		streamTabs.setFocusable(false);
		streamTabs.setBackground(SystemColor.inactiveCaptionBorder);

		presetHandler = new PresetHandler();
		presetHandler.load();
		final DefaultComboBoxModel<String> model = presetHandler.getPresets();

		JComboBox<String> comboBoxPresets = new JComboBox<String>();
		comboBoxPresets.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getSelectedItem().equals(PresetHandler.ADD_PRESET)) {
					String stream = JOptionPane.showInputDialog(frmKeplerbot, "Stream name", "Add Preset", JOptionPane.PLAIN_MESSAGE);
					if (stream != null && !stream.isEmpty()) {
						model.addElement(stream);
						model.setSelectedItem(stream);
						presetHandler.setPresets(model);
						presetHandler.save();
					}
				}
			}
		});
		comboBoxPresets.setModel(model);

		JLabel lblPresets = new JLabel("Presents");

		chatBox = new JTextField();
		chatBox.setColumns(10);

		JButton btnAddPreset = new JButton("Connect to Preset");
		btnAddPreset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!model.getSelectedItem().equals(PresetHandler.ADD_PRESET)) {
					int joinMessage = JOptionPane.showConfirmDialog(frmKeplerbot, "Would you like to show the join message?");

					if (joinMessage != 2) {
						MainFrame.getInstance().addStream((String) model.getSelectedItem(), joinMessage == 0);
					}
				}
			}
		});
		btnAddPreset.setFocusable(false);

		JButton btnSend = new JButton("Send");
		streamPanel.getRootPane().setDefaultButton(btnSend);
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StreamLogPannel panel = (StreamLogPannel)streamTabs.getSelectedComponent();
				
				if (panel != null) {
					if (!chatBox.getText().isEmpty() && !chatBox.getText().startsWith("!")) {
						panel.getWrapper().getBot().sendMessage(panel.getWrapper().getChannel(), chatBox.getText());
					} else if (chatBox.getText().startsWith("!")){
						panel.getWrapper().getCommandManager().processMessage("Console", chatBox.getText());
					}
					chatBox.setText("");
				}
			}
		});
		btnSend.setFocusable(false);

		JButton btnStreamConfig = new JButton("Stream Config");
		btnStreamConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StreamLogPannel panel = (StreamLogPannel)streamTabs.getSelectedComponent();
				
				if (panel != null) {
					StreamConfigDialog dialog = new StreamConfigDialog(frmKeplerbot, panel.getWrapper());
					dialog.setVisible(true);
				}
			}
		});
		btnStreamConfig.setFocusable(false);

		GroupLayout gl_streamPanel = new GroupLayout(streamPanel);
		gl_streamPanel.setHorizontalGroup(gl_streamPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_streamPanel.createSequentialGroup().addContainerGap().addGroup(gl_streamPanel.createParallelGroup(Alignment.LEADING).addComponent(lblPresets).addComponent(btnAddPreset, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE).addComponent(comboBoxPresets, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE).addComponent(btnStreamConfig, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE).addComponent(btnResetStream, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE).addComponent(btnRemoveStream, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE).addComponent(btnAddStream, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE)).addGap(18).addGroup(gl_streamPanel.createParallelGroup(Alignment.TRAILING).addGroup(gl_streamPanel.createSequentialGroup().addComponent(chatBox, GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSend)).addComponent(streamTabs, GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)).addGap(7)));
		gl_streamPanel.setVerticalGroup(gl_streamPanel.createParallelGroup(Alignment.TRAILING).addGroup(gl_streamPanel.createSequentialGroup().addGroup(gl_streamPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_streamPanel.createSequentialGroup().addContainerGap().addComponent(lblPresets).addPreferredGap(ComponentPlacement.RELATED).addComponent(comboBoxPresets, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnAddPreset).addGap(18).addComponent(btnAddStream).addPreferredGap(ComponentPlacement.RELATED, 152, Short.MAX_VALUE).addComponent(btnRemoveStream).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnResetStream).addGap(18).addComponent(btnStreamConfig)).addComponent(streamTabs, GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_streamPanel.createParallelGroup(Alignment.LEADING).addComponent(btnSend).addComponent(chatBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addContainerGap()));
		streamPanel.setLayout(gl_streamPanel);

		final JPanel configPanel = new JPanel();
		streamPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				if (errorPanel.hasErrors()) {
					tabbedPane.setSelectedComponent(configPanel);
				} else if (streamTabs.getSelectedComponent() == null) {
					MainFrame.getInstance().addStream(config.getString(ConfigConstants.USERNAME.getKey(), (String) ConfigConstants.USERNAME.getDefaultValue()), false);
				}
			}
		});
		configPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		configPanel.setBackground(SystemColor.activeCaptionBorder);
		tabbedPane.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Config</body></html>", null, configPanel, null);

		final JPanel loginPanel = new JPanel();
		loginPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		loginPanel.setBackground(SystemColor.activeCaptionBorder);

		final JPanel generalPanel = new JPanel();
		generalPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		generalPanel.setBackground(SystemColor.activeCaptionBorder);

		final ConfigFieldPanel botNameConfig = new ConfigFieldPanel("Bot name", ConfigConstants.BOT_NAME.getKey(), (String) ConfigConstants.BOT_NAME.getDefaultValue(), true, generalPanel.getBackground());

		final ConfigFieldPanel joinMessageConfig = new ConfigFieldPanel("Join Message", ConfigConstants.JOIN_MESSAGE.getKey(), (String) ConfigConstants.JOIN_MESSAGE.getDefaultValue(), true, generalPanel.getBackground());

		final ConfigFieldPanel leaveConfig = new ConfigFieldPanel("Leave Message", ConfigConstants.LEAVE_MESSAGE.getKey(), (String) ConfigConstants.LEAVE_MESSAGE.getDefaultValue(), true, generalPanel.getBackground());
		
		JLabel labelGeneral = new JLabel("General Bot Settings");
		labelGeneral.setHorizontalAlignment(SwingConstants.CENTER);
		labelGeneral.setFont(new Font("Dialog", Font.BOLD, 13));

		GroupLayout gl_generalPanel = new GroupLayout(generalPanel);
		gl_generalPanel.setHorizontalGroup(gl_generalPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_generalPanel.createSequentialGroup().addContainerGap().addGroup(gl_generalPanel.createParallelGroup(Alignment.LEADING).addComponent(leaveConfig, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(joinMessageConfig, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(botNameConfig, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(labelGeneral, GroupLayout.PREFERRED_SIZE, 716, GroupLayout.PREFERRED_SIZE)).addContainerGap()));
		gl_generalPanel.setVerticalGroup(gl_generalPanel.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, gl_generalPanel.createSequentialGroup().addContainerGap().addComponent(labelGeneral).addPreferredGap(ComponentPlacement.RELATED, 14, Short.MAX_VALUE).addComponent(botNameConfig, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(joinMessageConfig, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(leaveConfig, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE).addContainerGap()));
		generalPanel.setLayout(gl_generalPanel);

		JPanel panelProxy = new JPanel();
		panelProxy.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelProxy.setBackground(SystemColor.activeCaptionBorder);

		final ConfigFieldPanel proxyConfig = new ConfigFieldPanel("Socks Proxy Server", ConfigConstants.PROXYSERVER.getKey(), (String) ConfigConstants.PROXYSERVER.getDefaultValue(), false, panelProxy.getBackground());

		JLabel lblProxy = new JLabel("Proxy Settings");
		lblProxy.setFont(new Font("Dialog", Font.BOLD, 13));
		lblProxy.setHorizontalAlignment(SwingConstants.CENTER);

		final JCheckBox chckbxProxy = new JCheckBox("Use Proxy");
		chckbxProxy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				config.setBoolean(ConfigConstants.PROXY.getKey(), chckbxProxy.isSelected());
				proxyConfig.setEnabled(chckbxProxy.isSelected());
			}
		});
		chckbxProxy.setBackground(panelProxy.getBackground());
		chckbxProxy.setSelected(config.getBoolean(ConfigConstants.PROXY.getKey(), (boolean) ConfigConstants.PROXY.getDefaultValue()));
		proxyConfig.setEnabled(chckbxProxy.isSelected());

		GroupLayout gl_panelProxy = new GroupLayout(panelProxy);
		gl_panelProxy.setHorizontalGroup(gl_panelProxy.createParallelGroup(Alignment.LEADING).addGroup(gl_panelProxy.createSequentialGroup().addContainerGap().addGroup(gl_panelProxy.createParallelGroup(Alignment.LEADING).addGroup(gl_panelProxy.createSequentialGroup().addGap(12).addComponent(chckbxProxy)).addComponent(lblProxy, GroupLayout.PREFERRED_SIZE, 716, GroupLayout.PREFERRED_SIZE).addComponent(proxyConfig, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap()));
		gl_panelProxy.setVerticalGroup(gl_panelProxy.createParallelGroup(Alignment.LEADING).addGroup(gl_panelProxy.createSequentialGroup().addContainerGap().addComponent(lblProxy).addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(chckbxProxy).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(proxyConfig, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE).addContainerGap()));
		panelProxy.setLayout(gl_panelProxy);
		GroupLayout gl_configPanel = new GroupLayout(configPanel);
		gl_configPanel.setHorizontalGroup(gl_configPanel.createParallelGroup(Alignment.TRAILING).addGroup(gl_configPanel.createSequentialGroup().addContainerGap().addGroup(gl_configPanel.createParallelGroup(Alignment.LEADING).addComponent(loginPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(generalPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(panelProxy, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap()));
		gl_configPanel.setVerticalGroup(gl_configPanel.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING, gl_configPanel.createSequentialGroup().addContainerGap().addComponent(loginPanel, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE).addGap(18).addComponent(generalPanel, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE).addGap(18).addComponent(panelProxy, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE).addContainerGap(14, Short.MAX_VALUE)));

		final ConfigFieldPanel usernameConfig = new ConfigFieldPanel("Twitch username", ConfigConstants.USERNAME.getKey(), (String) ConfigConstants.USERNAME.getDefaultValue(), true, loginPanel.getBackground());
		final ConfigFieldPanel passwordConfig = new ConfigFieldPanel("Password", ConfigConstants.PASSWORD.getKey(), (String) ConfigConstants.PASSWORD.getDefaultValue(), true, loginPanel.getBackground());
		
		JLabel labelLogin = new JLabel("Login Details");
		labelLogin.setHorizontalAlignment(SwingConstants.CENTER);
		labelLogin.setFont(new Font("Dialog", Font.BOLD, 13));

		GroupLayout gl_loginPanel = new GroupLayout(loginPanel);
		gl_loginPanel.setHorizontalGroup(gl_loginPanel.createParallelGroup(Alignment.TRAILING).addGroup(gl_loginPanel.createSequentialGroup().addContainerGap().addGroup(gl_loginPanel.createParallelGroup(Alignment.LEADING).addComponent(labelLogin, GroupLayout.PREFERRED_SIZE, 716, GroupLayout.PREFERRED_SIZE).addComponent(passwordConfig, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(usernameConfig, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap()));
		gl_loginPanel.setVerticalGroup(gl_loginPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_loginPanel.createSequentialGroup().addContainerGap().addComponent(labelLogin).addGap(18).addComponent(usernameConfig, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(passwordConfig, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
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
				config.setString(proxyConfig.getConfigKey(), proxyConfig.getValue());

				config.saveConfig();
			}
		});

		frmKeplerbot.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent ev) {
				frmKeplerbot.setVisible(false);

				config.setString(botNameConfig.getConfigKey(), botNameConfig.getValue());
				config.setString(joinMessageConfig.getConfigKey(), joinMessageConfig.getValue());
				config.setString(leaveConfig.getConfigKey(), leaveConfig.getValue());
				config.setString(usernameConfig.getConfigKey(), usernameConfig.getValue());
				config.setString(passwordConfig.getConfigKey(), passwordConfig.getValue());
				config.setString(proxyConfig.getConfigKey(), proxyConfig.getValue());

				config.saveConfig();

				for(Component component : streamTabs.getComponents()) {
					((StreamLogPannel)component).getWrapper().dispose(false);
					streamTabs.remove(component);
				}

				checkerThread = null;
				System.exit(0);
			}
		});

		JPanel aboutPanel = new JPanel();
		aboutPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		aboutPanel.setBackground(SystemColor.activeCaptionBorder);
		tabbedPane.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>About</body></html>", null, aboutPanel, null);

		JTextPane aboutTextPane = new JTextPane();
		aboutTextPane.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
					DesktopUtils.openUrl(e.getURL().toString());
				}
			}
		});
		aboutTextPane.setFocusable(false);
		aboutTextPane.setBackground(aboutPanel.getBackground());
		aboutTextPane.setEditable(false);
		aboutTextPane.setContentType("text/html");
		aboutTextPane.setText("<center><a rel=\"license\" href=\"http://creativecommons.org/licenses/by-nc-sa/3.0/\"><img alt=\"Creative Commons License\" style=\"border-width:0\" src=\"http://i.creativecommons.org/l/by-nc-sa/3.0/88x31.png\" /></a><br /><span xmlns:dct=\"http://purl.org/dc/terms/\" href=\"http://purl.org/dc/dcmitype/Dataset\" property=\"dct:title\" rel=\"dct:type\">KeplerBot</span> by <a xmlns:cc=\"http://creativecommons.org/ns#\" href=\"http://keplergaming.com/\" property=\"cc:attributionName\" rel=\"cc:attributionURL\">KeplerGaming</a> is licensed under a <br /><a rel=\"license\" href=\"http://creativecommons.org/licenses/by-nc-sa/3.0/\">Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License</a>.<br /><br />Based on a work at <a xmlns:dct=\"http://purl.org/dc/terms/\" href=\"https://code.google.com/p/pircbotx\" rel=\"dct:source\">https://code.google.com/p/pircbotx</a>.</center>");

		JTextPane creditTextPane = new JTextPane();
		creditTextPane.setFocusable(false);
		creditTextPane.setEditable(false);
		creditTextPane.setContentType("text/html");
		creditTextPane.setText("<p>Credits:</p><ul><li>Crazyputje</li><li>Logomaster256</li><li>Spacerules</li></ul>");
		creditTextPane.setBackground(aboutPanel.getBackground());

		ImagePanel githubImage = new ImagePanel(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/net/keplergaming/keplerbot/resources/github.png")));

		ImagePanel keplerImage = new ImagePanel(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/net/keplergaming/keplerbot/resources/logo.png")));
		GroupLayout gl_aboutPanel = new GroupLayout(aboutPanel);
		gl_aboutPanel.setHorizontalGroup(gl_aboutPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_aboutPanel.createSequentialGroup().addGap(2).addGroup(gl_aboutPanel.createParallelGroup(Alignment.TRAILING).addComponent(aboutTextPane, GroupLayout.PREFERRED_SIZE, 359, GroupLayout.PREFERRED_SIZE).addComponent(githubImage, GroupLayout.PREFERRED_SIZE, 359, GroupLayout.PREFERRED_SIZE)).addGap(2).addGroup(gl_aboutPanel.createParallelGroup(Alignment.LEADING).addComponent(creditTextPane, GroupLayout.PREFERRED_SIZE, 359, GroupLayout.PREFERRED_SIZE).addComponent(keplerImage, GroupLayout.PREFERRED_SIZE, 359, GroupLayout.PREFERRED_SIZE)).addGap(24)));
		gl_aboutPanel.setVerticalGroup(gl_aboutPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_aboutPanel.createSequentialGroup().addGroup(gl_aboutPanel.createParallelGroup(Alignment.LEADING, false).addComponent(creditTextPane).addComponent(aboutTextPane, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_aboutPanel.createParallelGroup(Alignment.LEADING).addComponent(githubImage, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE).addComponent(keplerImage, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)).addGap(32)));
		aboutPanel.setLayout(gl_aboutPanel);
		keplerImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DesktopUtils.openUrl("www.keplergaming.com");
			}
		});
		githubImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				DesktopUtils.openUrl("https://github.com/KeplerGaming/KeplerBot");
			}
		});

		frmKeplerbot.getContentPane().setLayout(groupLayout);
		frmKeplerbot.setVisible(true);
	}

	public ErrorPanel getErrorPanel() {
		return errorPanel;
	}

	public static MainFrame getInstance() {
		return Instance;
	}

	public Configuration getConfig() {
		return config;
	}

	public void addStream(String streamer, boolean joinMessage) {
		StreamLogPannel panel = new StreamLogPannel(streamer, joinMessage);
		panel.setName(streamer);

		streamTabs.add(panel);
	}

	public void closeVisibleStream(boolean showMessage) {
		StreamLogPannel panel = (StreamLogPannel)streamTabs.getSelectedComponent();
		
		if (panel != null) {
			panel.getWrapper().dispose(showMessage);
			streamTabs.remove(panel);
			System.gc();
		}
	}
}
