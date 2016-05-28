package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class instanceofExpressionStatement extends expressionStatement{
	
	referedExpression rexp = null;
	type type = null;
	String typecode = null;
	
	public instanceofExpressionStatement(String smtcode, String typecode, referedExpression rexp, type type) {
		super(smtcode);
		this.typecode = typecode;
		this.rexp = rexp;
		this.type = type;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof instanceofExpressionStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof instanceofExpressionStatement)
		{
			return 0.4 + 0.6*(0.5*rexp.Similarity(((instanceofExpressionStatement) t).rexp) + 0.5*type.Similarity(((instanceofExpressionStatement) t).type));
		}
		return 0;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, smthandler);
		List<FlowLineNode<CSFlowLineData>> tpls = type.HandleCodeSynthesis(squeue, smthandler);
		List<FlowLineNode<CSFlowLineData>> res = CSFlowLineHelper.ForwardConcate(null, rels, " instanceof ", tpls, null, squeue, smthandler, null, null);
		if (res == null || res.size() == 0)
		{
			if (res != null && res.size() > 0)
			{
				return CSFlowLineHelper.ConcateOneFlowLineList(null, rels, " instanceof " + typecode);
			}
		}
		return res;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}
	
}