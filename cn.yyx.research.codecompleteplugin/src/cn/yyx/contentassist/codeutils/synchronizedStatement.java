package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;

public class synchronizedStatement extends statement{
	
	referedExpression rexp = null;
	
	public synchronizedStatement(referedExpression rexp) {
		this.rexp = rexp;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof synchronizedStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof synchronizedStatement)
		{
			return 0.6 + 0.4*(rexp.Similarity(((synchronizedStatement) t).rexp));
		}
		return 0;
	}
	
	/*@Override
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		Integer signal = cstack.peek();
		if (signal != StructureSignalMetaInfo.AllKindWaitingOver)
		{
			return true;
		}
		cstack.pop();
		return false;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode fcs = new CSNode(CSNodeType.WholeStatement);
		boolean conflict = rexp.HandleCodeSynthesis(squeue, expected, handler, fcs, ai);
		if (conflict)
		{
			return true;
		}
		fcs.setPrefix("");
		fcs.setPostfix("");
		squeue.add(fcs);
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, smthandler);
		return CSFlowLineHelper.ConcateOneFlowLineNodeList("synchronized (", rels, "){\n\n}");
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		cstack.EnsureAllSignalNull();
		return true;
	}
	
}