package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class referedFieldAccess extends fieldAccess {

	identifier id = null;
	referedExpression rexp = null;
	
	public referedFieldAccess(identifier name, referedExpression rexp) {
		this.id = name;
		this.rexp = rexp;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		return CodeSynthesisHelper.HandleFieldAccess(squeue, smthandler, rexp, null, null, id);
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof referedFieldAccess)
		{
			if (id.CouldThoughtSame(((referedFieldAccess) t).id) || rexp.CouldThoughtSame(((referedFieldAccess) t).rexp))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof chainFieldAccess)
		{
			if (id.CouldThoughtSame(((referedFieldAccess) t).id) || rexp.CouldThoughtSame(((referedFieldAccess) t).rexp))
			{
				return 0.2 + 0.4*(id.Similarity(((referedFieldAccess) t).id)) + 0.4*(rexp.Similarity(((referedFieldAccess) t).rexp));
			}
		}
		return 0;
	}
	
}