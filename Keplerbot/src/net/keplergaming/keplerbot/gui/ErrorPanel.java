package net.keplergaming.keplerbot.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ErrorPanel extends JPanel {
	private JTextField txtErrorsFound;
	private JTextArea txtrTre;
	private Map<String, String> errors;
	private int index;

	/**
	 * Create the panel.
	 */
	public ErrorPanel() {
		errors = new HashMap<String, String>();

		setBackground(new Color(255, 0, 0));

		ImagePanel leftPanel = new ImagePanel(Toolkit.getDefaultToolkit().getImage(ErrorPanel.class.getResource("/net/keplergaming/keplerbot/resources/left.png")));
		leftPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				index--;
				if (index < 0) {
					index = errors.size() - 1;
				}
				updateText();
			}
		});
		leftPanel.setBounds(571, 0, 32, 40);

		ImagePanel rightPanel = new ImagePanel(Toolkit.getDefaultToolkit().getImage(ErrorPanel.class.getResource("/net/keplergaming/keplerbot/resources/right.png")));
		rightPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				index++;
				if (index > errors.size() - 1) {
					index = 0;
				}
				updateText();
			}
		});
		rightPanel.setBounds(605, 0, 25, 40);
		setLayout(null);

		txtErrorsFound = new JTextField();
		txtErrorsFound.setHorizontalAlignment(JTextField.CENTER);
		txtErrorsFound.setBounds(256, 0, 120, 13);
		txtErrorsFound.setFont(new Font("Dialog", Font.BOLD, 15));
		txtErrorsFound.setBorder(null);
		txtErrorsFound.setBackground(new Color(255, 0, 0));
		txtErrorsFound.setEditable(false);
		txtErrorsFound.setColumns(10);
		add(txtErrorsFound);
		add(leftPanel);
		add(rightPanel);

		txtrTre = new JTextArea();
		txtrTre.setEditable(false);
		txtrTre.setFont(new Font("Dialog", Font.BOLD, 13));
		txtrTre.setBackground(new Color(255, 0, 0));
		txtrTre.setText("Error \r\n");
		txtrTre.setBounds(10, 11, 540, 18);
		add(txtrTre);
		setVisible(false);
	}

	public void addError(String key, String message) {
		errors.put(key, message);
		txtErrorsFound.setText(errors.size() + " errors found!");
		txtrTre.setText((String) errors.values().toArray()[0]);
		setVisible(true);
	}

	public void removeError(String key) {
		errors.remove(key);
		txtErrorsFound.setText(errors.size() + " errors found!");
		if (errors.isEmpty()) {
			setVisible(false);
		} else {
			txtrTre.setText((String) errors.values().toArray()[0]);
		}
	}

	public void updateText() {
		txtrTre.setText((String) errors.values().toArray()[index]);
		txtErrorsFound.setText(errors.size() + " errors found!");
	}
}
