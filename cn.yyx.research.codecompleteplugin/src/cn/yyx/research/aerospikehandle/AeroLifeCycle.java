package cn.yyx.research.aerospikehandle;

import java.util.List;

import cn.yyx.contentassist.codecompletion.IntelliJavaProposalComputer;

public class AeroLifeCycle {

	public static final int code1sim = 1;

	public static final int codengram = 2;
	
	boolean hasInitialized = false;

	public void Initialize() {
		Parameters param = new Parameters(IntelliJavaProposalComputer.ServerIp, 3000, null, null, "yyx", "code1sim");
		AeroHelper.ANewClient(code1sim, param);
		Parameters param2 = new Parameters(IntelliJavaProposalComputer.ServerIp, 3000, null, null, "yyx", "codengram");
		AeroHelper.ANewClient(codengram, param2);
		hasInitialized = true;
	}

	public void Destroy() {
		AeroHelper.CloseClient(1);
		AeroHelper.CloseClient(2);
		hasInitialized = false;
	}

	// must invoke in the environment of AeroLifeCycle.
	public List<PredictProbPair> AeroModelPredict(String key, int neededSize) {
		CheckInitialized();
		List<PredictProbPair> result = AeroHelper.GetNGramInAero(AeroLifeCycle.codengram, key, neededSize, null);
		// result.sort(new ProbPredictComparator());
		// int realsize = result.size();
		// if (realsize > neededSize)
		// {
		//	result = result.subList(0, neededSize);
		//	realsize = neededSize;
		// }
		return result;
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