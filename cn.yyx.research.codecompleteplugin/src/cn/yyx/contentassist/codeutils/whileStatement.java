package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.codesynthesis.CSNode;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class whileStatement extends statement{
	
	referedExpression rexp = null;
	
	public whileStatement(referedExpression rexp) {
		this.rexp = rexp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof whileStatement)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof whileStatement)
		{
			return 0.5 + 0.5*(rexp.Similarity(((whileStatement) t).rexp));
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
		CSNode ts = new CSNode(CSNodeType.TempUsed);
		boolean conflict = rexp.HandleCodeSynthesis(squeue, expected, handler, ts, null);
		if (conflict)
		{
			return true;
		}
		ts.setContenttype(CSNodeType.WholeStatement);
		ts.setPrefix("while (");
		ts.setPostfix(") {\n\n} ");
		squeue.add(ts);
		return false;
	}

}