package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class typeCreationInvocationStatement extends methodInvocationStatement{
	
	type tp = null;
	argumentList arglist = null;
	
	public typeCreationInvocationStatement(type tp, argumentList argList) {
		this.tp = tp;
		this.arglist = argList;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		return CodeSynthesisHelper.HandleMethodInvocation(squeue, smthandler, arglist, null, tp);
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof typeCreationInvocationStatement)
		{
			if (tp.CouldThoughtSame(((typeCreationInvocationStatement) t).tp) || arglist.CouldThoughtSame(((typeCreationInvocationStatement) t).arglist))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof typeCreationInvocationStatement)
		{
			return 0.2 + 0.4*tp.Similarity(((typeCreationInvocationStatement) t).tp) + 0.4*(arglist.Similarity(((typeCreationInvocationStatement) t).arglist));
		}
		return 0;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}

}