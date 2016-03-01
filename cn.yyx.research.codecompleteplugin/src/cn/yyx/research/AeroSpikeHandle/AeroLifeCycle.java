package cn.yyx.research.AeroSpikeHandle;

public class AeroLifeCycle {
	
	public static final int code1sim = 1;
	
	public static final int codengram = 2;
	
	public static void Initialize()
	{
		Parameters param = new Parameters("127.0.0.1", 3000, null, null, "yyx", "code1sim");
		AeroHelper.ANewClient(code1sim, param);
		Parameters param2 = new Parameters("127.0.0.1", 3000, null, null, "yyx", "codengram");
		AeroHelper.ANewClient(codengram, param2);
	}
	
	public static void Destroy()
	{
		AeroHelper.CloseClient(1);
		AeroHelper.CloseClient(2);
	}
	
}