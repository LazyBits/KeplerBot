package net.keplergaming.keplerbot.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import net.keplergaming.keplerbot.config.Configuration;
import net.keplergaming.keplerbot.utils.ColorCodes;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class StreamConfigDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel panelColor;

	/**
	 * Create the dialog.
	 */
	public StreamConfigDialog(JFrame parent, Configuration config) {
		super(parent, true);
		setTitle("StreamConfig");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		panelColor = new JPanel();
		panelColor.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,null, null));

		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addComponent(panelColor,GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE));
		gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPanel.createSequentialGroup().addComponent(panelColor, GroupLayout.PREFERRED_SIZE,128, GroupLayout.PREFERRED_SIZE).addContainerGap(91, Short.MAX_VALUE)));

		panelColor.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("90px"), ColumnSpec.decode("90px"),
				ColumnSpec.decode("90px"), ColumnSpec.decode("90px"),
				ColumnSpec.decode("90px"), }, new RowSpec[] {
				FormFactory.NARROW_LINE_GAP_ROWSPEC, RowSpec.decode("28px"),
				RowSpec.decode("28px"), RowSpec.decode("28px"),
				RowSpec.decode("28px"), }));

		JLabel lbl = new JLabel("Allowed chat colors");
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setFont(new Font("Dialog", Font.BOLD, 13));
		panelColor.add(lbl, "2, 2, 5, 1, fill, fill");

		ColorConfigFieldPanel colorChocolate = new ColorConfigFieldPanel(ColorCodes.CHOCOLATE, true, config, panelColor.getBackground());
		panelColor.add(colorChocolate, "6, 3, fill, fill");

		ColorConfigFieldPanel colorYellowGreen = new ColorConfigFieldPanel(ColorCodes.YELLOWGREEN, true, config, panelColor.getBackground());
		panelColor.add(colorYellowGreen, "5, 4, fill, fill");

		ColorConfigFieldPanel colorBlueViolet = new ColorConfigFieldPanel(ColorCodes.BLUEVIOLET, true, config, panelColor.getBackground());
		panelColor.add(colorBlueViolet, "2, 4, fill, fill");

		ColorConfigFieldPanel colorCadetBlue = new ColorConfigFieldPanel(ColorCodes.CADETBLUE, true, config, panelColor.getBackground());
		panelColor.add(colorCadetBlue, "3, 4, fill, fill");

		ColorConfigFieldPanel colorCoral = new ColorConfigFieldPanel(ColorCodes.CORAL, true, config, panelColor.getBackground());
		panelColor.add(colorCoral, "4, 4, fill, fill");

		ColorConfigFieldPanel colorGoldenRod = new ColorConfigFieldPanel(ColorCodes.GOLDENROD, true, config, panelColor.getBackground());
		panelColor.add(colorGoldenRod, "4, 5, fill, fill");

		ColorConfigFieldPanel colorRed = new ColorConfigFieldPanel(ColorCodes.RED, true, config, panelColor.getBackground());
		panelColor.add(colorRed, "3, 3, fill, fill");

		ColorConfigFieldPanel colorDodgerBlue = new ColorConfigFieldPanel(ColorCodes.DODGERBLUE, true, config, panelColor.getBackground());
		panelColor.add(colorDodgerBlue, "4, 3, fill, fill");

		ColorConfigFieldPanel colorBlue = new ColorConfigFieldPanel(ColorCodes.BLUE, true, config, panelColor.getBackground());
		panelColor.add(colorBlue, "5, 3, fill, fill");

		ColorConfigFieldPanel colorGreen = new ColorConfigFieldPanel(ColorCodes.GREEN, true, config, panelColor.getBackground());
		panelColor.add(colorGreen, "6, 4, fill, fill");

		ColorConfigFieldPanel colorFireBrick = new ColorConfigFieldPanel(ColorCodes.FIREBRICK, true, config, panelColor.getBackground());
		panelColor.add(colorFireBrick, "2, 5, fill, fill");

		ColorConfigFieldPanel colorSeaGreen = new ColorConfigFieldPanel(ColorCodes.SEAGREEN, true, config, panelColor.getBackground());
		panelColor.add(colorSeaGreen, "3, 5, fill, fill");

		ColorConfigFieldPanel colorSpringGreen = new ColorConfigFieldPanel(ColorCodes.SPRINGGREEN, false, config, panelColor.getBackground());
		panelColor.add(colorSpringGreen, "2, 3, fill, fill");

		ColorConfigFieldPanel colorHotPink = new ColorConfigFieldPanel(ColorCodes.HOTPINK, true, config, panelColor.getBackground());
		panelColor.add(colorHotPink, "5, 5, fill, fill");

		ColorConfigFieldPanel colorOrangeRed = new ColorConfigFieldPanel(ColorCodes.ORANGERED, true, config, panelColor.getBackground());
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
