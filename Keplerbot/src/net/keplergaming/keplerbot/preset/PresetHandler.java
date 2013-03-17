package net.keplergaming.keplerbot.preset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.DefaultComboBoxModel;

import net.keplergaming.keplerbot.logger.MainLogger;

public class PresetHandler {

	public void load() {
		MainLogger.fine("Loading presets");
		presets = new DefaultComboBoxModel();
		presets.addElement(PresetHandler.ADD_PRESET);

		if (presetsFile.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(presetsFile));
				String line;

				while ((line = br.readLine()) != null) {
					presets.addElement(line);
				}
				br.close();
			} catch (IOException e) {
				MainLogger.error("Failed to load presets", e);
			}
		}
	}

	public void save() {
		try {
			MainLogger.fine("Saving presets");
			PrintStream out = new PrintStream(presetsFile);

			int size = presets.getSize();
			for (int i = 0; i < size; i++) {
				if (!presets.getElementAt(i).equals(ADD_PRESET)) {
					out.println(presets.getElementAt(i));
				}
			}

			out.flush();
			out.close();
		} catch (IOException e) {
			MainLogger.error("Failed to save presets", e);
		}
	}

	public DefaultComboBoxModel getPresets() {
		return presets;
	}

	public void setPresets(DefaultComboBoxModel presets) {
		this.presets = presets;
	}

	private DefaultComboBoxModel presets;
	private File presetsFile = new File("./presets.txt");
	public static String ADD_PRESET = "Add Preset";
}
