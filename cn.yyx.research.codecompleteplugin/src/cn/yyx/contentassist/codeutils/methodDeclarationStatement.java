package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.codesynthesis.CSNode;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class methodDeclarationStatement extends statement{
	
	typeList typelist = null;
	identifier name = null;
	
	public methodDeclarationStatement(typeList typelist, identifier name) {
		this.typelist = typelist;
		this.name = name;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof methodDeclarationStatement)
		{
			if (typelist.CouldThoughtSame(((methodDeclarationStatement) t).typelist) || name.CouldThoughtSame(((methodDeclarationStatement) t).name))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof methodDeclarationStatement)
		{
			return 0.3 + 0.7*(0.6*(typelist.Similarity(((methodDeclarationStatement) t).typelist)) + 0.4*(name.Similarity(((methodDeclarationStatement) t).name)));
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
		CSNode nacs = new CSNode(CSNodeType.ReferedExpression);
		boolean conflict = name.HandleCodeSynthesis(squeue, expected, handler, nacs, ai);
		if (conflict)
		{
			return true;
		}
		CSNode tpscs = new CSNode(CSNodeType.ReferedExpression);
		conflict = typelist.HandleCodeSynthesis(squeue, expected, handler, tpscs, ai);
		if (conflict)
		{
			return true;
		}
		CSNode fcs = new CSNode(CSNodeType.WholeStatement);
		fcs.AddOneData(nacs.GetFirstDataWithoutTypeCheck() + tpscs.GetFirstDataWithoutTypeCheck(), null);
		squeue.add(fcs);
		return false;
	}
	
}