package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codecompletion.PredictMetaInfo;
import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codepredict.LCSComparison;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSArgTypeListData;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.InferredCCType;
import cn.yyx.research.language.JDTManager.GCodeMetaInfo;

public class argTypeList implements OneCode {
	
	List<argType> tps = new LinkedList<argType>();
	lastArgType lat = null; // warning lat could be null.
	
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
		List<LinkedList<String>> llls = null;
		if (lat != null)
		{
			ntpls = lat.HandleCodeSynthesis(squeue, smthandler);
			ArgTypeRefinedList(ntpls, llls);
		}
		Iterator<argType> itr = tps.iterator();
		char seed = 'A';
		while (itr.hasNext())
		{
			argType tp = itr.next();
			List<FlowLineNode<CSFlowLineData>> tpls = tp.HandleArgumentType(squeue, smthandler, seed);
			if (tpls == null || tpls.size() == 0)
			{
				return null;
			}
			seed = tp.HandleSeed(seed);
			llls = ArgTypeRefinedList(tpls, llls);
			if (ntpls == null)
			{
				ntpls = tpls;
			}
			else
			{
				ntpls = CSFlowLineHelper.ForwardConcate(null, tpls, ",", ntpls, null, squeue, smthandler, null);
			}
		}
		if (ntpls != null && ntpls.size() != llls.size())
		{
			new Exception("llls not the same as ntpls?").printStackTrace();
			System.exit(1);
		}
		Iterator<LinkedList<String>> llitr = llls.iterator();
		Iterator<FlowLineNode<CSFlowLineData>> npitr = ntpls.iterator();
		while (npitr.hasNext())
		{
			LinkedList<String> ll = llitr.next();
			FlowLineNode<CSFlowLineData> npfln = npitr.next();
			npfln.setData(new CSArgTypeListData(ll, npfln.getData()));
		}
		return ntpls;
	}
	
	private List<LinkedList<String>> ArgTypeRefinedList(List<FlowLineNode<CSFlowLineData>> nowtpls, List<LinkedList<String>> pretpns)
	{
		List<LinkedList<String>> tpandnames = new LinkedList<LinkedList<String>>();
		
		Iterator<FlowLineNode<CSFlowLineData>> tpitr = nowtpls.iterator();
		while (tpitr.hasNext())
		{
			FlowLineNode<CSFlowLineData> fln = tpitr.next();
			if (pretpns != null && pretpns.size() > 0) {
				Iterator<LinkedList<String>> itr = pretpns.iterator();
				while (itr.hasNext())
				{
					LinkedList<String> lls = itr.next();
					LinkedList<String> nl = new LinkedList<String>();
					nl.addAll(lls);
					GenerateOneTL(fln, nl);
					tpandnames.add(nl);
				}
			} else {
				LinkedList<String> nl = new LinkedList<String>();
				GenerateOneTL(fln, nl);
				tpandnames.add(nl);
			}
		}
		
		return tpandnames;
	}
	
	private void GenerateOneTL(FlowLineNode<CSFlowLineData> fln, LinkedList<String> nl)
	{
		CCType dcls = fln.getData().getDcls();
		if (dcls instanceof InferredCCType) {
			nl.add(GCodeMetaInfo.InferedType + " " + fln.getData().getData());
		} else {
			int widx = fln.getData().getData().indexOf(' ');
			String vname = fln.getData().getData().substring(widx+1);
			if (dcls.getCls() != null) {
				nl.add(dcls.getCls().getSimpleName() + " " + vname);
			} else {
				nl.add(dcls.getClstr() + " " + vname);
			}
		}
	}
	
}