package cn.yyx.contentassist.commonutils;

public class StructureSignalMetaInfo {

	public static final int ArrayInitialBlock = 1;
	public static final int ArrayAccessBlcok = 2;
	public static final int ParentheseBlock = 3;
	// predict over waiting.
	public static final int AllKindWaitingOver = 4;
	public static final int CommonForKindWaitingOver = 5;
	public static final int CommonForInitWaitingOver = 6;
	public static final int CommonForExpWaitingOver = 7;
	public static final int ConditionExpressionOver = 8;
	public static final int IfOver = 9;

}