package cn.yyx.contentassist.codeutils;

import java.util.List;
import java.util.Map;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.ErrorCheck;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.MethodTypeSignature;

public class commonClassMemberInvoke extends firstArg{
	
	firstArgReferedExpression rexp = null;
	
	public commonClassMemberInvoke(firstArgReferedExpression rexp) {
		this.rexp = rexp;
	}

	@Override
	@Deprecated
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		ErrorCheck.NoGenerationCheck("commonClassMemberInvoke should not invoke HandleCodeSynthesis, but HandleClassOrMethodInvoke instead.");
		// CodeSynthesisHelper.HandleClassInvokeCodeSynthesis(squeue, smthandler, rexp, null)
		return null;
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

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleClassOrMethodInvoke(CSFlowLineQueue squeue,
			CSStatementHandler smthandler, String methodname, Map<String, MethodTypeSignature> mts)
			throws CodeSynthesisException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlowLineNode<CSFlowLineData> MostReachedFar() throws CodeSynthesisException {
		// TODO Auto-generated method stub
		return null;
	}
	
}