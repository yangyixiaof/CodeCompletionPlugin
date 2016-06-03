package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.ErrorCheck;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class anonymousClassPreOverStatement extends statement {
	
	identifier id = null;
	
	public anonymousClassPreOverStatement(String smtcode, identifier id) {
		super(smtcode);
		this.id = id;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		ErrorCheck.NoGenerationCheck("anonymousClassPreStatement");
		return null;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof anonymousClassPreStatement)
		{
			if (id.CouldThoughtSame(((anonymousClassPreStatement)t).id))
			{
				return true;
			}
		}
		if (t instanceof anonymousClassPreOverStatement)
		{
			if (id.CouldThoughtSame(((anonymousClassPreOverStatement)t).id))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof anonymousClassPreStatement)
		{
			return 0.4 + 0.6*(id.Similarity(((anonymousClassPreStatement) t).id));
		}
		if (t instanceof anonymousClassPreOverStatement)
		{
			return 0.4 + 0.6*(id.Similarity(((anonymousClassPreOverStatement) t).id));
		}
		return 0;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}

}
