package cn.yyx.contentassist.codepredict;

import java.util.ArrayList;
import java.util.List;

import cn.yyx.contentassist.commonutils.ListHelper;

public class PredictionFetch {
	
	public static int PrePredictWindow = 10;
	
	public static void FetchPrediction(List<String> analist, ArrayList<String> result) {
		int size = analist.size();
		if (size > PrePredictWindow) {
			analist = analist.subList(size - PrePredictWindow, size);
			size = PrePredictWindow;
		}
		System.out.println("ArrayListType:" + analist.getClass());
		System.out.println("ArrayListRealSize:" + analist.size() + ";OSize;" + size + ";They should be the same.");
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