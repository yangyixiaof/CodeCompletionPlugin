package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetRefHandler;

public class anonymousClassPreStatement extends statement{
	
	identifier id = null;
	
	public anonymousClassPreStatement(identifier id) {
		this.id = id;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof anonymousClassPreStatement)
		{
			if (id.CouldThoughtSame(((anonymousClassPreStatement)t).id))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof anonymousClassPreStatement)
		{
			return 0.4 + 0.6*(id.Similarity(((anonymousClassPreStatement) t).id));
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, ScopeOffsetRefHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		return false;
	}
	
}