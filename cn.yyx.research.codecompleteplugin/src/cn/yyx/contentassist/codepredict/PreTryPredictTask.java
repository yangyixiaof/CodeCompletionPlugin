package cn.yyx.contentassist.codepredict;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.aerospikehandle.AeroLifeCycle;
import cn.yyx.contentassist.aerospikehandle.PredictProbPair;
import cn.yyx.contentassist.codecompletion.PredictMetaInfo;
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
	List<PreTryFlowLineNode<Sentence>> result = new LinkedList<PreTryFlowLineNode<Sentence>>();
	PreTryFlowLineNode<Sentence> fln = null;
	List<statement> oraclesmtlist = null;
	List<statement> oraclesmtmilist = null;
	
	public PreTryPredictTask(int id, AeroLifeCycle alc, List<TKey> keys, List<StatementsMIs> smtmises, PreTryFlowLineNode<Sentence> fln, List<statement> smtlist, List<statement> smtmilist) {
		this.id = id;
		this.alc = alc;
		this.keys.addAll(keys);
		this.smtmises.addAll(smtmises);
		this.fln = fln;
		this.oraclesmtlist = smtlist;
		this.oraclesmtmilist = smtmilist;
	}
	
	@Override
	public void run() {
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
					PreTryFlowLineNode<Sentence> nf = new PreTryFlowLineNode<Sentence>(pred, ppp.getProb() + fln.getProbability(), sim, fln, ppp.getKeylen());
					result.add(nf);
				}
				((LinkedList<statement>)triedcmp).removeLast();
				if (ClassInstanceOfUtil.ObjectInstanceOf(predsmt, methodInvocationStatement.class))
				{
					((LinkedList<statement>)triedcmpsmi).removeLast();
				}
			}
		}
	}

	public List<PreTryFlowLineNode<Sentence>> GetResultList() {
		return result;
	}
	
}