package cn.yyx.contentassist.codeutils;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSFlowLineStamp;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;

public interface OneCode extends CodeSimilarity<OneCode> {
	
	// public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler, CSNode result, AdditionalInfo ai);
	public CSFlowLineStamp HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler) throws CodeSynthesisException;
	
}