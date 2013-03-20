package net.keplergaming.keplerbot.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class AddStreamDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JCheckBox chckbxJoinMsg;

	/**
	 * Create the dialog.
	 */
	public AddStreamDialog(Frame parent) {
		super(parent, true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setTitle("Connect to Stream");
		setResizable(false);
		setBounds(100, 100, 350, 150);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setLocationRelativeTo(parent);

		textField = new JTextField();
		textField.setColumns(10);

		JLabel lblStream = new JLabel("Stream name");

		chckbxJoinMsg = new JCheckBox("Display join message");
		chckbxJoinMsg.setFocusable(false);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPanel.createSequentialGroup().addContainerGap().addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addComponent(chckbxJoinMsg).addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup().addComponent(lblStream, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(textField, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE))).addContainerGap()));

		gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPanel.createSequentialGroup().addContainerGap().addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblStream, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE).addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE).addComponent(chckbxJoinMsg)));
		contentPanel.setLayout(gl_contentPanel);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!textField.getText().isEmpty()) {
					MainFrame.getInstance().addStream(textField.getText(), chckbxJoinMsg.isSelected());
					setVisible(false);
					dispose();
				}
			}
		});
		okButton.setFocusable(false);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		cancelButton.setFocusable(false);
		buttonPane.add(cancelButton);
	}
}
