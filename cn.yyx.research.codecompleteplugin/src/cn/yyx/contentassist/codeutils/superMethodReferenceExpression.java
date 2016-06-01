package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.ErrorCheck;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class superMethodReferenceExpression extends methodReferenceExpression{
	
	identifier id = null;
	referedExpression rexp = null; // warning: rexp could be null.
	
	public superMethodReferenceExpression(identifier id, referedExpression rexp) {
		this.id = id;
		this.rexp = rexp;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> idls = id.HandleCodeSynthesis(squeue, smthandler);
		if (rexp == null)
		{
			List<FlowLineNode<CSFlowLineData>> sups = new LinkedList<FlowLineNode<CSFlowLineData>>();
			sups.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), "super", null, null, squeue.GetLastHandler()), 0));
			List<FlowLineNode<CSFlowLineData>> ls = CodeSynthesisHelper.HandleFieldSpecificationInfer(sups, idls, squeue, smthandler, "::");
			/*if (ls == null || ls.size() == 0)
			{
				return CSFlowLineHelper.ConcateOneFlowLineList("super::", idls, null);
			}*/
			return ls;
		}
		else
		{
			/*List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, smthandler);
			rels = CSFlowLineHelper.ConcateOneFlowLineList(null, rels, ".super");
			List<FlowLineNode<CSFlowLineData>> ls = CodeSynthesisHelper.HandleFieldSpecificationInfer(rels, idls, squeue, smthandler, "::");
			if (ls == null || ls.size() == 0)
			{
				return CSFlowLineHelper.ForwardConcate(null, rels, "::", idls, null, squeue, smthandler, null);
			}
			return ls;*/
			return rexp.HandleInferredMethodReference(squeue, smthandler, "super", idls);
		}
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof superMethodReferenceExpression)
		{
			if (id.CouldThoughtSame(((superMethodReferenceExpression) t).id) || rexp.CouldThoughtSame(((superMethodReferenceExpression) t).rexp))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof superMethodReferenceExpression)
		{
			return 0.2+0.4*(id.Similarity(((superMethodReferenceExpression) t).id))+0.4*(rexp.Similarity(((superMethodReferenceExpression) t).rexp));
		}
		return 0;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleInferredField(CSFlowLineQueue squeue, CSStatementHandler smthandler,
			String reservedword, List<FlowLineNode<CSFlowLineData>> expectedinfer) throws CodeSynthesisException {
		ErrorCheck.NoGenerationCheck("superMethodReferenceExpression should handle inferring field.");
		return null;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleInferredMethodReference(CSFlowLineQueue squeue,
			CSStatementHandler smthandler, String reservedword, List<FlowLineNode<CSFlowLineData>> expectedinfer)
			throws CodeSynthesisException {
		ErrorCheck.NoGenerationCheck("superMethodReferenceExpression should handle inferring MethodReference.");
		return null;
	}
	
}