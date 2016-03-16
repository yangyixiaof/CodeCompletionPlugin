package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class assignmentStatement extends expressionStatement{
	referedExpression left = null;
	String optr = null;
	referedExpression right = null;
	
	public assignmentStatement(referedExpression left, String optr, referedExpression right) {
		this.left = left;
		this.optr = optr;
		this.right = right;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof assignmentStatement)
		{
			if (optr.equals(((assignmentStatement) t).optr))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof assignmentStatement)
		{
			return 0.3*(left.Similarity(((assignmentStatement) t).left)) + 0.4*(optr.equals(((assignmentStatement) t).optr) ? 1 : 0) + 0.3*(right.Similarity(((assignmentStatement) t).right));
		}
		return 0;
	}
	
	@Override
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		return false;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SynthesisHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		StringBuilder fin = new StringBuilder("");
		StringBuilder tle = new StringBuilder("");
		left.HandleCodeSynthesis(squeue, handler, tle, null);
		fin.append(tle.toString() + optr);
		squeue.add(fin.toString(), true);
		StringBuilder tre = new StringBuilder("");
		right.HandleCodeSynthesis(squeue, handler, tre, null);
		if (tre.length() > 0)
		{
			fin.append(tre.toString());
			squeue.SetLast(fin.toString());
		}
		return false;
	}
	
}