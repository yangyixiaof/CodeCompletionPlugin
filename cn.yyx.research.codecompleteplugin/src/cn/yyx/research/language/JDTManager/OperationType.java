package cn.yyx.research.language.JDTManager;

public class OperationType {
	public static final int ArrayAccess = 1;
	public static final int Expression = 2;
	public static final int Assignment = 3;
	public static final int CastExpression = 4;
	public static final int ClassInstanceCreation = 5;
	public static final int InfixExpression = 6;
	public static final int ConditionalExpression = 7;
	public static final int WhileStatement = 8;
	public static final int DoStatement = 9;
	public static final int EnhancedForStatement = 10;
	public static final int ForStatement = 11;
	public static final int IfStatement = 12;
	public static final int ElseStatement = 13;
	public static final int FieldAccess = 14;
	public static final int FieldDeclaration = 15;
	public static final int MethodInvocation = 16;
	public static final int SuperMethodInvocation = 17;
	public static final int InstanceofExpression = 18;
	public static final int ReturnStatement = 19;
	public static final int PostfixExpression = 20;
	public static final int PrefixExpression = 21;
	public static final int StringLiteral = 22;
	public static final int SynchronizedStatement = 23;
	public static final int SwitchStatement = 24;
	public static final int SwitchCase = 25;
	public static final int BreakStatement = 26;
	public static final int ContinueStatement = 27;
	public static final int ConstructorInvocation = 28;
	public static final int SuperConstructorInvocation = 29;
	public static final int ThrowStatement = 30;
	public static final int CatchClause = 31;
	public static final int VariableDeclarationStatement = 32;
	public static final int SingleVariableDeclaration = 33;
	public static final int VariableDeclarationExpression = 34;
	public static final int NearlyCommonText = 35;
	public static final int BlockCommand = 36;
	public static final int ExpressionMethodReference = 37;
	public static final int LambdaExpression = 38;
	public static final int LambdaParam = 39;
	public static final int MethodDeclaration = 40;
	public static final int EnumDeclaration = 41;
	public static final int EnumConstant = 42;
	public static final int DoRawCode = 43;
	public static final int LabeledStatement = 44;
	
	public static int GetTypeDescriptionId(Class<?> ASTClass)
	{
		if (org.eclipse.jdt.core.dom.ArrayAccess.class == ASTClass)
		{
			return ArrayAccess;
		}
		if (org.eclipse.jdt.core.dom.Expression.class == ASTClass)
		{
			return Expression;
		}
		if (org.eclipse.jdt.core.dom.Assignment.class == ASTClass)
		{
			return Assignment;
		}
		if (org.eclipse.jdt.core.dom.CastExpression.class == ASTClass)
		{
			return CastExpression;
		}
		if (org.eclipse.jdt.core.dom.ClassInstanceCreation.class == ASTClass)
		{
			return ClassInstanceCreation;
		}
		if (org.eclipse.jdt.core.dom.InfixExpression.class == ASTClass)
		{
			return InfixExpression;
		}
		if (org.eclipse.jdt.core.dom.ConditionalExpression.class == ASTClass)
		{
			return ConditionalExpression;
		}
		if (org.eclipse.jdt.core.dom.WhileStatement.class == ASTClass)
		{
			return WhileStatement;
		}
		if (org.eclipse.jdt.core.dom.DoStatement.class == ASTClass)
		{
			return DoStatement;
		}
		if (org.eclipse.jdt.core.dom.EnhancedForStatement.class == ASTClass)
		{
			return EnhancedForStatement;
		}
		if (org.eclipse.jdt.core.dom.ForStatement.class == ASTClass)
		{
			return ForStatement;
		}
		if (org.eclipse.jdt.core.dom.IfStatement.class == ASTClass)
		{
			return IfStatement;
		}
		if (org.eclipse.jdt.core.dom.FieldAccess.class == ASTClass)
		{
			// System.err.println("FieldAccess should not have a single line");
			// new Exception().printStackTrace();
			return FieldAccess;
		}
		if (org.eclipse.jdt.core.dom.FieldDeclaration.class == ASTClass)
		{
			return FieldDeclaration;
		}
		if (org.eclipse.jdt.core.dom.MethodInvocation.class == ASTClass)
		{
			return MethodInvocation;
		}
		if (org.eclipse.jdt.core.dom.SuperMethodInvocation.class == ASTClass)
		{
			return SuperMethodInvocation;
		}
		if (org.eclipse.jdt.core.dom.InstanceofExpression.class == ASTClass)
		{
			return InstanceofExpression;
		}
		if (org.eclipse.jdt.core.dom.ReturnStatement.class == ASTClass)
		{
			return ReturnStatement;
		}
		if (org.eclipse.jdt.core.dom.PostfixExpression.class == ASTClass)
		{
			return PostfixExpression;
		}
		if (org.eclipse.jdt.core.dom.PrefixExpression.class == ASTClass)
		{
			return PrefixExpression;
		}
		if (org.eclipse.jdt.core.dom.StringLiteral.class == ASTClass)
		{
			return StringLiteral;
		}
		if (org.eclipse.jdt.core.dom.SynchronizedStatement.class == ASTClass)
		{
			return SynchronizedStatement;
		}
		if (org.eclipse.jdt.core.dom.SwitchStatement.class == ASTClass)
		{
			return SwitchStatement;
		}
		if (org.eclipse.jdt.core.dom.SwitchCase.class == ASTClass)
		{
			return SwitchCase;
		}
		if (org.eclipse.jdt.core.dom.BreakStatement.class == ASTClass)
		{
			return BreakStatement;
		}
		if (org.eclipse.jdt.core.dom.ContinueStatement.class == ASTClass)
		{
			return ContinueStatement;
		}
		if (org.eclipse.jdt.core.dom.ConstructorInvocation.class == ASTClass)
		{
			return ConstructorInvocation;
		}
		if (org.eclipse.jdt.core.dom.SuperConstructorInvocation.class == ASTClass)
		{
			return SuperConstructorInvocation;
		}
		if (org.eclipse.jdt.core.dom.ThrowStatement.class == ASTClass)
		{
			return ThrowStatement;
		}
		if (org.eclipse.jdt.core.dom.CatchClause.class == ASTClass)
		{
			return CatchClause;
		}
		if (org.eclipse.jdt.core.dom.VariableDeclarationStatement.class == ASTClass)
		{
			return VariableDeclarationStatement;
		}
		if (org.eclipse.jdt.core.dom.SingleVariableDeclaration.class == ASTClass)
		{
			return SingleVariableDeclaration;
		}
		if (org.eclipse.jdt.core.dom.VariableDeclarationExpression.class == ASTClass)
		{
			return VariableDeclarationExpression;
		}
		// NearlyCommonText escaped;
		// BlockCommand escaped.
		if (org.eclipse.jdt.core.dom.ExpressionMethodReference.class == ASTClass)
		{
			return ExpressionMethodReference;
		}
		if (org.eclipse.jdt.core.dom.LambdaExpression.class == ASTClass)
		{
			return LambdaExpression;
		}
		// LambdaParam escaped.
		if (org.eclipse.jdt.core.dom.MethodDeclaration.class == ASTClass)
		{
			return MethodDeclaration;
		}
		// EnumDeclaration escaped.
		// EnumConstant escaped.
		if (org.eclipse.jdt.core.dom.LabeledStatement.class == ASTClass)
		{
			return LabeledStatement;
		}
		System.err.println("Serious errir: no catched ast node class. The system will exit." + " The class is" + ASTClass);
		new Exception().printStackTrace();
		System.exit(1);
		return -1;
	}
	
}