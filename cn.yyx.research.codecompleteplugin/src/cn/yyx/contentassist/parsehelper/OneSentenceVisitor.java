package cn.yyx.contentassist.parsehelper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codeutils.*;
import cn.yyx.contentassist.commonutils.StringUtil;
import cn.yyx.parse.szparse8java.Java8BaseVisitor;
import cn.yyx.parse.szparse8java.Java8Parser;
import cn.yyx.parse.szparse8java.Java8Parser.AddInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.AddPrefixExpressionContext;
import cn.yyx.parse.szparse8java.Java8Parser.AddPrefixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.AddassignAssignmentStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.AndInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.AndassignAssignmentStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.ArgTypeContext;
import cn.yyx.parse.szparse8java.Java8Parser.ArgTypeListContext;
import cn.yyx.parse.szparse8java.Java8Parser.AssignAssignmentStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.BangPrefixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.BitandInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.BitorInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.BothTypeContext;
import cn.yyx.parse.szparse8java.Java8Parser.CaretInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.ChainFieldAccessContext;
import cn.yyx.parse.szparse8java.Java8Parser.CommonClassMemberInvokeContext;
import cn.yyx.parse.szparse8java.Java8Parser.CommonMethodInvocationStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.CommonMethodReferenceExpressionContext;
import cn.yyx.parse.szparse8java.Java8Parser.CommonNameStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.CommonNewMethodReferenceExpressionContext;
import cn.yyx.parse.szparse8java.Java8Parser.CommonOverStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.DecPostfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.DecPrefixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.DivInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.DivassignAssignmentStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.ElseStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.EndOfArrayDeclarationIndexExpressionContext;
import cn.yyx.parse.szparse8java.Java8Parser.EnumConstantDeclarationSplitCommaStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.EqualInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.ExtendBoundContext;
import cn.yyx.parse.szparse8java.Java8Parser.FieldRefNameStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.FinallyStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.FirstArgReferedExpressionContext;
import cn.yyx.parse.szparse8java.Java8Parser.GeInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.GtInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.IncPostfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.IncPrefixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.IntersectionFirstTypeContext;
import cn.yyx.parse.szparse8java.Java8Parser.IntersectionSecondTypeContext;
import cn.yyx.parse.szparse8java.Java8Parser.LambdaEndStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.LastArgTypeContext;
import cn.yyx.parse.szparse8java.Java8Parser.LeInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.LshiftInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.LshiftassignAssignmentStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.LtInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.MethodArgReferedExpressionContext;
import cn.yyx.parse.szparse8java.Java8Parser.ModInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.ModassignAssignmentStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.MulInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.MulassignAssignmentStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.NotequalInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.OrInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.OrassignAssignmentStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.ParameterizedTypeContext;
import cn.yyx.parse.szparse8java.Java8Parser.QualifiedAccessStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.ReferedExpressionContext;
import cn.yyx.parse.szparse8java.Java8Parser.ReferedFieldAccessContext;
import cn.yyx.parse.szparse8java.Java8Parser.RshiftInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.RshiftassignAssignmentStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.SimpleTypeContext;
import cn.yyx.parse.szparse8java.Java8Parser.SubInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.SubPrefixExpressionContext;
import cn.yyx.parse.szparse8java.Java8Parser.SubPrefixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.SubassignAssignmentStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.SuperConstructionInvocationStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.SuperFieldAccessContext;
import cn.yyx.parse.szparse8java.Java8Parser.SuperMethodReferenceExpressionContext;
import cn.yyx.parse.szparse8java.Java8Parser.ThisConstructionInvocationStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.ThisFieldAccessContext;
import cn.yyx.parse.szparse8java.Java8Parser.TildePrefixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.TryStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.TypeArgumentContext;
import cn.yyx.parse.szparse8java.Java8Parser.TypeArgumentsContext;
import cn.yyx.parse.szparse8java.Java8Parser.TypeContext;
import cn.yyx.parse.szparse8java.Java8Parser.TypeCreationInvocationStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.TypeLiteralContext;
import cn.yyx.parse.szparse8java.Java8Parser.UnionFirstTypeContext;
import cn.yyx.parse.szparse8java.Java8Parser.UnionSecondTypeContext;
import cn.yyx.parse.szparse8java.Java8Parser.UrshiftInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.UrshiftassignAssignmentStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.VarRefNameStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.WildcardBoundsContext;
import cn.yyx.parse.szparse8java.Java8Parser.XorassignAssignmentStatementContext;

public class OneSentenceVisitor extends Java8BaseVisitor<Integer> {

	private statement smt = null;

	private Stack<Object> usedobj = new Stack<Object>();

	@Override
	public Integer visitStatement(Java8Parser.StatementContext ctx) {
		return visitChildren(ctx);
	}
	
	@Override
	public Integer visitMethodArgumentEndStatement(Java8Parser.MethodArgumentEndStatementContext ctx) {
		Integer res = visitChildren(ctx);
		smt = new methodArgumentEndStatement(smt, ctx.getText());
		return res;
	}
	
	@Override
	public Integer visitMethodPreRerferedExpressionEndStatement(
			Java8Parser.MethodPreRerferedExpressionEndStatementContext ctx) {
		Integer res = visitChildren(ctx);
		smt = new methodPreRerferedExpressionEndStatement(smt, ctx.getText());
		return res;
	}
	
	@Override
	public Integer visitCommonOverStatement(CommonOverStatementContext ctx) {
		Integer res = visitChildren(ctx);
		smt = new commonOverStatement(smt, ctx.getText());
		return res;
	}

	@Override
	public Integer visitForIniOverStatement(Java8Parser.ForIniOverStatementContext ctx) {
		Integer res = visitChildren(ctx);
		smt = new forIniOverStatement(smt, ctx.getText());
		return res;
	}

	@Override
	public Integer visitForExpOverStatement(Java8Parser.ForExpOverStatementContext ctx) {
		Integer res = visitChildren(ctx);
		smt = new forExpOverStatement(smt, ctx.getText());
		return res;
	}

	@Override
	public Integer visitForUpdOverStatement(Java8Parser.ForUpdOverStatementContext ctx) {
		Integer res = visitChildren(ctx);
		smt = new forUpdOverStatement(smt, ctx.getText());
		return res;
	}
	
	
	public Integer visitRawForIniOverStatement(Java8Parser.RawForIniOverStatementContext ctx) {
		Integer res = visitChildren(ctx);
		smt = new rawForIniOverStatement(ctx.getText());
		return res;
	};
	
