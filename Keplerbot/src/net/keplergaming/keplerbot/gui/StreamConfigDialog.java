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

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

@SuppressWarnings("serial")
public class StreamConfigDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel panelColor;

	/**
	 * Create the dialog.
	 */
	public StreamConfigDialog(JFrame parent, final KeplerBotWrapper wrapper) {
		super(parent, true);
		setTitle("Stream Config");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 519, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setLocationRelativeTo(parent);

		panelColor = new JPanel();
		panelColor.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,gl_contentPanel.createSequentialGroup().addContainerGap().addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING).addComponent(panel,Alignment.LEADING,GroupLayout.DEFAULT_SIZE,473,Short.MAX_VALUE).addComponent(panelColor,Alignment.LEADING,GroupLayout.DEFAULT_SIZE,473,Short.MAX_VALUE)).addContainerGap()));
		gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPanel.createSequentialGroup().addComponent(panelColor, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(panel, GroupLayout.PREFERRED_SIZE, 74,Short.MAX_VALUE).addContainerGap()));
		panel.setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblFilters = new JLabel("Enabled Filters");
		lblFilters.setFont(new Font("Dialog", Font.BOLD, 13));
		lblFilters.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblFilters, "2, 2, 23, 1");

		JLabel lblCapsFilter = new JLabel("Caps Filter");
		panel.add(lblCapsFilter, "2, 4");

		final JCheckBox chckbxCapsFilter = new JCheckBox();
		chckbxCapsFilter.setSelected(wrapper.getConfig().getBoolean(Configuration.CAPS_FILTER[0], Boolean.parseBoolean(Configuration.CAPS_FILTER[1])));
		chckbxCapsFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wrapper.getConfig().setBoolean(Configuration.CAPS_FILTER[0], chckbxCapsFilter.isSelected());
				try {
					wrapper.getFilterManager().getFilter("caps").setDisabled(chckbxCapsFilter.isSelected());
				} catch (BotException ex) {
					wrapper.getStreamLogger().error("Can't change filter state", ex);
				}
				wrapper.getConfig().saveConfig();
			}
		});
		panel.add(chckbxCapsFilter, "4, 4");

		JLabel lblLinkFilter = new JLabel("Link Filter");
		panel.add(lblLinkFilter, "8, 4");

		final JCheckBox chckbxLinkFilter = new JCheckBox();
		chckbxLinkFilter.setSelected(wrapper.getConfig().getBoolean(Configuration.LINK_FILTER[0], Boolean.parseBoolean(Configuration.LINK_FILTER[1])));
		chckbxLinkFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wrapper.getConfig().setBoolean(Configuration.LINK_FILTER[0], chckbxLinkFilter.isSelected());
				try {
					wrapper.getFilterManager().getFilter("link").setDisabled(chckbxLinkFilter.isSelected());
				} catch (BotException ex) {
					wrapper.getStreamLogger().error("Can't change filter state", ex);
				}
				wrapper.getConfig().saveConfig();
			}
		});
		panel.add(chckbxLinkFilter, "10, 4");

		JLabel lblColorFilter = new JLabel("Color Filter");
		panel.add(lblColorFilter, "14, 4");

		final JCheckBox chckbxColorFilter = new JCheckBox();
		chckbxColorFilter.setSelected(wrapper.getConfig().getBoolean(Configuration.COLOR_FILTER[0], Boolean.parseBoolean(Configuration.COLOR_FILTER[1])));
		chckbxColorFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wrapper.getConfig().setBoolean(Configuration.COLOR_FILTER[0], chckbxColorFilter.isSelected());
				try {
					wrapper.getFilterManager().getFilter("color").setDisabled(chckbxColorFilter.isSelected());
				} catch (BotException ex) {
					wrapper.getStreamLogger().error("Can't change filter state", ex);
				}
				wrapper.getConfig().saveConfig();
			}
		});
		panel.add(chckbxColorFilter, "16, 4");

		JLabel lblCensorFilter = new JLabel("Censor Filter");
		panel.add(lblCensorFilter, "20, 4");

		final JCheckBox chckbxCensorFilter = new JCheckBox();
		chckbxCensorFilter.setSelected(wrapper.getConfig().getBoolean(Configuration.CENSOR_FILTER[0], Boolean.parseBoolean(Configuration.CENSOR_FILTER[1])));
		chckbxCensorFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wrapper.getConfig().setBoolean(Configuration.CENSOR_FILTER[0], chckbxCensorFilter.isSelected());
				try {
					wrapper.getFilterManager().getFilter("censor").setDisabled(chckbxLinkFilter.isSelected());
				} catch (BotException ex) {
					wrapper.getStreamLogger().error("Can't change filter state", ex);
				}
				wrapper.getConfig().saveConfig();
			}
		});
		panel.add(chckbxCensorFilter, "22, 4");

		panelColor.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("90px"), ColumnSpec.decode("90px"),
				ColumnSpec.decode("90px"), ColumnSpec.decode("90px"),
				ColumnSpec.decode("90px"), }, new RowSpec[] {
				FormFactory.NARROW_LINE_GAP_ROWSPEC, RowSpec.decode("28px"),
				RowSpec.decode("28px"), RowSpec.decode("28px"),
				RowSpec.decode("28px"), }));

		JLabel lblColors = new JLabel("Allowed Chat Colors");
		lblColors.setHorizontalAlignment(SwingConstants.CENTER);
		lblColors.setFont(new Font("Dialog", Font.BOLD, 13));
		panelColor.add(lblColors, "2, 2, 5, 1, fill, fill");

		ColorConfigFieldPanel colorChocolate = new ColorConfigFieldPanel(ColorCodes.CHOCOLATE, true, wrapper.getConfig(), panelColor.getBackground());
		panelColor.add(colorChocolate, "6, 3, fill, fill");

		ColorConfigFieldPanel colorYellowGreen = new ColorConfigFieldPanel(ColorCodes.YELLOWGREEN, true, wrapper.getConfig(), panelColor.getBackground());
		panelColor.add(colorYellowGreen, "5, 4, fill, fill");

		ColorConfigFieldPanel colorBlueViolet = new ColorConfigFieldPanel(ColorCodes.BLUEVIOLET, true, wrapper.getConfig(), panelColor.getBackground());
		panelColor.add(colorBlueViolet, "2, 4, fill, fill");

		ColorConfigFieldPanel colorCadetBlue = new ColorConfigFieldPanel(ColorCodes.CADETBLUE, true, wrapper.getConfig(), panelColor.getBackground());
		panelColor.add(colorCadetBlue, "3, 4, fill, fill");

		ColorConfigFieldPanel colorCoral = new ColorConfigFieldPanel(ColorCodes.CORAL, true, wrapper.getConfig(), panelColor.getBackground());
		panelColor.add(colorCoral, "4, 4, fill, fill");

		ColorConfigFieldPanel colorGoldenRod = new ColorConfigFieldPanel(ColorCodes.GOLDENROD, true, wrapper.getConfig(), panelColor.getBackground());
		panelColor.add(colorGoldenRod, "4, 5, fill, fill");

		ColorConfigFieldPanel colorRed = new ColorConfigFieldPanel(ColorCodes.RED, true, wrapper.getConfig(), panelColor.getBackground());
		panelColor.add(colorRed, "3, 3, fill, fill");

		ColorConfigFieldPanel colorDodgerBlue = new ColorConfigFieldPanel(ColorCodes.DODGERBLUE, true, wrapper.getConfig(), panelColor.getBackground());
		panelColor.add(colorDodgerBlue, "4, 3, fill, fill");

		ColorConfigFieldPanel colorBlue = new ColorConfigFieldPanel(ColorCodes.BLUE, true, wrapper.getConfig(), panelColor.getBackground());
		panelColor.add(colorBlue, "5, 3, fill, fill");

		ColorConfigFieldPanel colorGreen = new ColorConfigFieldPanel(ColorCodes.GREEN, true, wrapper.getConfig(), panelColor.getBackground());
		panelColor.add(colorGreen, "6, 4, fill, fill");

		ColorConfigFieldPanel colorFireBrick = new ColorConfigFieldPanel(ColorCodes.FIREBRICK, true, wrapper.getConfig(), panelColor.getBackground());
		panelColor.add(colorFireBrick, "2, 5, fill, fill");

		ColorConfigFieldPanel colorSeaGreen = new ColorConfigFieldPanel(ColorCodes.SEAGREEN, true, wrapper.getConfig(), panelColor.getBackground());
		panelColor.add(colorSeaGreen, "3, 5, fill, fill");

		ColorConfigFieldPanel colorSpringGreen = new ColorConfigFieldPanel(ColorCodes.SPRINGGREEN, false, wrapper.getConfig(), panelColor.getBackground());
		panelColor.add(colorSpringGreen, "2, 3, fill, fill");

		ColorConfigFieldPanel colorHotPink = new ColorConfigFieldPanel(ColorCodes.HOTPINK, true, wrapper.getConfig(), panelColor.getBackground());
		panelColor.add(colorHotPink, "5, 5, fill, fill");

		ColorConfigFieldPanel colorOrangeRed = new ColorConfigFieldPanel(ColorCodes.ORANGERED, true, wrapper.getConfig(), panelColor.getBackground());
		panelColor.add(colorOrangeRed, "6, 5, fill, fill");
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
