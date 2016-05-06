package cn.yyx.research.aerospikehandle;

import java.util.Comparator;

public class ProbPredictComparator implements Comparator<PredictProbPair> {
	
	@Override
	public int compare(PredictProbPair o1, PredictProbPair o2) {
		return Double.compare(o1.getProb(), o2.getProb());
	}

}
