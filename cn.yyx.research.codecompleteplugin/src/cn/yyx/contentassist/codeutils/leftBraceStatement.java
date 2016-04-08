package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSEnterParamInfoData;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;

public class leftBraceStatement extends statement{
	
	int times = 0;
	
	public leftBraceStatement(int count) {
		this.times = count;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof leftBraceStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof leftBraceStatement)
		{
			return 1;
		}
		return 0;
	}

	/*@Override
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		cstack.pop();
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		squeue.SetLastHasHole();
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		result.add(new FlowLineNode<CSFlowLineData>(new CSEnterParamInfoData(times, squeue.GenerateNewNodeId(), smthandler.getSete(), "{", null, null, true, squeue.GetLastHandler()), smthandler.getProb()));
		return result;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}
	
}