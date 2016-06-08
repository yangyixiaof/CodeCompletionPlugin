package cn.yyx.contentassist.codepredict;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.aerospikehandle.AeroLifeCycle;
import cn.yyx.contentassist.aerospikehandle.PredictProbPair;
import cn.yyx.contentassist.codecompletion.PredictMetaInfo;
import cn.yyx.contentassist.codesynthesis.flowline.PreTryFlowLineNode;
import cn.yyx.contentassist.commonutils.StatementsMIs;
import cn.yyx.contentassist.commonutils.TKey;

public class PreTryPredictTask implements Runnable {
	
	int id = -1;
	AeroLifeCycle alc = null;
	List<TKey> keys = new LinkedList<TKey>();
	List<StatementsMIs> smtmises = new LinkedList<StatementsMIs>();
	
	public PreTryPredictTask(int id, AeroLifeCycle alc, List<TKey> keys, List<StatementsMIs> smtmises) {
		this.id = id;
		this.alc = alc;
		this.keys.addAll(keys);
		this.smtmises.addAll(smtmises);
	}
	
	@Override
	public void run() {
		Iterator<TKey> itr = keys.iterator();
		Iterator<StatementsMIs> smitr = smtmises.iterator();
		while (itr.hasNext())
		{
			TKey key = itr.next();
			StatementsMIs smi = smitr.next();
			List<PredictProbPair> pps = alc.AeroModelPredict(id, key.getKey(), PredictMetaInfo.PreTryMaxParSize, key.getKeylen());
		}
	}

	public List<PreTryFlowLineNode<Sentence>> GetResultList() {
		// TODO Auto-generated method stub
		return null;
	}
	
}