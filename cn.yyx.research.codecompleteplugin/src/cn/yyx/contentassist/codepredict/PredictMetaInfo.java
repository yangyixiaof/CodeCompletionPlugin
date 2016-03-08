package cn.yyx.contentassist.codepredict;

public class PredictMetaInfo {
	
	public static final double NotExistProbability = 0;
	
	public static final double SequenceSimilarThreshold = 0.7;
	public static final double OneSentenceSimilarThreshold = 0.7;
	
	public static final int PredictMaxSequence = 15;
	public static final int PrePredictWindow = 10;
	
	public static final int ExtendFinalMaxSequence = 3;
	public static final int ExtendTempMaxSequence = 8;
	
	public static final int NgramMaxSize = 5;
	
	// predict
	public static final String AnonymousClassBegin = "AB@";
	public static final String AnonymousClassPreHint = "HT@";
	public static final String ClassDeclarationHint = "CD@";
	public static final String ClassInnerDeclarationHint = "ICD@";
	public static final String EnumDeclarationHint = "ED@";
	public static final String MethodDeclarationHint = "MD@";
	public static final String EnumConstantDeclarationHint = "EMD@";
	public static final String LabelDeclarationHint = "LD@";
	public static final String VariableDeclarationHint = "VD@";
	public static final String LambdaExpressionHint = "LE@";
	public static final String MethodReferenceHint = "MR@";
	public static final String CastExpressionHint = "CE@";
	public static final String AssignmentHint = "A@";
	public static final String BreakHint = "B@";
	public static final String ContinueHint = "C@";
	public static final String DoWhileHint = "DW@";
	public static final String InfixExpressionHint = "IxE@";
	public static final String InstanceofExpressionHint = "InE@";
	public static final String PrefixExpressionHint = "PeE@";
	public static final String PostfixExpressionHint = "PtE@";
	public static final String ReturnHint = "RT@";
	public static final String SwitchHint = "SW@";
	public static final String CaseHint = "CS@";
	public static final String DefaultHint = "DF@";
	public static final String SynchronizedHint = "SC@";
	public static final String ThrowStatementHint = "TS@";
	public static final String CatchHint = "CT@";
	public static final String WhileStatementHint = "WS@";
	public static final String IfStatementHint = "IF@";
	public static final String MethodInvocationHint = "MI@";
	public static final String ArrayCreationHint = "AC@";
	public static final String LiteralHint = "L@";
	public static final String NameHint = "N@";
	public static final String QualifiedNameHint = "QN@";
	public static final String FieldAccessHint = "FA@";
	public static final String QualifiedHint = "Q@";
	public static final String Initializer = "IB@";
	public static final String DescriptionHint = "DH@";
	public static final String VariableDeclarationHolder = "VH@";
	public static final String EnhancedFor = "EF@";
	public static final String ArrayAccess = "[@";
	public static final int ifcond = 1;
	public static final int whilecond = 2;
	// TODO ...
	
	// predict over waiting.
	public static final int AllKindWaitingOver = 1;
}
