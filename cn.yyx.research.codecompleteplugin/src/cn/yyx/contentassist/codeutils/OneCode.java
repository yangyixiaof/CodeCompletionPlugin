package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;

public interface OneCode extends CodeSimilarity<OneCode> {
	
	// public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler, CSNode result, AdditionalInfo ai);
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler) throws CodeSynthesisException;
	
}