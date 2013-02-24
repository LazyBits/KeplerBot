package net.keplerbot.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import net.keplerbot.Main;
import net.keplerbot.config.KeplerConfig;
import net.keplerbot.log.KeplerLogger;
import net.keplerbot.managers.CommandManager;
import net.keplerbot.utils.StringUtils;

public class InputListener implements ActionListener
{
    final JTextField textField;
    
    private String tab;

    public InputListener(String currenttab, JTextField field)
    {
    	this.tab = currenttab;
        this.textField = field;
    }

    public void actionPerformed(ActionEvent par1ActionEvent)
    {
        String message = this.textField.getText().trim();
        
        if(tab.equalsIgnoreCase("Main")) {
        	if(!onCommand(message)) {
        		KeplerLogger.getInstance(tab).warn("Unable to handle '" + message + "'");
        	}
        }else{
        	if(tab != KeplerConfig.getInstance().login) {
            	if(message.equalsIgnoreCase("!terminate") || message.equalsIgnoreCase("!leave") || message.equalsIgnoreCase("!terminate") || message.equalsIgnoreCase("/leave")) {
            		Main.instance.markremoveBot(tab);
            	}else{
            		Main.instance.getBot(tab).sendMessage(message);
            	}
        	}else{
            	if(!onCommand(message)) {
                	if(message.startsWith("!")) {
                		CommandManager.getInstance(Main.instance.getBot(tab).getChannel()).handleCommand(Main.instance.getBot(tab), Main.instance.getBot(tab).getChannel(), "Console", message);
                	}else{
                		Main.instance.getBot(tab).sendMessage(message);
                	}
            	}
        	}
        }

        this.textField.setText("");
    }
    
    public void setTab(String tabname) {
    	tab = tabname;
    }
    
    private boolean onCommand(String command) {
    	String args[] = command.split(" ");
    	if(command.startsWith("/") || command.startsWith("!")) {
    		if(args.length == 2 && (args[0].equalsIgnoreCase("/join") || args[0].equalsIgnoreCase("!join")) && !args[1].isEmpty()) {
    			Main.instance.addStreamer(args[1]);
    			return true;
    		}else if(args.length == 2 && (args[0].equalsIgnoreCase("/leave") || args[0].equalsIgnoreCase("!leave")) && !args[1].isEmpty()) {
    			Main.instance.markremoveBot(tab);
    			return true;
    		}else if(args.length == 1 && ((args[0].equalsIgnoreCase("/exit") || args[0].equalsIgnoreCase("/shutdown") || (args[0].equalsIgnoreCase("!exit") || args[0].equalsIgnoreCase("!shutdown"))))) {
    			Main.instance.broadcast(tab);
    			System.exit(1);
    			return true;
    		}else if(args.length > 1 && ((args[0].equalsIgnoreCase("/broadcast") || (args[0].equalsIgnoreCase("!broadcast")))) && !args[1].isEmpty()) {
    			String message = StringUtils.joinString(StringUtils.dropFirstString(args));
    			Main.instance.broadcast(message);
    			return true;
    		}else{
    			return false;
    		}
    	}else{
    		return false;
    	}
    }
}
