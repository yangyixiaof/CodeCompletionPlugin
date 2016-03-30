package cn.yyx.contentassist.commonutils;

public class ComplicatedSignal {
	
	public static final int SignBits = 5;
	
	private int sign = -1;
	private int count = -1;
	
	public ComplicatedSignal(int sign, int count) {
		this.setSign(sign);
		this.setCount(count);
	}
	
	public static ComplicatedSignal ParseComplicatedSignal(Integer hint)
	{
		if (hint == null)
		{
			return null;
		}
		if (hint < (1<<SignBits))
		{
			return null;
		}
		return new ComplicatedSignal(hint>>SignBits, hint&((1<<SignBits)-1));
	}
	
	public static Integer GenerateComplicatedSignal(int sign, int count)
	{
		return (sign<<SignBits)+count;
	}

	public int getSign() {
		return sign;
	}

	public void setSign(int sign) {
		this.sign = sign;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}