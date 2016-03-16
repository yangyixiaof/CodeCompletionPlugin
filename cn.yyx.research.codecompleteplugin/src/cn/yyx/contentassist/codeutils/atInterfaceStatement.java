package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class atInterfaceStatement extends statement{
	
	identifier id = null;
	
	public atInterfaceStatement(identifier id) {
		this.id = id;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof atInterfaceStatement)
		{
			if (id.CouldThoughtSame(((atInterfaceStatement) t).id))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof atInterfaceStatement)
		{
			return id.Similarity(((atInterfaceStatement) t).id);
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
		id.HandleCodeSynthesis(squeue, handler, idsb, null);
		squeue.add("public @interface " + idsb.toString() + "{\n}");
		return false;
	}
	
}