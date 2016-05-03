package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class commonNameStatement extends nameStatement{
	
	identifier id = null;
	
	public commonNameStatement(identifier name) {
		this.id = name;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		return id.HandleCodeSynthesis(squeue, smthandler);
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof commonNameStatement)
		{
			if (id.CouldThoughtSame(((commonNameStatement) t).id))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof commonNameStatement)
		{
			return 0.4 + 0.6*id.Similarity(((commonNameStatement) t).id);
		}
		return 0;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}
	
}