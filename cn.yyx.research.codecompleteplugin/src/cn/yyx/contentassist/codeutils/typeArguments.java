package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codecompletion.PredictMetaInfo;
import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codepredict.LCSComparison;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class typeArguments implements OneCode{
	
	List<typeArgument> tas = new LinkedList<typeArgument>();
	
	public typeArguments() {
	}
	
	public void AddToFirst(typeArgument ta)
	{
		tas.add(0, ta);
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof typeArguments)
		{
			if (Similarity(t) > PredictMetaInfo.SequenceSimilarThreshold)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof typeArguments)
		{
			return 0.4 + 0.6*(LCSComparison.LCSSimilarityTypeArgument(tas, ((typeArguments) t).tas));
		}
		return 0;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		Iterator<typeArgument> itr = tas.iterator();
		typeArgument ta = itr.next();
		List<FlowLineNode<CSFlowLineData>> tals = ta.HandleCodeSynthesis(squeue, smthandler);
		if (tals == null || tals.size() == 0)
		{
			return null;
		}
		while (itr.hasNext())
		{
			ta = itr.next();
			tals = CSFlowLineHelper.ForwardConcate(null, tals, ",", ta.HandleCodeSynthesis(squeue, smthandler), null, squeue, smthandler, null);
		}
		return CSFlowLineHelper.ConcateOneFlowLineList("<", tals, ">");
	}
	
}