package cn.yyx.contentassist.codeutils;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public interface OneCode extends CodeSimilarity<OneCode> {
	
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, SynthesisHandler handler, CSNode result, AdditionalInfo ai);
	
}
