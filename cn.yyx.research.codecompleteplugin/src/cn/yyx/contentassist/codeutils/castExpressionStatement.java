package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CSNodeHelper;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class castExpressionStatement extends expressionStatement{
	
	type tp = null;
	referedExpression rexp = null;
	
	public castExpressionStatement(type tp, referedExpression rexp) {
		this.tp = tp;
		this.rexp = rexp;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof castExpressionStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof castExpressionStatement)
		{
			return 0.4 + 0.6*(tp.Similarity(((castExpressionStatement) t).tp) + rexp.Similarity(((castExpressionStatement) t).rexp));
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
		CSNode ttp = new CSNode(CSNodeType.HalfFullExpression);
		tp.HandleCodeSynthesis(squeue, expected, handler, ttp, null);
		ttp.setPrefix("(");
		ttp.setPostfix(")");
		
		expected.push(null);
		CSNode resb = new CSNode(CSNodeType.ReferedExpression);
		boolean conflict = rexp.HandleCodeSynthesis(squeue, expected, handler, resb, null);
		if (conflict)
		{
			return true;
		}
		CSNode fin = new CSNode(CSNodeType.WholeStatement);
		fin.setDatas(CSNodeHelper.ConcatTwoNodesDatas(ttp, resb, -1));
		squeue.add(fin);
		expected.pop();
		return false;
	}
	
}