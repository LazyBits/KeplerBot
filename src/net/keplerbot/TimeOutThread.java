package net.keplerbot;

import net.keplerbot.config.KeplerConfig;

import org.jibble.pircbot.Queue;

public class TimeOutThread extends Thread {
	
    public TimeOutThread(KeplerBot bot, Queue outQueue) {
        this.bot = bot;
        this.outQueue = outQueue;
        this.setName("TimeOutThread");
    }
    
    public void run() {
        try {
            while (true) {
                Thread.sleep(KeplerConfig.getInstance().timeOutInterval * 4);
                
                String line = (String) outQueue.next();
                if (line != null) {
                    bot.sendRawLine(line);
                }
            }
        }
        catch (InterruptedException e) {
        }
    }
    
    private KeplerBot bot = null;
    private Queue outQueue = null;
    
}
