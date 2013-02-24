package net.keplerbot.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import net.keplerbot.log.KeplerLogger;

public class TimeUtils {
	
	private static String timeServer = "http://132.163.4.101:13";
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm:ss");

	public static String getTime(Date date, SimpleDateFormat dateformat) {
		return dateformat.format(date);
	}
	
	public static String getTime(SimpleDateFormat dateformat) {
		return dateformat.format(new Date());
	}
	
	public static String getTime(Date date) {
		return sdf.format(date);
	}
	
	public static String getTime() {
		return sdf.format(new Date());
	}
	
	public static String getAtomicTime() {
		String time;
		try {
			time = "Atomic time : " + new SimpleDateFormat("dd-MM-yy HH:mm:ss:SS").format(getAtomicTime(timeServer).getTime());
		}catch(Exception e) {
			KeplerLogger.getInstance("Main").warn("Can't get Atomic time, switching to system time", e);
			time = "time : " + sdf.format(new Date());
		}
		return time;
	}
	
	private static GregorianCalendar getAtomicTime(String server) throws MalformedURLException, IOException {
		URLConnection conn = new URL(server).openConnection();
		BufferedReader buff = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		
		String atomicTime;
		while (true) {
			if ((atomicTime = buff.readLine()).indexOf("*") > -1) {
				break;
			}
		}
		buff.close();
		
		String[] fields = atomicTime.split(" ");
		GregorianCalendar calendar = new GregorianCalendar();

		String[] date = fields[1].split("-");
		calendar.set(Calendar.YEAR, 2000 + Integer.parseInt(date[0]));
		calendar.set(Calendar.MONTH, Integer.parseInt(date[1]) - 1);
		calendar.set(Calendar.DATE, Integer.parseInt(date[2]));

		String[] time = fields[2].split(":");
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(time[1]));
		calendar.set(Calendar.SECOND, Integer.parseInt(time[2]));
		return calendar;
	}
}
