package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class thisFieldAccess extends fieldAccess {
	
	referedExpression rexp = null;
	type tp = null;
	// rexp and tp only one can be not null or both null.
	
	public thisFieldAccess(referedExpression rexp, type tp) {
		this.rexp = rexp;
		this.tp = tp;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		if (rexp != null)
		{
			List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, smthandler);
			return CSFlowLineHelper.ConcateOneFlowLineList(null, rels, ".this");
		}
		if (tp != null)
		{
			List<FlowLineNode<CSFlowLineData>> rels = tp.HandleCodeSynthesis(squeue, smthandler);
			return CSFlowLineHelper.ConcateOneFlowLineList(null, rels, ".this");
		}
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), "this", null, null, squeue.GetLastHandler()), smthandler.getProb()));
		return result;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof thisFieldAccess)
		{
			if (rexp != null)
			{
				return rexp.CouldThoughtSame(((thisFieldAccess) t).rexp);
			}
			if (tp != null)
			{
				return tp.CouldThoughtSame(((thisFieldAccess) t).tp);
			}
			if (((thisFieldAccess) t).tp == null && ((thisFieldAccess) t).rexp == null)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof thisFieldAccess)
		{
			if (rexp != null)
			{
				return 0.4 + 0.6*rexp.Similarity(((thisFieldAccess) t).rexp);
			}
			if (tp != null)
			{
				return 0.4 + 0.6*tp.Similarity(((thisFieldAccess) t).tp);
			}
			if (((thisFieldAccess) t).tp == null && ((thisFieldAccess) t).rexp == null)
			{
				return 1;
			}
			return 0.4;
		}
		return 0;
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
