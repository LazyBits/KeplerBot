package net.keplerbot.gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.keplerbot.Main;
import net.keplerbot.config.KeplerConfig;
import net.keplerbot.log.KeplerLogger;

public class LoginDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private JPanel panel = new JPanel();

	private JTextField username = new JTextField(1);
	private JPasswordField password = new JPasswordField(1);
	private JLabel userLabel = new JLabel("Username:");
	private JLabel passLabel = new JLabel("Password:");
	private JButton addButton = new JButton("Login");

	public LoginDialog(MainFrame instance, boolean modal) {
		super(instance, modal);
		KeplerLogger.getInstance("Main").info("Opening Login dialog");
		URL url = ClassLoader.getSystemResource("net/keplerbot/gui/icon.png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(url);
		setIconImage(img);
		setTitle("Twitch.tv login");
		setBounds(300, 300, 300, 160);
		setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(null, "Are You Sure You Want To Close This Application?", "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                   System.exit(0);
                }
            }
        };
        addWindowListener(exitListener);

		getRootPane().setDefaultButton(addButton);

		panel.setBounds(0, 0, 300, 160);
		setContentPane(panel);
		panel.setLayout(null);

		userLabel.setBounds(10, 10, 80, 30);
		userLabel.setVisible(true);
		panel.add(userLabel);

		username.setBounds(100, 10, 170, 30);
		username.setVisible(true);
		username.setBackground(new Color(90, 90, 90));
		panel.add(username);

		passLabel.setBounds(10, 60, 80, 30);
		passLabel.setVisible(true);
		panel.add(passLabel);

		password.setBounds(100, 60, 170, 30);
		password.setVisible(true);
		password.setBackground(new Color(90, 90, 90));
		panel.add(password);

		addButton.setBounds(105, 100, 70, 30);
		addButton.setVisible(true);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if(validate(username.getText(), password.getPassword())) {
					KeplerConfig.getInstance().setProperty("twitch_login", username.getText());
					KeplerConfig.getInstance().setProperty("twitch_pass", new String(password.getPassword()));
					Main.instance.init();
					setVisible(false);
				} else {
					KeplerLogger.getInstance("Main").error("Unable to login.");
				}
			}
		});
		panel.add(addButton);
	}

	private boolean validate(String user, char[] pass) {
		if(!user.isEmpty() && pass.length > 1) {
			return true;
		}
		return false;
	}
}
