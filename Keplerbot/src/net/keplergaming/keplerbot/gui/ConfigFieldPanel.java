package net.keplergaming.keplerbot.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import net.keplergaming.keplerbot.config.Configuration;

@SuppressWarnings("serial")
public class ConfigFieldPanel extends JPanel {
	private JTextField configValue;
	private final String configKey;
	private final JLabel configName;

	/**
	 * Create the panel.
	 */
	public ConfigFieldPanel(final String name, final String key, final String defaultValue, final boolean throwsError, Color background) {
		configKey = key;
		Configuration config = MainFrame.getInstance().getConfig();

		setBackground(background);

		configName = new JLabel(name);
		configName.setFont(new Font("Dialog", Font.BOLD, 13));

		if (config.getString(configKey, defaultValue).isEmpty() && throwsError) {
			configName.setForeground(new Color(255, 0, 0));
			MainFrame.getInstance().getErrorPanel().addError(configKey, "Please configure your " + name);
		}

		configValue = new JTextField();
		configValue.setEditable(isEnabled());
		configValue.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if (configValue.getText().isEmpty() && throwsError) {
					configName.setForeground(new Color(255, 0, 0));
					MainFrame.getInstance().getErrorPanel().addError(configKey, "Please configure your " + name);
				} else if (throwsError) {
					configName.setForeground(SystemColor.desktop);
					if (MainFrame.getInstance().getErrorPanel().hasError(configKey)) {
						MainFrame.getInstance().getErrorPanel().removeError(configKey);
					}
				}
			}
		});
		configValue.setText(config.getString(configKey, defaultValue));
		configValue.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(configName, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(configValue, GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(configValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(configName)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		setLayout(groupLayout);

		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				configValue.setEnabled(isEnabled());
			}
		});
	}

	public String getValue() {
		return configValue.getText();
	}

	public String getConfigKey() {
		return configKey;
	}
}
