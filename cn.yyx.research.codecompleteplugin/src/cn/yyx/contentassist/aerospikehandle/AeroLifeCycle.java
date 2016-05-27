package cn.yyx.contentassist.aerospikehandle;

import java.util.List;

import cn.yyx.contentassist.codecompletion.AeroMetaData;
import cn.yyx.contentassist.codecompletion.CodeCompletionMetaInfo;

public class AeroLifeCycle {
	
	private static AeroLifeCycle alc = new AeroLifeCycle();
	
	boolean hasInitialized = false;
	
	private AeroLifeCycle() {
		Initialize();
	}
	
	public static AeroLifeCycle GetInstance()
	{
		return alc;
	}

	private void Initialize() {
		Parameters param = new Parameters(CodeCompletionMetaInfo.ServerIp, 3000, null, null, "yyx", "code1sim");
		AeroHelper.ANewClient(AeroMetaData.code1sim, param);
		int alen = AeroMetaData.codengram.length;
		for (int i=0;i<alen;i++)
		{
			Parameters param2 = new Parameters(CodeCompletionMetaInfo.ServerIp, 3000, null, null, "yyx", "codengram");
			AeroHelper.ANewClient(AeroMetaData.codengram[i], param2);
		}
		hasInitialized = true;
	}

	/*private void Destroy() {
		AeroHelper.CloseClient(1);
		AeroHelper.CloseClient(2);
		hasInitialized = false;
	}*/

	// must invoke in the environment of AeroLifeCycle.
	public List<PredictProbPair> AeroModelPredict(int id, String key, int neededSize, int keylen) {
		CheckInitialized();
		List<PredictProbPair> result = AeroHelper.GetNGramInAero(id, key, neededSize, null, keylen);
		// pre first arg : AeroMetaData.codengram.
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