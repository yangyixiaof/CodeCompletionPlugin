package cn.yyx.research.language.JDTManager;

public class KindHint {
	private int low = -1;
	private int high = -1;
	public KindHint(int low, int high) {
		this.setLow(low);
		this.setHigh(high);
	}
	public int getLow() {
		return low;
	}
	public void setLow(int low) {
		this.low = low;
	}
	public int getHigh() {
		return high;
	}
	public void setHigh(int high) {
		this.high = high;
	}
}