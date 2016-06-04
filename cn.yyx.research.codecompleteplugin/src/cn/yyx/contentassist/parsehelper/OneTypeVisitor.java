package cn.yyx.contentassist.parsehelper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.codeutils.addPrefixExpression;
import cn.yyx.contentassist.codeutils.argType;
import cn.yyx.contentassist.codeutils.argTypeList;
import cn.yyx.contentassist.codeutils.argumentList;
import cn.yyx.contentassist.codeutils.arrayType;
import cn.yyx.contentassist.codeutils.booleanLiteral;
import cn.yyx.contentassist.codeutils.chainFieldAccess;
import cn.yyx.contentassist.codeutils.characterLiteral;
import cn.yyx.contentassist.codeutils.classOrInterfaceType;
import cn.yyx.contentassist.codeutils.classRef;
import cn.yyx.contentassist.codeutils.codeHole;
import cn.yyx.contentassist.codeutils.commonClassMemberInvoke;
import cn.yyx.contentassist.codeutils.commonFieldRef;
import cn.yyx.contentassist.codeutils.commonMethodReferenceExpression;
import cn.yyx.contentassist.codeutils.commonNewMethodReferenceExpression;
import cn.yyx.contentassist.codeutils.commonVarRef;
import cn.yyx.contentassist.codeutils.fieldAccess;
import cn.yyx.contentassist.codeutils.finalFieldRef;
import cn.yyx.contentassist.codeutils.finalVarRef;
import cn.yyx.contentassist.codeutils.firstArg;
import cn.yyx.contentassist.codeutils.firstArgReferedExpression;
import cn.yyx.contentassist.codeutils.floatingPointLiteral;
import cn.yyx.contentassist.codeutils.identifier;
import cn.yyx.contentassist.codeutils.integerLiteral;
import cn.yyx.contentassist.codeutils.intersectionType;
import cn.yyx.contentassist.codeutils.lastArgType;
import cn.yyx.contentassist.codeutils.newClassInvoke;
import cn.yyx.contentassist.codeutils.nullLiteral;
import cn.yyx.contentassist.codeutils.parameterizedType;
import cn.yyx.contentassist.codeutils.preExist;
import cn.yyx.contentassist.codeutils.primitiveType;
import cn.yyx.contentassist.codeutils.referedExpression;
import cn.yyx.contentassist.codeutils.referedFieldAccess;
import cn.yyx.contentassist.codeutils.selfClassMemberInvoke;
import cn.yyx.contentassist.codeutils.simpleType;
import cn.yyx.contentassist.codeutils.stringLiteral;
import cn.yyx.contentassist.codeutils.subPrefixExpression;
import cn.yyx.contentassist.codeutils.superClassMemberInvoke;
import cn.yyx.contentassist.codeutils.superFieldAccess;
import cn.yyx.contentassist.codeutils.superMethodReferenceExpression;
import cn.yyx.contentassist.codeutils.thisFieldAccess;
import cn.yyx.contentassist.codeutils.type;
import cn.yyx.contentassist.codeutils.typeArgument;
import cn.yyx.contentassist.codeutils.typeArguments;
import cn.yyx.contentassist.codeutils.typeLiteral;
import cn.yyx.contentassist.codeutils.unionType;
import cn.yyx.contentassist.codeutils.virtualInferredType;
import cn.yyx.contentassist.codeutils.wildCardType;
import cn.yyx.parse.szparse8java.Java8BaseVisitor;
import cn.yyx.parse.szparse8java.Java8Parser;
import cn.yyx.parse.szparse8java.Java8Parser.AddInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.AddPrefixExpressionContext;
import cn.yyx.parse.szparse8java.Java8Parser.AddassignAssignmentStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.AndInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.AndassignAssignmentStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.ArgTypeContext;
import cn.yyx.parse.szparse8java.Java8Parser.ArgTypeListContext;
import cn.yyx.parse.szparse8java.Java8Parser.AssignAssignmentStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.BitandInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.BitorInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.BothTypeContext;
import cn.yyx.parse.szparse8java.Java8Parser.CaretInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.ChainFieldAccessContext;
import cn.yyx.parse.szparse8java.Java8Parser.CommonClassMemberInvokeContext;
import cn.yyx.parse.szparse8java.Java8Parser.CommonMethodReferenceExpressionContext;
import cn.yyx.parse.szparse8java.Java8Parser.CommonNewMethodReferenceExpressionContext;
import cn.yyx.parse.szparse8java.Java8Parser.DivInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.DivassignAssignmentStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.EqualInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.ExtendBoundContext;
import cn.yyx.parse.szparse8java.Java8Parser.FirstArgReferedExpressionContext;
import cn.yyx.parse.szparse8java.Java8Parser.GeInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.GtInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.IntersectionFirstTypeContext;
import cn.yyx.parse.szparse8java.Java8Parser.IntersectionSecondTypeContext;
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
import cn.yyx.parse.szparse8java.Java8Parser.ReferedExpressionContext;
import cn.yyx.parse.szparse8java.Java8Parser.ReferedFieldAccessContext;
import cn.yyx.parse.szparse8java.Java8Parser.RshiftInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.RshiftassignAssignmentStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.SimpleTypeContext;
import cn.yyx.parse.szparse8java.Java8Parser.SubInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.SubPrefixExpressionContext;
import cn.yyx.parse.szparse8java.Java8Parser.SubassignAssignmentStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.SuperFieldAccessContext;
import cn.yyx.parse.szparse8java.Java8Parser.SuperMethodReferenceExpressionContext;
import cn.yyx.parse.szparse8java.Java8Parser.ThisFieldAccessContext;
import cn.yyx.parse.szparse8java.Java8Parser.TypeArgumentContext;
import cn.yyx.parse.szparse8java.Java8Parser.TypeArgumentsContext;
import cn.yyx.parse.szparse8java.Java8Parser.TypeContext;
import cn.yyx.parse.szparse8java.Java8Parser.TypeLiteralContext;
import cn.yyx.parse.szparse8java.Java8Parser.UnionFirstTypeContext;
import cn.yyx.parse.szparse8java.Java8Parser.UnionSecondTypeContext;
import cn.yyx.parse.szparse8java.Java8Parser.UrshiftInfixExpressionStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.UrshiftassignAssignmentStatementContext;
import cn.yyx.parse.szparse8java.Java8Parser.WildcardBoundsContext;
import cn.yyx.parse.szparse8java.Java8Parser.XorassignAssignmentStatementContext;

