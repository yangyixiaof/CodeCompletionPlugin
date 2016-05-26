package cn.yyx.contentassist.commonutils;

public class TKey {
	
	private String key = null;
	private String trim1key = null;
	private int keylen = 0;
	
	public TKey(String key, int keylen, String trim1key) {
		this.setKey(key);
		this.setTrim1key(trim1key);
		this.setKeylen(keylen);
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
	
	@Override
	public String toString() {
		return "key:" + key + "\n" + "trim1key:" + trim1key;
	}

	public int getKeylen() {
		return keylen;
	}

	public void setKeylen(int keylen) {
		this.keylen = keylen;
	}
	
}