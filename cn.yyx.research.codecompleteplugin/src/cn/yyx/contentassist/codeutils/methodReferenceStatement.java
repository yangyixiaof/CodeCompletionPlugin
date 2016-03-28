package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class methodReferenceStatement extends expressionStatement{
	
	identifier name = null;
	methodReferenceExpression mrexp = null;
	
	public methodReferenceStatement(identifier name, methodReferenceExpression mrexp) {
		this.name = name;
		this.mrexp = mrexp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof methodReferenceStatement)
		{
			if (name.CouldThoughtSame(((methodReferenceStatement) t).name) || mrexp.CouldThoughtSame(((methodReferenceStatement) t).mrexp))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof methodReferenceStatement)
		{
			return 0.3 + 0.7*(0.5*(name.Similarity(((methodReferenceStatement) t).name)) + 0.5*(mrexp.Similarity(((methodReferenceStatement) t).mrexp)));
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
			return conflict;
		}
		AdditionalInfo nai = new AdditionalInfo();
		nai.setDirectlyMemberHint(nacs.GetFirstDataWithoutTypeCheck());
		nai.setDirectlyMemberIsMethod(true);
		CSNode fcs = new CSNode(CSNodeType.WholeStatement);
		mrexp.HandleCodeSynthesis(squeue, expected, handler, fcs, nai);
		squeue.add(fcs);
		return false;
	}
	
}