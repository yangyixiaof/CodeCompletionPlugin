package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class firstArgReferedExpression extends referedExpression{
	
	referedExpression rexp = null;
	type tp = null; // warning: rexp and tp and not both be noull.
	
	public firstArgReferedExpression(referedExpression rexp, type tp) {
		this.rexp = rexp;
		this.tp = tp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof firstArgReferedExpression)
		{
			if (rexp != null)
			{
				if (rexp.CouldThoughtSame(((firstArgReferedExpression) t).rexp))
				{
					return true;
				}
			}
			else
			{
				if (tp.CouldThoughtSame(((firstArgReferedExpression) t).tp))
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof firstArgReferedExpression)
		{
			if (rexp != null)
			{
				return 0.4+0.6*(rexp.Similarity(((firstArgReferedExpression) t).rexp));
			}
			else
			{
				return 0.4+0.6*(tp.Similarity(((firstArgReferedExpression) t).tp));
			}
		}
		return 0;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		if (rexp != null)
		{
			return rexp.HandleCodeSynthesis(squeue, smthandler);
		} else {
			return tp.HandleCodeSynthesis(squeue, smthandler);
		}
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleInferredField(CSFlowLineQueue squeue, CSStatementHandler smthandler,
			String reservedword, List<FlowLineNode<CSFlowLineData>> expectedinfer) throws CodeSynthesisException {
		return CodeSynthesisHelper.HandleInferredField(HandleCodeSynthesis(squeue, smthandler), squeue, smthandler, reservedword, expectedinfer);
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleInferredMethodReference(CSFlowLineQueue squeue,
			CSStatementHandler smthandler, String reservedword, List<FlowLineNode<CSFlowLineData>> expectedinfer)
			throws CodeSynthesisException {
		return CodeSynthesisHelper.HandleInferredMethodReference(HandleCodeSynthesis(squeue, smthandler), squeue, smthandler, reservedword, expectedinfer);
	}
	
}