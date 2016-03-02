package cn.yyx.contentassist.codepredict;

public class PredictMetaInfo {
	
	public static final double NotExistProbability = 0;
	
	public static final double SequenceSimilarThreshold = 0.7;
	public static final double OneSentenceSimilarThreshold = 0.7;
	
	public static final int PredictMaxSequence = 15;
	public static final int PrePredictWindow = 10;
	
	public static final int NgramMaxSize = 5;
	
	// predict
	public static final int ifcond = 1;
	public static final int whilecond = 2;
	// TODO ...
}
