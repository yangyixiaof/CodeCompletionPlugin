package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

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
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SynthesisHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		StringBuilder fin = new StringBuilder("");
		StringBuilder idsb = new StringBuilder("");
		id.HandleCodeSynthesis(squeue, handler, idsb, null);
		if (arglist == null)
		{
			fin.append(idsb.toString());
		}
		else
		{
			AdditionalInfo nai = new AdditionalInfo();
			nai.setMethodName(idsb.toString());
			arglist.HandleCodeSynthesis(squeue, handler, fin, nai);
		}
		squeue.add(fin.toString());
		return false;
	}
	
}