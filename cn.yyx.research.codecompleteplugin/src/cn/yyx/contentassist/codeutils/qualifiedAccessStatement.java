package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class qualifiedAccessStatement extends expressionStatement{
	
	fieldAccess fa = null;
	
	public qualifiedAccessStatement(fieldAccess fa) {
		this.fa = fa;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		return fa.HandleCodeSynthesis(squeue, smthandler);
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof qualifiedAccessStatement)
		{
			return fa.CouldThoughtSame(((qualifiedAccessStatement) t).fa);
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof qualifiedAccessStatement)
		{
			return 0.4+0.6*(fa.Similarity(((qualifiedAccessStatement) t).fa));
		}
		return 0;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}

}