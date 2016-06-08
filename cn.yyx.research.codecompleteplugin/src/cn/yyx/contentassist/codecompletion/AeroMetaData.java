package cn.yyx.contentassist.codecompletion;

public class AeroMetaData {
	
	// public static final int code1sim = 1;

	public static final int[] codengram = {1,2,3,4,5,6,7,8};
	
	// if -1, infinite.
	public static final int MaxPutAllLineNum = -1;
	// must > 0
	public static final int MaxMethodSimilarNum = 50;
	
	// code1sim
	public static final String BinSimilarName = "similar";
	// codengram
	public static final String BinPredictName = "predict";
	public static final String BinProbabilityName = "probability";
	
}