package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class commonClassMemberInvoke extends classInvoke{
	
	referedExpression rexp = null;
	
	public commonClassMemberInvoke(referedExpression rexp) {
		this.rexp = rexp;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		return CodeSynthesisHelper.HandleClassInvokeCodeSynthesis(squeue, smthandler, rexp, null);
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof commonClassMemberInvoke)
		{
			if (rexp.CouldThoughtSame(((commonClassMemberInvoke) t).rexp))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof commonClassMemberInvoke)
		{
			return 0.3+0.7*(rexp.Similarity(((commonClassMemberInvoke) t).rexp));
		}
		return 0;
	}
	
}