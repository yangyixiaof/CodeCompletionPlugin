package cn.yyx.contentassist.parsehelper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import SJ8Parse.Java8BaseVisitor;
import SJ8Parse.Java8Parser;
import SJ8Parse.Java8Parser.ArgumentListContext;
import SJ8Parse.Java8Parser.BothTypeContext;
import SJ8Parse.Java8Parser.EndOfArrayDeclarationIndexExpressionContext;
import SJ8Parse.Java8Parser.ExtendBoundContext;
import SJ8Parse.Java8Parser.IntersectionFirstTypeContext;
import SJ8Parse.Java8Parser.IntersectionSecondTypeContext;
import SJ8Parse.Java8Parser.OffsetContext;
import SJ8Parse.Java8Parser.ParameterizedTypeContext;
import SJ8Parse.Java8Parser.ReferedExpressionContext;
import SJ8Parse.Java8Parser.SimpleTypeContext;
import SJ8Parse.Java8Parser.TypeContext;
import SJ8Parse.Java8Parser.TypeListContext;
import SJ8Parse.Java8Parser.UnionFirstTypeContext;
import SJ8Parse.Java8Parser.UnionSecondTypeContext;
import SJ8Parse.Java8Parser.WildcardBoundsContext;
import cn.yyx.contentassist.codeutils.annotationTypeMemberDeclarationStatement;
import cn.yyx.contentassist.codeutils.anonymousClassBeginStatement;
import cn.yyx.contentassist.codeutils.anonymousClassPreStatement;
import cn.yyx.contentassist.codeutils.argumentList;
import cn.yyx.contentassist.codeutils.arrayAccessStatement;
import cn.yyx.contentassist.codeutils.arrayCreationStatement;
import cn.yyx.contentassist.codeutils.arrayInitializerStartStatement;
import cn.yyx.contentassist.codeutils.arrayType;
import cn.yyx.contentassist.codeutils.assignmentOperator;
import cn.yyx.contentassist.codeutils.assignmentStatement;
import cn.yyx.contentassist.codeutils.atInterfaceStatement;
import cn.yyx.contentassist.codeutils.binaryOperator;
import cn.yyx.contentassist.codeutils.booleanLiteral;
import cn.yyx.contentassist.codeutils.breakStatement;
import cn.yyx.contentassist.codeutils.castExpressionStatement;
import cn.yyx.contentassist.codeutils.catchClauseStatement;
import cn.yyx.contentassist.codeutils.characterLiteral;
import cn.yyx.contentassist.codeutils.classDeclarationStatement;
import cn.yyx.contentassist.codeutils.classInnerDeclarationStatement;
import cn.yyx.contentassist.codeutils.classOrInterfaceType;
import cn.yyx.contentassist.codeutils.classRef;
import cn.yyx.contentassist.codeutils.codeHole;
import cn.yyx.contentassist.codeutils.commonFieldRef;
import cn.yyx.contentassist.codeutils.commonVarRef;
import cn.yyx.contentassist.codeutils.condExpBeginStatement;
import cn.yyx.contentassist.codeutils.condExpColonMarkStatement;
import cn.yyx.contentassist.codeutils.condExpQuestionMarkStatement;
import cn.yyx.contentassist.codeutils.continueStatement;
import cn.yyx.contentassist.codeutils.defaultStatement;
import cn.yyx.contentassist.codeutils.doWhileStatement;
import cn.yyx.contentassist.codeutils.endOfStatement;
import cn.yyx.contentassist.codeutils.enhancedForStatement;
import cn.yyx.contentassist.codeutils.enterMethodParamStatement;
import cn.yyx.contentassist.codeutils.enumConstantDeclarationStatement;
import cn.yyx.contentassist.codeutils.enumDeclarationStatement;
import cn.yyx.contentassist.codeutils.expressionStatement;
import cn.yyx.contentassist.codeutils.fieldAccess;
import cn.yyx.contentassist.codeutils.fieldAccessStatement;
import cn.yyx.contentassist.codeutils.finalFieldRef;
import cn.yyx.contentassist.codeutils.finalVarRef;
import cn.yyx.contentassist.codeutils.floatingPointLiteral;
import cn.yyx.contentassist.codeutils.forExpOverStatement;
import cn.yyx.contentassist.codeutils.forIniOverStatement;
import cn.yyx.contentassist.codeutils.forStatement;
import cn.yyx.contentassist.codeutils.forUpdOverStatement;
import cn.yyx.contentassist.codeutils.idRawLetter;
import cn.yyx.contentassist.codeutils.identifier;
import cn.yyx.contentassist.codeutils.ifStatement;
import cn.yyx.contentassist.codeutils.infixExpressionStatement;
import cn.yyx.contentassist.codeutils.initializerStatement;
import cn.yyx.contentassist.codeutils.instanceofExpressionStatement;
import cn.yyx.contentassist.codeutils.integerLiteral;
import cn.yyx.contentassist.codeutils.intersectionType;
import cn.yyx.contentassist.codeutils.labeledStatement;
import cn.yyx.contentassist.codeutils.lambdaExpressionStatement;
import cn.yyx.contentassist.codeutils.leftBraceStatement;
import cn.yyx.contentassist.codeutils.leftParentheseStatement;
import cn.yyx.contentassist.codeutils.literal;
import cn.yyx.contentassist.codeutils.literalStatement;
import cn.yyx.contentassist.codeutils.methodDeclarationStatement;
import cn.yyx.contentassist.codeutils.methodInvocationStatement;
import cn.yyx.contentassist.codeutils.methodReferenceStatement;
import cn.yyx.contentassist.codeutils.nameStatement;
import cn.yyx.contentassist.codeutils.nullLiteral;
import cn.yyx.contentassist.codeutils.parameterizedType;
import cn.yyx.contentassist.codeutils.partialEndArrayAccessStatement;
import cn.yyx.contentassist.codeutils.partialEndArrayInitializerStatement;
import cn.yyx.contentassist.codeutils.postfixExpressionStatement;
import cn.yyx.contentassist.codeutils.preExist;
import cn.yyx.contentassist.codeutils.prefixExpressionStatement;
import cn.yyx.contentassist.codeutils.primitiveType;
import cn.yyx.contentassist.codeutils.referedExpression;
import cn.yyx.contentassist.codeutils.returnStatement;
import cn.yyx.contentassist.codeutils.rightBraceStatement;
import cn.yyx.contentassist.codeutils.rightParentheseStatement;
import cn.yyx.contentassist.codeutils.simpleType;
import cn.yyx.contentassist.codeutils.statement;
import cn.yyx.contentassist.codeutils.stringLiteral;
import cn.yyx.contentassist.codeutils.switchCaseStatement;
import cn.yyx.contentassist.codeutils.switchStatement;
import cn.yyx.contentassist.codeutils.synchronizedStatement;
import cn.yyx.contentassist.codeutils.throwStatement;
import cn.yyx.contentassist.codeutils.type;
import cn.yyx.contentassist.codeutils.typeList;
import cn.yyx.contentassist.codeutils.unaryOperator;
import cn.yyx.contentassist.codeutils.unionType;
import cn.yyx.contentassist.codeutils.variableDeclarationHolderStatement;
import cn.yyx.contentassist.codeutils.variableDeclarationStatement;
import cn.yyx.contentassist.codeutils.whileStatement;
import cn.yyx.contentassist.codeutils.wildCardType;

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
		smt = new literalStatement((literal) usedobj.poll());
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
		smt = new anonymousClassBeginStatement();
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
		smt = new atInterfaceStatement((identifier) id);
		return res;
	}

	@Override
	public Integer visitAnnotationTypeMemberDeclarationStatement(
			Java8Parser.AnnotationTypeMemberDeclarationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object drexp = null;
		if (ctx.referedExpression() != null) {
			drexp = usedobj.poll();
		}
		Object type = usedobj.poll();
		smt = new annotationTypeMemberDeclarationStatement((type) type, (referedExpression) drexp);
		return res;
	}

	@Override
	public Integer visitClassDeclarationStatement(Java8Parser.ClassDeclarationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object name = usedobj.poll();
		smt = new classDeclarationStatement((identifier) name);
		return res;
	}

	@Override
	public Integer visitClassInnerDeclarationStatement(Java8Parser.ClassInnerDeclarationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object name = usedobj.poll();
		smt = new classInnerDeclarationStatement((identifier) name);
		return res;
	}

	@Override
	public Integer visitEnumDeclarationStatement(Java8Parser.EnumDeclarationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object name = usedobj.poll();
		smt = new enumDeclarationStatement((identifier) name);
		return res;
	}

	@Override
	public Integer visitMethodDeclarationStatement(Java8Parser.MethodDeclarationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object name = usedobj.poll();
		Object typelist = null;
		if (ctx.typeList() != null) {
			typelist = usedobj.poll();
		}
		smt = new methodDeclarationStatement((typeList) typelist, (identifier) name);
		return res;
	}

	@Override
	public Integer visitEnumConstantDeclarationStatement(Java8Parser.EnumConstantDeclarationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object arglist = null;
		if (ctx.argumentList() != null) {
			arglist = usedobj.poll();
		}
		Object name = usedobj.poll();
		smt = new enumConstantDeclarationStatement((identifier) name, (argumentList) arglist);
		return res;
	}

	@Override
	public Integer visitLabeledStatement(Java8Parser.LabeledStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object name = usedobj.poll();
		smt = new labeledStatement((identifier) name);
		return res;
	}

	@Override
	public Integer visitVariableDeclarationStatement(Java8Parser.VariableDeclarationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object tp = usedobj.poll();
		smt = new variableDeclarationStatement((type) tp);
		return res;
	}

	protected int CountDims(String ds) {
		int count = 0;
		int dslen = ds.length();
		for (int i = 0; i < dslen; i++) {
			if (ds.charAt(i) == '[') {
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
		if (tl != null) {
			tlist = usedobj.poll();
		}
		smt = new lambdaExpressionStatement((typeList) tlist);
		return res;
	}

	@Override
	public Integer visitBreakStatement(Java8Parser.BreakStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object name = null;
		if (ctx.identifier() != null) {
			name = usedobj.poll();
		}
		smt = new breakStatement((identifier) name);
		return res;
	}

	@Override
	public Integer visitContinueStatement(Java8Parser.ContinueStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object name = null;
		if (ctx.identifier() != null) {
			name = usedobj.poll();
		}
		smt = new continueStatement((identifier) name);
		return res;
	}

	@Override
	public Integer visitDoWhileStatement(Java8Parser.DoWhileStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = usedobj.poll();
		smt = new doWhileStatement((referedExpression) rexp);
		return res;
	}

	@Override
	public Integer visitWhileStatement(Java8Parser.WhileStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = usedobj.poll();
		smt = new whileStatement((referedExpression) rexp);
		return res;
	}

	@Override
	public Integer visitReturnStatement(Java8Parser.ReturnStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = null;
		if (ctx.referedExpression() != null) {
			rexp = usedobj.poll();
		}
		smt = new returnStatement((referedExpression) rexp);
		return res;
	}

	@Override
	public Integer visitSwitchStatement(Java8Parser.SwitchStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = usedobj.poll();
		smt = new switchStatement((referedExpression) rexp);
		return res;
	}

	@Override
	public Integer visitSwitchCaseStatement(Java8Parser.SwitchCaseStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = usedobj.poll();
		smt = new switchCaseStatement((referedExpression) rexp);
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
		smt = new synchronizedStatement((referedExpression) rexp);
		return res;
	}

	@Override
	public Integer visitThrowStatement(Java8Parser.ThrowStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = usedobj.poll();
		smt = new throwStatement((referedExpression) rexp);
		return res;
	}

	@Override
	public Integer visitCatchClauseStatement(Java8Parser.CatchClauseStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rt = usedobj.poll();
		smt = new catchClauseStatement((type) rt);
		return res;
	}

	@Override
	public Integer visitIfStatement(Java8Parser.IfStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = usedobj.poll();
		smt = new ifStatement((referedExpression) rexp);
		return res;
	}

	@Override
	public Integer visitArrayCreationStatement(Java8Parser.ArrayCreationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rt = usedobj.poll();
		smt = new arrayCreationStatement((type) rt);
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
		if (ctx.referedExpression() != null) {
			rexp = usedobj.poll();
		}
		smt = new variableDeclarationHolderStatement((referedExpression) rexp);
		return res;
	}

	@Override
	public Integer visitEnhancedForStatement(Java8Parser.EnhancedForStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = usedobj.poll();
		Object rt = usedobj.poll();
		smt = new enhancedForStatement((type) rt, (referedExpression) rexp);
		return res;
	}

	@Override
	public Integer visitArrayAccessStatement(Java8Parser.ArrayAccessStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = usedobj.poll();
		Object rarr = usedobj.poll();
		EndOfArrayDeclarationIndexExpressionContext eoad = ctx.endOfArrayDeclarationIndexExpression();
		smt = new arrayAccessStatement((referedExpression) rarr, (referedExpression) rexp, (eoad == null ? false : true));
		return res;
	}

	@Override
	public Integer visitPartialEndArrayAccessStatement(Java8Parser.PartialEndArrayAccessStatementContext ctx) {
		Integer res = visitChildren(ctx);
		if (smt == null || !(smt instanceof expressionStatement)) {
			System.err.println("PartialEndArrayAccessStatement does handle expressionStatement.");
			new Exception().printStackTrace();
			System.exit(1);
		}
		smt = new partialEndArrayAccessStatement((expressionStatement)smt);
		return res;
	}
	
	@Override
	public Integer visitPartialEndArrayInitializerStatement(Java8Parser.PartialEndArrayInitializerStatementContext ctx) {
		Integer res = visitChildren(ctx);
		if (smt == null || !(smt instanceof expressionStatement)) {
			System.err.println("PartialEndArrayInitializerStatement does handle expressionStatement.");
			new Exception().printStackTrace();
			System.exit(1);
		}
		smt = new partialEndArrayInitializerStatement((expressionStatement)smt);
		return res;
	}

	@Override
	public Integer visitLeftParentheseStatement(Java8Parser.LeftParentheseStatementContext ctx) {
		String text = ctx.getText();
		int tlen = text.length();
		int count = 0;
		for (int i = 0; i < tlen; i++) {
			if (text.charAt(i) == '(') {
				count++;
			}
		}
		smt = new leftParentheseStatement(count);
		return visitChildren(ctx);
	}

	@Override
	public Integer visitRightParentheseStatement(Java8Parser.RightParentheseStatementContext ctx) {
		String text = ctx.getText();
		int tlen = text.length();
		int count = 0;
		for (int i = 0; i < tlen; i++) {
			if (text.charAt(i) == ')') {
				count++;
			}
		}
		smt = new rightParentheseStatement(count);
		return visitChildren(ctx);
	}

	@Override
	public Integer visitLeftBraceStatement(Java8Parser.LeftBraceStatementContext ctx) {
		String text = ctx.getText();
		int tlen = text.length();
		int count = 0;
		for (int i = 0; i < tlen; i++) {
			if (text.charAt(i) == '{') {
				count++;
			}
		}
		smt = new leftBraceStatement(count);
		return visitChildren(ctx);
	}

	@Override
	public Integer visitRightBraceStatement(Java8Parser.RightBraceStatementContext ctx) {
		String text = ctx.getText();
		int tlen = text.length();
		int count = 0;
		for (int i = 0; i < tlen; i++) {
			if (text.charAt(i) == '}') {
				count++;
			}
		}
		smt = new rightBraceStatement(count);
		return visitChildren(ctx);
	}
	
	@Override
	public Integer visitEnterMethodParamStatement(Java8Parser.EnterMethodParamStatementContext ctx) {
		int allcount = 0;
		String text = ctx.getText();
		for (int i=0;i<text.length();i++)
		{
			char c = text.charAt(i);
			if (c == 'E')
			{
				allcount++;
			}
		}
		smt = new enterMethodParamStatement(allcount);
		return visitChildren(ctx);
	}

	@Override
	public Integer visitArrayInitializerStartStatement(Java8Parser.ArrayInitializerStartStatementContext ctx) {
		smt = new arrayInitializerStartStatement();
		return visitChildren(ctx);
	}

	@Override
	public Integer visitForStatement(Java8Parser.ForStatementContext ctx) {
		smt = new forStatement();
		return visitChildren(ctx);
	}

	@Override
	public Integer visitForIniOverStatement(Java8Parser.ForIniOverStatementContext ctx) {
		smt = new forIniOverStatement();
		return visitChildren(ctx);
	}

	@Override
	public Integer visitForExpOverStatement(Java8Parser.ForExpOverStatementContext ctx) {
		smt = new forExpOverStatement();
		return visitChildren(ctx);
	}

	@Override
	public Integer visitForUpdOverStatement(Java8Parser.ForUpdOverStatementContext ctx) {
		smt = new forUpdOverStatement();
		return visitChildren(ctx);
	}

	@Override
	public Integer visitCondExpBeginStatement(Java8Parser.CondExpBeginStatementContext ctx) {
		smt = new condExpBeginStatement();
		return visitChildren(ctx);
	}

	@Override
	public Integer visitCondExpQuestionMarkStatement(Java8Parser.CondExpQuestionMarkStatementContext ctx) {
		smt = new condExpQuestionMarkStatement();
		return visitChildren(ctx);
	}

	@Override
	public Integer visitCondExpColonMarkStatement(Java8Parser.CondExpColonMarkStatementContext ctx) {
		smt = new condExpColonMarkStatement();
		return visitChildren(ctx);
	}

	@Override
	public Integer visitEndOfStatement(Java8Parser.EndOfStatementContext ctx) {
		if (ctx.fullEnd() != null) {
			smt = new endOfStatement(true);
		} else {
			smt = new endOfStatement(false);
		}
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
		Integer res = visitChildren(ctx);
		argumentList al = new argumentList();
		List<ReferedExpressionContext> rl = ctx.referedExpression();
		Iterator<ReferedExpressionContext> itr = rl.iterator();
		while (itr.hasNext()) {
			itr.next();
			Object o = usedobj.poll();
			al.AddToFirst((referedExpression) o);
		}
		usedobj.add(al);
		return res;
	}

	@Override
	public Integer visitTypeList(Java8Parser.TypeListContext ctx) {
		Integer res = visitChildren(ctx);
		typeList al = new typeList();
		List<TypeContext> rl = ctx.type();
		Iterator<TypeContext> itr = rl.iterator();
		while (itr.hasNext()) {
			itr.next();
			Object o = usedobj.poll();
			al.AddToFirst((type) o);
		}
		usedobj.add(al);
		return res;
	}

	@Override
	public Integer visitLiteral(Java8Parser.LiteralContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitNumberLiteral(Java8Parser.NumberLiteralContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitStringLiteral(Java8Parser.StringLiteralContext ctx) {
		usedobj.add(new stringLiteral());
		return visitChildren(ctx);
	}

	@Override
	public Integer visitType(Java8Parser.TypeContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitPrimitiveType(Java8Parser.PrimitiveTypeContext ctx) {
		usedobj.add(new primitiveType(ctx.getText()));
		return visitChildren(ctx);
	}

	@Override
	public Integer visitSimpleType(SimpleTypeContext ctx) {
		usedobj.add(new simpleType(ctx.getText()));
		return visitChildren(ctx);
	}

	@Override
	public Integer visitParameterizedType(ParameterizedTypeContext ctx) {
		Integer res = visitChildren(ctx);
		Object tlist = usedobj.poll();
		Object id = usedobj.poll();
		usedobj.add(new parameterizedType((identifier) id, (typeList) tlist));
		return res;
	}

	@Override
	public Integer visitBothType(BothTypeContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitClassOrInterfaceType(Java8Parser.ClassOrInterfaceTypeContext ctx) {
		Integer res = visitChildren(ctx);
		List<BothTypeContext> slist = ctx.bothType();
		Iterator<BothTypeContext> itr = slist.iterator();
		List<type> result = new LinkedList<type>();
		while (itr.hasNext()) {
			itr.next();
			result.add(0, (type)usedobj.poll());
		}
		usedobj.add(new classOrInterfaceType(result));
		return res;
	}

	@Override
	public Integer visitArrayType(Java8Parser.ArrayTypeContext ctx) {
		Integer res = visitChildren(ctx);
		int count = 0;
		if (ctx.dims() != null) {
			String ds = ctx.dims().getText();
			count = CountDims(ds);
		}
		Object tp = usedobj.poll();
		usedobj.add(new arrayType((type)tp, count));
		return res;
	}

	@Override
	public Integer visitDims(Java8Parser.DimsContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitWildCardType(Java8Parser.WildCardTypeContext ctx) {
		Integer res = visitChildren(ctx);
		WildcardBoundsContext wb = ctx.wildcardBounds();
		Object tp = usedobj.poll();
		boolean extended = false;
		if (wb != null)
		{
			ExtendBoundContext eb = wb.extendBound();
			if (eb != null)
			{
				extended = true;
			}
		}
		usedobj.add(new wildCardType(extended, (type)tp));
		return res;
	}

	@Override
	public Integer visitWildcardBounds(Java8Parser.WildcardBoundsContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitIntersectionFirstType(IntersectionFirstTypeContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitIntersectionSecondType(IntersectionSecondTypeContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitIntersectionType(Java8Parser.IntersectionTypeContext ctx) {
		Integer res = visitChildren(ctx);
		List<IntersectionSecondTypeContext> list = ctx.intersectionSecondType();
		Iterator<IntersectionSecondTypeContext> itr = list.iterator();
		List<type> tps = new LinkedList<type>();
		while (itr.hasNext())
		{
			itr.next();
			tps.add(0, (type)usedobj.poll());
		}
		// for intersectionFirstType()
		tps.add(0, (type)usedobj.poll());
		usedobj.add(new intersectionType(tps));
		return res;
	}

	@Override
	public Integer visitUnionFirstType(UnionFirstTypeContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitUnionSecondType(UnionSecondTypeContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitUnionType(Java8Parser.UnionTypeContext ctx) {
		Integer res = visitChildren(ctx);
		List<UnionSecondTypeContext> list = ctx.unionSecondType();
		Iterator<UnionSecondTypeContext> itr = list.iterator();
		List<type> tps = new LinkedList<type>();
		while (itr.hasNext())
		{
			itr.next();
			tps.add(0, (type)usedobj.poll());
		}
		// for intersectionFirstType()
		tps.add(0, (type)usedobj.poll());
		usedobj.add(new unionType(tps));
		return res;
	}

	@Override
	public Integer visitIdentifier(Java8Parser.IdentifierContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitIdRawLetter(Java8Parser.IdRawLetterContext ctx) {
		usedobj.add(new idRawLetter(ctx.getText()));
		return visitChildren(ctx);
	}

	@Override
	public Integer visitClassRef(Java8Parser.ClassRefContext ctx) {
		List<OffsetContext> list = ctx.offset();
		Iterator<OffsetContext> itr = list.iterator();
		OffsetContext scopelevel = itr.next();
		int scope = Integer.parseInt(scopelevel.getText());
		OffsetContext offsetlevel = itr.next();
		int off = Integer.parseInt(offsetlevel.getText());
		usedobj.add(new classRef(scope, off));
		return visitChildren(ctx);
	}

	@Override
	public Integer visitFinalFieldRef(Java8Parser.FinalFieldRefContext ctx) {
		List<OffsetContext> list = ctx.offset();
		Iterator<OffsetContext> itr = list.iterator();
		OffsetContext scopelevel = itr.next();
		int scope = Integer.parseInt(scopelevel.getText());
		OffsetContext offsetlevel = itr.next();
		int off = Integer.parseInt(offsetlevel.getText());
		usedobj.add(new finalFieldRef(scope, off));
		return visitChildren(ctx);
	}

	@Override
	public Integer visitFinalVarRef(Java8Parser.FinalVarRefContext ctx) {
		List<OffsetContext> list = ctx.offset();
		Iterator<OffsetContext> itr = list.iterator();
		OffsetContext scopelevel = itr.next();
		int scope = Integer.parseInt(scopelevel.getText());
		OffsetContext offsetlevel = itr.next();
		int off = Integer.parseInt(offsetlevel.getText());
		usedobj.add(new finalVarRef(scope, off));
		return visitChildren(ctx);
	}

	@Override
	public Integer visitCommonFieldRef(Java8Parser.CommonFieldRefContext ctx) {
		List<OffsetContext> list = ctx.offset();
		Iterator<OffsetContext> itr = list.iterator();
		OffsetContext scopelevel = itr.next();
		int scope = Integer.parseInt(scopelevel.getText());
		OffsetContext offsetlevel = itr.next();
		int off = Integer.parseInt(offsetlevel.getText());
		usedobj.add(new commonFieldRef(scope, off));
		return visitChildren(ctx);
	}

	@Override
	public Integer visitCommonVarRef(Java8Parser.CommonVarRefContext ctx) {
		List<OffsetContext> list = ctx.offset();
		Iterator<OffsetContext> itr = list.iterator();
		OffsetContext scopelevel = itr.next();
		int scope = Integer.parseInt(scopelevel.getText());
		OffsetContext offsetlevel = itr.next();
		int off = Integer.parseInt(offsetlevel.getText());
		usedobj.add(new commonVarRef(scope, off));
		return visitChildren(ctx);
	}

	@Override
	public Integer visitOffset(Java8Parser.OffsetContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitCodeHole(Java8Parser.CodeHoleContext ctx) {
		usedobj.add(new codeHole());
		return visitChildren(ctx);
	}

	@Override
	public Integer visitPreExist(Java8Parser.PreExistContext ctx) {
		usedobj.add(new preExist());
		return visitChildren(ctx);
	}

	@Override
	public Integer visitEndOfArrayDeclarationIndexExpression(
			Java8Parser.EndOfArrayDeclarationIndexExpressionContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitIntegerLiteral(Java8Parser.IntegerLiteralContext ctx) {
		usedobj.add(new integerLiteral(Integer.parseInt(ctx.getText())));
		return visitChildren(ctx);
	}

	@Override
	public Integer visitFloatingPointLiteral(Java8Parser.FloatingPointLiteralContext ctx) {
		usedobj.add(new floatingPointLiteral(Double.parseDouble(ctx.getText())));
		return visitChildren(ctx);
	}

	@Override
	public Integer visitBooleanLiteral(Java8Parser.BooleanLiteralContext ctx) {
		usedobj.add(new booleanLiteral(Boolean.parseBoolean(ctx.getText())));
		return visitChildren(ctx);
	}

	@Override
	public Integer visitCharacterLiteral(Java8Parser.CharacterLiteralContext ctx) {
		usedobj.add(new characterLiteral(ctx.getText()));
		return visitChildren(ctx);
	}

	@Override
	public Integer visitNullLiteral(Java8Parser.NullLiteralContext ctx) {
		usedobj.add(new nullLiteral());
		return visitChildren(ctx);
	}

	@Override
	public Integer visitUnaryOperator(Java8Parser.UnaryOperatorContext ctx) {
		usedobj.add(new unaryOperator(ctx.getText()));
		return visitChildren(ctx);
	}

	@Override
	public Integer visitBinaryOperator(Java8Parser.BinaryOperatorContext ctx) {
		usedobj.add(new binaryOperator(ctx.getText()));
		return visitChildren(ctx);
	}

	@Override
	public Integer visitAssignmentOperator(Java8Parser.AssignmentOperatorContext ctx) {
		usedobj.add(new assignmentOperator(ctx.getText()));
		return visitChildren(ctx);
	}

	public statement getSmt() {
		return smt;
	}

	public void setSmt(statement smt) {
		this.smt = smt;
	}

}