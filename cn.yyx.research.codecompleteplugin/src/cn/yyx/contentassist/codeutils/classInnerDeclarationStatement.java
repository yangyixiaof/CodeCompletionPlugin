package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetRefHandler;

public class classInnerDeclarationStatement extends statement{
	
	identifier id = null;
	
	public classInnerDeclarationStatement(identifier name) {
		this.id = name;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof classInnerDeclarationStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof classInnerDeclarationStatement)
		{
			return 0.7 + 0.3*(id.Similarity(((classInnerDeclarationStatement) t).id));
		}
		return 0;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
		cstack.pop();
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, ScopeOffsetRefHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		StringBuilder idsb = new StringBuilder("");
		boolean conflict = id.HandleCodeSynthesis(squeue, handler, idsb, null);
		if (conflict)
		{
			return true;
		}
		squeue.add("protected class " + idsb.toString() + " {\n}");
		return false;
	}
	
}