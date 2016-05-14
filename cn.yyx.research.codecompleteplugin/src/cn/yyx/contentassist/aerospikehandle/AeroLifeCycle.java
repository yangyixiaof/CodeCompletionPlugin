package cn.yyx.contentassist.aerospikehandle;

import java.util.List;

import cn.yyx.contentassist.codecompletion.IntelliJavaProposalComputer;

public class AeroLifeCycle {
	
	boolean hasInitialized = false;

	public void Initialize() {
		Parameters param = new Parameters(IntelliJavaProposalComputer.ServerIp, 3000, null, null, "yyx", "code1sim");
		AeroHelper.ANewClient(AeroMetaData.code1sim, param);
		Parameters param2 = new Parameters(IntelliJavaProposalComputer.ServerIp, 3000, null, null, "yyx", "codengram");
		AeroHelper.ANewClient(AeroMetaData.codengram, param2);
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
		List<PredictProbPair> result = AeroHelper.GetNGramInAero(AeroMetaData.codengram, key, neededSize, null);
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