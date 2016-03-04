package cn.yyx.contentassist.parsehelper;

import java.util.LinkedList;
import java.util.Queue;

import SJ8Parse.Java8BaseVisitor;
import SJ8Parse.Java8Parser;
import SJ8Parse.Java8Parser.ArgumentListContext;
import SJ8Parse.Java8Parser.TypeListContext;
import cn.yyx.contentassist.codeutils.annotationTypeMemberDeclarationStatement;
import cn.yyx.contentassist.codeutils.anonymousClassBeginStatement;
import cn.yyx.contentassist.codeutils.anonymousClassPreStatement;
import cn.yyx.contentassist.codeutils.argumentList;
import cn.yyx.contentassist.codeutils.arrayCreationStatement;
import cn.yyx.contentassist.codeutils.assignmentStatement;
import cn.yyx.contentassist.codeutils.breakStatement;
import cn.yyx.contentassist.codeutils.castExpressionStatement;
import cn.yyx.contentassist.codeutils.catchClauseStatement;
import cn.yyx.contentassist.codeutils.classDeclarationStatement;
import cn.yyx.contentassist.codeutils.classInnerDeclarationStatement;
import cn.yyx.contentassist.codeutils.continueStatement;
import cn.yyx.contentassist.codeutils.defaultStatement;
import cn.yyx.contentassist.codeutils.doWhileStatement;
import cn.yyx.contentassist.codeutils.enhancedForStatement;
import cn.yyx.contentassist.codeutils.enumConstantDeclarationStatement;
import cn.yyx.contentassist.codeutils.enumDeclarationStatement;
import cn.yyx.contentassist.codeutils.fieldAccess;
import cn.yyx.contentassist.codeutils.fieldAccessStatement;
import cn.yyx.contentassist.codeutils.identifier;
import cn.yyx.contentassist.codeutils.ifStatement;
import cn.yyx.contentassist.codeutils.infixExpressionStatement;
import cn.yyx.contentassist.codeutils.initializerStatement;
import cn.yyx.contentassist.codeutils.instanceofExpressionStatement;
import cn.yyx.contentassist.codeutils.labeledStatement;
import cn.yyx.contentassist.codeutils.lambdaExpressionStatement;
import cn.yyx.contentassist.codeutils.methodDeclarationStatement;
import cn.yyx.contentassist.codeutils.methodInvocationStatement;
import cn.yyx.contentassist.codeutils.methodReferenceStatement;
import cn.yyx.contentassist.codeutils.nameStatement;
import cn.yyx.contentassist.codeutils.postfixExpressionStatement;
import cn.yyx.contentassist.codeutils.prefixExpressionStatement;
import cn.yyx.contentassist.codeutils.referedExpression;
import cn.yyx.contentassist.codeutils.returnStatement;
import cn.yyx.contentassist.codeutils.statement;
import cn.yyx.contentassist.codeutils.switchCaseStatement;
import cn.yyx.contentassist.codeutils.switchStatement;
import cn.yyx.contentassist.codeutils.synchronizedStatement;
import cn.yyx.contentassist.codeutils.throwStatement;
import cn.yyx.contentassist.codeutils.type;
import cn.yyx.contentassist.codeutils.typeList;
import cn.yyx.contentassist.codeutils.variableDeclarationHolderStatement;
import cn.yyx.contentassist.codeutils.variableDeclarationStatement;
import cn.yyx.contentassist.codeutils.whileStatement;

public class OneSentenceVisitor extends Java8BaseVisitor<Integer> {

	private statement smt = null;

	private Queue<Object> usedobj = new LinkedList<Object>();

