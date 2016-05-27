package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.ErrorCheck;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class commonNewMethodReferenceExpression extends methodReferenceExpression{
	
	referedExpression rexp = null;
	
	public commonNewMethodReferenceExpression(referedExpression rexp) {
		this.rexp = rexp;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, smthandler);
		return CSFlowLineHelper.ConcateOneFlowLineList(null, rels, "::new");
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof commonNewMethodReferenceExpression)
		{
			if (rexp.CouldThoughtSame(((commonNewMethodReferenceExpression) t).rexp))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof commonNewMethodReferenceExpression)
		{
			return 0.4+0.6*(rexp.Similarity(((commonNewMethodReferenceExpression) t).rexp));
		}
		return 0;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleInferredField(CSFlowLineQueue squeue, CSStatementHandler smthandler,
			String reservedword, List<FlowLineNode<CSFlowLineData>> expectedinfer) throws CodeSynthesisException {
		ErrorCheck.NoGenerationCheck("commonNewMethodReferenceExpression should handle inferring field.");
		return null;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleInferredMethodReference(CSFlowLineQueue squeue,
			CSStatementHandler smthandler, String reservedword, List<FlowLineNode<CSFlowLineData>> expectedinfer)
			throws CodeSynthesisException {
		ErrorCheck.NoGenerationCheck("commonNewMethodReferenceExpression should handle inferring MethodReference.");
		return null;
	}
	
}