package net.keplergaming.keplerbot.updater;

import javax.swing.JDialog; 
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;


/**
 * @author Chris
 * @version 2012-27-2013
 * 
 * This Class runs the update.jar file and updates the program automatically.
 */
public class Updater extends JDialog implements ActionListener
{
    private JPanel myPanel = null;        //adds the buttons to the screen
    
    private JButton yesButton = null;     //used to choose to update
    private JButton noButton = null;      //used to ignore the update
    
    //used to store all of the xml information
    //xmlList.get(index)[?] where ? is: 0 = date, 1 = version, 2 = name, 3 = link
    private List<String[]> xmlList = new ArrayList();
    
    
    /**
     * @param frame JFrame this is so i can get the style and make a new Dialog box 
     * @param version String This is the client version, so i can compare it to the recent version
     */
    public Updater(JFrame frame, String version)
    {
    	super(frame, true);
    	
    	//stores all the versions in an array, may do something with that later
    	versionToArray();
    	//deletes the updater, if it exists still.
    	deleteUpdater();
    	
    	//checks the client version against the top of the array list (will update to sort the list later)
    	if (!version.matches(xmlList.get(0)[1]))
    	{
    		askToUpdate(frame);
    	}
    }
    
    /**
     * Deletes the updater after it did it's job.
     */
    public void deleteUpdater()
    {
    	try
    	{
    		//wait for the other program to close
    	    Thread.sleep(1000);
    	    
    	    String filename2 = findJar().substring(0, findJar().indexOf(".jar"))+"2.jar";
    	    File file = new File("./update.jar");
    	    //The file to be deleted
    		File file2 = new File("./"+ filename2);
 
    		if(file.delete())
    		{
    			//System.out.println(file.getName() + " is deleted!");
    		}
    		else{
    			//System.out.println("Delete operation is failed.");
    		}
    		
    		if(file2.delete())
    		{
    			//System.out.println(file.getName() + " is deleted!");
    		}
    		else{
    			//System.out.println("Delete operation is failed.");
    		}
 
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    
    /**
     * Stores the xml list to an array
     */
    /**
     * 
     */
    public void versionToArray()
    {
    	try
    	{
    		//get the xml and put it in a scanner (doesn't need to be complex)
    		URL url = new URL("http://dl.dropbox.com/u/21162139/keplerbot-updates.xml");
			InputStream input = url.openStream();
			Scanner in = new Scanner(input);
			
			//setup an array that will change for each version
			String[] temp = new String[4];
			while (in.hasNext())
			{
				String s = in.nextLine();
				
				//reset the array
				if(s.toLowerCase().contentEquals("<version>"))
				{
					temp[0] = null;
					temp[1] = null;
					temp[2] = null;
					temp[3] = null;
				}
				else if(s.toLowerCase().contains("<date>"))
				{
					temp[0] = s.substring(s.indexOf("<date>")+ 6, s.indexOf("</date>"));
				}
				else if(s.toLowerCase().contains("<version>"))
				{
					temp[1] = s.substring(s.indexOf("<version>")+ 9, s.indexOf("</version>"));
				}
				else if(s.toLowerCase().contains("<name>"))
				{
					temp[2] = s.substring(s.indexOf("<name>")+ 6, s.indexOf("</name>"));
				}
				else if(s.toLowerCase().contains("<link>"))
				{
					temp[3] = s.substring(s.indexOf("<link>")+ 6, s.indexOf("</link>"));
				}
				else if(s.toLowerCase().contentEquals("</version>"))
				{
					//store the array
					xmlList.add(temp);
				}
				
			}
			//close the input
			input.close();
    	} catch (IOException e)
    	{
			e.printStackTrace();
		}
    }
    
    
    /**
     * @param frame JFrame is only for the location in here. 
     */
    public void askToUpdate(JFrame frame)
    {
    	//set the title
    	this.setTitle("Update?");
    	
    	yesButton = new JButton("Yes");
        yesButton.addActionListener(this);
        
        noButton = new JButton("No");
        noButton.addActionListener(this);
        
    	//make and add a panel with all the buttons and asking the question
        myPanel = new JPanel();
        getContentPane().add(myPanel);
        myPanel.add(new JLabel("Would you like to Update?"));
        myPanel.add(yesButton); 
        myPanel.add(noButton);
        pack();
        //center it to the frame and make it visible
        setLocationRelativeTo(frame);
        setVisible(true);
        
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e)
    {
    	//if the user chooses to update
        if(yesButton == e.getSource())
        {
            try
            {
            	//copy's the update file out of this program
            	File configFile = new File("./update.jar");
            	if(!configFile.exists())
            	{
                        URL url = ClassLoader.getSystemResource("net/keplergaming/keplerbot/updater/update.jar");
                        FileOutputStream output = new FileOutputStream(configFile);
                        InputStream input = url.openStream();
                        byte [] buffer = new byte[40000];
                        int bytesRead = input.read(buffer);
                        while (bytesRead != -1)
                        {
                            output.write(buffer, 0, bytesRead);
                            bytesRead = input.read(buffer);
                        }
                        output.close();
                        input.close();
 
                }
            	//gets the look of the manager and sends it to the update.jar file along with the link to download from and the jarname
            	String look = UIManager.getLookAndFeel().toString().substring(UIManager.getLookAndFeel().toString().indexOf('-')+2, UIManager.getLookAndFeel().toString().length()-1);
            	Runtime.getRuntime().exec("java -jar ./update.jar "+ look + " "+ xmlList.get(0)[3] + " ./" + findJar());
            	System.exit(EXIT_ON_CLOSE);
            	
            }catch(Exception exception)
            {
            	exception.printStackTrace();
            }
        }
    
        
        //if the user doesn't want to update
        if(noButton == e.getSource()) {
            setVisible(false);
        }
        
        
        
    }
    
    /**
     * @return String the name of the jar file so that I can replace this jar with an updated one
     */
    public String findJar(){
    	try
    	{
    	Class<?> context = this.getClass();
    	URL location = context.getResource('/' + context.getName().replace(".", "/") + ".class");
    	String jarPath = location.getPath();
    	if (jarPath.contains("!"))
    	{
    		jarPath = jarPath.substring(0,jarPath.indexOf("!"));
    	}
    	else
    	{
    		jarPath = jarPath.replace(".class", ".jar");
    	}
    	while(jarPath.contains("/"))
    	{
    		jarPath = jarPath.substring(jarPath.indexOf("/")+1);
    	}
    	return jarPath.substring(0);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return "./Keplerbot.jar";
    	}
    	}
}