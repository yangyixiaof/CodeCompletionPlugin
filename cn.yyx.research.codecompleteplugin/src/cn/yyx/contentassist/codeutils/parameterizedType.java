package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class parameterizedType extends type{
	
	identifier id = null;
	typeList tlist = null;
	
	public parameterizedType(identifier id, typeList tlist) {
		this.id = id;
		this.tlist = tlist;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof parameterizedType)
		{
			if (id.CouldThoughtSame(((parameterizedType) t).id))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof parameterizedType)
		{
			return 0.4 + 0.6*(id.Similarity(((parameterizedType) t).id));
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode cs = new CSNode(CSNodeType.HalfFullExpression);
		boolean conflict = id.HandleCodeSynthesis(squeue, expected, handler, cs, ai);
		if (conflict)
		{
			return true;
		}
		CSNode tcs = new CSNode(CSNodeType.HalfFullExpression);
		conflict = tlist.HandleCodeSynthesis(squeue, expected, handler, tcs, ai);
		if (conflict)
		{
			return true;
		}
		result.AddOneData(cs.GetFirstDataWithoutTypeCheck()+"<"+tcs.GetFirstDataWithoutTypeCheck()+">", null);
		return false;
	}
	
}