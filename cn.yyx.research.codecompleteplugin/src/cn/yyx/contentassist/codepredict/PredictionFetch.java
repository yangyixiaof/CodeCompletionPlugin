package cn.yyx.contentassist.codepredict;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.yyx.contentassist.commonutils.ListHelper;
import cn.yyx.research.AeroSpikeHandle.AeroHelper;
import cn.yyx.research.AeroSpikeHandle.AeroLifeCycle;
import cn.yyx.research.AeroSpikeHandle.PredictProbPair;
import cn.yyx.research.AeroSpikeHandle.ProbPredictComparator;

public class PredictionFetch {
	
	public static final int PrePredictWindow = 10;
	public static final double SequenceSimilarThreshold = 0.7;
	public static final double OneSentenceSimilarThreshold = 0.7;
	
	public static void FetchPrediction(List<String> analist, ArrayList<String> result) {
		
		AeroLifeCycle.Initialize();
		
		int size = analist.size();
		if (size > PrePredictWindow) {
			analist = analist.subList(size - PrePredictWindow, size);
			size = PrePredictWindow;
		}
		
		Iterator<String> itr = analist.iterator();
		while (itr.hasNext())
		{
			String ons = itr.next();
			// TODO
			
		}
		
		// AeroHelper.testListStrings(2);
		
		// System.out.println("ArrayListType:" + analist.getClass());
		// System.out.println("ArrayListRealSize:" + analist.size() + ";OSize;" + size + ";They should be the same.");
		
		AeroLifeCycle.Destroy();
	}
	
	// must invoke in the environment of AeroLifeCycle.
	private static void OnlyOnePredict(String one, int neededSize, String oraclePredict)
	{
		List<PredictProbPair> result = AeroHelper.GetNGramInAero(AeroLifeCycle.codengram, one, neededSize, null);
		result.sort(new ProbPredictComparator());
	}
	
	private static void TwoOrMorePredict(ArrayList<String> ctx, int neededSize, String oraclePredict)
	{
		
	}
	
	public static void main(String[] args) {
		ArrayList<String> result = new ArrayList<String>();
		String[] analistarr = { "ABC", "ASD", "GFS", "LOI", "POI", "LKI", "HGF", "DFG", "WER", "TRY", "UYI", "OIU", "KIY", "QAW"};
		ArrayList<String> analist = new ArrayList<String>();
		for (int i = 0; i < analistarr.length; i++) {
			analist.add(analistarr[i]);
		}
		PredictionFetch.FetchPrediction(analist, result);
		ListHelper.PrintList(result);
	}
	
}