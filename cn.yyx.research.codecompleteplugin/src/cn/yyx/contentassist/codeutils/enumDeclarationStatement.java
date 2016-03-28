package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

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
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		cstack.pop();
		return false;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode idcs = new CSNode(CSNodeType.WholeStatement);
		id.HandleCodeSynthesis(squeue, expected, handler, idcs, ai);
		idcs.setPrefix("public enum ");
		idcs.setPostfix(" {\n}");
		squeue.add(idcs);
		return false;
	}
	
}