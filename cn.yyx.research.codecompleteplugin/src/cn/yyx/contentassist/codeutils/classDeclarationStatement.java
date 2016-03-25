package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class classDeclarationStatement extends statement{
	
	identifier id = null;
	
	public classDeclarationStatement(identifier name) {
		this.id = name;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof classDeclarationStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof classDeclarationStatement)
		{
			return 0.7 + 0.3*(id.Similarity(((classDeclarationStatement) t).id));
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
		CSNode tid = new CSNode(CSNodeType.HalfFullExpression);
		id.HandleCodeSynthesis(squeue, expected, handler, tid, null);
		tid.setPrefix("public class ");
		squeue.add(tid);
		return false;
	}
	
}