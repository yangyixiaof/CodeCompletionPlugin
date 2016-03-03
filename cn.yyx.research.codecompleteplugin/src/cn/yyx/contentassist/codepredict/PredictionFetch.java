package cn.yyx.contentassist.codepredict;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.yyx.research.AeroSpikeHandle.AeroLifeCycle;
import cn.yyx.research.language.simplified.JDTHelper.SimplifiedCodeGenerateASTVisitor;

public class PredictionFetch {
	
	// public static final int ParallelSize = 10;
	
	public static List<String> FetchPrediction(SimplifiedCodeGenerateASTVisitor fmastv, List<String> analist, ArrayList<String> result) {
		
		AeroLifeCycle alc = new AeroLifeCycle();
		alc.Initialize();
		
		int size = analist.size();
		if (size > PredictMetaInfo.PrePredictWindow) {
			analist = analist.subList(size - PredictMetaInfo.PrePredictWindow, size);
			size = PredictMetaInfo.PrePredictWindow;
		}
		
		SequenceManager manager = new SequenceManager();
		Iterator<String> itr = analist.iterator();
		while (itr.hasNext())
		{
			String ons = itr.next();
			manager = DoSequenceManager(alc, manager, ons);
		}
		
		PredictManager pm = DoSequencePredict(alc, manager);
		List<String> list = DoRealCodeSynthesis(fmastv, pm);
		// AeroHelper.testListStrings(2);
		// System.out.println("ArrayListType:" + analist.getClass());
		// System.out.println("ArrayListRealSize:" + analist.size() + ";OSize;" + size + ";They should be the same.");
		
		alc.Destroy();
		alc = null;
		
		return list;
	}
	
	private static List<String> DoRealCodeSynthesis(SimplifiedCodeGenerateASTVisitor fmastv, PredictManager pm) {
		// TODO Auto-generated method stub
		
		return null;
	}

	private static PredictManager DoSequencePredict(AeroLifeCycle alc, SequenceManager manager) {
		// TODO Auto-generated method stub
		
		return null;
	}

	private static SequenceManager DoSequenceManager(AeroLifeCycle alc, SequenceManager manager, String ons)
	{
		SequenceManager managerresult = manager.HandleANewInSentence(alc, ons);
		return managerresult;
	}
	
	/*public static void main(String[] args) {
		ArrayList<String> result = new ArrayList<String>();
		String[] analistarr = { "ABC", "ASD", "GFS", "LOI", "POI", "LKI", "HGF", "DFG", "WER", "TRY", "UYI", "OIU", "KIY", "QAW"};
		ArrayList<String> analist = new ArrayList<String>();
		for (int i = 0; i < analistarr.length; i++) {
			analist.add(analistarr[i]);
		}
		PredictionFetch.FetchPrediction(null, analist, result);
		ListHelper.PrintList(result);
	}*/
	
}