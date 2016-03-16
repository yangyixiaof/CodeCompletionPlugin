package cn.yyx.contentassist.codepredict;

public class PredictMetaInfo {
	
	public static final double NotExistProbability = 0;
	
	public static final double SequenceSimilarThreshold = 0.7;
	public static final double OneSentenceSimilarThreshold = 0.7;
	
	public static final double TwoStringSimilarThreshold = 0.65;
	
	public static final int PredictMaxSequence = 15;
	public static final int PrePredictWindow = 10;
	
	public static final int ExtendFinalMaxSequence = 3;
	public static final int ExtendTempMaxSequence = 8;
	
	public static final int NgramMaxSize = 5;
	
	// predict over waiting.
	public static final int AllKindWaitingOver = 1;
	public static final int CommonForKindWaitingOver = 2;
	public static final int CommonForInitWaitingOver = 3;
	public static final int CommonForExpWaitingOver = 4;
	public static final int ConditionExpressionOver = 5;
	public static final int IfOver = 6;
}
