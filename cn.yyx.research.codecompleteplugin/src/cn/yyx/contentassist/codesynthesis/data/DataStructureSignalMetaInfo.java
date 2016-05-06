package cn.yyx.contentassist.codesynthesis.data;

public class DataStructureSignalMetaInfo {

	public static final int ArrayInitialBlock = 1;
	public static final int ArrayAccessBlcok = 2;
	public static final int ParentheseBlock = 3;
	
	public static final int MethodEnterParam = 4;
	public static final int MethodPs = 5;
	public static final int MethodPr = 6;
	public static final int MethodInvocation = 7;
	
	// predict over waiting.
	// public static final int AllKindWaitingOver = 0;// INVALID
	public static final int CommonForKindWaitingOver = 8;
	public static final int CommonForInitWaitingOver = 9;
	public static final int CommonForExpWaitingOver = 10;
	public static final int CommonForUpdWaitingOver = 11;
	public static final int ConditionExpressionStart = 12;
	public static final int ConditionExpressionQuestion = 13;
	public static final int ConditionExpressionColon = 14;
	// public static final int IfOver = 12;

}