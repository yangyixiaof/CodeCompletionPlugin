package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class fieldAccess extends referedExpression{
	
	identifier id = null;
	referedExpression rexp = null;
	
	public fieldAccess(identifier name, referedExpression rexp) {
		this.id = name;
		this.rexp = rexp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof fieldAccess)
		{
			return true;
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof fieldAccess)
		{
			return 0.4 + 0.6*(0.6*id.Similarity(((fieldAccess) t).id) + 0.4*(rexp.Similarity(((fieldAccess) t).rexp)));
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode idcs = new CSNode(CSNodeType.TempUsed);
		id.HandleCodeSynthesis(squeue, expected, handler, idcs, null);
		AdditionalInfo nai = new AdditionalInfo();
		nai.setDirectlyMemberHint(idcs.GetFirstDataWithoutTypeCheck());
		nai.setDirectlyMemberIsMethod(false);
		result.setContenttype(CSNodeType.HalfFullExpression);
		boolean conflict = rexp.HandleCodeSynthesis(squeue, expected, handler, result, nai);
		return conflict;
	}
	
}