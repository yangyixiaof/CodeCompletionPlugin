package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.codesynthesis.CSNode;
import cn.yyx.contentassist.codesynthesis.CSNodeHelper;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

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
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode lt = new CSNode(CSNodeType.ReferedExpression);
		boolean conflict = left.HandleCodeSynthesis(squeue, expected, handler, lt, null);
		if (conflict)
		{
			return true;
		}
		lt.setPostfix(optr);
		CSNode rt = new CSNode(CSNodeType.ReferedExpression);
		conflict = right.HandleCodeSynthesis(squeue, expected, handler, rt, null);
		if (conflict)
		{
			return true;
		}
		CSNode fin = new CSNode(CSNodeType.WholeStatement);
		fin.setDatas(CSNodeHelper.ConcatTwoNodesDatasWithTypeChecking(lt, rt, -1));
		squeue.add(fin);
		return false;
	}
	
}