package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class postfixExpressionStatement extends expressionStatement{
	
	referedExpression rexp = null;
	String optr = null;
	
	public postfixExpressionStatement(String smtcode, referedExpression rexp, String optr) {
		super(smtcode);
		this.rexp = rexp;
		this.optr = optr;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof postfixExpressionStatement)
		{
			if (optr.equals(((postfixExpressionStatement) t).optr))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof postfixExpressionStatement)
		{
			if (optr.equals(((postfixExpressionStatement) t).optr))
			{
				return 1;
			}
		}
		return 0;
	}
	
	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode cs = new CSNode(CSNodeType.WholeStatement);
		boolean conflict = rexp.HandleCodeSynthesis(squeue, expected, handler, cs, ai);
		cs.setPostfix(optr);
		squeue.add(cs);
		return conflict;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, smthandler);
		
		return CSFlowLineHelper.ConcateOneFlowLineList(null, rels, optr);
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}
	
}