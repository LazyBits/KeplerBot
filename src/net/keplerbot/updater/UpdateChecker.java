package net.keplerbot.updater;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.keplerbot.log.KeplerLogger;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;

public class UpdateChecker {
	private int version;
	public int latest;
	public static String verString = "";
	public String information;

	public UpdateChecker(int version) {
		this.version = version;
		getVersion();
	}

	private void getVersion() {
		try {
			KeplerLogger.getInstance("Main").info("Getting update information");
			Document doc = downloadXML(new URL("http://www.keplergaming.com/keplerbot/version.xml"));
			if(doc == null) {
				return;
			}
			NamedNodeMap updateAttributes = doc.getDocumentElement().getAttributes();
			latest = Integer.parseInt(updateAttributes.getNamedItem("currentBuild").getTextContent());
			KeplerLogger.getInstance("Main").info("Latest build: " + latest);
			char[] temp = String.valueOf(latest).toCharArray();
			for(int i = 0; i < (temp.length - 1); i++) {
				verString += temp[i] + ".";
			}
			verString += temp[temp.length - 1];
			information = updateAttributes.getNamedItem("info").getTextContent();
		} catch (MalformedURLException e) { 
		} catch (IOException e) { 
		} catch (SAXException e) { 
		}
	}

	public boolean shouldUpdate() {
		return version < latest;
	}
	
	public static Document downloadXML(URL url) throws IOException, SAXException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		try {
			return docFactory.newDocumentBuilder().parse(url.openStream());
		} catch (ParserConfigurationException ignored) { 
		} catch (UnknownHostException e) { }
		return null;
	}
}