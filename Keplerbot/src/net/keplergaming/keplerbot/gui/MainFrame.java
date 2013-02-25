package net.keplergaming.keplerbot.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.UIManager;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.SystemColor;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MainFrame {

	private JFrame frmKeplerbot;
	private JTextField txtNothingToSee;
	private JTextField txtOrHere;
	private JTextField txtYupNothingHere;
	private JTextField txtAndNothingAgain;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
					
					MainFrame window = new MainFrame();
					window.frmKeplerbot.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmKeplerbot = new JFrame();
		frmKeplerbot.setTitle("Keplerbot");
		frmKeplerbot.setBounds(100, 100, 800, 500);
		frmKeplerbot.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		GroupLayout groupLayout = new GroupLayout(frmKeplerbot.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel homePanel = new JPanel();
		homePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		homePanel.setBackground(SystemColor.activeCaptionBorder);
		tabbedPane.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Home</body></html>", null, homePanel, null);
		
		txtNothingToSee = new JTextField();
		txtNothingToSee.setHorizontalAlignment(SwingConstants.CENTER);
		txtNothingToSee.setText("Nothing to see here ;p");
		txtNothingToSee.setColumns(10);
		GroupLayout gl_homePanel = new GroupLayout(homePanel);
		gl_homePanel.setHorizontalGroup(
			gl_homePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_homePanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtNothingToSee, GroupLayout.PREFERRED_SIZE, 395, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(249, Short.MAX_VALUE))
		);
		gl_homePanel.setVerticalGroup(
			gl_homePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_homePanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtNothingToSee, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(242, Short.MAX_VALUE))
		);
		homePanel.setLayout(gl_homePanel);
		
		JPanel streamPanel = new JPanel();
		streamPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		streamPanel.setBackground(SystemColor.activeCaptionBorder);
		tabbedPane.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Streams</body></html>", null, streamPanel, null);
		
		txtOrHere = new JTextField();
		txtOrHere.setText("Or here");
		txtOrHere.setHorizontalAlignment(SwingConstants.CENTER);
		txtOrHere.setColumns(10);
		GroupLayout gl_streamPanel = new GroupLayout(streamPanel);
		gl_streamPanel.setHorizontalGroup(
			gl_streamPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_streamPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtOrHere, GroupLayout.PREFERRED_SIZE, 395, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(249, Short.MAX_VALUE))
		);
		gl_streamPanel.setVerticalGroup(
			gl_streamPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_streamPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtOrHere, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(242, Short.MAX_VALUE))
		);
		streamPanel.setLayout(gl_streamPanel);
		
		JPanel configPanel = new JPanel();
		configPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		configPanel.setBackground(SystemColor.activeCaptionBorder);
		tabbedPane.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Config</body></html>", null, configPanel, null);
		
		txtYupNothingHere = new JTextField();
		txtYupNothingHere.setText("yup, Nothing here");
		txtYupNothingHere.setHorizontalAlignment(SwingConstants.CENTER);
		txtYupNothingHere.setColumns(10);
		GroupLayout gl_configPanel = new GroupLayout(configPanel);
		gl_configPanel.setHorizontalGroup(
			gl_configPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_configPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtYupNothingHere, GroupLayout.PREFERRED_SIZE, 395, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(249, Short.MAX_VALUE))
		);
		gl_configPanel.setVerticalGroup(
			gl_configPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_configPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtYupNothingHere, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(242, Short.MAX_VALUE))
		);
		configPanel.setLayout(gl_configPanel);
		
		JPanel aboutPanel = new JPanel();
		aboutPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		aboutPanel.setBackground(SystemColor.activeCaptionBorder);
		tabbedPane.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>About</body></html>", null, aboutPanel, null);
		
		txtAndNothingAgain = new JTextField();
		txtAndNothingAgain.setText("And nothing again :O");
		txtAndNothingAgain.setHorizontalAlignment(SwingConstants.CENTER);
		txtAndNothingAgain.setColumns(10);
		GroupLayout gl_aboutPanel = new GroupLayout(aboutPanel);
		gl_aboutPanel.setHorizontalGroup(
			gl_aboutPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_aboutPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtAndNothingAgain, GroupLayout.PREFERRED_SIZE, 395, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(249, Short.MAX_VALUE))
		);
		gl_aboutPanel.setVerticalGroup(
			gl_aboutPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_aboutPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtAndNothingAgain, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(242, Short.MAX_VALUE))
		);
		aboutPanel.setLayout(gl_aboutPanel);
		
		frmKeplerbot.getContentPane().setLayout(groupLayout);
	}
}
