package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codesynthesis.CSBackQueue;
import cn.yyx.contentassist.codesynthesis.CSParLineNode;

public interface OneCode extends CodeSimilarity<OneCode> {
	
	// public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler, CSNode result, AdditionalInfo ai);
	public boolean HandleCodeSynthesis(CSBackQueue squeue, List<CSParLineNode> nextpars);
	
}
