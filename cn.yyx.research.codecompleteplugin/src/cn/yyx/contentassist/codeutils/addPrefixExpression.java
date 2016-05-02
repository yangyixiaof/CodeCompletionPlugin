package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class addPrefixExpression extends referedExpression{
	
	private referedExpression rexp = null;
	
	public addPrefixExpression(referedExpression rexp) {
		this.setRexp(rexp);
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> result = rexp.HandleCodeSynthesis(squeue, smthandler);
		return CSFlowLineHelper.ConcateOneFlowLineList("+", result, null);
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof addPrefixExpression)
		{
			if (rexp.CouldThoughtSame(((addPrefixExpression) t).rexp))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof addPrefixExpression)
		{
			return 0.3+0.7*(rexp.Similarity(((addPrefixExpression) t).rexp));
		}
		return 0;
	}

	public referedExpression getRexp() {
		return rexp;
	}

	public void setRexp(referedExpression rexp) {
		this.rexp = rexp;
	}
	
}