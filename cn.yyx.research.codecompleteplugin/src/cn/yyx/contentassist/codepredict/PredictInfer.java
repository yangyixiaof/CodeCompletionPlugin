package cn.yyx.contentassist.codepredict;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.yyx.contentassist.aerospikehandle.AeroLifeCycle;
import cn.yyx.contentassist.aerospikehandle.PredictProbPair;
import cn.yyx.contentassist.codecompletion.PredictMetaInfo;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineHelper;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.PreTryFlowLineNode;
import cn.yyx.contentassist.commonutils.ListHelper;
import cn.yyx.contentassist.commonutils.TKey;

public class PredictInfer {
	
	Map<String, Boolean> keynull = new TreeMap<String, Boolean>();
	Map<String, Boolean> handledkey = new TreeMap<String, Boolean>();
	
	public PredictInfer() {
	}
	
	public void BeginOperation()
	{
		// do nothing. Just a uniform API.
	}
	
	public List<PredictProbPair> InferNextGeneration(AeroLifeCycle alc, int neededsize, FlowLineNode<?> fln, PreTryFlowLineNode<Sentence> pretrylast)
	{
		List<PredictProbPair> result = new LinkedList<PredictProbPair>();
		
		int ngramtrim1size = PredictMetaInfo.NgramMaxSize-1;
		int remainsize = neededsize;
		
		Map<String, Boolean> happenedinfer = new TreeMap<String, Boolean>();
		
		// HandleOneInOneTurnPreTrySequencePredict
		while ((remainsize > 0) && (ngramtrim1size >= 1))
		{
			List<Sentence> ls = FlowLineHelper.LastNeededSentenceQueue(ngramtrim1size, fln, pretrylast);
			
			// update n-gram size.
			ngramtrim1size = ls.size();
			ngramtrim1size--;
			// if key or trim1key return no records, just continue.
			TKey tkey = ListHelper.ConcatJoin(ls);
			String key = tkey.getKey();
			String trim1key = tkey.getTrim1key();
			int keylen = tkey.getKeylen();
			if (trim1key != null)
			{
				if (keynull.containsKey(trim1key)) //  || keynull.containsKey(key)
				{
					keynull.put(key, true);
					continue;
				}
			}
			
			// record handled key.
			if (handledkey.containsKey(key))
			{
				break;
			}
			else
			{
				handledkey.put(key, true);
			}
			
			// not handled key.
			List<PredictProbPair> pps = alc.AeroModelPredict(key, remainsize, keylen);
			Iterator<PredictProbPair> ppsitr = pps.iterator();
			while (ppsitr.hasNext())
			{
				PredictProbPair ppp = ppsitr.next();

				// check whether this prediction has happened.
				if (happenedinfer.containsKey(ppp.getPred().getSentence()))
				{
					continue;
				}
				else {
					happenedinfer.put(ppp.getPred().getSentence(), true);
				}
				
				remainsize--;
				result.add(ppp);
			}
			
			// set the key-null optimized map to speed up.
			if (pps.isEmpty())
			{
				keynull.put(key, true);
			}
		}
		
		happenedinfer.clear();
		
		return result;
	}
	
	public void EndOperation()
	{
		handledkey.clear();
	}
	
	public void Clear()
	{
		keynull.clear();
	}
	
}