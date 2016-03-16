package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class enumDeclarationStatement extends statement{
	
	identifier id = null;
	
	public enumDeclarationStatement(identifier name) {
		this.id = name;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof enumDeclarationStatement)
		{
			if (id.CouldThoughtSame(((enumDeclarationStatement) t).id))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof enumDeclarationStatement)
		{
			return 0.4 + 0.6*(id.Similarity(((enumDeclarationStatement) t).id));
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
		cstack.pop();
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SynthesisHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		StringBuilder idsb = new StringBuilder("");
		squeue.add("public enum " + idsb.toString() + " {\n}");
		return false;
	}
	
}