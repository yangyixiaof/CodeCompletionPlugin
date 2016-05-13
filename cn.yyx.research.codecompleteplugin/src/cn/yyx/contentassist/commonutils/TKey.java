package cn.yyx.contentassist.commonutils;

public class TKey {
	
	private String key = null;
	private String trim1key = null;
	
	public TKey(String key, String trim1key) {
		this.setKey(key);
		this.setTrim1key(trim1key);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTrim1key() {
		return trim1key;
	}

	public void setTrim1key(String trim1key) {
		this.trim1key = trim1key;
	}
	
}