package net.keplergaming.keplerbot.gui;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import net.keplergaming.keplerbot.config.Configuration;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ColorConfigFieldPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public ColorConfigFieldPanel(final String color, boolean defaultValue, final Configuration config, Color background) {
		setBackground(background);
		
		final JCheckBox chckbx = new JCheckBox();
		chckbx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				config.setBoolean(color, chckbx.isSelected());
				config.saveConfig();
			}
		});
		chckbx.setSelected(config.getBoolean(color, defaultValue));
		
		JLabel lblColor = new JLabel("Color");
		lblColor.setFont(new Font("Dialog", Font.BOLD, 13));
		lblColor.setHorizontalAlignment(SwingConstants.CENTER);
		lblColor.setForeground(Color.decode(color));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(lblColor, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, 42, Short.MAX_VALUE).addComponent(chckbx).addContainerGap()));
		groupLayout.setVerticalGroup(
		groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblColor, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE).addComponent(chckbx)).addContainerGap()));
		setLayout(groupLayout);

	}
}
