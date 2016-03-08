package cn.yyx.contentassist.codepredict;

public class GCodeMetaInfo {
	
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
	
	// another display of ']'
	public static final String EndOfArrayDeclarationIndexExpression = "@]";
	
	public static final String LeftParenthese = "@(";
	public static final String RightParenthese = "@)";
	
	// another display of ' '
	public static final String WhiteSpace = "'@w'";
	
	// another display of ';'
	public static final String EndOfAStatement = ";";
	// another display of ',' ')'
	public static final String EndOfAPartialStatement = ",";
	
	// public static final String ArrayInitial = "@ARI";
	// public static final String ArrayCreation = "@ARC";
	
	public static final String StringHolder = "@STR";
	// public static final String NumberHolder = "@NUB";
	// public static final String CharHolder = "@CHR";
	
	// public static String NoStatement = "nu#";
	// public static final String NullLiteral = "@NUL";
	public static final String CommonSplitter = "#";
	
	public static final String InferedType = "@IT";
	public static final String NoDeclaredType = "@NT";
	
	public static final String CodeHole = "@HO";
	public static final String PreExist = "@PE";
	
	// The white space in code is replaced with '#'.
	public static final String WhiteSpaceReplacer = "#";
	
	public static final String OffsetSpiliter = "?";
	
	public static final String ContentHolder = "<!%CH!>";
	
	public static final String DataRefIndicator = "$";
	
	public static final String HackedNoType = "<$NT$>";
	
	// public static int OutofScopeVarOrObject = -10;
	// public static String FirstDeclaredData = "F";
	// public static String OutofScopeDesc = "$INF";
	
	//for field only which means data only.
	//public static int IsField = -9;
	//public static String IsFieldDesc = "$FREF#";
	
	// corpus name
	public static final String EnumCorpus = "BigEnumDetail";
	public static final String NumberCorpus = "BigNumberDetail";
	public static final String StringCorpus = "BigStringDetail";
	public static final String CharCorpus = "BigCharDetail";
	public static final String LogicCorpus = "BigClassDetail";
	public static final String AnonymousLogicCorpus = "BigAnonymousClassDetail";
}
