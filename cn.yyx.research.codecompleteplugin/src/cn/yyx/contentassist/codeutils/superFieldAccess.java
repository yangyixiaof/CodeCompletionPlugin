package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class superFieldAccess extends fieldAccess{
	
	identifier id = null;
	referedExpression rexp = null;
	type tp = null;
	// rexp and tp only one can be not null or both null.
	
	public superFieldAccess(identifier name, referedExpression rexp, type tp) {
		this.id = name;
		this.rexp = rexp;
		this.tp = tp;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		if (tp != null && rexp == null)
		{
			List<FlowLineNode<CSFlowLineData>> idls = id.HandleCodeSynthesis(squeue, smthandler);
			List<FlowLineNode<CSFlowLineData>> tpls = tp.HandleCodeSynthesis(squeue, smthandler);
			return CSFlowLineHelper.ForwardConcate(null, tpls, ".super.", idls, null, squeue, smthandler, null);
		}
		if (tp == null && rexp != null)
		{
			List<FlowLineNode<CSFlowLineData>> idls = id.HandleCodeSynthesis(squeue, smthandler);
			List<FlowLineNode<CSFlowLineData>> rexpls = rexp.HandleCodeSynthesis(squeue, smthandler);
			return CSFlowLineHelper.ForwardConcate(null, rexpls, ".super.", idls, null, squeue, smthandler, null);
		}
		List<FlowLineNode<CSFlowLineData>> idls = id.HandleCodeSynthesis(squeue, smthandler);
		return CSFlowLineHelper.ConcateOneFlowLineList("super.", idls, null);
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof superFieldAccess)
		{
			if (tp == null && rexp == null)
			{
				if (id.CouldThoughtSame(((superFieldAccess) t).id))
				{
					return true;
				}
			}
			if (tp != null && rexp == null)
			{
				if (tp.CouldThoughtSame(((superFieldAccess) t).tp))
				{
					return true;
				}
			}
			if (tp == null && rexp != null)
			{
				if (rexp.CouldThoughtSame(((superFieldAccess) t).rexp))
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof superFieldAccess)
		{
			if (tp == null && rexp == null)
			{
				return 0.4+0.6*(id.Similarity(((superFieldAccess) t).id));
			}
			if (tp != null && rexp == null)
			{
				return 0.2+0.4*(id.Similarity(((superFieldAccess) t).id))+0.4*(tp.Similarity(((superFieldAccess) t).tp));
			}
			if (tp == null && rexp != null)
			{
				return 0.2+0.4*(id.Similarity(((superFieldAccess) t).id))+0.4*(rexp.Similarity(((superFieldAccess) t).rexp));
			}
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