public class OneTypeVisitor extends Java8BaseVisitor<Integer> {
	
	private type tp = null;
	
	private Stack<Object> usedobj = new Stack<Object>();

	@Override
	public Integer visitStatement(Java8Parser.StatementContext ctx) {
		return visitChildren(ctx);
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
	
	private void AssignmentStatementHandler(String smtcode, String optr)
	{
		// Object right = usedobj.pop();
		// Object left = usedobj.pop();
		// smt = new assignmentStatement(smtcode, (referedExpression) left, optr, (referedExpression) right);
	}
	
	@Override
	public Integer visitAssignmentStatement(Java8Parser.AssignmentStatementContext ctx) {
		return visitChildren(ctx);
	}
	
	@Override
	public Integer visitLiteralStatement(Java8Parser.LiteralStatementContext ctx) {
		return visitChildren(ctx);
	}
	
	@Override
	public Integer visitMethodInvocationStatement(Java8Parser.MethodInvocationStatementContext ctx) {
		return visitChildren(ctx);
	}
	
	@Override
	public Integer visitChainFieldAccess(ChainFieldAccessContext ctx) {
		Integer res = visitChildren(ctx);
		fieldAccess fa = (fieldAccess)usedobj.pop();
		identifier id = (identifier)usedobj.pop();
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
		if (rexpctx != null)
		{
			rexp = (referedExpression) usedobj.pop();
		}
		if (tpctx != null)
		{
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
		if (rexpctx != null)
		{
			rexp = (referedExpression) usedobj.pop();
		}
		if (tpctx != null)
		{
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
	
	private void InfixExpressionStatementHandler(String smtcode, String optr)
	{
		// Object rexp = usedobj.pop();
		// Object lexp = usedobj.pop();
		// smt = new infixExpressionStatement(smtcode, (referedExpression) lexp, optr, (referedExpression) rexp);
	}
	
	@Override
	public Integer visitInfixExpressionStatement(Java8Parser.InfixExpressionStatementContext ctx) {
		return visitChildren(ctx);
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
		if (ctx.referedExpression() != null)
		{
			rexp = (referedExpression) usedobj.pop();
		}
		identifier name = (identifier) usedobj.pop();
		usedobj.push(new superMethodReferenceExpression(name, rexp));
		return res;
	}
	
	@Override
	public Integer visitNameStatement(Java8Parser.NameStatementContext ctx) {
		return visitChildren(ctx);
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
	public Integer visitPrefixExpressionStatement(Java8Parser.PrefixExpressionStatementContext ctx) {
		return visitChildren(ctx);
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
		if (rexpctx != null)
		{
			rexp = (referedExpression) usedobj.pop();
		}
		type tp = null;
		TypeContext tpctx = ctx.type();
		if (tpctx != null)
		{
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
		if (rexpctx != null)
		{
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
		if (rexpctx != null)
		{
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
		if (rexpctx != null)
		{
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
		type tpa = (type) usedobj.pop();
		usedobj.add(new lastArgType(tpa));
		tp = (type) usedobj.peek();
		return res;
	}

	/*@Override
	public Integer visitTypeList(Java8Parser.TypeListContext ctx) {
		Integer res = visitChildren(ctx);
		typeList al = new typeList();
		List<TypeContext> rl = ctx.type();
		Iterator<TypeContext> itr = rl.iterator();
		while (itr.hasNext()) {
			itr.next();
			Object o = usedobj.pop();
			al.AddToFirst((type) o);
		}
		usedobj.push(al);
		return res;
	}*/
	
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
		/*LastArgTypeContext latctx = ctx.lastArgType();
		lastArgType lat = null;
		if (latctx != null)
		{
			lat = (lastArgType) usedobj.pop();
		}*/
		lastArgType lat = null;
		LastArgTypeContext lastArgTypeCtx = ctx.lastArgType();
		if (lastArgTypeCtx != null)
		{
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
		Integer res = visitChildren(ctx);
		tp = (type) usedobj.peek();
		return res;
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
		if (ctx.type() != null)
		{
			tp =(type) usedobj.pop() ;
		}
		wildCardType wct = null;
		if (ctx.wildCardType() != null)
		{
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
		while (itr.hasNext())
		{
			itr.next(); // TypeArgumentContext tac = 
			Object taobj = usedobj.pop();
			
			/*System.err.println("tas:" + ctx.getText());
			System.err.println("tacs:" + tac.getText());
			System.err.println("taobj:" + taobj.getClass());
			if (taobj instanceof identifier)
			{
				System.out.println("taid:"+(((identifier) taobj).getValue()));
			}*/
			
			typeArgument ta = (typeArgument) taobj;
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
			// important, the handle in code completion environment is opposite as the handle of program-process.
			result.add((type)usedobj.pop());
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
		Object tppara = null;
		WildcardBoundsContext wb = ctx.wildcardBounds();
		boolean extended = false;
		if (wb != null)
		{
			tppara = usedobj.pop();
			ExtendBoundContext eb = wb.extendBound();
			if (eb != null)
			{
				extended = true;
			}
		}
		usedobj.push(new wildCardType(extended, (type)tppara));
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
			tps.add(0, (type)usedobj.pop());
		}
		// for intersectionFirstType()
		tps.add(0, (type)usedobj.pop());
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
		while (itr.hasNext())
		{
			itr.next();
			tps.add(0, (type)usedobj.pop());
		}
		// for intersectionFirstType()
		tps.add(0, (type)usedobj.pop());
		usedobj.push(new unionType(tps));
		return res;
	}
	
	/*@Override
	public Integer visitIdRawLetter(Java8Parser.IdRawLetterContext ctx) {
		usedobj.push(new idRawLetter(ctx.getText()));
		return visitChildren(ctx);
	}*/

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
	 * IntegerLiteralContext scopelevel = itr.next();
	 * int scope = Integer.parseInt(scopelevel.getText());
	 * IntegerLiteralContext offsetlevel = itr.next();
	 * int off = Integer.parseInt(offsetlevel.getText());
	 * usedobj.push(new commonVarRef(scope, off));
	 * return visitChildren(ctx);
	 */
	
	/*@Override
	public Integer visitThisExpression(Java8Parser.ThisExpressionContext ctx)
	{
		usedobj.push(new ThisExpression());
		return null;
	}*/
	
	/*@Override
	public Integer visitOffset(Java8Parser.OffsetContext ctx) {
		return visitChildren(ctx);
	}*/

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

	public type getTp() {
		return tp;
	}

	public void setTp(type tp) {
		this.tp = tp;
	}

}