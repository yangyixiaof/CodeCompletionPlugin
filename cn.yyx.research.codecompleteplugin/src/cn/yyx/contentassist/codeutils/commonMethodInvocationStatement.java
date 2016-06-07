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

public class commonMethodInvocationStatement extends methodInvocationStatement{
	
	identifier id = null;
	argumentList arglist = null;
	
	public commonMethodInvocationStatement(String smtcode, identifier name, argumentList argList) {
		super(smtcode);
		this.id = name;
		this.arglist = argList;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof commonMethodInvocationStatement)
		{
			if (id.CouldThoughtSame(((commonMethodInvocationStatement) t).id) || arglist.CouldThoughtSame(((commonMethodInvocationStatement) t).arglist))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof commonMethodInvocationStatement)
		{
			double idsim = id.Similarity(((commonMethodInvocationStatement) t).id);
			double argsim = arglist.Similarity(((commonMethodInvocationStatement) t).arglist);
			return 0.2 + 0.8*(0.5*(idsim) + 0.5*(argsim));
		}
		return 0;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		return CodeSynthesisHelper.HandleMethodInvocation(squeue, smthandler, arglist, null, id, SignalHelper.HasEmBeforeMethod(squeue));
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}
	
}