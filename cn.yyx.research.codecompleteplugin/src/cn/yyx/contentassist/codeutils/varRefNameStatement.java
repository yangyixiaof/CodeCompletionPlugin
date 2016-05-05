package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class varRefNameStatement extends nameStatement{
	
	commonVarRef cvr = null;
	
	public varRefNameStatement(commonVarRef cvr) {
		this.cvr = cvr;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		return cvr.HandleCodeSynthesis(squeue, smthandler);
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof varRefNameStatement)
		{
			if (cvr.CouldThoughtSame(((varRefNameStatement) t).cvr))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof varRefNameStatement)
		{
			return 0.3 + 0.7*(cvr.Similarity(((varRefNameStatement) t).cvr));
		}
		return 0;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}

}