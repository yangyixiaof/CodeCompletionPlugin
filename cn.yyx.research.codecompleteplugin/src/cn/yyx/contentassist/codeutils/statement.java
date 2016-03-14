package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.research.language.simplified.JDTHelper.SimplifiedCodeGenerateASTVisitor;

public abstract class statement extends OneCode {
	
	public abstract void HandleOverSignal(Stack<Integer> cstack);
	public abstract boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SimplifiedCodeGenerateASTVisitor fmastv);
	
}
