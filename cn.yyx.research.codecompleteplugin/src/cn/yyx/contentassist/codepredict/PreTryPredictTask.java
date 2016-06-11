package cn.yyx.contentassist.codepredict;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.aerospikehandle.AeroLifeCycle;
import cn.yyx.contentassist.aerospikehandle.PredictProbPair;
import cn.yyx.contentassist.codecompletion.PredictMetaInfo;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineHelper;
import cn.yyx.contentassist.codesynthesis.flowline.PreTryFlowLineNode;
import cn.yyx.contentassist.codeutils.methodInvocationStatement;
import cn.yyx.contentassist.codeutils.statement;
import cn.yyx.contentassist.commonutils.ClassInstanceOfUtil;
import cn.yyx.contentassist.commonutils.StatementsMIs;
import cn.yyx.contentassist.commonutils.TKey;

public class PreTryPredictTask implements Runnable {
	
	int id = -1;
	AeroLifeCycle alc = null;
	List<TKey> keys = new LinkedList<TKey>();
	List<StatementsMIs> smtmises = new LinkedList<StatementsMIs>();
	LinkedList<PreTryFlowLineNode<Sentence>> result = new LinkedList<PreTryFlowLineNode<Sentence>>();
	PreTryFlowLineNode<Sentence> fln = null;
	List<statement> oraclesmtlist = null;
	List<statement> oraclesmtmilist = null;
	Sentence ons = null;
	String flnwholekey = null;
	
	public PreTryPredictTask(int id, AeroLifeCycle alc, List<TKey> keys, List<StatementsMIs> smtmises, PreTryFlowLineNode<Sentence> fln, List<statement> smtlist, List<statement> smtmilist, Sentence ons) {
		this.id = id;
		this.alc = alc;
		this.keys.addAll(keys);
		this.smtmises.addAll(smtmises);
		this.fln = fln;
		this.oraclesmtlist = smtlist;
		this.oraclesmtmilist = smtmilist;
		this.ons = ons;
		this.flnwholekey = FlowLineHelper.GetWholeTraceKey(fln);
		
	}
	
	@Override
	public void run() {
		boolean exactsamefound = false;
		Iterator<TKey> itr = keys.iterator();
		Iterator<StatementsMIs> smitr = smtmises.iterator();
		while (itr.hasNext())
		{
			TKey key = itr.next();
			StatementsMIs smi = smitr.next();
			List<statement> triedcmp = smi.getSmts();
			List<statement> triedcmpsmi = smi.getSmis();
			List<PredictProbPair> pps = alc.AeroModelPredict(id, key.getKey(), PredictMetaInfo.PreTryMaxParSize, key.getKeylen());
			Iterator<PredictProbPair> ppsitr = pps.iterator();
			while (ppsitr.hasNext())
			{
				PredictProbPair ppp = ppsitr.next();
				Sentence pred = ppp.getPred();
				statement predsmt = pred.getSmt();
				triedcmp.add(predsmt);
				if (ClassInstanceOfUtil.ObjectInstanceOf(predsmt, methodInvocationStatement.class))
				{
					triedcmpsmi.add(predsmt);
				}
				double mtsim = LCSComparison.LCSSimilarity(oraclesmtlist, triedcmp);
				
				if (pred.getSentence().equals("MI@getActionCommand(@PE);"))
				{
					System.err.println("Debugging Sentence.");
				}
				
				double misim = LCSComparison.LCSSimilarity(oraclesmtmilist, triedcmpsmi);
				double sim = 0.5*mtsim + 0.5*misim;
				if (sim > PredictMetaInfo.MinSimilarity)
				{
					PreTryFlowLineNode<Sentence> nf = new PreTryFlowLineNode<Sentence>(pred, ppp.getProb() + fln.getProbability(), sim, fln, key.getKey() + " " + pred.getSentence(), flnwholekey + " " + pred.getSentence(), ppp.getKeylen());
					result.add(nf);
					
					if (ons != null && ons.getSentence().equals(pred.getSentence()))
					{
						exactsamefound = true;
					}
					if (fln.isIsexactsame() && exactsamefound)
					{
						nf.setIsexactsame(true);
					}
				}
				((LinkedList<statement>)triedcmp).removeLast();
				if (ClassInstanceOfUtil.ObjectInstanceOf(predsmt, methodInvocationStatement.class))
				{
					((LinkedList<statement>)triedcmpsmi).removeLast();
				}
			}
			
			if (fln.isIsexactsame() && !exactsamefound)
			{
				if (ExactSameExists())
				{
					PreTryFlowLineNode<Sentence> lst = result.getLast();
					PreTryFlowLineNode<Sentence> nf = new PreTryFlowLineNode<Sentence>(ons, lst.getProbability()*4.0/5.0 + fln.getProbability(), 1, fln, key.getKey() + " " + ons.getSentence(), flnwholekey + " " + ons.getSentence(), fln.getKeylen()+1);
					result.add(nf);
				}
			}
		}
	}
	
	private boolean ExactSameExists()
	{
		List<PredictProbPair> pps = alc.AeroModelPredict(id, fln.getKey() + ons.getSentence(), PredictMetaInfo.PreTryMaxParSize, fln.getKeylen()+1);
		if (pps != null && pps.size() > 0)
		{
			return true;
		}
		return false;
	}

	public List<PreTryFlowLineNode<Sentence>> GetResultList() {
		return result;
	}
	
}