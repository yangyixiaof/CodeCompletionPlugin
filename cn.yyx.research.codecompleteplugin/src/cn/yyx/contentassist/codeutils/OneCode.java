package cn.yyx.contentassist.codeutils;

import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetRefHandler;

public abstract class OneCode  implements CodeSimilarity<OneCode> {
	
	public abstract boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, ScopeOffsetRefHandler handler, StringBuilder result);
	
}
