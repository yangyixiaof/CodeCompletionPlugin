package cn.yyx.contentassist.codepredict.preference;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.preference.PreferenceStore;

public class PreferenceManager {

	public static final String IPPreference = "pccIpPreference";

	public static PreferenceStore GetPreference() {
		String osname = System.getProperties().getProperty("os.name");
		String f_path = "~/PCC.properties";
		if (osname.toLowerCase().contains("windows")) {
			f_path = System.getProperties().getProperty("user.home") + "/PCC.properties";
		}
		File f = new File(f_path);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		PreferenceStore preferenceStore = null;
		preferenceStore = new PreferenceStore(f_path);
		preferenceStore.setDefault(IPPreference, "127.0.0.1");
		try {
			preferenceStore.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return preferenceStore;
	}

}