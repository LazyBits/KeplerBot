package net.keplergaming.keplerbot.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import net.keplergaming.keplerbot.config.Configuration;

public class ConfigFieldPanel extends JPanel {
	private JTextField configName;
	private JTextField configValue;
	private final String configKey;

	/**
	 * Create the panel.
	 */
	public ConfigFieldPanel(final String name, final String key, final String defaultValue, Color background) {
		configKey = key;
		Configuration config = MainFrame.getInstance().getConfig();
		
		setBackground(background);
		
		configName = new JTextField();
		configName.setText(name);
		configName.setFont(new Font("Dialog", Font.BOLD, 13));
		configName.setEditable(false);
		configName.setBackground(background);
		configName.setBorder(null);
		configName.setColumns(10);
		
		if (config.getString(configKey, defaultValue).isEmpty()) {
			configName.setForeground(new Color(255, 0, 0));
			MainFrame.getInstance().addError(configKey, "Please configure your " + name);
		}
		
		configValue = new JTextField();
		configValue.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if (configValue.getText().isEmpty()) {
					configName.setForeground(new Color(255, 0, 0));
					MainFrame.getInstance().addError(configKey, "Please configure your " + name);
				} else {
					configName.setForeground(SystemColor.desktop);
					MainFrame.getInstance().removeError(configKey);
				}
			}
		});
		configValue.setText(config.getString(configKey, defaultValue));
		configValue.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(configName, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(configValue, GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(configName, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE)
						.addComponent(configValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}

	public String getValue() {
		return configValue.getText();
	}

	public String getConfigKey() {
		return configKey;
	}
}