	@Override
	public Integer visitRawForExpOverStatement(Java8Parser.RawForExpOverStatementContext ctx) {
		Integer res = visitChildren(ctx);
		smt = new rawForExpOverStatement(ctx.getText());
		return res;
	}

	@Override
	public Integer visitRawForUpdOverStatement(Java8Parser.RawForUpdOverStatementContext ctx) {
		Integer res = visitChildren(ctx);
		smt = new rawForUpdOverStatement(ctx.getText());
		return res;
	}
	
	@Override
	public Integer visitLambdaEndStatement(LambdaEndStatementContext ctx) {
		Integer res = visitChildren(ctx);
		// smt = new rawForUpdOverStatement(ctx.getText());
		if (!(smt instanceof expressionStatement))
		{
			System.err.println("what the fuck? smt is not expressionStatement in LambdaEndStatement?");
			System.exit(1);
		}
		smt = new lambdaEndStatement(smt, ctx.getText());
		return res;
	}

	@Override
	public Integer visitExpressionStatement(Java8Parser.ExpressionStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitAssignAssignmentStatement(AssignAssignmentStatementContext ctx) {
		Integer res = visitChildren(ctx);
		AssignmentStatementHandler(ctx.getText(), "=");
		return res;
	}

	@Override
	public Integer visitMulassignAssignmentStatement(MulassignAssignmentStatementContext ctx) {
		Integer res = visitChildren(ctx);
		AssignmentStatementHandler(ctx.getText(), "*=");
		return res;
	}

	@Override
	public Integer visitDivassignAssignmentStatement(DivassignAssignmentStatementContext ctx) {
		Integer res = visitChildren(ctx);
		AssignmentStatementHandler(ctx.getText(), "/=");
		return res;
	}

	@Override
	public Integer visitModassignAssignmentStatement(ModassignAssignmentStatementContext ctx) {
		Integer res = visitChildren(ctx);
		AssignmentStatementHandler(ctx.getText(), "%=");
		return res;
	}

	@Override
	public Integer visitAddassignAssignmentStatement(AddassignAssignmentStatementContext ctx) {
		Integer res = visitChildren(ctx);
		AssignmentStatementHandler(ctx.getText(), "+=");
		return res;
	}

	@Override
	public Integer visitSubassignAssignmentStatement(SubassignAssignmentStatementContext ctx) {
		Integer res = visitChildren(ctx);
		AssignmentStatementHandler(ctx.getText(), "-=");
		return res;
	}

	@Override
	public Integer visitLshiftassignAssignmentStatement(LshiftassignAssignmentStatementContext ctx) {
		Integer res = visitChildren(ctx);
		AssignmentStatementHandler(ctx.getText(), "<<=");
		return res;
	}

	@Override
	public Integer visitRshiftassignAssignmentStatement(RshiftassignAssignmentStatementContext ctx) {
		Integer res = visitChildren(ctx);
		AssignmentStatementHandler(ctx.getText(), ">>=");
		return res;
	}

	@Override
	public Integer visitUrshiftassignAssignmentStatement(UrshiftassignAssignmentStatementContext ctx) {
		Integer res = visitChildren(ctx);
		AssignmentStatementHandler(ctx.getText(), ">>>=");
		return res;
	}

	@Override
	public Integer visitAndassignAssignmentStatement(AndassignAssignmentStatementContext ctx) {
		Integer res = visitChildren(ctx);
		AssignmentStatementHandler(ctx.getText(), "&=");
		return res;
	}

	@Override
	public Integer visitXorassignAssignmentStatement(XorassignAssignmentStatementContext ctx) {
		Integer res = visitChildren(ctx);
		AssignmentStatementHandler(ctx.getText(), "^=");
		return res;
	}

	@Override
	public Integer visitOrassignAssignmentStatement(OrassignAssignmentStatementContext ctx) {
		Integer res = visitChildren(ctx);
		AssignmentStatementHandler(ctx.getText(), "|=");
		return res;
	}

	private void AssignmentStatementHandler(String smtcode, String optr) {
		Object right = usedobj.pop();
		Object left = usedobj.pop();
		smt = new assignmentStatement(smtcode, (referedExpression) left, optr, (referedExpression) right);
	}

	@Override
	public Integer visitAssignmentStatement(Java8Parser.AssignmentStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitNormalLiteralStatement(Java8Parser.NormalLiteralStatementContext ctx) {
		Integer res = visitChildren(ctx);
		smt = new literalStatement(ctx.getText(), (literal) usedobj.pop());
		return res;
	}

	@Override
	public Integer visitNegativeLiteralStatement(Java8Parser.NegativeLiteralStatementContext ctx) {
		Integer res = visitChildren(ctx);
		literal lt = (literal) usedobj.pop();
		try {
			lt.HandleNegativeOperator();
		} catch (CodeSynthesisException e) {
			System.err.println("Parse error, some lt can not execute negative operator.");
			e.printStackTrace();
			System.exit(1);
		}
		smt = new literalStatement(ctx.getText(), lt);
		return res;
	}

	@Override
	public Integer visitPositiveLiteralStatement(Java8Parser.PositiveLiteralStatementContext ctx) {
		Integer res = visitChildren(ctx);
		smt = new literalStatement(ctx.getText(), (literal) usedobj.pop());
		return res;
	}

	@Override
	public Integer visitLiteralStatement(Java8Parser.LiteralStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitCastExpressionStatement(Java8Parser.CastExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression rexp = (referedExpression) usedobj.pop();
		type type = (cn.yyx.contentassist.codeutils.type) usedobj.pop();
		smt = new castExpressionStatement(ctx.getText(), type, rexp);
		return res;
	}

	@Override
	public Integer visitCommonMethodInvocationStatement(CommonMethodInvocationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		argumentList argList = (argumentList) usedobj.pop();
		identifier name = (identifier) usedobj.pop();
		smt = new commonMethodInvocationStatement(ctx.getText(), name, argList);
		return res;
	}

	@Override
	public Integer visitTypeCreationInvocationStatement(TypeCreationInvocationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		argumentList argList = (argumentList) usedobj.pop();
		type tp = (type) usedobj.pop();
		smt = new typeCreationInvocationStatement(ctx.getText(), tp, argList);
		return res;
	}

	@Override
	public Integer visitSuperConstructionInvocationStatement(SuperConstructionInvocationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		argumentList argList = (argumentList) usedobj.pop();
		smt = new superConstructionInvocationStatement(ctx.getText(), argList);
		return res;
	}

	@Override
	public Integer visitThisConstructionInvocationStatement(ThisConstructionInvocationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		argumentList argList = (argumentList) usedobj.pop();
		smt = new thisConstructionInvocationStatement(ctx.getText(), argList);
		return res;
	}

	@Override
	public Integer visitMethodInvocationStatement(Java8Parser.MethodInvocationStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitQualifiedAccessStatement(QualifiedAccessStatementContext ctx) {
		Integer res = visitChildren(ctx);
		fieldAccess fa = (fieldAccess) usedobj.pop();
		smt = new qualifiedAccessStatement(ctx.getText(), fa);
		return res;
	}

	@Override
	public Integer visitFieldAccessStatement(Java8Parser.FieldAccessStatementContext ctx) {
		Integer res = visitChildren(ctx);
		fieldAccess fa = (fieldAccess) usedobj.pop();
		smt = new fieldAccessStatement(ctx.getText(), fa);
		return res;
	}

	@Override
	public Integer visitChainFieldAccess(ChainFieldAccessContext ctx) {
		Integer res = visitChildren(ctx);
		fieldAccess fa = (fieldAccess) usedobj.pop();
		identifier id = (identifier) usedobj.pop();
		usedobj.push(new chainFieldAccess(id, fa));
		return res;
	}

	@Override
	public Integer visitReferedFieldAccess(ReferedFieldAccessContext ctx) {
		// tested
		// System.out.println(ctx.getText());
		// System.out.println("usedobj pre size:" + usedobj.size());
		Integer res = visitChildren(ctx);
		// System.out.println("usedobj post size:" + usedobj.size());
		referedExpression rexp = (referedExpression) usedobj.pop();
		identifier name = (identifier) usedobj.pop();
		usedobj.push(new referedFieldAccess(name, rexp));
		return res;
	}

	@Override
	public Integer visitSuperFieldAccess(SuperFieldAccessContext ctx) {
		Integer res = visitChildren(ctx);
		ReferedExpressionContext rexpctx = ctx.referedExpression();
		TypeContext tpctx = ctx.type();
		referedExpression rexp = null;
		type tp = null;
		if (rexpctx != null) {
			rexp = (referedExpression) usedobj.pop();
		}
		if (tpctx != null) {
			tp = (type) usedobj.pop();
		}
		identifier name = (identifier) usedobj.pop();
		usedobj.push(new superFieldAccess(name, rexp, tp));
		return res;
	}

	@Override
	public Integer visitThisFieldAccess(ThisFieldAccessContext ctx) {
		Integer res = visitChildren(ctx);
		ReferedExpressionContext rexpctx = ctx.referedExpression();
		TypeContext tpctx = ctx.type();
		referedExpression rexp = null;
		type tp = null;
		if (rexpctx != null) {
			rexp = (referedExpression) usedobj.pop();
		}
		if (tpctx != null) {
			tp = (type) usedobj.pop();
		}
		usedobj.push(new thisFieldAccess(rexp, tp));
		return res;
	}

	@Override
	public Integer visitFieldAccess(Java8Parser.FieldAccessContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitGtInfixExpressionStatement(GtInfixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		InfixExpressionStatementHandler(ctx.getText(), ">");
		return res;
	}

	@Override
	public Integer visitLtInfixExpressionStatement(LtInfixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		InfixExpressionStatementHandler(ctx.getText(), "<");
		return res;
	}

	@Override
	public Integer visitEqualInfixExpressionStatement(EqualInfixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		InfixExpressionStatementHandler(ctx.getText(), "==");
		return res;
	}

	@Override
	public Integer visitLeInfixExpressionStatement(LeInfixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		InfixExpressionStatementHandler(ctx.getText(), "<=");
		return res;
	}

	@Override
	public Integer visitGeInfixExpressionStatement(GeInfixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		InfixExpressionStatementHandler(ctx.getText(), ">=");
		return res;
	}

	@Override
	public Integer visitNotequalInfixExpressionStatement(NotequalInfixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		InfixExpressionStatementHandler(ctx.getText(), "!=");
		return res;
	}

	@Override
	public Integer visitAndInfixExpressionStatement(AndInfixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		InfixExpressionStatementHandler(ctx.getText(), "&&");
		return res;
	}

	@Override
	public Integer visitOrInfixExpressionStatement(OrInfixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		InfixExpressionStatementHandler(ctx.getText(), "||");
		return res;
	}

	@Override
	public Integer visitAddInfixExpressionStatement(AddInfixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		InfixExpressionStatementHandler(ctx.getText(), "+");
		return res;
	}

	@Override
	public Integer visitSubInfixExpressionStatement(SubInfixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		InfixExpressionStatementHandler(ctx.getText(), "-");
		return res;
	}

	@Override
	public Integer visitMulInfixExpressionStatement(MulInfixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		InfixExpressionStatementHandler(ctx.getText(), "*");
		return res;
	}

	@Override
	public Integer visitDivInfixExpressionStatement(DivInfixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		InfixExpressionStatementHandler(ctx.getText(), "/");
		return res;
	}

	@Override
	public Integer visitBitandInfixExpressionStatement(BitandInfixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		InfixExpressionStatementHandler(ctx.getText(), "&");
		return res;
	}

	@Override
	public Integer visitBitorInfixExpressionStatement(BitorInfixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		InfixExpressionStatementHandler(ctx.getText(), "|");
		return res;
	}

	@Override
	public Integer visitCaretInfixExpressionStatement(CaretInfixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		InfixExpressionStatementHandler(ctx.getText(), "^");
		return res;
	}

	@Override
	public Integer visitModInfixExpressionStatement(ModInfixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		InfixExpressionStatementHandler(ctx.getText(), "%");
		return res;
	}

	@Override
	public Integer visitLshiftInfixExpressionStatement(LshiftInfixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		InfixExpressionStatementHandler(ctx.getText(), "<<");
		return res;
	}

	@Override
	public Integer visitRshiftInfixExpressionStatement(RshiftInfixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		InfixExpressionStatementHandler(ctx.getText(), ">>");
		return res;
	}

	@Override
	public Integer visitUrshiftInfixExpressionStatement(UrshiftInfixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		InfixExpressionStatementHandler(ctx.getText(), ">>>");
		return res;
	}

	private void InfixExpressionStatementHandler(String smtcode, String optr) {
		Object rexp = usedobj.pop();
		Object lexp = usedobj.pop();
		smt = new infixExpressionStatement(smtcode, (referedExpression) lexp, optr, (referedExpression) rexp);
	}

	@Override
	public Integer visitInfixExpressionStatement(Java8Parser.InfixExpressionStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitInstanceofExpressionStatement(Java8Parser.InstanceofExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object type = usedobj.pop();
		Object rexp = usedobj.pop();
		smt = new instanceofExpressionStatement(ctx.getText(), ctx.type().getText(), (referedExpression) rexp, (type) type);
		return res;
	}

	@Override
	public Integer visitCommonMethodReferenceExpression(CommonMethodReferenceExpressionContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression rexp = (referedExpression) usedobj.pop();
		identifier name = (identifier) usedobj.pop();
		usedobj.push(new commonMethodReferenceExpression(name, rexp));
		return res;
	}

	@Override
	public Integer visitCommonNewMethodReferenceExpression(CommonNewMethodReferenceExpressionContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression rexp = (referedExpression) usedobj.pop();
		usedobj.push(new commonNewMethodReferenceExpression(rexp));
		return res;
	}

	@Override
	public Integer visitSuperMethodReferenceExpression(SuperMethodReferenceExpressionContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression rexp = null;
		if (ctx.referedExpression() != null) {
			rexp = (referedExpression) usedobj.pop();
		}
		identifier name = (identifier) usedobj.pop();
		usedobj.push(new superMethodReferenceExpression(name, rexp));
		return res;
	}

	@Override
	public Integer visitMethodReferenceStatement(Java8Parser.MethodReferenceStatementContext ctx) {
		Integer res = visitChildren(ctx);
		methodReferenceExpression mre = (methodReferenceExpression) usedobj.pop();
		smt = new methodReferenceStatement(ctx.getText(), mre);
		return res;
	}

	@Override
	public Integer visitCommonNameStatement(CommonNameStatementContext ctx) {
		Integer res = visitChildren(ctx);
		identifier name = (identifier) usedobj.pop();
		smt = new commonNameStatement(ctx.getText(), name);
		return res;
	}

	@Override
	public Integer visitVarRefNameStatement(VarRefNameStatementContext ctx) {
		Integer res = visitChildren(ctx);
		commonVarRef name = (commonVarRef) usedobj.pop();
		smt = new varRefNameStatement(ctx.getText(), name);
		return res;
	}

	@Override
	public Integer visitFieldRefNameStatement(FieldRefNameStatementContext ctx) {
		Integer res = visitChildren(ctx);
		commonFieldRef name = (commonFieldRef) usedobj.pop();
		smt = new fieldRefNameStatement(ctx.getText(), name);
		return res;
	}

	@Override
	public Integer visitNameStatement(Java8Parser.NameStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitBangPrefixExpressionStatement(BangPrefixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression rexp = (referedExpression) usedobj.pop();
		String optr = "!";
		smt = new prefixExpressionStatement(ctx.getText(), optr, rexp);
		return res;
	}

	@Override
	public Integer visitTildePrefixExpressionStatement(TildePrefixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression rexp = (referedExpression) usedobj.pop();
		String optr = "~";
		smt = new prefixExpressionStatement(ctx.getText(), optr, rexp);
		return res;
	}

	@Override
	public Integer visitIncPrefixExpressionStatement(IncPrefixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression rexp = (referedExpression) usedobj.pop();
		String optr = "++";
		smt = new prefixExpressionStatement(ctx.getText(), optr, rexp);
		return res;
	}

	@Override
	public Integer visitDecPrefixExpressionStatement(DecPrefixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression rexp = (referedExpression) usedobj.pop();
		String optr = "--";
		smt = new prefixExpressionStatement(ctx.getText(), optr, rexp);
		return res;
	}

	@Override
	public Integer visitAddPrefixExpression(AddPrefixExpressionContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression rexp = (referedExpression) usedobj.pop();
		usedobj.add(new addPrefixExpression(rexp));
		return res;
	}

	@Override
	public Integer visitSubPrefixExpression(SubPrefixExpressionContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression rexp = (referedExpression) usedobj.pop();
		usedobj.add(new subPrefixExpression(rexp));
		return res;
	}

	@Override
	public Integer visitAddPrefixExpressionStatement(AddPrefixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		addPrefixExpression addrexp = (addPrefixExpression) usedobj.pop();
		String optr = "+";
		smt = new prefixExpressionStatement(ctx.getText(), optr, addrexp.getRexp());
		return res;
	}

	@Override
	public Integer visitSubPrefixExpressionStatement(SubPrefixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		subPrefixExpression subrexp = (subPrefixExpression) usedobj.pop();
		String optr = "-";
		smt = new prefixExpressionStatement(ctx.getText(), optr, subrexp.getRexp());
		return res;
	}

	@Override
	public Integer visitPrefixExpressionStatement(Java8Parser.PrefixExpressionStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitIncPostfixExpressionStatement(IncPostfixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression rexp = (referedExpression) usedobj.pop();
		smt = new postfixExpressionStatement(ctx.getText(), rexp, "++");
		return res;
	}

	@Override
	public Integer visitDecPostfixExpressionStatement(DecPostfixExpressionStatementContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression rexp = (referedExpression) usedobj.pop();
		smt = new postfixExpressionStatement(ctx.getText(), rexp, "--");
		return res;
	}

	@Override
	public Integer visitPostfixExpressionStatement(Java8Parser.PostfixExpressionStatementContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitReferedExpression(Java8Parser.ReferedExpressionContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitIdentifier(Java8Parser.IdentifierContext ctx) {
		usedobj.push(new identifier(ctx.getText()));
		return visitChildren(ctx);
	}

	@Override
	public Integer visitAnonymousClassBeginStatement(Java8Parser.AnonymousClassBeginStatementContext ctx) {
		Integer res = visitChildren(ctx);
		smt = new anonymousClassBeginStatement(ctx.getText());
		return res;
	}

	@Override
	public Integer visitAnonymousClassPlaceHolderStatement(Java8Parser.AnonymousClassPlaceHolderStatementContext ctx) {
		smt = new anonymousClassPlaceHolderStatement(ctx.getText());
		return visitChildren(ctx);
	}

	@Override
	public Integer visitAnonymousClassPreStatement(Java8Parser.AnonymousClassPreStatementContext ctx) {
		Integer res = visitChildren(ctx);
		identifier id = (identifier) usedobj.pop();
		smt = new anonymousClassPreStatement(ctx.getText(), id);
		return res;
	}

	@Override
	public Integer visitAtInterfaceStatement(Java8Parser.AtInterfaceStatementContext ctx) {
		Integer res = visitChildren(ctx);
		identifier id = (identifier) usedobj.pop();
		smt = new atInterfaceStatement(ctx.getText(), id);
		return res;
	}

	@Override
	public Integer visitAnnotationTypeMemberDeclarationStatement(
			Java8Parser.AnnotationTypeMemberDeclarationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression drexp = null;
		if (ctx.referedExpression() != null) {
			drexp = (referedExpression) usedobj.pop();
		}
		identifier id = (identifier) usedobj.pop();
		type type = (type) usedobj.pop();
		smt = new annotationTypeMemberDeclarationStatement(ctx.getText(), type, id, drexp);
		return res;
	}

	@Override
	public Integer visitClassDeclarationStatement(Java8Parser.ClassDeclarationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		identifier name = (identifier) usedobj.pop();
		smt = new classDeclarationStatement(ctx.getText(), name);
		return res;
	}

	@Override
	public Integer visitClassInnerDeclarationStatement(Java8Parser.ClassInnerDeclarationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		identifier name = (identifier) usedobj.pop();
		smt = new classInnerDeclarationStatement(ctx.getText(), name);
		return res;
	}

	@Override
	public Integer visitEnumDeclarationStatement(Java8Parser.EnumDeclarationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		identifier name = (identifier) usedobj.pop();
		smt = new enumDeclarationStatement(ctx.getText(), name);
		return res;
	}

	@Override
	public Integer visitMethodDeclarationStatement(Java8Parser.MethodDeclarationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		identifier name = (identifier) usedobj.pop();
		argTypeList typelist = null;
		if (ctx.argTypeList() != null) {
			typelist = (argTypeList) usedobj.pop();
		}
		type rt = (type) usedobj.pop();
		smt = new methodDeclarationStatement(ctx.getText(), typelist, name, rt);
		return res;
	}

	@Override
	public Integer visitConstructionDeclarationStatement(Java8Parser.ConstructionDeclarationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		identifier name = (identifier) usedobj.pop();
		argTypeList typelist = null;
		if (ctx.argTypeList() != null) {
			typelist = (argTypeList) usedobj.pop();
		}
		smt = new constructionDeclarationStatement(ctx.getText(), typelist, name);
		return res;
	}

	@Override
	public Integer visitEnumConstantDeclarationStatement(Java8Parser.EnumConstantDeclarationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		argumentList arglist = (argumentList) usedobj.pop();
		identifier name = (identifier) usedobj.pop();
		smt = new enumConstantDeclarationStatement(ctx.getText(), (identifier) name, (argumentList) arglist);
		return res;
	}

	@Override
	public Integer visitLabeledStatement(Java8Parser.LabeledStatementContext ctx) {
		Integer res = visitChildren(ctx);
		identifier name = (identifier) usedobj.pop();
		smt = new labeledStatement(ctx.getText(), name);
		return res;
	}

	@Override
	public Integer visitVariableDeclarationStatement(Java8Parser.VariableDeclarationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		type tp = (type) usedobj.pop();
		smt = new variableDeclarationStatement(ctx.getText(), tp);
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
		referedExpression rexp = null;
		ReferedExpressionContext rexpctx = ctx.referedExpression();
		if (rexpctx != null) {
			rexp = (referedExpression) usedobj.pop();
		}
		ArgTypeListContext tl = ctx.argTypeList();
		argTypeList tlist = null;
		if (tl != null) {
			tlist = (argTypeList) usedobj.pop();
		}
		smt = new lambdaExpressionStatement(ctx.getText(), tlist, rexp);
		return res;
	}

	@Override
	public Integer visitBreakStatement(Java8Parser.BreakStatementContext ctx) {
		Integer res = visitChildren(ctx);
		identifier name = null;
		if (ctx.identifier() != null) {
			name = (identifier) usedobj.pop();
		}
		smt = new breakStatement(ctx.getText(), name);
		return res;
	}

	@Override
	public Integer visitContinueStatement(Java8Parser.ContinueStatementContext ctx) {
		Integer res = visitChildren(ctx);
		identifier name = null;
		if (ctx.identifier() != null) {
			name = (identifier) usedobj.pop();
		}
		smt = new continueStatement(ctx.getText(), name);
		return res;
	}

	@Override
	public Integer visitDoWhileStatement(Java8Parser.DoWhileStatementContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression rexp = (referedExpression) usedobj.pop();
		smt = new doWhileStatement(ctx.getText(), rexp);
		return res;
	}

	@Override
	public Integer visitWhileStatement(Java8Parser.WhileStatementContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression rexp = (referedExpression) usedobj.pop();
		smt = new whileStatement(ctx.getText(), rexp);
		return res;
	}

	@Override
	public Integer visitReturnStatement(Java8Parser.ReturnStatementContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression rexp = null;
		if (ctx.referedExpression() != null) {
			rexp = (referedExpression) usedobj.pop();
		}
		smt = new returnStatement(ctx.getText(), rexp);
		return res;
	}

	@Override
	public Integer visitSwitchStatement(Java8Parser.SwitchStatementContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression rexp = (referedExpression) usedobj.pop();
		smt = new switchStatement(ctx.getText(), rexp);
		return res;
	}

	@Override
	public Integer visitSwitchCaseStatement(Java8Parser.SwitchCaseStatementContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression rexp = (referedExpression) usedobj.pop();
		smt = new switchCaseStatement(ctx.getText(), rexp);
		return res;
	}

	@Override
	public Integer visitDefaultStatement(Java8Parser.DefaultStatementContext ctx) {
		smt = new defaultStatement(ctx.getText());
		return visitChildren(ctx);
	}

	@Override
	public Integer visitSynchronizedStatement(Java8Parser.SynchronizedStatementContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression rexp = (referedExpression) usedobj.pop();
		smt = new synchronizedStatement(ctx.getText(), rexp);
		return res;
	}

	@Override
	public Integer visitTryStatement(TryStatementContext ctx) {
		Integer res = visitChildren(ctx);
		smt = new tryStatement(ctx.getText());
		return res;
	}

	@Override
	public Integer visitThrowStatement(Java8Parser.ThrowStatementContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression rexp = (referedExpression) usedobj.pop();
		smt = new throwStatement(ctx.getText(), rexp);
		return res;
	}

	@Override
	public Integer visitCatchClauseStatement(Java8Parser.CatchClauseStatementContext ctx) {
		Integer res = visitChildren(ctx);
		type rt = (type) usedobj.pop();
		smt = new catchClauseStatement(ctx.getText(), rt);
		return res;
	}

	@Override
	public Integer visitFinallyStatement(FinallyStatementContext ctx) {
		Integer res = visitChildren(ctx);
		smt = new finallyStatement(ctx.getText());
		return res;
	}

	@Override
	public Integer visitIfStatement(Java8Parser.IfStatementContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression rexp = (referedExpression) usedobj.pop();
		smt = new ifStatement(ctx.getText(), rexp);
		return res;
	}

	public Integer visitThenStatement(Java8Parser.ThenStatementContext ctx) {
		Integer res = visitChildren(ctx);
		smt = new thenStatement(ctx.getText());
		return res;
	}

	@Override
	public Integer visitElseStatement(ElseStatementContext ctx) {
		Integer res = visitChildren(ctx);
		smt = new elseStatement(ctx.getText());
		return res;
	}

	@Override
	public Integer visitArrayCreationStatement(Java8Parser.ArrayCreationStatementContext ctx) {
		Integer res = visitChildren(ctx);
		type rt = (type) usedobj.pop();
		smt = new arrayCreationStatement(ctx.getText(), rt);
		return res;
	}

	@Override
	public Integer visitInitializerStatement(Java8Parser.InitializerStatementContext ctx) {
		smt = new initializerStatement(ctx.getText());
		return visitChildren(ctx);
	}
	
	@Override
	public Integer visitVariableDeclarationHolderStatement(Java8Parser.VariableDeclarationHolderStatementContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression rexp = null;
		if (ctx.referedExpression() != null) {
			rexp = (referedExpression) usedobj.pop();
		}
		smt = new variableDeclarationHolderStatement(ctx.getText(), rexp);
		return res;
	}
	
	@Override
	public Integer visitEnhancedForStatement(Java8Parser.EnhancedForStatementContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression rexp = (referedExpression) usedobj.pop();
		type rt = (type) usedobj.pop();
		smt = new enhancedForStatement(ctx.getText(), rt, rexp);
		return res;
	}

	@Override
	public Integer visitArrayAccessStatement(Java8Parser.ArrayAccessStatementContext ctx) {
		Integer res = visitChildren(ctx);
		Object rexp = usedobj.pop();
		Object rarr = usedobj.pop();
		EndOfArrayDeclarationIndexExpressionContext eoad = ctx.endOfArrayDeclarationIndexExpression();
		boolean arend = eoad == null ? false : true;
		smt = new arrayAccessStatement(ctx.getText(), (referedExpression) rarr, (referedExpression) rexp, arend);
		return res;
	}

	@Override
	public Integer visitPartialEndArrayAccessStatement(Java8Parser.PartialEndArrayAccessStatementContext ctx) {
		Integer res = visitChildren(ctx);
		if (smt == null || !(smt instanceof expressionStatement)) {
			System.err.println("PartialEndArrayAccessStatement does handle expressionStatement.");
			System.err.println(smt == null ? "smt is null" : "smt class:" + smt.getClass());
			new Exception().printStackTrace();
			System.err.println(1 / 0); // cause fatal error.
			System.exit(1);
		}
		EndOfArrayDeclarationIndexExpressionContext eae = ctx.endOfArrayDeclarationIndexExpression();
		String eaetx = eae.getText();
		int acount = StringUtil.CountHappenTimes(eaetx, ']');
		smt = new arrayAccessEndStatement(ctx.getText(), (expressionStatement) smt, acount);
		return res;
	}

	/*
	 * @Override public Integer
	 * visitPartialEndArrayInitializerStatement(Java8Parser.
	 * PartialEndArrayInitializerStatementContext ctx) { Integer res =
	 * visitChildren(ctx); if (smt == null || !(smt instanceof
	 * expressionStatement)) { System.err.println(
	 * "PartialEndArrayInitializerStatement does handle expressionStatement.");
	 * new Exception().printStackTrace(); System.exit(1); } smt = new
	 * partialEndArrayInitializerStatement((expressionStatement)smt); return
	 * res; }
	 */

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
		smt = new leftParentheseStatement(ctx.getText(), count);
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
		smt = new rightParentheseStatement(ctx.getText(), count);
		return visitChildren(ctx);
	}

	@Override
	public Integer visitEnterMethodParamStatement(Java8Parser.EnterMethodParamStatementContext ctx) {
		int allcount = 0;
		String text = ctx.getText();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == 'E') {
				allcount++;
			}
		}
		smt = new enterMethodParamStatement(ctx.getText(), allcount);
		return visitChildren(ctx);
	}

	@Override
	public Integer visitArrayInitializerStartStatement(Java8Parser.ArrayInitializerStartStatementContext ctx) {
		smt = new arrayInitializerStartStatement(ctx.getText());
		return visitChildren(ctx);
	}

	@Override
	public Integer visitArrayInitializerSplitCommaStatement(
			Java8Parser.ArrayInitializerSplitCommaStatementContext ctx) {
		smt = new arrayInitializerSplitCommaStatement(ctx.getText());
		return visitChildren(ctx);
	}

	@Override
	public Integer visitArrayInitializerEndStatement(Java8Parser.ArrayInitializerEndStatementContext ctx) {
		smt = new arrayInitializerEndStatement(ctx.getText());
		return visitChildren(ctx);
	}

	@Override
	public Integer visitEnumConstantDeclarationSplitCommaStatement(
			EnumConstantDeclarationSplitCommaStatementContext ctx) {
		smt = new enumConstantDeclarationSplitCommaStatement(ctx.getText());
		return visitChildren(ctx);
	}

	@Override
	public Integer visitForStatement(Java8Parser.ForStatementContext ctx) {
		smt = new forStatement(ctx.getText());
		return visitChildren(ctx);
	}

	@Override
	public Integer visitCondExpBeginStatement(Java8Parser.CondExpBeginStatementContext ctx) {
		smt = new condExpBeginStatement(ctx.getText());
		return visitChildren(ctx);
	}

	@Override
	public Integer visitCondExpQuestionMarkStatement(Java8Parser.CondExpQuestionMarkStatementContext ctx) {
		smt = new condExpQuestionMarkStatement(ctx.getText());
		return visitChildren(ctx);
	}

	@Override
	public Integer visitCondExpColonMarkStatement(Java8Parser.CondExpColonMarkStatementContext ctx) {
		smt = new condExpColonMarkStatement(ctx.getText());
		return visitChildren(ctx);
	}

	@Override
	public Integer visitPartialEndStatement(Java8Parser.PartialEndStatementContext ctx) {
		smt = new partialEndStatement(ctx.getText());
		return visitChildren(ctx);
	}
	
	/*@Override
	public Integer visitFirstArgPreExist(FirstArgPreExistContext ctx) {
		usedobj.push(new firstArgPreExist());
		return visitChildren(ctx);
	}*/

	@Override
	public Integer visitFirstArgReferedExpression(FirstArgReferedExpressionContext ctx) {
		Integer res = visitChildren(ctx);
		referedExpression rexp = null;
		ReferedExpressionContext rexpctx = ctx.referedExpression();
		if (rexpctx != null) {
			rexp = (referedExpression) usedobj.pop();
		}
		type tp = null;
		TypeContext tpctx = ctx.type();
		if (tpctx != null) {
			tp = (type) usedobj.pop();
		}
		firstArgReferedExpression fare = new firstArgReferedExpression(rexp, tp);
		usedobj.push(fare);
		return res;
	}

	@Override
	public Integer visitCommonClassMemberInvoke(CommonClassMemberInvokeContext ctx) {
		Integer res = visitChildren(ctx);
		firstArgReferedExpression rexp = (firstArgReferedExpression) usedobj.pop();
		commonClassMemberInvoke ccmi = new commonClassMemberInvoke(rexp);
		usedobj.push(ccmi);
		return res;
	}

	@Override
	public Integer visitSelfClassMemberInvoke(Java8Parser.SelfClassMemberInvokeContext ctx) {
		Integer res = visitChildren(ctx);
		firstArgReferedExpression rexp = null;
		FirstArgReferedExpressionContext rexpctx = ctx.firstArgReferedExpression();
		if (rexpctx != null) {
			rexp = (firstArgReferedExpression) usedobj.pop();
		}
		selfClassMemberInvoke scmi = new selfClassMemberInvoke(rexp);
		usedobj.push(scmi);
		return res;
	}

	@Override
	public Integer visitSuperClassMemberInvoke(Java8Parser.SuperClassMemberInvokeContext ctx) {
		Integer res = visitChildren(ctx);
		firstArgReferedExpression rexp = null;
		FirstArgReferedExpressionContext rexpctx = ctx.firstArgReferedExpression();
		if (rexpctx != null) {
			rexp = (firstArgReferedExpression) usedobj.pop();
		}
		superClassMemberInvoke scmi = new superClassMemberInvoke(rexp);
		usedobj.push(scmi);
		return res;
	}

	@Override
	public Integer visitNewClassInvoke(Java8Parser.NewClassInvokeContext ctx) {
		Integer res = visitChildren(ctx);
		firstArgReferedExpression rexp = null;
		FirstArgReferedExpressionContext rexpctx = ctx.firstArgReferedExpression();
		if (rexpctx != null) {
			rexp = (firstArgReferedExpression) usedobj.pop();
		}
		newClassInvoke scmi = new newClassInvoke(rexp);
		usedobj.push(scmi);
		return res;
	}

	@Override
	public Integer visitFirstArg(Java8Parser.FirstArgContext ctx) {
		return visitChildren(ctx);
	}

	/*@Override
	public Integer visitMethodArgPreExist(MethodArgPreExistContext ctx) {
		usedobj.push(new methodArgPreExist());
		return super.visitMethodArgPreExist(ctx);
	}*/

	@Override
	public Integer visitMethodArgReferedExpression(MethodArgReferedExpressionContext ctx) {
		return super.visitMethodArgReferedExpression(ctx);
	}

	@Override
	public Integer visitArgumentList(Java8Parser.ArgumentListContext ctx) {
		Integer res = visitChildren(ctx);
		argumentList al = new argumentList();
		List<MethodArgReferedExpressionContext> rl = ctx.methodArgReferedExpression();
		Iterator<MethodArgReferedExpressionContext> itr = rl.iterator();
		while (itr.hasNext()) {
			itr.next();
			Object o = usedobj.pop();
			al.AddToFirst((referedExpression) o);
		}
		firstArg firstArg = (firstArg) usedobj.pop();
		al.setFirstArgument(firstArg);
		usedobj.push(al);
		return res;
	}

	@Override
	public Integer visitLastArgType(LastArgTypeContext ctx) {
		Integer res = visitChildren(ctx);
		type tp = (type) usedobj.pop();
		usedobj.add(new lastArgType(tp));
		return res;
	}

	/*
	 * @Override public Integer visitTypeList(Java8Parser.TypeListContext ctx) {
	 * Integer res = visitChildren(ctx); typeList al = new typeList();
	 * List<TypeContext> rl = ctx.type(); Iterator<TypeContext> itr =
	 * rl.iterator(); while (itr.hasNext()) { itr.next(); Object o =
	 * usedobj.pop(); al.AddToFirst((type) o); } usedobj.push(al); return res; }
	 */

	@Override
	public Integer visitArgType(ArgTypeContext ctx) {
		Integer res = visitChildren(ctx);
		type tp = (type) usedobj.pop();
		usedobj.push(new argType(tp));
		return res;
	}

	@Override
	public Integer visitArgTypeList(ArgTypeListContext ctx) {
		Integer res = visitChildren(ctx);
		/*
		 * LastArgTypeContext latctx = ctx.lastArgType(); lastArgType lat =
		 * null; if (latctx != null) { lat = (lastArgType) usedobj.pop(); }
		 */
		lastArgType lat = null;
		LastArgTypeContext lastArgTypeCtx = ctx.lastArgType();
		if (lastArgTypeCtx != null) {
			lat = (lastArgType) usedobj.pop();
			// al.AddToFirst(lat);
		}
		argTypeList al = new argTypeList(lat);
		List<ArgTypeContext> rl = ctx.argType();
		Iterator<ArgTypeContext> itr = rl.iterator();
		while (itr.hasNext()) {
			itr.next();
			argType o = (argType) usedobj.pop();
			al.AddToFirst(o);
		}
		usedobj.push(al);
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
		usedobj.push(new stringLiteral());
		return visitChildren(ctx);
	}

	@Override
	public Integer visitIntegerLiteral(Java8Parser.IntegerLiteralContext ctx) {
		usedobj.push(new integerLiteral(ctx.getText()));
		return visitChildren(ctx);
	}

	@Override
	public Integer visitFloatingPointLiteral(Java8Parser.FloatingPointLiteralContext ctx) {
		usedobj.push(new floatingPointLiteral(Double.parseDouble(ctx.getText())));
		return visitChildren(ctx);
	}

	@Override
	public Integer visitBooleanLiteral(Java8Parser.BooleanLiteralContext ctx) {
		usedobj.push(new booleanLiteral(Boolean.parseBoolean(ctx.getText())));
		return visitChildren(ctx);
	}

	@Override
	public Integer visitCharacterLiteral(Java8Parser.CharacterLiteralContext ctx) {
		usedobj.push(new characterLiteral(ctx.getText()));
		return visitChildren(ctx);
	}

	@Override
	public Integer visitNullLiteral(Java8Parser.NullLiteralContext ctx) {
		usedobj.push(new nullLiteral());
		return visitChildren(ctx);
	}

	@Override
	public Integer visitTypeLiteral(TypeLiteralContext ctx) {
		Integer res = visitChildren(ctx);
		type tp = (type) usedobj.pop();
		usedobj.push(new typeLiteral(tp, ctx.getText().trim().substring("class.".length())));
		return res;
	}

	@Override
	public Integer visitType(Java8Parser.TypeContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public Integer visitVirtualInferredType(Java8Parser.VirtualInferredTypeContext ctx) {
		usedobj.push(new virtualInferredType());
		return visitChildren(ctx);
	}

	@Override
	public Integer visitPrimitiveType(Java8Parser.PrimitiveTypeContext ctx) {
		usedobj.push(new primitiveType(ctx.getText()));
		return visitChildren(ctx);
	}

	@Override
	public Integer visitSimpleType(SimpleTypeContext ctx) {
		Integer res = visitChildren(ctx);
		identifier id = (identifier) usedobj.pop();
		usedobj.push(new simpleType(id.getValue()));
		return res;
	}

	@Override
	public Integer visitTypeArgument(TypeArgumentContext ctx) {
		Integer res = visitChildren(ctx);
		type tp = null;
		if (ctx.type() != null) {
			tp = (type) usedobj.pop();
		}
		wildCardType wct = null;
		if (ctx.wildCardType() != null) {
			wct = (wildCardType) usedobj.pop();
		}
		usedobj.push(new typeArgument(tp, wct));
		return res;
	}

	@Override
	public Integer visitTypeArguments(TypeArgumentsContext ctx) {
		Integer res = visitChildren(ctx);
		List<TypeArgumentContext> tasctx = ctx.typeArgument();
		Iterator<TypeArgumentContext> itr = tasctx.iterator();
		typeArguments tas = new typeArguments();
		while (itr.hasNext()) {
			itr.next();
			typeArgument ta = (typeArgument) usedobj.pop();
			tas.AddToFirst(ta);
		}
		usedobj.push(tas);
		return res;
	}

	@Override
	public Integer visitParameterizedType(ParameterizedTypeContext ctx) {
		Integer res = visitChildren(ctx);
		typeArguments tas = (typeArguments) usedobj.pop();
		identifier id = (identifier) usedobj.pop();
		usedobj.push(new parameterizedType(id, tas));
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
			result.add(0, (type) usedobj.pop());
		}
		usedobj.push(new classOrInterfaceType(result));
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
		type tp = (type) usedobj.pop();
		usedobj.push(new arrayType(tp, count));
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
		Object tp = usedobj.pop();
		boolean extended = false;
		if (wb != null) {
			ExtendBoundContext eb = wb.extendBound();
			if (eb != null) {
				extended = true;
			}
		}
		usedobj.push(new wildCardType(extended, (type) tp));
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
		while (itr.hasNext()) {
			itr.next();
			tps.add(0, (type) usedobj.pop());
		}
		// for intersectionFirstType()
		tps.add(0, (type) usedobj.pop());
		usedobj.push(new intersectionType(tps));
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
		while (itr.hasNext()) {
			itr.next();
			tps.add(0, (type) usedobj.pop());
		}
		// for intersectionFirstType()
		tps.add(0, (type) usedobj.pop());
		usedobj.push(new unionType(tps));
		return res;
	}

	/*
	 * @Override public Integer visitIdRawLetter(Java8Parser.IdRawLetterContext
	 * ctx) { usedobj.push(new idRawLetter(ctx.getText())); return
	 * visitChildren(ctx); }
	 */

	@Override
	public Integer visitClassRef(Java8Parser.ClassRefContext ctx) {
		Integer res = visitChildren(ctx);
		integerLiteral offl = (integerLiteral) usedobj.pop();
		integerLiteral scopel = (integerLiteral) usedobj.pop();
		usedobj.push(new classRef(Integer.parseInt(scopel.getValue()), Integer.parseInt(offl.getValue())));
		return res;
	}

	@Override
	public Integer visitFinalFieldRef(Java8Parser.FinalFieldRefContext ctx) {
		Integer res = visitChildren(ctx);
		integerLiteral offl = (integerLiteral) usedobj.pop();
		integerLiteral scopel = (integerLiteral) usedobj.pop();
		usedobj.push(new finalFieldRef(Integer.parseInt(scopel.getValue()), Integer.parseInt(offl.getValue())));
		return res;
	}

	@Override
	public Integer visitFinalVarRef(Java8Parser.FinalVarRefContext ctx) {
		Integer res = visitChildren(ctx);
		integerLiteral offl = (integerLiteral) usedobj.pop();
		integerLiteral scopel = (integerLiteral) usedobj.pop();
		usedobj.push(new finalVarRef(Integer.parseInt(scopel.getValue()), Integer.parseInt(offl.getValue())));
		return res;
	}

	@Override
	public Integer visitCommonFieldRef(Java8Parser.CommonFieldRefContext ctx) {
		Integer res = visitChildren(ctx);
		integerLiteral offl = (integerLiteral) usedobj.pop();
		integerLiteral scopel = (integerLiteral) usedobj.pop();
		usedobj.push(new commonFieldRef(Integer.parseInt(scopel.getValue()), Integer.parseInt(offl.getValue())));
		return res;
	}

	@Override
	public Integer visitCommonVarRef(Java8Parser.CommonVarRefContext ctx) {
		Integer res = visitChildren(ctx);
		integerLiteral offl = (integerLiteral) usedobj.pop();
		integerLiteral scopel = (integerLiteral) usedobj.pop();
		usedobj.push(new commonVarRef(Integer.parseInt(scopel.getValue()), Integer.parseInt(offl.getValue())));
		return res;
	}

	/*
	 * List<IntegerLiteralContext> list = ctx.integerLiteral();
	 * Iterator<IntegerLiteralContext> itr = list.iterator();
	 * IntegerLiteralContext scopelevel = itr.next(); int scope =
	 * Integer.parseInt(scopelevel.getText()); IntegerLiteralContext offsetlevel
	 * = itr.next(); int off = Integer.parseInt(offsetlevel.getText());
	 * usedobj.push(new commonVarRef(scope, off)); return visitChildren(ctx);
	 */

	/*
	 * @Override public Integer
	 * visitThisExpression(Java8Parser.ThisExpressionContext ctx) {
	 * usedobj.push(new ThisExpression()); return null; }
	 */

	/*
	 * @Override public Integer visitOffset(Java8Parser.OffsetContext ctx) {
	 * return visitChildren(ctx); }
	 */

	@Override
	public Integer visitCodeHole(Java8Parser.CodeHoleContext ctx) {
		usedobj.push(new codeHole());
		return visitChildren(ctx);
	}

	@Override
	public Integer visitPreExist(Java8Parser.PreExistContext ctx) {
		usedobj.push(new preExist());
		return visitChildren(ctx);
	}

	@Override
	public Integer visitEndOfArrayDeclarationIndexExpression(
			Java8Parser.EndOfArrayDeclarationIndexExpressionContext ctx) {
		return visitChildren(ctx);
	}

	public statement getSmt() {
		return smt;
	}

	public void setSmt(statement smt) {
		this.smt = smt;
	}

}