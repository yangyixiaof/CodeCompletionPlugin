package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSMethodReferenceStatementHandler;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class commonMethodReferenceExpression extends methodReferenceExpression{
	
	identifier id = null;
	referedExpression rexp = null;
	
	public commonMethodReferenceExpression(identifier id, referedExpression rexp) {
		this.id = id;
		this.rexp = rexp;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> idls = id.HandleCodeSynthesis(squeue, smthandler);
		CSMethodReferenceStatementHandler csmrsh = new CSMethodReferenceStatementHandler(idls.get(0).getData().getData(), smthandler);
		List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, csmrsh);
		if (!(csmrsh.isFieldused()))
		{
			List<FlowLineNode<CSFlowLineData>> ls = CodeSynthesisHelper.HandleFieldSpecificationInfer(rels, idls, squeue, smthandler, "::");
			if (ls.size() == 0)
			{
				return CSFlowLineHelper.ForwardMerge(null, rels, "::", idls, null, squeue, smthandler, null, null);
			}
			return ls;
		}
		return rels;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof commonMethodReferenceExpression)
		{
			if (id.CouldThoughtSame(((commonMethodReferenceExpression) t).id) || rexp.CouldThoughtSame(((commonMethodReferenceExpression) t).rexp))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof commonMethodReferenceExpression)
		{
			return 0.2+0.4*(id.Similarity(((commonMethodReferenceExpression) t).id))+0.4*(rexp.Similarity(((commonMethodReferenceExpression) t).rexp));
		}
		return 0;
	}
	
}