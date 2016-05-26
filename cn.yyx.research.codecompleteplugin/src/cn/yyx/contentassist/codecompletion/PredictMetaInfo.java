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
	public static final int PrePredictWindow = 6;
	public static final int PreTryMaxStep = 4;
	public static final int PreTryNeedSize = 2;
	
	public static final int OneExtendFirstTotalStep = 20;
	public static final int OneFirstMaxTotalSuccess = 1;
	public static final int OneExtendFirstMaxSequence = 2;
	public static final int OneExtendMaxSequence = 4;
	public static final int OneLevelExtendMaxSequence = 1; // must be the power of 2.
	
	public static final int MaxExtendLength = 15;
	
	public static final int NgramMaxSize = 5;
	
	public static final int MaxTypeConcateSize = 2;
	public static final int MaxTypeSpecificationSize = 2;
	public static final int MaxFieldSpecificationSize = 2;
	public static final int MaxMethodSpecificationSize = 4;
	
	public static final int OneCodeSynthesisTaskValidFinalSize = 2;
	
}