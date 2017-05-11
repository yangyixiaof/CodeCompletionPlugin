package cn.yyx.contentassist.codepredict;

import java.util.Iterator;
import java.util.List;

import cn.yyx.contentassist.aerospikehandle.AeroLifeCycle;
import cn.yyx.contentassist.codesynthesis.flowline.PreTryFlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.PreTryFlowLines;
import cn.yyx.contentassist.commonutils.StatementsMIs;

public class ExactPredictionFetch extends PredictionFetch {
	
	@Override
	protected PreTryFlowLines<Sentence> DoPreTrySequencePredict(AeroLifeCycle alc, final List<Sentence> setelist,
			final StatementsMIs smtmis, final Class<?> lastkind) {
		PreTryFlowLines<Sentence> fls = new PreTryFlowLines<Sentence>();
		Iterator<Sentence> itr = setelist.iterator();
		PreTryFlowLineNode<Sentence> prenf = null;
		int keylen = 0;
		String key = null;
		while (itr.hasNext())
		{
			Sentence sete = itr.next();
			fls.BeginOperation();
			if (key == null) {
				key = sete.getSentence();
			} else {
				key = key + " " + sete.getSentence();
			}
			PreTryFlowLineNode<Sentence> nf = new PreTryFlowLineNode<Sentence>(sete, 0, 1, prenf, key, key, keylen);
			nf.setIsexactsame(true);
			fls.AddToNextLevel(nf, prenf);
			fls.EndOperation();
			
			if (!itr.hasNext())
			{
				fls.AddOverFlowLineNode(nf, prenf);
			}
			
			keylen++;
			prenf = nf;
		}
		return fls;
	}
	
}
