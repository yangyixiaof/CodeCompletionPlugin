package cn.yyx.contentassist.codepredict.preference;

import java.io.IOException;

import org.eclipse.jface.preference.PreferenceStore;

public class PreferenceManager {

	public static final String IPPreference = "pccIpPreference";
	
	public static PreferenceStore GetPreference() {
		PreferenceStore preferenceStore = null;
		String osname = System.getProperties().getProperty("os.name");
		if (osname.toLowerCase().contains("windows"))
		{
			preferenceStore = new PreferenceStore("C:\\PCC.properties");
		} else {
			preferenceStore = new PreferenceStore("~/PCC.properties");
		}
		preferenceStore.setDefault(IPPreference, "192.168.1.101");
		try {
			preferenceStore.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return preferenceStore;
	}

}