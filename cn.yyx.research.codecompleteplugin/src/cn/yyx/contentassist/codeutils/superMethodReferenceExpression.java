package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
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
			return CSFlowLineHelper.ConcateOneFlowLineList("super::", idls, null);
		}
		else
		{
			List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, smthandler);
			return CSFlowLineHelper.ForwardMerge(null, rels, ".super::", idls, null, squeue, smthandler, null, null);
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
	
}