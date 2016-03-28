package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class enumConstantDeclarationStatement extends statement{
	
	identifier id = null;
	argumentList arglist = null;
	
	public enumConstantDeclarationStatement(identifier name, argumentList arglist) {
		this.id = name;
		this.arglist = arglist;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof enumConstantDeclarationStatement)
		{
			if (id.CouldThoughtSame(((enumConstantDeclarationStatement) t).id))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof enumConstantDeclarationStatement)
		{
			return 0.4 + 0.6*(id.Similarity(((enumConstantDeclarationStatement) t).id));
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
		CSNode idcs = new CSNode(CSNodeType.TempUsed);
		boolean conflict = id.HandleCodeSynthesis(squeue, expected, handler, idcs, null);
		if (arglist == null)
		{
			idcs.setContenttype(CSNodeType.WholeStatement);
			squeue.add(idcs);
		}
		else
		{
			AdditionalInfo nai = new AdditionalInfo();
			nai.setMethodName(idcs.GetFirstDataWithoutTypeCheck());
			CSNode acs = new CSNode(CSNodeType.WholeStatement);
			conflict = arglist.HandleCodeSynthesis(squeue, expected, handler, acs, nai);
			squeue.add(acs);
		}
		return conflict;
	}
	
}