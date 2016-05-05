package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class typeArgument implements OneCode{
	
	type tp = null;
    wildCardType wct = null; // tp and wct can not both be unnull
    
    public typeArgument(type tp, wildCardType wct) {
    	this.tp = tp;
    	this.wct = wct;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof typeArgument)
		{
			if (tp != null)
			{
				return tp.CouldThoughtSame(((typeArgument) t).tp);
			}
			if (wct != null)
			{
				return wct.CouldThoughtSame(((typeArgument) t).wct);
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof typeArgument)
		{
			if (tp != null)
			{
				return 0.4 + 0.6*tp.Similarity(((typeArgument) t).tp);
			}
			if (wct != null)
			{
				return 0.4 + 0.6*wct.Similarity(((typeArgument) t).wct);
			}
		}
		return 0;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		if (tp !=  null)
		{
			return tp.HandleCodeSynthesis(squeue, smthandler);
		}
		return wct.HandleCodeSynthesis(squeue, smthandler);
	}
	
}