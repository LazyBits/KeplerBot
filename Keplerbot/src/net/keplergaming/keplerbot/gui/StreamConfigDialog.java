package net.keplergaming.keplerbot.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.config.Configuration;
import net.keplergaming.keplerbot.exception.BotException;
import net.keplergaming.keplerbot.utils.ColorCodes;

@SuppressWarnings("serial")
public class StreamConfigDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public StreamConfigDialog(JFrame parent, final KeplerBotWrapper wrapper) {
		super(parent, true);
		setResizable(false);
		setType(Type.POPUP);
		setTitle("Stream Config");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 450);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setLocationRelativeTo(parent);

		JPanel panelColor = new JPanel();
		panelColor.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		JPanel panelFilter = new JPanel();
		panelFilter.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		JPanel panelMute = new JPanel();
		panelMute.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPanel.createSequentialGroup().addContainerGap().addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addComponent(panelMute, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE).addComponent(panelColor, GroupLayout.PREFERRED_SIZE, 464, GroupLayout.PREFERRED_SIZE).addComponent(panelFilter, GroupLayout.PREFERRED_SIZE, 464, Short.MAX_VALUE)).addContainerGap()));
		gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPanel.createSequentialGroup().addContainerGap().addComponent(panelColor, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(panelFilter, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(panelMute, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE).addContainerGap()));

		JLabel lblMute = new JLabel("Muted Response");
		lblMute.setHorizontalAlignment(SwingConstants.CENTER);
		lblMute.setFont(new Font("Dialog", Font.BOLD, 13));

		final JCheckBox chckbxAll = new JCheckBox("All");
		chckbxAll.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxAll.setFont(new Font("Dialog", Font.PLAIN, 13));
		chckbxAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wrapper.getConfig().setBoolean(Configuration.MUTE_ALL[0], chckbxAll.isSelected());
				wrapper.muteAll(chckbxAll.isSelected());
				wrapper.getConfig().saveConfig();
			}
		});
		chckbxAll.setSelected(wrapper.getConfig().getBoolean(Configuration.MUTE_ALL[0], Boolean.parseBoolean(Configuration.MUTE_ALL[1])));

		final JCheckBox chckbxWarnings = new JCheckBox("Warnings");
		chckbxWarnings.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxWarnings.setFont(new Font("Dialog", Font.PLAIN, 13));
		chckbxWarnings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wrapper.getConfig().setBoolean(Configuration.MUTE_WARNINGS[0], chckbxWarnings.isSelected());
				wrapper.muteAll(chckbxWarnings.isSelected());
				wrapper.getConfig().saveConfig();
			}
		});
		chckbxWarnings.setSelected(wrapper.getConfig().getBoolean(Configuration.MUTE_WARNINGS[0], Boolean.parseBoolean(Configuration.MUTE_WARNINGS[1])));

		final JCheckBox chckbxErrors = new JCheckBox("Errors");
		chckbxErrors.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxErrors.setFont(new Font("Dialog", Font.PLAIN, 13));
		chckbxErrors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wrapper.getConfig().setBoolean(Configuration.MUTE_ERRORS[0], chckbxErrors.isSelected());
				wrapper.muteAll(chckbxErrors.isSelected());
				wrapper.getConfig().saveConfig();
			}
		});
		chckbxErrors.setSelected(wrapper.getConfig().getBoolean(Configuration.MUTE_ERRORS[0], Boolean.parseBoolean(Configuration.MUTE_ERRORS[1])));

		GroupLayout gl_panelMute = new GroupLayout(panelMute);
		gl_panelMute.setHorizontalGroup(gl_panelMute.createParallelGroup(Alignment.LEADING).addGroup(gl_panelMute.createSequentialGroup().addContainerGap().addGroup(gl_panelMute.createParallelGroup(Alignment.LEADING).addGroup(gl_panelMute.createSequentialGroup().addGap(95).addComponent(chckbxAll, GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE).addGap(20).addComponent(chckbxWarnings, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(20).addComponent(chckbxErrors, GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE).addGap(99)).addComponent(lblMute, GroupLayout.PREFERRED_SIZE, 444, GroupLayout.PREFERRED_SIZE)).addGap(6)));gl_panelMute.setVerticalGroup(gl_panelMute.createParallelGroup(Alignment.LEADING).addGroup(gl_panelMute.createSequentialGroup().addContainerGap().addComponent(lblMute, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panelMute.createParallelGroup(Alignment.BASELINE).addComponent(chckbxAll, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE).addComponent(chckbxWarnings, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addComponent(chckbxErrors, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)).addContainerGap(14, Short.MAX_VALUE)));
		panelMute.setLayout(gl_panelMute);

		JLabel lblFilter = new JLabel("Enabled Filters");
		lblFilter.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilter.setFont(new Font("Dialog", Font.BOLD, 13));

		final JCheckBox chckbxCapsFilter = new JCheckBox("Caps Filter");
		chckbxCapsFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wrapper.getConfig().setBoolean(Configuration.CAPS_FILTER[0], chckbxCapsFilter.isSelected());
				try {
					wrapper.getFilterManager().getFilter("caps").setDisabled(chckbxCapsFilter.isSelected());
				} catch (BotException ex) {
					wrapper.getStreamLogger().error("Unable change filter state", ex);
				}
				wrapper.getConfig().saveConfig();
			}
		});
		chckbxCapsFilter.setSelected(wrapper.getConfig().getBoolean(Configuration.CAPS_FILTER[0], Boolean.parseBoolean(Configuration.CAPS_FILTER[1])));
		chckbxCapsFilter.setHorizontalAlignment(SwingConstants.CENTER);

		final JCheckBox chckbxLinkFilter = new JCheckBox("Link Filter");
		chckbxLinkFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wrapper.getConfig().setBoolean(Configuration.LINK_FILTER[0], chckbxLinkFilter.isSelected());
				try {
					wrapper.getFilterManager().getFilter("links").setDisabled(chckbxLinkFilter.isSelected());
				} catch (BotException ex) {
					wrapper.getStreamLogger().error("Unable change filter state", ex);
				}
				wrapper.getConfig().saveConfig();
			}
		});
		chckbxLinkFilter.setSelected(wrapper.getConfig().getBoolean(Configuration.LINK_FILTER[0], Boolean.parseBoolean(Configuration.LINK_FILTER[1])));
		chckbxLinkFilter.setHorizontalAlignment(SwingConstants.CENTER);

		final JCheckBox chckbxColorFilter = new JCheckBox("Color Filter");
		chckbxColorFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wrapper.getConfig().setBoolean(Configuration.COLOR_FILTER[0], chckbxColorFilter.isSelected());
				try {
					wrapper.getFilterManager().getFilter("color").setDisabled(chckbxColorFilter.isSelected());
				} catch (BotException ex) {
					wrapper.getStreamLogger().error("Unable change filter state", ex);
				}
				wrapper.getConfig().saveConfig();
			}
		});
		chckbxColorFilter.setSelected(wrapper.getConfig().getBoolean(Configuration.COLOR_FILTER[0], Boolean.parseBoolean(Configuration.COLOR_FILTER[1])));
		chckbxColorFilter.setHorizontalAlignment(SwingConstants.CENTER);

		final JCheckBox chckbxCensorFilter = new JCheckBox("Censor Filter");
		chckbxCensorFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wrapper.getConfig().setBoolean(Configuration.CENSOR_FILTER[0], chckbxCensorFilter.isSelected());
				try {
					wrapper.getFilterManager().getFilter("censor").setDisabled(chckbxCensorFilter.isSelected());
				} catch (BotException ex) {
					wrapper.getStreamLogger().error("Unable change filter state", ex);
				}
				wrapper.getConfig().saveConfig();
			}
		});
		chckbxCensorFilter.setSelected(wrapper.getConfig().getBoolean(Configuration.CENSOR_FILTER[0], Boolean.parseBoolean(Configuration.CENSOR_FILTER[1])));
		chckbxCensorFilter.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_panelFilter = new GroupLayout(panelFilter);
		gl_panelFilter.setHorizontalGroup(gl_panelFilter.createParallelGroup(Alignment.LEADING).addGroup(gl_panelFilter.createSequentialGroup().addContainerGap().addGroup(gl_panelFilter.createParallelGroup(Alignment.LEADING).addGroup(gl_panelFilter.createSequentialGroup().addComponent(lblFilter, GroupLayout.PREFERRED_SIZE, 442, GroupLayout.PREFERRED_SIZE).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGroup(gl_panelFilter.createSequentialGroup().addComponent(chckbxCapsFilter, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(18).addComponent(chckbxLinkFilter, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(18).addComponent(chckbxColorFilter, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(18).addComponent(chckbxCensorFilter, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(10)))));
		gl_panelFilter.setVerticalGroup(gl_panelFilter.createParallelGroup(Alignment.LEADING).addGroup(gl_panelFilter.createSequentialGroup().addContainerGap().addComponent(lblFilter, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panelFilter.createParallelGroup(Alignment.BASELINE).addComponent(chckbxCapsFilter, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addComponent(chckbxLinkFilter, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addComponent(chckbxColorFilter, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addComponent(chckbxCensorFilter, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)).addContainerGap(13, Short.MAX_VALUE)));
		panelFilter.setLayout(gl_panelFilter);

		JLabel lblColor = new JLabel("Allowed Chat Colors");
		lblColor.setFont(new Font("Dialog", Font.BOLD, 13));
		lblColor.setHorizontalAlignment(SwingConstants.CENTER);

		ColorConfigFieldPanel colorSpringGreen = new ColorConfigFieldPanel(ColorCodes.SPRINGGREEN, false, wrapper.getConfig(), panelColor.getBackground());

		ColorConfigFieldPanel colorRed = new ColorConfigFieldPanel(ColorCodes.RED, true, wrapper.getConfig(), panelColor.getBackground());

		ColorConfigFieldPanel colorDodgerBlue = new ColorConfigFieldPanel(ColorCodes.DODGERBLUE, true, wrapper.getConfig(), panelColor.getBackground());

		ColorConfigFieldPanel colorGreen = new ColorConfigFieldPanel(ColorCodes.GREEN, true, wrapper.getConfig(), panelColor.getBackground());

		ColorConfigFieldPanel colorBlue = new ColorConfigFieldPanel(ColorCodes.BLUE, true, wrapper.getConfig(), panelColor.getBackground());

		ColorConfigFieldPanel colorChocolate = new ColorConfigFieldPanel(ColorCodes.CHOCOLATE, true, wrapper.getConfig(), panelColor.getBackground());

		ColorConfigFieldPanel colorBlueViolet = new ColorConfigFieldPanel(ColorCodes.BLUEVIOLET, true, wrapper.getConfig(), panelColor.getBackground());

		ColorConfigFieldPanel colorFireBrick = new ColorConfigFieldPanel(ColorCodes.FIREBRICK, true, wrapper.getConfig(), panelColor.getBackground());

		ColorConfigFieldPanel colorCadetBlue = new ColorConfigFieldPanel(ColorCodes.CADETBLUE, true, wrapper.getConfig(), panelColor.getBackground());

		ColorConfigFieldPanel colorCoral = new ColorConfigFieldPanel(ColorCodes.CORAL, true, wrapper.getConfig(), panelColor.getBackground());

		ColorConfigFieldPanel colorYellowGreen = new ColorConfigFieldPanel(ColorCodes.YELLOWGREEN, true, wrapper.getConfig(), panelColor.getBackground());

		ColorConfigFieldPanel colorSeaGreen = new ColorConfigFieldPanel(ColorCodes.SEAGREEN, true, wrapper.getConfig(), panelColor.getBackground());

		ColorConfigFieldPanel colorGoldenRod = new ColorConfigFieldPanel(ColorCodes.GOLDENROD, true, wrapper.getConfig(), panelColor.getBackground());

		ColorConfigFieldPanel colorHotPink = new ColorConfigFieldPanel(ColorCodes.HOTPINK, true, wrapper.getConfig(), panelColor.getBackground());

		ColorConfigFieldPanel colorOrangeRed = new ColorConfigFieldPanel(ColorCodes.ORANGERED, true, wrapper.getConfig(), panelColor.getBackground());
		GroupLayout gl_panelColor = new GroupLayout(panelColor);
		gl_panelColor.setHorizontalGroup(gl_panelColor.createParallelGroup(Alignment.LEADING).addGroup(gl_panelColor.createSequentialGroup().addContainerGap().addComponent(lblColor, GroupLayout.PREFERRED_SIZE, 440, GroupLayout.PREFERRED_SIZE).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGroup(gl_panelColor.createSequentialGroup().addGap(69).addGroup(gl_panelColor.createParallelGroup(Alignment.LEADING, false).addGroup(gl_panelColor.createSequentialGroup().addComponent(colorSpringGreen, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(colorBlue, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(colorCadetBlue, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)).addGroup(gl_panelColor.createSequentialGroup().addComponent(colorRed,GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(colorChocolate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(colorCoral, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)).addGroup(gl_panelColor.createSequentialGroup().addComponent(colorDodgerBlue, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(colorBlueViolet, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(colorYellowGreen, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)).addGroup(gl_panelColor.createSequentialGroup().addComponent(colorGoldenRod, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(colorHotPink, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(colorOrangeRed, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)).addGroup(gl_panelColor.createSequentialGroup().addComponent(colorGreen, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(colorFireBrick, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(colorSeaGreen, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))).addGap(67)));
		gl_panelColor.setVerticalGroup(gl_panelColor.createParallelGroup(Alignment.LEADING).addGroup(gl_panelColor.createSequentialGroup().addContainerGap().addComponent(lblColor).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panelColor.createParallelGroup(Alignment.LEADING).addComponent(colorSpringGreen, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE).addComponent(colorBlue, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE).addComponent(colorCadetBlue, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panelColor.createParallelGroup(Alignment.LEADING).addComponent(colorRed, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE).addComponent(colorChocolate, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE).addComponent(colorCoral, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)).addGap(6).addGroup(gl_panelColor.createParallelGroup(Alignment.LEADING).addComponent(colorBlueViolet, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE).addComponent(colorDodgerBlue, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE).addComponent(colorYellowGreen, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panelColor.createParallelGroup(Alignment.TRAILING).addComponent(colorGoldenRod, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE).addComponent(colorHotPink, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE).addComponent(colorOrangeRed, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panelColor.createParallelGroup(Alignment.TRAILING).addComponent(colorGreen, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE).addComponent(colorFireBrick,GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE).addComponent(colorSeaGreen, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)).addContainerGap(35, Short.MAX_VALUE)));
		panelColor.setLayout(gl_panelColor);
		contentPanel.setLayout(gl_contentPanel);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		buttonPane.add(btnClose);
		getRootPane().setDefaultButton(btnClose);
	}
}
