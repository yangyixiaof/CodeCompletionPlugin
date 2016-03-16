package cn.yyx.contentassist.codepredict;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;

import cn.yyx.contentassist.commonutils.ContextHandler;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.parsehelper.ComplexParser;
import cn.yyx.research.AeroSpikeHandle.AeroLifeCycle;
import cn.yyx.research.language.simplified.JDTHelper.SimplifiedCodeGenerateASTVisitor;
import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetRefHandler;

public class PredictionFetch {
	
	// public static final int ParallelSize = 10;
	
	public List<String> FetchPrediction(JavaContentAssistInvocationContext javacontext, IProgressMonitor monitor, SimplifiedCodeGenerateASTVisitor fmastv, List<String> analist, ArrayList<String> result) {
		
		AeroLifeCycle alc = new AeroLifeCycle();
		alc.Initialize();
		
		int size = analist.size();
		if (size > PredictMetaInfo.PrePredictWindow) {
			analist = analist.subList(size - PredictMetaInfo.PrePredictWindow, size);
			size = PredictMetaInfo.PrePredictWindow;
		}
		
		PreTrySequenceManager manager = new PreTrySequenceManager();
		Iterator<String> itr = analist.iterator();
		while (itr.hasNext())
		{
			String str = itr.next();
			Sentence ons = ComplexParser.GetSentence(str);
			manager = DoPreTrySequencePredict(alc, manager, ons);
		}
		ScopeOffsetRefHandler handler = fmastv.GenerateScopeOffsetRefHandler();
		ContextHandler ch = new ContextHandler(javacontext, monitor);
		SynthesisHandler sh = new SynthesisHandler(handler, ch);
		PredictSequenceManager pm = DoSequencesPredictAndRealCodeSynthesis(sh, alc, manager);
		// List<String> list = DoRealCodeSynthesis(fmastv, pm);
		// AeroHelper.testListStrings(2);
		// System.out.println("ArrayListType:" + analist.getClass());
		// System.out.println("ArrayListRealSize:" + analist.size() + ";OSize;" + size + ";They should be the same.");
		
		alc.Destroy();
		alc = null;
		List<String> list = pm.GetAllSynthesisdCodes();
		return list;
	}
	
	private PredictSequenceManager DoSequencesPredictAndRealCodeSynthesis(SynthesisHandler handler, AeroLifeCycle alc, SequenceManager manager)
	{
		Iterator<Sequence> itr = manager.Iterator();
		PredictSequenceManager pm = null;
		while (itr.hasNext())
		{
			Sequence s = itr.next();
			PredictSequenceManager temppm = DoOneSequencePredict(handler, alc, s, PredictMetaInfo.ExtendFinalMaxSequence, PredictMetaInfo.ExtendTempMaxSequence);
			if (pm == null)
			{
				pm = temppm;
			}
			else
			{
				pm.Merge(temppm);
			}
		}
		pm.Restrain(PredictMetaInfo.ExtendFinalMaxSequence);
		return pm;
	}
	
	private PredictSequenceManager DoOneSequencePredict(SynthesisHandler handler, AeroLifeCycle alc, Sequence oneseq, int finalsize, int maxextendsize)
	{
		int normalExtendSize = (int)Math.max(Math.sqrt(maxextendsize), maxextendsize/2);
		PredictSequenceManager result = new PredictSequenceManager();
		PredictSequenceManager firstlevel = new PredictSequenceManager();
		PredictSequence tpd = new PredictSequence(oneseq);
		tpd.PredictStart();
		firstlevel.AddSequence(tpd, -1);
		PredictSequenceManager olevel = firstlevel;
		while (!result.CouldOver(finalsize) && !olevel.IsEmpty())
		{
			PredictSequenceManager tempolevel = new PredictSequenceManager();
			Iterator<Sequence> oitr = olevel.Iterator();
			while (oitr.hasNext())
			{
				PredictSequence pd = (PredictSequence) oitr.next();
				PredictSequenceManager pm = pd.ExtendOneSentence(handler, alc, normalExtendSize);
				Iterator<Sequence> itr = pm.Iterator();
				while (itr.hasNext())
				{
					PredictSequence opd = (PredictSequence) itr.next();
					if (opd.isOver())
					{
						result.AddSequence(opd, finalsize);
					}
					else
					{
						tempolevel.AddSequence(opd, maxextendsize);
					}
				}
			}
			olevel = tempolevel;
		}
		return result;
	}

	private PreTrySequenceManager DoPreTrySequencePredict(AeroLifeCycle alc, PreTrySequenceManager manager, Sentence ons)
	{
		if (manager.IsEmpty())
		{
			PreTrySequence em = new PreTrySequence(true);
			em.AddOneSentence(ons);
			manager.setExactmatch(em);
			manager.AddSequence(em);
			return manager;
		}
		else
		{
			PreTrySequenceManager sm = null;
			int existSize = manager.GetSize();
			int averagePredict = PredictMetaInfo.PredictMaxSequence / existSize;
			Iterator<Sequence> itr = manager.Iterator();
			int isize = existSize;
			while (itr.hasNext())
			{
				isize--;
				PreTrySequence seq = (PreTrySequence) itr.next();
				SequenceManager tempsm = seq.PredictSentences(alc, averagePredict + (int)(5*(isize*1.0/(existSize*1.0))));
				PreTrySequenceManager ptsm = new PreTrySequenceManager(tempsm, ons, seq.isExactmatch());
				if (sm == null)
				{
					sm = ptsm;
				}
				else
				{
					sm.Merge(ptsm);
				}
			}
			return sm;
		}
	}
	
	/*private List<String> DoRealCodeSynthesis(SimplifiedCodeGenerateASTVisitor fmastv, PredictSequenceManager pm) {
		return null;
	}*/
	
	/*public static void main(String[] args) {
		ArrayList<String> result = new ArrayList<String>();
		String[] analistarr = { "ABC", "ASD", "GFS", "LOI", "POI", "LKI", "HGF", "DFG", "WER", "TRY", "UYI", "OIU", "KIY", "QAW"};
		ArrayList<String> analist = new ArrayList<String>();
		for (int i = 0; i < analistarr.length; i++) {
			analist.add(analistarr[i]);
		}
		PredictionFetch.FetchPrediction(null, analist, result);
		ListHelper.PrintList(result);
	}*/
	
}