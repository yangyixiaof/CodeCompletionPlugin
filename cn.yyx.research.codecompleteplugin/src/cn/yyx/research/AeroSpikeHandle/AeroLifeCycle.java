package cn.yyx.research.AeroSpikeHandle;

import java.util.ArrayList;
import java.util.List;

public class AeroLifeCycle {

	public static final int code1sim = 1;

	public static final int codengram = 2;
	
	boolean hasInitialized = false;

	public void Initialize() {
		Parameters param = new Parameters("127.0.0.1", 3000, null, null, "yyx", "code1sim");
		AeroHelper.ANewClient(code1sim, param);
		Parameters param2 = new Parameters("127.0.0.1", 3000, null, null, "yyx", "codengram");
		AeroHelper.ANewClient(codengram, param2);
		hasInitialized = true;
	}

	public void Destroy() {
		AeroHelper.CloseClient(1);
		AeroHelper.CloseClient(2);
		hasInitialized = false;
	}

	// must invoke in the environment of AeroLifeCycle.
	public List<PredictProbPair> OnlyOnePredict(String one, int neededSize, String oraclePredict) {
		CheckInitialized();
		List<PredictProbPair> result = AeroHelper.GetNGramInAero(AeroLifeCycle.codengram, one, neededSize, null);
		result.sort(new ProbPredictComparator());
		result = result.subList(0, neededSize);
		boolean containOPP = result.contains(oraclePredict);
		if (!containOPP) {
			result.set(neededSize - 1, new PredictProbPair(oraclePredict, (double) 0));
		}
		return result;
	}
	
	public void TwoOrMorePredict(ArrayList<String> ctx, int neededSize, String oraclePredict) {
		CheckInitialized();
	}
	
	private void CheckInitialized()
	{
		if (!hasInitialized)
		{
			System.err.println("Not Initialized. the system will exit.");
			System.exit(1);
		}
	}
	
}