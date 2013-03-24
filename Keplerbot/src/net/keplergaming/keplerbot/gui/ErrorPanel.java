package net.keplergaming.keplerbot.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.keplergaming.keplerbot.logger.MainLogger;

@SuppressWarnings("serial")
public class ErrorPanel extends JPanel {
	private JTextField txtErrorsFound;
	private JTextField txtrTre;
	private Map<String, String> errors;
	private int index;

	/**
	 * Create the panel.
	 */
	public ErrorPanel() {
		errors = new HashMap<String, String>();
		setBackground(new Color(255, 0, 0));

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

		txtErrorsFound = new JTextField();
		txtErrorsFound.setHorizontalAlignment(SwingConstants.CENTER);
		txtErrorsFound.setFont(new Font("Dialog", Font.BOLD, 15));
		txtErrorsFound.setBorder(null);
		txtErrorsFound.setBackground(new Color(255, 0, 0));
		txtErrorsFound.setEditable(false);
		txtErrorsFound.setColumns(10);

		txtrTre = new JTextField();
		txtrTre.setEditable(false);
		txtrTre.setBorder(null);
		txtrTre.setFont(new Font("Dialog", Font.BOLD, 13));
		txtrTre.setBackground(new Color(255, 0, 0));
		txtrTre.setText("Error \r\n");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(txtErrorsFound, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(5).addComponent(rightPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(12).addComponent(txtrTre, GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE).addGap(41)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false).addComponent(rightPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(txtErrorsFound).addComponent(txtrTre)).addContainerGap(28, Short.MAX_VALUE)));
		setLayout(groupLayout);
		setVisible(false);
	}

	public void addError(String key, String message) {
		MainLogger.fine("Adding error to errorPanel");
		errors.put(key, message);
		if (errors.size() == 1) {
			txtErrorsFound.setText(errors.size() + " error found!");
		} else {
			txtErrorsFound.setText(errors.size() + " errors found!");
		}
		txtrTre.setText((String) errors.values().toArray()[0]);
		setVisible(true);
	}

	public void removeError(String key) {
		MainLogger.fine("Removing error from errorPanel");
		errors.remove(key);
		if (errors.size() == 1) {
			txtErrorsFound.setText(errors.size() + " error found!");
		} else {
			txtErrorsFound.setText(errors.size() + " errors found!");
		}

		if (errors.isEmpty()) {
			setVisible(false);
		} else {
			txtrTre.setText((String) errors.values().toArray()[0]);
		}
	}

	public void updateText() {
		txtrTre.setText((String) errors.values().toArray()[index]);
		if (errors.size() == 1) {
			txtErrorsFound.setText(errors.size() + " error found!");
		} else {
			txtErrorsFound.setText(errors.size() + " errors found!");
		}
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	public boolean hasError(String key) {
		return errors.containsKey(key);
	}
}
