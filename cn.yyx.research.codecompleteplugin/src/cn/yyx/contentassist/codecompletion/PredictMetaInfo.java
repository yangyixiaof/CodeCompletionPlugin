package cn.yyx.contentassist.codecompletion;

public class PredictMetaInfo {
	
	public static final double NotExistProbability = 0;
	
	public static final double SequenceSimilarThreshold = 0.6;
	public static final double OneSentenceSimilarThreshold = 0.7;
	
	public static final double TwoStringSimilarThreshold = 0.75;
	public static final double TwoTypeStringSimilarThreshold = 0.75;
	public static final double TwoFieldStringSimilarThreshold = 0.75;
	public static final double TwoMethodStringSimilarityThreshold = 0.75;
	
	// public static final int PredictMaxSequence = 15;
	public static final int PrePredictWindow = 9;
	public static final int PreTryTotalMaxParSize = 16;
	public static final int PreTryMaxParSize = 4;
	public static final int PreTryMaxExtendStep = 4;
	public static final int PreTryNeedSize = 4;
	
	public static final int OneExtendFirstTotalStep = 25;
	public static final int OneFirstMaxTotalSuccess = 2;
	public static final int OneFlowLineMaxTotalSuccess = 1;
	
	public static final int OneExtendFirstMaxSequence = 2;
	public static final int OneExtendMaxSequence = 4;
	public static final int OneLevelExtendMaxSequence = 2; // must be the power of 2.
	
	public static final int MaxExtendLength = 15;
	
	public static final int NgramMaxSize = 9;
	
	public static final int MaxTypeConcateSize = 2;
	public static final int MaxTypeSpecificationSize = 6;
	public static final int MaxFieldSpecificationSize = 6;
	public static final int MaxMethodSpecificationSize = 6;
	
	public static final int OneCodeSynthesisTaskValidFinalSize = 2;
	
}