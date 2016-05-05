package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class typeLiteral extends literal{
	
	type tp = null;
	
	public typeLiteral(type tp) {
		this.tp = tp;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> tpls = tp.HandleCodeSynthesis(squeue, smthandler);
		return CSFlowLineHelper.ConcateOneFlowLineList(null, tpls, ".class");
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof typeLiteral)
		{
			if (tp.CouldThoughtSame(((typeLiteral) t).tp))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof typeLiteral)
		{
			return 0.4 + 0.6*(tp.Similarity(((typeLiteral) t).tp));
		}
		return 0;
	}

	@Override
	public void HandleNegativeOperator() {
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