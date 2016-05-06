package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codepredict.LCSComparison;
import cn.yyx.contentassist.codepredict.PredictMetaInfo;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class argTypeList implements OneCode {
	
	List<argType> tps = new LinkedList<argType>();
	lastArgType lat = null;
	
	public argTypeList(lastArgType lat) {
		this.lat = lat;
	}
	
	public void AddToFirst(argType re)
	{
		tps.add(0, re);
	}
	
	public int Size()
	{
		int allsize = 0;
		if (lat != null)
		{
			allsize++;
		}
		allsize += tps.size();
		return allsize;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof argTypeList)
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
		if (t instanceof argTypeList)
		{
			if (lat != null)
			{
				double latpriority = 1/(1+tps.size());
				return (1-latpriority)*(LCSComparison.LCSSimilarityArgType(tps, ((argTypeList) t).tps)) + latpriority*(lat.Similarity(((argTypeList) t).lat));
			}
			else
			{
				return LCSComparison.LCSSimilarityArgType(tps, ((argTypeList) t).tps);
			}
		}
		return 0;
	}

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> ntpls = null;
		if (lat != null)
		{
			ntpls = lat.HandleCodeSynthesis(squeue, smthandler);
		}
		Iterator<argType> itr = tps.iterator();
		char seed = 'A';
		while (itr.hasNext())
		{
			argType tp = itr.next();
			List<FlowLineNode<CSFlowLineData>> tpls = tp.HandleArgumentType(squeue, smthandler, seed);
			seed = tp.HandleSeed(seed);
			if (ntpls == null)
			{
				ntpls = tpls;
			}
			else
			{
				ntpls = CSFlowLineHelper.ForwardMerge(null, tpls, ",", ntpls, null, squeue, smthandler, null, null);
			}
		}
		return ntpls;
	}
	
}