	@Override
	public Integer visitStatement(Java8Parser.StatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitExpressionStatement(Java8Parser.ExpressionStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitAssignmentStatement(Java8Parser.AssignmentStatementContext ctx) {
		// do nothing.
		Integer res = visitChildren(ctx);
		Object right = usedobj.poll();
		Object optr = usedobj.poll();
		Object left = usedobj.poll();
		smt = new assignmentStatement((referedExpression) left, (String) optr, (referedExpression) right);
		return res;
	}

	@Override
	public Integer visitLiteralStatement(Java8Parser.LiteralStatementContext ctx) {
		usedobj.add(ctx.literal().getText());
		return 0;
	}

	@Override
	public Integer visitCastExpressionStatement(Java8Parser.CastExpressionStatementContext ctx) {
		// do nothing.

		Integer res = visitChildren(ctx);
		Object rexp = usedobj.poll();
		Object type = usedobj.poll();
		smt = new castExpressionStatement((type) type, (referedExpression) rexp);
		return res;
	}

	@Override
	public Integer visitMethodInvocationStatement(Java8Parser.MethodInvocationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		ArgumentListContext arg = ctx.argumentList();
		Object argList = null;
		if (arg != null) {
			argList = usedobj.poll();
		}
		Object name = usedobj.poll();
		smt = new methodInvocationStatement((identifier) name, (argumentList) argList);
		return res;
	}

	@Override
	public Integer visitFieldAccessStatement(Java8Parser.FieldAccessStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object fa = usedobj.poll();
		smt = new fieldAccessStatement((fieldAccess) fa);
		return res;
	}

	@Override
	public Integer visitFieldAccess(Java8Parser.FieldAccessContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = usedobj.poll();
		Object name = usedobj.poll();
		usedobj.add(new fieldAccess((identifier) name, (referedExpression) rexp));
		return res;
	}

	@Override
	public Integer visitInfixExpressionStatement(Java8Parser.InfixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = usedobj.poll();
		Object optr = usedobj.poll();
		Object lexp = usedobj.poll();
		smt = new infixExpressionStatement((referedExpression) rexp, (String) optr, (referedExpression) lexp);
		return res;
	}

	@Override
	public Integer visitInstanceofExpressionStatement(Java8Parser.InstanceofExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object type = usedobj.poll();
		Object rexp = usedobj.poll();
		smt = new instanceofExpressionStatement((referedExpression) rexp, (type) type);
		return res;
	}

	@Override
	public Integer visitMethodReferenceStatement(Java8Parser.MethodReferenceStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = usedobj.poll();
		Object name = usedobj.poll();
		smt = new methodReferenceStatement((identifier) name, (referedExpression) rexp);
		return res;
	}

	@Override
	public Integer visitNameStatement(Java8Parser.NameStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object name = usedobj.poll();
		smt = new nameStatement((identifier) name);
		return res;
	}

	@Override
	public Integer visitPrefixExpressionStatement(Java8Parser.PrefixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = usedobj.poll();
		Object optr = usedobj.poll();
		smt = new prefixExpressionStatement((String) optr, (referedExpression) rexp);
		return res;
	}

	@Override
	public Integer visitPostfixExpressionStatement(Java8Parser.PostfixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object optr = usedobj.poll();
		Object rexp = usedobj.poll();
		smt = new postfixExpressionStatement((referedExpression) rexp, (String) optr);
		return res;
	}

	@Override
	public Integer visitReferedExpression(Java8Parser.ReferedExpressionContext ctx) {
		// do nothing.
		Integer res = visitChildren(ctx);
		return res;
	}

	@Override
	public Integer visitAnonymousClassBeginStatement(Java8Parser.AnonymousClassBeginStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object id = usedobj.poll();
		smt = new anonymousClassBeginStatement((identifier) id);
		return res;
	}

	@Override
	public Integer visitAnonymousClassPreStatement(Java8Parser.AnonymousClassPreStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object id = usedobj.poll();
		smt = new anonymousClassPreStatement((identifier) id);
		return res;
	}

	@Override
	public Integer visitAtInterfaceStatement(Java8Parser.AtInterfaceStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object id = usedobj.poll();
		smt = new anonymousClassPreStatement((identifier) id);
		return res;
	}

	@Override
	public Integer visitAnnotationTypeMemberDeclarationStatement(
			Java8Parser.AnnotationTypeMemberDeclarationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object drexp = null;
		if (ctx.referedExpression() != null)
		{
			drexp = usedobj.poll();
		}
		Object type = usedobj.poll();
		smt = new annotationTypeMemberDeclarationStatement((type)type, (referedExpression)drexp);
		return res;
	}

	@Override
	public Integer visitClassDeclarationStatement(Java8Parser.ClassDeclarationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object name = usedobj.poll();
		smt = new classDeclarationStatement((identifier)name);
		return res;
	}

	@Override
	public Integer visitClassInnerDeclarationStatement(Java8Parser.ClassInnerDeclarationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object name = usedobj.poll();
		smt = new classInnerDeclarationStatement((identifier)name);
		return res;
	}

	@Override
	public Integer visitEnumDeclarationStatement(Java8Parser.EnumDeclarationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object name = usedobj.poll();
		smt = new enumDeclarationStatement((identifier)name);
		return res;
	}

	@Override
	public Integer visitMethodDeclarationStatement(Java8Parser.MethodDeclarationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object name = usedobj.poll();
		Object typelist = null;
		if (ctx.typeList() != null)
		{
			typelist = usedobj.poll();
		}
		smt = new methodDeclarationStatement((typeList)typelist, (identifier)name);
		return res;
	}

	@Override
	public Integer visitEnumConstantDeclarationStatement(Java8Parser.EnumConstantDeclarationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object arglist = null;
		if (ctx.argumentList() != null)
		{
			arglist = usedobj.poll();
		}
		Object name = usedobj.poll();
		smt = new enumConstantDeclarationStatement((identifier)name, (argumentList)arglist);
		return res;
	}

	@Override
	public Integer visitLabeledStatement(Java8Parser.LabeledStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object name = usedobj.poll();
		smt = new labeledStatement((identifier)name);
		return res;
	}

	@Override
	public Integer visitVariableDeclarationStatement(Java8Parser.VariableDeclarationStatementContext ctx) {
		int count = 0;
		if (ctx.dims() != null)
		{
			String ds = ctx.dims().getText();
			count = CountDims(ds);
		}
		Integer res = visitChildren(ctx);
		Object tp = usedobj.poll();
		smt = new variableDeclarationStatement((type)tp, count);
		return res;
	}
	
	protected int CountDims(String ds)
	{
		int count = 0;
		int dslen = ds.length();
		for (int i=0;i<dslen;i++)
		{
			if (ds.charAt(i) == '[')
			{
				count++;
			}
		}
		return count;
	}

	@Override
	public Integer visitLambdaExpressionStatement(Java8Parser.LambdaExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		TypeListContext tl = ctx.typeList();
		Object tlist = null;
		if (tl != null)
		{
			tlist = usedobj.poll();
		}
		smt = new lambdaExpressionStatement((typeList)tlist);
		return res;
	}

	@Override
	public Integer visitBreakStatement(Java8Parser.BreakStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object name = null;
		if (ctx.identifier() != null)
		{
			name = usedobj.poll();
		}
		smt = new breakStatement((identifier)name);
		return res;
	}

	@Override
	public Integer visitContinueStatement(Java8Parser.ContinueStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object name = null;
		if (ctx.identifier() != null)
		{
			name = usedobj.poll();
		}
		smt = new continueStatement((identifier)name);
		return res;
	}

	@Override
	public Integer visitDoWhileStatement(Java8Parser.DoWhileStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = usedobj.poll();
		smt = new doWhileStatement((referedExpression)rexp);
		return res;
	}

	@Override
	public Integer visitWhileStatement(Java8Parser.WhileStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = usedobj.poll();
		smt = new whileStatement((referedExpression)rexp);
		return res;
	}

	@Override
	public Integer visitReturnStatement(Java8Parser.ReturnStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = null;
		if (ctx.referedExpression() != null)
		{
			rexp = usedobj.poll();
		}
		smt = new returnStatement((referedExpression)rexp);
		return res;
	}

	@Override
	public Integer visitSwitchStatement(Java8Parser.SwitchStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = usedobj.poll();
		smt = new switchStatement((referedExpression)rexp);
		return res;
	}

	@Override
	public Integer visitSwitchCaseStatement(Java8Parser.SwitchCaseStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = usedobj.poll();
		smt = new switchCaseStatement((referedExpression)rexp);
		return res;
	}

	@Override
	public Integer visitDefaultStatement(Java8Parser.DefaultStatementContext ctx) {
		smt = new defaultStatement();
		return visitChildren(ctx);
	}

	@Override
	public Integer visitSynchronizedStatement(Java8Parser.SynchronizedStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = usedobj.poll();
		smt = new synchronizedStatement((referedExpression)rexp);
		return res;
	}

	@Override
	public Integer visitThrowStatement(Java8Parser.ThrowStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = usedobj.poll();
		smt = new throwStatement((referedExpression)rexp);
		return res;
	}

	@Override
	public Integer visitCatchClauseStatement(Java8Parser.CatchClauseStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rt = usedobj.poll();
		smt = new catchClauseStatement((type)rt);
		return res;
	}

	@Override
	public Integer visitIfStatement(Java8Parser.IfStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = usedobj.poll();
		smt = new ifStatement((referedExpression)rexp);
		return res;
	}

	@Override
	public Integer visitArrayCreationStatement(Java8Parser.ArrayCreationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rt = usedobj.poll();
		smt = new arrayCreationStatement((type)rt);
		return res;
	}

	@Override
	public Integer visitInitializerStatement(Java8Parser.InitializerStatementContext ctx) {
		smt = new initializerStatement();
		return visitChildren(ctx);
	}

	@Override
	public Integer visitVariableDeclarationHolderStatement(Java8Parser.VariableDeclarationHolderStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = null;
		if (ctx.referedExpression() != null)
		{
			rexp = usedobj.poll();
		}
		smt = new variableDeclarationHolderStatement((referedExpression)rexp);
		return res;
	}

	@Override
	public Integer visitEnhancedForStatement(Java8Parser.EnhancedForStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = usedobj.poll();
		Object rt = usedobj.poll();
		smt = new enhancedForStatement((type)rt, (referedExpression)rexp);
		return res;
	}

	@Override
	public Integer visitArrayAccessStatement(Java8Parser.ArrayAccessStatementContext ctx) {
		
		return visitChildren(ctx);
	}

	@Override
	public Integer visitPartialEndArrayAccessStatement(Java8Parser.PartialEndArrayAccessStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitLeftParentheseStatement(Java8Parser.LeftParentheseStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitRightParentheseStatement(Java8Parser.RightParentheseStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitLeftBraceStatement(Java8Parser.LeftBraceStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitRightBraceStatement(Java8Parser.RightBraceStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitDoStatement(Java8Parser.DoStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitArrayInitializerStartStatement(Java8Parser.ArrayInitializerStartStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitForStatement(Java8Parser.ForStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitForIniOverStatement(Java8Parser.ForIniOverStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitForExpOverStatement(Java8Parser.ForExpOverStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitForUpdOverStatement(Java8Parser.ForUpdOverStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitCondExpBeginStatement(Java8Parser.CondExpBeginStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitCondExpQuestionMarkStatement(Java8Parser.CondExpQuestionMarkStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitCondExpColonMarkStatement(Java8Parser.CondExpColonMarkStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitEndOfStatement(Java8Parser.EndOfStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitPartialEnd(Java8Parser.PartialEndContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitFullEnd(Java8Parser.FullEndContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitArgumentList(Java8Parser.ArgumentListContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitTypeList(Java8Parser.TypeListContext ctx) {
		return visitChildren(ctx);
	}
	
	@Override
	public Integer visitLiteral(Java8Parser.LiteralContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitReferenceType(Java8Parser.ReferenceTypeContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitNumberLiteral(Java8Parser.NumberLiteralContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitStringLiteral(Java8Parser.StringLiteralContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitType(Java8Parser.TypeContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitPrimitiveType(Java8Parser.PrimitiveTypeContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitClassOrInterfaceType(Java8Parser.ClassOrInterfaceTypeContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitArrayType(Java8Parser.ArrayTypeContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitDims(Java8Parser.DimsContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitTypeArguments(Java8Parser.TypeArgumentsContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitTypeArgumentList(Java8Parser.TypeArgumentListContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitTypeArgument(Java8Parser.TypeArgumentContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitWildCard(Java8Parser.WildCardContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitWildcardBounds(Java8Parser.WildcardBoundsContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitIntersectionType(Java8Parser.IntersectionTypeContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitUnionType(Java8Parser.UnionTypeContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitIdentifier(Java8Parser.IdentifierContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitCodeHole(Java8Parser.CodeHoleContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitPreExist(Java8Parser.PreExistContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitEndOfArrayDeclarationIndexExpression(
			Java8Parser.EndOfArrayDeclarationIndexExpressionContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitIntegerLiteral(Java8Parser.IntegerLiteralContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitFloatingPointLiteral(Java8Parser.FloatingPointLiteralContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitBooleanLiteral(Java8Parser.BooleanLiteralContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitCharacterLiteral(Java8Parser.CharacterLiteralContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitNullLiteral(Java8Parser.NullLiteralContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitUnaryOperator(Java8Parser.UnaryOperatorContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitBinaryOperator(Java8Parser.BinaryOperatorContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitAssignmentOperator(Java8Parser.AssignmentOperatorContext ctx) {
		return visitChildren(ctx);
	}

	public statement getSmt() {
		return smt;
	}

	public void setSmt(statement smt) {
		this.smt = smt;
	}

}