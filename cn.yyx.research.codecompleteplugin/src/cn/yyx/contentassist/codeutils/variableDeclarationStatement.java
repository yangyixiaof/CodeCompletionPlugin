package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class variableDeclarationStatement extends statement{
	
	type tp = null;
	
	public variableDeclarationStatement(type tp) {
		this.tp = tp;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof variableDeclarationStatement)
		{
			if (tp.CouldThoughtSame(((variableDeclarationStatement) t).tp))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof variableDeclarationStatement)
		{
			return 0.5 + 0.5*(tp.Similarity(((variableDeclarationStatement) t).tp));
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
		CSNode tpcs = new CSNode(CSNodeType.VariableDeclaration);
		boolean conflict = tp.HandleCodeSynthesis(squeue, expected, handler, tpcs, ai);
		if (conflict)
		{
			return true;
		}
		handler.setRecenttype(tpcs.GetFirstDataWithoutTypeCheck());
		squeue.add(tpcs);
		return false;
	}
	
}