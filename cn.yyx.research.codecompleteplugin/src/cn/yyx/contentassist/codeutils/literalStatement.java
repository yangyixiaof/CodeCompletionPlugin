package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;

public class literalStatement extends expressionStatement{
	
	literal lt = null;
	
	public literalStatement(literal lt) {
		this.lt = lt;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof literalStatement)
		{
			return lt.CouldThoughtSame(((literalStatement) t).lt);
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof literalStatement)
		{
			return lt.Similarity(((literalStatement) t).lt);
		}
		return 0;
	}
	
	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode fcs = new CSNode(CSNodeType.HalfFullExpression);
		lt.HandleCodeSynthesis(squeue, expected, handler, fcs, ai);
		squeue.add(fcs);
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		return lt.HandleCodeSynthesis(squeue, smthandler);
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}
	
}