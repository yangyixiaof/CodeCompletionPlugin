package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class fieldRefNameStatement extends nameStatement {
	
	commonFieldRef cfr = null;
	
	public fieldRefNameStatement(String smtcode, commonFieldRef cfr) {
		super(smtcode);
		this.cfr = cfr;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		return cfr.HandleCodeSynthesis(squeue, smthandler);
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof fieldRefNameStatement)
		{
			if (cfr.CouldThoughtSame(((fieldRefNameStatement) t).cfr))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof fieldRefNameStatement)
		{
			return 0.4+0.6*(cfr.Similarity(((fieldRefNameStatement) t).cfr));
		}
		return 0;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}
	
}