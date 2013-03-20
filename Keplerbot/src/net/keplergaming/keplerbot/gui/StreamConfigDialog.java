package net.keplergaming.keplerbot.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.keplergaming.keplerbot.KeplerBotWrapper;
import net.keplergaming.keplerbot.config.Configuration;
import net.keplergaming.keplerbot.exception.BotException;
import net.keplergaming.keplerbot.filters.Filter;
import net.keplergaming.keplerbot.utils.ColorCodes;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

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
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 440);
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

		JPanel panelActions = new JPanel();
		panelActions.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup().addContainerGap().addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPanel.createSequentialGroup().addComponent(panelActions, GroupLayout.PREFERRED_SIZE, 757, GroupLayout.PREFERRED_SIZE).addContainerGap()).addGroup(gl_contentPanel.createSequentialGroup().addComponent(panelColor, GroupLayout.PREFERRED_SIZE, 464, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE).addComponent(panelFilter, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(panelMute, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(40)))));
		gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup().addContainerGap().addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING, false).addComponent(panelMute, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(panelFilter, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE).addComponent(panelColor, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED, 9, Short.MAX_VALUE).addComponent(panelActions, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE).addGap(74)));

		JLabel lblActions = new JLabel("Filter Actions");
		lblActions.setHorizontalAlignment(SwingConstants.CENTER);
		lblActions.setFont(new Font("Dialog", Font.BOLD, 13));

		JPanel panelStrikes = new JPanel();
		panelStrikes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		final JNumberTextField txtValue1 = new JNumberTextField();
		txtValue1.setFont(new Font("Dialog", Font.PLAIN, 13));

		final JNumberTextField txtValue2 = new JNumberTextField();
		txtValue2.setFont(new Font("Dialog", Font.PLAIN, 13));

		final JNumberTextField txtValue3 = new JNumberTextField();
		txtValue3.setFont(new Font("Dialog", Font.PLAIN, 13));

		final JSlider slider1 = new JSlider();
		slider1.setMaximum(500);
		txtValue1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String typed = txtValue1.getText();
				slider1.setValue(0);
				if (!typed.isEmpty()) {
					int value = Integer.parseInt(typed);
					if (value > 500) {
						slider1.setMaximum(value);
					} else {
						slider1.setMaximum(500);
					}
					slider1.setValue(value);
				}
			}
		});
		slider1.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				txtValue1.setInt(slider1.getValue());
			}
		});
		slider1.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				slider1.setMaximum(500);
			}
		});
		slider1.setMinorTickSpacing(1);

		final JSlider slider2 = new JSlider();
		slider2.setMaximum(60);
		txtValue2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String typed = txtValue2.getText();
				slider2.setValue(0);
				if (!typed.isEmpty()) {
					int value = Integer.parseInt(typed);
					if (value > 60) {
						slider2.setMaximum(value);
					} else {
						slider2.setMaximum(60);
					}
					slider2.setValue(value);
				}
			}
		});
		slider2.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				txtValue2.setInt(slider2.getValue());
			}
		});
		slider2.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				slider2.setMaximum(60);
			}
		});
		slider2.setMinorTickSpacing(1);

		final JSlider slider3 = new JSlider();
		slider3.setMaximum(500);
		txtValue3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String typed = txtValue3.getText();
				slider3.setValue(0);
				if (!typed.isEmpty()) {
					int value = Integer.parseInt(typed);
					if (value > 500) {
						slider3.setMaximum(value);
					} else {
						slider3.setMaximum(500);
					}
					slider3.setValue(value);
				}
			}
		});
		slider3.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				txtValue3.setInt(slider3.getValue());
			}
		});
		slider3.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				slider3.setMaximum(500);
			}
		});
		slider3.setMinorTickSpacing(1);

		ButtonGroup group1 = new ButtonGroup();

		ButtonGroup group2 = new ButtonGroup();

		ButtonGroup group3 = new ButtonGroup();

		final JTextField Warning1 = new JTextField();
		Warning1.setColumns(10);

		final JTextField Warning2 = new JTextField();
		Warning2.setColumns(10);

		final JTextField Warning3 = new JTextField();
		Warning3.setColumns(10);

		final JRadioButton rdbtnTimeOut1 = new JRadioButton("Time Out");
		rdbtnTimeOut1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Warning1.setEnabled(!rdbtnTimeOut1.isSelected());
				txtValue1.setEnabled(rdbtnTimeOut1.isSelected());
				slider1.setEnabled(rdbtnTimeOut1.isSelected());
			}
		});
		rdbtnTimeOut1.setFont(new Font("Dialog", Font.PLAIN, 13));
		group1.add(rdbtnTimeOut1);

		final JRadioButton rdbtnTimeOut2 = new JRadioButton("Time Out");
		rdbtnTimeOut2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Warning2.setEnabled(!rdbtnTimeOut2.isSelected());
				txtValue2.setEnabled(rdbtnTimeOut2.isSelected());
				slider2.setEnabled(rdbtnTimeOut2.isSelected());
			}
		});
		rdbtnTimeOut2.setFont(new Font("Dialog", Font.PLAIN, 13));
		group2.add(rdbtnTimeOut2);

		final JRadioButton rdbtnTimeOut3 = new JRadioButton("Time Out");
		rdbtnTimeOut3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Warning3.setEnabled(!rdbtnTimeOut3.isSelected());
				txtValue3.setEnabled(rdbtnTimeOut3.isSelected());
				slider3.setEnabled(rdbtnTimeOut3.isSelected());
			}
		});
		rdbtnTimeOut3.setFont(new Font("Dialog", Font.PLAIN, 13));
		group3.add(rdbtnTimeOut3);

		final JRadioButton rdbtnWarning1 = new JRadioButton("Warning");
		rdbtnWarning1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Warning1.setEnabled(rdbtnWarning1.isSelected());
				txtValue1.setEnabled(!rdbtnWarning1.isSelected());
				slider1.setEnabled(!rdbtnWarning1.isSelected());
			}
		});
		rdbtnWarning1.setFont(new Font("Dialog", Font.PLAIN, 13));
		group1.add(rdbtnWarning1);

		final JRadioButton rdbtnWarning2 = new JRadioButton("Warning");
		rdbtnWarning2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Warning2.setEnabled(rdbtnWarning2.isSelected());
				txtValue2.setEnabled(!rdbtnWarning2.isSelected());
				slider2.setEnabled(!rdbtnWarning2.isSelected());
			}
		});
		rdbtnWarning2.setFont(new Font("Dialog", Font.PLAIN, 13));
		group2.add(rdbtnWarning2);

		final JRadioButton rdbtnWarning3 = new JRadioButton("Warning");
		rdbtnWarning3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Warning3.setEnabled(rdbtnWarning3.isSelected());
				txtValue3.setEnabled(!rdbtnWarning3.isSelected());
				slider3.setEnabled(!rdbtnWarning3.isSelected());
			}
		});
		rdbtnWarning3.setFont(new Font("Dialog", Font.PLAIN, 13));
		group3.add(rdbtnWarning3);

		JLabel lblFirstStrike = new JLabel("Strike 1");
		lblFirstStrike.setFont(new Font("Dialog", Font.PLAIN, 13));

		JLabel lblSecondStrike = new JLabel("Strike 2");
		lblSecondStrike.setFont(new Font("Dialog", Font.PLAIN, 13));

		JLabel labelThirdStrike = new JLabel("Strike 3");
		labelThirdStrike.setFont(new Font("Dialog", Font.PLAIN, 13));

		JLabel lblS1 = new JLabel("s");

		JLabel lblS2 = new JLabel("m");

		JLabel lblS3 = new JLabel("m");

		GroupLayout gl_panelStrikes = new GroupLayout(panelStrikes);
		gl_panelStrikes.setHorizontalGroup(gl_panelStrikes.createParallelGroup(Alignment.LEADING).addGroup(gl_panelStrikes.createSequentialGroup().addGap(16).addGroup(gl_panelStrikes.createParallelGroup(Alignment.TRAILING).addGroup(gl_panelStrikes.createSequentialGroup().addComponent(labelThirdStrike).addGap(18).addComponent(rdbtnWarning3).addGap(18).addComponent(Warning3, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(rdbtnTimeOut3)).addGroup(gl_panelStrikes.createSequentialGroup().addGroup(gl_panelStrikes.createParallelGroup(Alignment.TRAILING).addComponent(lblSecondStrike).addComponent(lblFirstStrike)).addGap(18).addGroup(gl_panelStrikes.createParallelGroup(Alignment.TRAILING, false).addGroup(gl_panelStrikes.createSequentialGroup().addComponent(rdbtnWarning2).addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(Warning2, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(rdbtnTimeOut2)).addGroup(gl_panelStrikes.createSequentialGroup().addComponent(rdbtnWarning1).addGap(18).addComponent(Warning1, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(rdbtnTimeOut1))))).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panelStrikes.createParallelGroup(Alignment.LEADING, false).addComponent(slider3, 0, 0, Short.MAX_VALUE).addComponent(slider2, 0, 0, Short.MAX_VALUE).addComponent(slider1, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panelStrikes.createParallelGroup(Alignment.LEADING, false).addComponent(txtValue3).addComponent(txtValue1).addComponent(txtValue2, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panelStrikes.createParallelGroup(Alignment.LEADING, false).addComponent(lblS1, GroupLayout.DEFAULT_SIZE, 10, Short.MAX_VALUE).addComponent(lblS2, GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE).addComponent(lblS3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGap(15)));
		gl_panelStrikes.setVerticalGroup(gl_panelStrikes.createParallelGroup(Alignment.LEADING).addGroup(gl_panelStrikes.createSequentialGroup().addContainerGap().addGroup(gl_panelStrikes.createParallelGroup(Alignment.TRAILING).addGroup(gl_panelStrikes.createParallelGroup(Alignment.BASELINE).addComponent(rdbtnWarning1).addComponent(Warning1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblFirstStrike).addComponent(rdbtnTimeOut1)).addGroup(gl_panelStrikes.createParallelGroup(Alignment.LEADING).addGroup(gl_panelStrikes.createParallelGroup(Alignment.BASELINE).addComponent(txtValue1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblS1)).addComponent(slider1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))).addGap(6).addGroup(gl_panelStrikes.createParallelGroup(Alignment.TRAILING).addGroup(gl_panelStrikes.createParallelGroup(Alignment.BASELINE).addComponent(Warning2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(rdbtnWarning2).addComponent(lblSecondStrike).addComponent(rdbtnTimeOut2)).addGroup(gl_panelStrikes.createParallelGroup(Alignment.LEADING).addGroup(gl_panelStrikes.createParallelGroup(Alignment.BASELINE).addComponent(txtValue2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblS2)).addComponent(slider2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))).addGap(6).addGroup(gl_panelStrikes.createParallelGroup(Alignment.TRAILING).addGroup(gl_panelStrikes.createParallelGroup(Alignment.BASELINE).addComponent(Warning3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(rdbtnWarning3).addComponent(labelThirdStrike).addComponent(rdbtnTimeOut3)).addGroup(gl_panelStrikes.createParallelGroup(Alignment.LEADING).addGroup(gl_panelStrikes.createParallelGroup(Alignment.BASELINE).addComponent(txtValue3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblS3, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)).addComponent(slider3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))).addContainerGap(15, Short.MAX_VALUE)));
		panelStrikes.setLayout(gl_panelStrikes);

		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					try {
						Filter filter = wrapper.getFilterManager().getFilter(((String) e.getItem()).toLowerCase());

						filter.setWarning(wrapper.getConfig(), 1, Warning1.getText());
						filter.setWarning(wrapper.getConfig(), 2, Warning2.getText());
						filter.setWarning(wrapper.getConfig(), 3, Warning3.getText());

						filter.setTimeOutValue(wrapper.getConfig(), 1, txtValue1.getInt());
						filter.setTimeOutValue(wrapper.getConfig(), 2, txtValue2.getInt() * 60);
						filter.setTimeOutValue(wrapper.getConfig(), 3, txtValue3.getInt() * 60);

						filter.setTimeOutEnabled(wrapper.getConfig(), 1, rdbtnTimeOut1.isSelected());
						filter.setTimeOutEnabled(wrapper.getConfig(), 2, rdbtnTimeOut2.isSelected());
						filter.setTimeOutEnabled(wrapper.getConfig(), 3, rdbtnTimeOut3.isSelected());

						wrapper.getConfig().saveConfig();
					} catch (BotException ex) {
						wrapper.getStreamLogger().error("Can't find filter " + e.getItem(), ex);
					}
				} else if (e.getStateChange() == ItemEvent.SELECTED) {
					try {
						Filter filter = wrapper.getFilterManager().getFilter(((String) comboBox.getSelectedItem()).toLowerCase());

						Warning1.setText(filter.getWarning(wrapper.getConfig(), 1));
						Warning2.setText(filter.getWarning(wrapper.getConfig(), 2));
						Warning3.setText(filter.getWarning(wrapper.getConfig(), 3));

						slider1.setValue(filter.getTimeOutValue(wrapper.getConfig(), 1));
						slider2.setValue(filter.getTimeOutValue(wrapper.getConfig(), 2) / 60);
						slider3.setValue(filter.getTimeOutValue(wrapper.getConfig(), 3) / 60);

						txtValue1.setInt(filter.getTimeOutValue(wrapper.getConfig(), 1));
						txtValue2.setInt(filter.getTimeOutValue(wrapper.getConfig(), 2) / 60);
						txtValue3.setInt(filter.getTimeOutValue(wrapper.getConfig(), 3) / 60);

						rdbtnTimeOut1.setSelected(filter.isTimeOutEnabled(wrapper.getConfig(), 1));
						rdbtnWarning1.setSelected(!filter.isTimeOutEnabled(wrapper.getConfig(), 1));
						Warning1.setEnabled(!rdbtnTimeOut1.isSelected());
						txtValue1.setEnabled(rdbtnTimeOut1.isSelected());
						slider1.setEnabled(rdbtnTimeOut1.isSelected());

						rdbtnTimeOut2.setSelected(filter.isTimeOutEnabled(wrapper.getConfig(), 2));
						rdbtnWarning2.setSelected(!filter.isTimeOutEnabled(wrapper.getConfig(), 2));
						Warning2.setEnabled(!rdbtnTimeOut2.isSelected());
						txtValue2.setEnabled(rdbtnTimeOut2.isSelected());
						slider2.setEnabled(rdbtnTimeOut2.isSelected());

						rdbtnTimeOut3.setSelected(filter.isTimeOutEnabled(wrapper.getConfig(), 3));
						rdbtnWarning3.setSelected(!filter.isTimeOutEnabled(wrapper.getConfig(), 3));
						Warning3.setEnabled(!rdbtnTimeOut3.isSelected());
						txtValue3.setEnabled(rdbtnTimeOut3.isSelected());
						slider3.setEnabled(rdbtnTimeOut3.isSelected());
					} catch (BotException ex) {
						wrapper.getStreamLogger().error("Can't find filter " + (String) comboBox.getSelectedItem(), ex);
					}
				}
			}
		});
		comboBox.setFont(new Font("Dialog", Font.PLAIN, 13));
		comboBox.addItem("Color");
		comboBox.addItem("Links");
		comboBox.addItem("Caps");
		comboBox.addItem("Censor");

		JLabel lblFilterBox = new JLabel("Filter");
		lblFilterBox.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilterBox.setFont(new Font("Dialog", Font.PLAIN, 13));

		GroupLayout gl_panelActions = new GroupLayout(panelActions);
		gl_panelActions.setHorizontalGroup(gl_panelActions.createParallelGroup(Alignment.TRAILING).addGroup(gl_panelActions.createSequentialGroup().addContainerGap().addGroup(gl_panelActions.createParallelGroup(Alignment.TRAILING).addComponent(lblActions, GroupLayout.DEFAULT_SIZE, 733, Short.MAX_VALUE).addGroup(gl_panelActions.createSequentialGroup().addGroup(gl_panelActions.createParallelGroup(Alignment.TRAILING).addComponent(lblFilterBox, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE).addComponent(comboBox, 0, 96, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.RELATED).addComponent(panelStrikes, GroupLayout.PREFERRED_SIZE, 649, GroupLayout.PREFERRED_SIZE))).addContainerGap()));
		gl_panelActions.setVerticalGroup(gl_panelActions.createParallelGroup(Alignment.LEADING).addGroup(gl_panelActions.createSequentialGroup().addGap(5).addComponent(lblActions).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panelActions.createParallelGroup(Alignment.LEADING).addGroup(gl_panelActions.createSequentialGroup().addComponent(lblFilterBox).addPreferredGap(ComponentPlacement.RELATED).addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addComponent(panelStrikes, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)).addContainerGap(14, Short.MAX_VALUE)));
		panelActions.setLayout(gl_panelActions);

		final JCheckBox chckbxErrors = new JCheckBox("Errors");
		chckbxErrors.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxErrors.setFont(new Font("Dialog", Font.PLAIN, 13));
		chckbxErrors.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wrapper.getConfig().setBoolean(Configuration.MUTE_ERRORS[0], chckbxErrors.isSelected());
				wrapper.muteAll(chckbxErrors.isSelected());
			}
		});
		chckbxErrors.setSelected(wrapper.getConfig().getBoolean(Configuration.MUTE_ERRORS[0], Boolean.parseBoolean(Configuration.MUTE_ERRORS[1])));

		final JCheckBox chckbxWarnings = new JCheckBox("Warnings");
		chckbxWarnings.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxWarnings.setFont(new Font("Dialog", Font.PLAIN, 13));
		chckbxWarnings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wrapper.getConfig().setBoolean(Configuration.MUTE_WARNINGS[0], chckbxWarnings.isSelected());
				wrapper.muteAll(chckbxWarnings.isSelected());
			}
		});
		chckbxWarnings.setSelected(wrapper.getConfig().getBoolean(Configuration.MUTE_WARNINGS[0], Boolean.parseBoolean(Configuration.MUTE_WARNINGS[1])));

		final JCheckBox chckbxAll = new JCheckBox("All");
		chckbxAll.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxAll.setFont(new Font("Dialog", Font.PLAIN, 13));
		chckbxAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wrapper.getConfig().setBoolean(Configuration.MUTE_ALL[0], chckbxAll.isSelected());
				wrapper.muteAll(chckbxAll.isSelected());
			}
		});
		chckbxAll.setSelected(wrapper.getConfig().getBoolean(Configuration.MUTE_ALL[0], Boolean.parseBoolean(Configuration.MUTE_ALL[1])));

		JLabel lblMute = new JLabel("Mute Response");
		lblMute.setHorizontalAlignment(SwingConstants.CENTER);
		lblMute.setFont(new Font("Dialog", Font.BOLD, 13));

		GroupLayout gl_panelMute = new GroupLayout(panelMute);
		gl_panelMute.setHorizontalGroup(gl_panelMute.createParallelGroup(Alignment.LEADING).addGroup(gl_panelMute.createSequentialGroup().addContainerGap().addGroup(gl_panelMute.createParallelGroup(Alignment.LEADING).addComponent(chckbxErrors, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE).addComponent(chckbxAll, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE).addComponent(lblMute).addComponent(chckbxWarnings, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)).addContainerGap(19, Short.MAX_VALUE)));
		gl_panelMute.setVerticalGroup(gl_panelMute.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING, gl_panelMute.createSequentialGroup().addContainerGap().addComponent(lblMute).addGap(18).addComponent(chckbxAll, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(chckbxWarnings, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(chckbxErrors, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addContainerGap(32, Short.MAX_VALUE)));
		panelMute.setLayout(gl_panelMute);

		JLabel lblFilter = new JLabel("Enable Filters");
		lblFilter.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilter.setFont(new Font("Dialog", Font.BOLD, 13));

		final JCheckBox chckbxCapsFilter = new JCheckBox("Caps Filter");
		chckbxCapsFilter.setFont(new Font("Dialog", Font.PLAIN, 13));
		chckbxCapsFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					wrapper.getFilterManager().getFilter("caps").setEnabled(wrapper.getConfig(), chckbxCapsFilter.isSelected());
					if (chckbxCapsFilter.isSelected()) {
						wrapper.getStreamLogger().fine("Caps Filter enabled");
					} else {
						wrapper.getStreamLogger().fine("Caps Filter disabled");
					}
				} catch (BotException ex) {
					wrapper.getStreamLogger().error("Unable change filter state", ex);
				}

			}
		});
		try {
			chckbxCapsFilter.setSelected(wrapper.getFilterManager().getFilter("caps").isEnabled(wrapper.getConfig()));
		} catch (BotException ex) {
			wrapper.getStreamLogger().error("Unable to get filter state", ex);
		}
		chckbxCapsFilter.setHorizontalAlignment(SwingConstants.LEFT);

		final JCheckBox chckbxLinkFilter = new JCheckBox("Link Filter");
		chckbxLinkFilter.setFont(new Font("Dialog", Font.PLAIN, 13));
		chckbxLinkFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					wrapper.getFilterManager().getFilter("links").setEnabled(wrapper.getConfig(), chckbxCapsFilter.isSelected());
					if (chckbxLinkFilter.isSelected()) {
						wrapper.getStreamLogger().fine("Link Filter enabled");
					} else {
						wrapper.getStreamLogger().fine("Link Filter disabled");
					}
				} catch (BotException ex) {
					wrapper.getStreamLogger().error("Unable change filter state", ex);
				}
			}
		});
		try {
			chckbxLinkFilter.setSelected(wrapper.getFilterManager().getFilter("links").isEnabled(wrapper.getConfig()));
		} catch (BotException ex) {
			wrapper.getStreamLogger().error("Unable to get filter state", ex);
		}
		chckbxLinkFilter.setHorizontalAlignment(SwingConstants.LEFT);

		final JCheckBox chckbxColorFilter = new JCheckBox("Color Filter");
		chckbxColorFilter.setFont(new Font("Dialog", Font.PLAIN, 13));
		chckbxColorFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					wrapper.getFilterManager().getFilter("color").setEnabled(wrapper.getConfig(), chckbxCapsFilter.isSelected());
					if (chckbxColorFilter.isSelected()) {
						wrapper.getStreamLogger().fine("Color Filter enabled");
					} else {
						wrapper.getStreamLogger().fine("Color Filter disabled");
					}
				} catch (BotException ex) {
					wrapper.getStreamLogger().error("Unable change filter state", ex);
				}
			}
		});
		try {
			chckbxColorFilter.setSelected(wrapper.getFilterManager().getFilter("color").isEnabled(wrapper.getConfig()));
		} catch (BotException ex) {
			wrapper.getStreamLogger().error("Unable to get filter state", ex);
		}
		chckbxColorFilter.setHorizontalAlignment(SwingConstants.LEFT);

		final JCheckBox chckbxCensorFilter = new JCheckBox("Censor Filter");
		chckbxCensorFilter.setFont(new Font("Dialog", Font.PLAIN, 13));
		chckbxCensorFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					wrapper.getFilterManager().getFilter("censor").setEnabled(wrapper.getConfig(), chckbxCapsFilter.isSelected());
					if (chckbxCensorFilter.isSelected()) {
						wrapper.getStreamLogger().fine("Censor Filter enabled");
					} else {
						wrapper.getStreamLogger().fine("Censor Filter disabled");
					}
				} catch (BotException ex) {
					wrapper.getStreamLogger().error("Unable change filter state", ex);
				}
			}
		});
		try {
			chckbxCensorFilter.setSelected(wrapper.getFilterManager().getFilter("censor").isEnabled(wrapper.getConfig()));
		} catch (BotException ex) {
			wrapper.getStreamLogger().error("Unable to get filter state", ex);
		}
		chckbxCensorFilter.setHorizontalAlignment(SwingConstants.LEFT);

		GroupLayout gl_panelFilter = new GroupLayout(panelFilter);
		gl_panelFilter.setHorizontalGroup(gl_panelFilter.createParallelGroup(Alignment.LEADING).addGroup(gl_panelFilter.createSequentialGroup().addContainerGap().addGroup(gl_panelFilter.createParallelGroup(Alignment.LEADING).addGroup(gl_panelFilter.createSequentialGroup().addGroup(gl_panelFilter.createParallelGroup(Alignment.LEADING, false).addComponent(chckbxLinkFilter, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE).addGroup(gl_panelFilter.createSequentialGroup().addComponent(chckbxCapsFilter).addPreferredGap(ComponentPlacement.RELATED, 32, Short.MAX_VALUE)).addGroup(gl_panelFilter.createParallelGroup(Alignment.TRAILING, false).addComponent(chckbxCensorFilter, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(chckbxColorFilter, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))).addContainerGap()).addGroup(gl_panelFilter.createSequentialGroup().addComponent(lblFilter, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(13)))));
		gl_panelFilter.setVerticalGroup(gl_panelFilter.createParallelGroup(Alignment.TRAILING).addGroup(gl_panelFilter.createSequentialGroup().addContainerGap().addComponent(lblFilter, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, 15, Short.MAX_VALUE).addComponent(chckbxCensorFilter, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(chckbxColorFilter, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(chckbxLinkFilter, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(chckbxCapsFilter, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addContainerGap()));
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
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		buttonPane.add(btnClose);
		getRootPane().setDefaultButton(btnClose);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				try {
					Filter filter = wrapper.getFilterManager().getFilter(((String) comboBox.getSelectedItem()).toLowerCase());

					filter.setWarning(wrapper.getConfig(), 1, Warning1.getText());
					filter.setWarning(wrapper.getConfig(), 2, Warning2.getText());
					filter.setWarning(wrapper.getConfig(), 3, Warning3.getText());

					filter.setTimeOutValue(wrapper.getConfig(), 1, txtValue1.getInt());
					filter.setTimeOutValue(wrapper.getConfig(), 2, txtValue2.getInt() * 60);
					filter.setTimeOutValue(wrapper.getConfig(), 3, txtValue3.getInt() * 60);

					filter.setTimeOutEnabled(wrapper.getConfig(), 1, rdbtnTimeOut1.isSelected());
					filter.setTimeOutEnabled(wrapper.getConfig(), 2, rdbtnTimeOut2.isSelected());
					filter.setTimeOutEnabled(wrapper.getConfig(), 3, rdbtnTimeOut3.isSelected());
				} catch (BotException ex) {
					wrapper.getStreamLogger().error("Can't find filter " + comboBox.getSelectedItem(), ex);
				}

				wrapper.getConfig().saveConfig();
			}
		});
	}
}
