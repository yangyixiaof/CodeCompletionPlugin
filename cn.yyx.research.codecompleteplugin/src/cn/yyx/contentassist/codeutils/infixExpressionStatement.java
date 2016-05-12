package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputer;

public class infixExpressionStatement extends expressionStatement{
	
	referedExpression left = null;
	referedExpression right = null;
	String optr = null;
	
	public infixExpressionStatement(String smtcode, referedExpression lexp, String optr, referedExpression rexp) {
		super(smtcode);
		this.left = lexp;
		this.right = rexp;
		this.optr = optr;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof infixExpressionStatement)
		{
			if (optr.equals(((infixExpressionStatement) t).optr))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof infixExpressionStatement)
		{
			if (optr.equals(((infixExpressionStatement) t).optr))
			{
				return 0.5 + 0.5*(0.5*(right.Similarity(((infixExpressionStatement) t).right)) + 0.5*(left.Similarity(((infixExpressionStatement) t).left)));
			}
		}
		return 0;
	}
	
	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode ltcs = new CSNode(CSNodeType.HalfFullExpression);
		boolean conflict = left.HandleCodeSynthesis(squeue, expected, handler, ltcs, ai);
		if (conflict)
		{
			return true;
		}
		ltcs.setPostfix(optr);
		squeue.add(ltcs);
		CSNode rtcs = new CSNode(CSNodeType.HalfFullExpression);
		conflict = right.HandleCodeSynthesis(squeue, expected, handler, rtcs, ai);
		if (conflict)
		{
			return true;
		}
		squeue.add(rtcs);
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> leftls = left.HandleCodeSynthesis(squeue, smthandler);
		List<FlowLineNode<CSFlowLineData>> rightls = right.HandleCodeSynthesis(squeue, smthandler);
		TypeComputationKind tc = TypeComputer.ComputeKindFromRawString(optr);
		return CSFlowLineHelper.ForwardMerge(null, leftls, optr, rightls, null, squeue, smthandler, tc, tc);
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}
	
}