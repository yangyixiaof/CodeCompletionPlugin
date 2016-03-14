package cn.yyx.contentassist.codeutils;

import java.util.Stack;
import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.research.language.simplified.JDTHelper.SimplifiedCodeGenerateASTVisitor;

public abstract class statement extends OneCode {
	
	public abstract void HandleOverSignal(Stack<Integer> cstack);
	public abstract boolean HandleCodeSynthesis(Stack<Sentence> sstack, SimplifiedCodeGenerateASTVisitor fmastv, StringBuilder syncode);
	
}
