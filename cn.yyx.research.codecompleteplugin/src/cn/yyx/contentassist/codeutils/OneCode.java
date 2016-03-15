package cn.yyx.contentassist.codeutils;

import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.research.language.simplified.JDTHelper.SimplifiedCodeGenerateASTVisitor;

public abstract class OneCode  implements CodeSimilarity<OneCode> {
	
	public abstract boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SimplifiedCodeGenerateASTVisitor fmastv, StringBuilder result);
	
}
