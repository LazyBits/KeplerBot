package net.keplerbot.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LogPannel extends JScrollPane{
	
	private static final long serialVersionUID = 1L;
	
	private JTextArea textArea;
	
	public LogPannel() {
		textArea = new JTextArea();
		textArea.setColumns(20);
		textArea.setFont(new Font("Courier New", 0, 12));
		textArea.setRows(5);
		textArea.setEditable(false);
		textArea.setBackground(new Color(90, 90, 90));
		this.setBorder(BorderFactory.createBevelBorder(2));
		this.add(textArea);
		this.setViewportView(textArea);
	}

	public void addLogString(String log) {
		textArea.append(log + "\n");
	}
}
