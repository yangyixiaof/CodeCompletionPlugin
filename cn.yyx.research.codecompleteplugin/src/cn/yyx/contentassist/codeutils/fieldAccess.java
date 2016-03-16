package cn.yyx.contentassist.codeutils;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

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
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SynthesisHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		StringBuilder idsb = new StringBuilder("");
		id.HandleCodeSynthesis(squeue, handler, idsb, null);
		AdditionalInfo nai = new AdditionalInfo();
		nai.setDirectlyMemberHint(idsb.toString());
		nai.setDirectlyMemberIsMethod(false);
		StringBuilder resb = new StringBuilder("");
		rexp.HandleCodeSynthesis(squeue, handler, resb, nai);
		String member = nai.getModifiedMember();
		String ref = resb.toString();
		result.append(ref + "." + member);
		return false;
	}
	
}