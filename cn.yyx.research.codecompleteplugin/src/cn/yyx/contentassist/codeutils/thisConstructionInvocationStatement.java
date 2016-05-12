package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.commonutils.SignalHelper;

public class thisConstructionInvocationStatement extends methodInvocationStatement{
	
	argumentList arglist = null;
	
	public thisConstructionInvocationStatement(String smtcode, argumentList argList) {
		super(smtcode);
		this.arglist = argList;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		return CodeSynthesisHelper.HandleMethodInvocation(squeue, smthandler, arglist, "this", null, SignalHelper.HasEmBeforeMethod(squeue));
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof thisConstructionInvocationStatement)
		{
			if (arglist.CouldThoughtSame(((thisConstructionInvocationStatement) t).arglist))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof thisConstructionInvocationStatement)
		{
			return 0.4 + 0.6*(arglist.Similarity(((thisConstructionInvocationStatement) t).arglist));
		}
		return 0;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}

}