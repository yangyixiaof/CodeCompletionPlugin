package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisHelper;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;

public class elseStatement extends statement {

	public elseStatement(String smtcode) {
		super(smtcode);
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), "else " + CodeSynthesisHelper.GenerateBlockCode(smthandler), new CCType(void.class, "void"), null, squeue.GetLastHandler()), smthandler.getProb()));
		return result;
		// ErrorCheck.NoGenerationCheck("DH@else should not be generated.");
		// return null;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof elseStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof elseStatement)
		{
			return 1;
		}
		return 0;
	}
	
	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		// ErrorCheck.NoGenerationCheck("DH@else should not handle over signal.");
		// cstack.EnsureAllSignalNull();
		return true;
	}
	
}