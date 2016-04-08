package cn.yyx.contentassist.codeutils;

import java.util.Map;
import java.util.Set;
import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.RefAndModifiedMember;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;
import cn.yyx.contentassist.specification.SpecificationHelper;

public class methodReferenceExpression implements OneCode{
	
	referedExpression rexp = null;
	
	public methodReferenceExpression(referedExpression rexp) {
		this.rexp = rexp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof methodReferenceExpression)
		{
			if (rexp.CouldThoughtSame(((methodReferenceExpression) t).rexp))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof methodReferenceExpression)
		{
			return 0.3+0.7*(rexp.Similarity(((methodReferenceExpression) t).rexp));
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		String hint = ai.getDirectlyMemberHint();
		boolean hintismethod = ai.isDirectlyMemberIsMethod();
		CSNode cs = new CSNode(CSNodeType.ReferedExpression);
		rexp.HandleCodeSynthesis(squeue, expected, handler, cs, ai);
		Map<String, TypeCheck> po = cs.getDatas();
		Set<String> codes = po.keySet();
		RefAndModifiedMember mmf = SpecificationHelper.GetMostLikelyRef(handler.getContextHandler(), codes, hint, hintismethod);
		if (mmf == null)
		{
			result.setDatas(cs.getDatas());
			result.setPostfix("::"+hint);
		}
		else
		{
			TypeCheck tc = new TypeCheck();
			tc.addExpreturntype(mmf.getMembertype());
			result.AddOneData(mmf.getRef() + "::" + mmf.getMember(), tc);
		}
		return false;
	}
	
}