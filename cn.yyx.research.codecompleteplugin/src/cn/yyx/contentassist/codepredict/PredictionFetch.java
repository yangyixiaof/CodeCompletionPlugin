package cn.yyx.contentassist.codepredict;

import java.util.ArrayList;

import cn.yyx.research.AeroSpikeHandle.AeroHelper;
import cn.yyx.research.AeroSpikeHandle.AeroLifeCycle;

public class PredictionFetch {
	
	public static void FetchPrediction(ArrayList<String> analist, ArrayList<String> result)
	{
		AeroLifeCycle.Initialize();
		
		try {
			AeroHelper.testListStrings(1);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		AeroLifeCycle.Destroy();
	}
	
}
