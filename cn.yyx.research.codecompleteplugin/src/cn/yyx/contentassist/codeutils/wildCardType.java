package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class wildCardType extends type{
	
	boolean extended = false;
	type tp = null;
	
	public wildCardType(boolean extended, type tp) {
		this.extended = extended;
		this.tp = tp;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof wildCardType)
		{
			if (tp == null)
			{
				return true;
			}
			else
			{
				if (tp.CouldThoughtSame(((wildCardType) t).tp))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof wildCardType)
		{
			if (tp == null)
			{
				return 1;
			}
			else
			{
				
				return 0.4 + 0.6*(tp.Similarity(((wildCardType) t).tp));
			}
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		if (tp != null)
		{
			
		}
		return false;
	}
	
}