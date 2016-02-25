package cn.yyx.research.AeroSpikeHandle;

public class AeroLifeCycle {
	
	public static void Initialize() {
		Parameters param2 = new Parameters("127.0.0.1", 3000, null, null, "yyx", "codengram");
		AeroHelper.ANewClient(1, param2);
		Parameters param = new Parameters("127.0.0.1", 3000, null, null, "yyx", "code1sim");
		AeroHelper.ANewClient(2, param);
	}
	
	public static void Destroy() {
		AeroHelper.CloseClient(1);
		AeroHelper.CloseClient(2);
	}
	
}