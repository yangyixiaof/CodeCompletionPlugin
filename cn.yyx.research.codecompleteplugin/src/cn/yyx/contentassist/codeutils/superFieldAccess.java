package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
