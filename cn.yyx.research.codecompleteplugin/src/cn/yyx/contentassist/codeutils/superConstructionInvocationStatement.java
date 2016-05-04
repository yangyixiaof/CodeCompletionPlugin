package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class superConstructionInvocationStatement extends methodInvocationStatement{
	
	argumentList arglist = null;
	
	public superConstructionInvocationStatement(argumentList arglist) {
		this.arglist = arglist;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		return CodeSynthesisHelper.HandleMethodInvocation(squeue, smthandler, arglist, "super", null);
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof superConstructionInvocationStatement)
		{
			if (arglist.CouldThoughtSame(((superConstructionInvocationStatement) t).arglist))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof superConstructionInvocationStatement)
		{
			return 0.4 + 0.6*(arglist.Similarity(((superConstructionInvocationStatement) t).arglist));
		}
		return 0;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}
	
}