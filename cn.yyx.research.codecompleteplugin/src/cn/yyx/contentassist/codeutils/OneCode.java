package cn.yyx.contentassist.codeutils;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public abstract class OneCode  implements CodeSimilarity<OneCode> {
	
	public abstract boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SynthesisHandler handler, StringBuilder result, AdditionalInfo ai);
	
}
