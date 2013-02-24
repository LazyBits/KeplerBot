package net.keplerbot.gui;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URI;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.keplerbot.Main;
import net.keplerbot.log.KeplerLogger;

public class UpdateDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private JPanel panel = new JPanel();
	private JLabel textOne = new JLabel("A newer version is available");
	private JLabel textTwo = new JLabel("Your build: " + Main.build + " -- New build: " + Main.instance.checker.latest);
	private JLabel textThree = new JLabel("Extra info: " + Main.instance.checker.information);
	private JButton ButtonOne = new JButton("Download new version");
	private JButton ButtonTwo = new JButton("Don't update");

	public UpdateDialog() {
		KeplerLogger.getInstance("Main").info("Opening Update dialog");
		URL url = ClassLoader.getSystemResource("net/keplerbot/gui/icon.png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(url);
		setIconImage(img);
		setTitle("Keplerbot update available");
		setBounds(300, 330, 330, 140);
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

		panel.setLayout(null);
		panel.setBounds(0, 0, 320, 140);
		setContentPane(panel);

		textOne.setBounds(0, 0, 320, 30);
		textOne.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(textOne);

		textTwo.setBounds(0, 15, 320, 30);
		textTwo.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(textTwo);
		
		textThree.setBounds(0, 40, 320, 30);
		textThree.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(textThree);

		ButtonOne.setBounds(5, 80, 155, 25);
		ButtonOne.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					try {
						desktop.browse(new URI("http://keplergaming.com/keplerbot/#DOWNLOAD"));
					} catch(Exception exc) {
						KeplerLogger.getInstance("Main").error("Could not open url: " + exc.getMessage());
					}
				} else {
					KeplerLogger.getInstance("Main").warn("Could not open url, not supported");
				}
				setVisible(false);
				System.exit(1);
			}
		});
		panel.add(ButtonOne);

		ButtonTwo.setBounds(165, 80, 155, 25);
		ButtonTwo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				MainFrame frame = new MainFrame();
				frame.setVisible(true);
				Main.instance.checkLogin();
			}
		});
		panel.add(ButtonTwo);
	}
}