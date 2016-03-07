package cn.yyx.contentassist.codepredict;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import cn.yyx.research.AeroSpikeHandle.AeroLifeCycle;
import cn.yyx.research.language.simplified.JDTHelper.SimplifiedCodeGenerateASTVisitor;

public class PredictionFetch {
	
	// public static final int ParallelSize = 10;
	
	public List<String> FetchPrediction(SimplifiedCodeGenerateASTVisitor fmastv, List<String> analist, ArrayList<String> result) {
		
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
			String ons = itr.next();
			manager = DoPreTrySequencePredict(alc, manager, ons);
		}
		
		PredictSequenceManager pm = DoSequencesPredict(alc, manager);
		List<String> list = DoRealCodeSynthesis(fmastv, pm);
		// AeroHelper.testListStrings(2);
		// System.out.println("ArrayListType:" + analist.getClass());
		// System.out.println("ArrayListRealSize:" + analist.size() + ";OSize;" + size + ";They should be the same.");
		
		alc.Destroy();
		alc = null;
		
		return list;
	}
	
	private PredictSequenceManager DoSequencesPredict(AeroLifeCycle alc, SequenceManager manager)
	{
		Iterator<Sequence> itr = manager.Iterator();
		PredictSequenceManager pm = null;
		while (itr.hasNext())
		{
			Sequence s = itr.next();
			PredictSequenceManager temppm = DoOneSequencePredict(alc, s, PredictMetaInfo.ExtendFinalMaxSequence, PredictMetaInfo.ExtendTempMaxSequence);
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
	
	private PredictSequenceManager DoOneSequencePredict(AeroLifeCycle alc, Sequence oneseq, int finalsize, int maxextendsize)
	{
		int normalExtendSize = (int)Math.max(Math.sqrt(maxextendsize), maxextendsize/2);
		PredictSequenceManager result = new PredictSequenceManager();
		PredictSequenceManager firstlevel = new PredictSequenceManager();
		PredictSequence tpd = new PredictSequence(oneseq);
		firstlevel.AddOnePredict(tpd, -1);
		PredictSequenceManager olevel = firstlevel;
		while (!result.CouldOver(finalsize) && !olevel.IsEmpty())
		{
			PredictSequenceManager tempolevel = new PredictSequenceManager();
			Iterator<Sequence> oitr = olevel.Iterator();
			while (oitr.hasNext())
			{
				PredictSequence pd = (PredictSequence) oitr.next();
				PredictSequenceManager pm = pd.ExtendOneSentence(alc, normalExtendSize);
				Iterator<Sequence> itr = pm.Iterator();
				while (itr.hasNext())
				{
					PredictSequence opd = (PredictSequence) itr.next();
					if (opd.isOver())
					{
						result.AddOnePredict(opd, finalsize);
					}
					else
					{
						tempolevel.AddOnePredict(opd, maxextendsize);
					}
				}
			}
			olevel = tempolevel;
		}
		return result;
	}
	
	private List<String> DoRealCodeSynthesis(SimplifiedCodeGenerateASTVisitor fmastv, PredictSequenceManager pm) {
		// TODO Auto-generated method stub
		
		return null;
	}
	
	private PreTrySequenceManager DoPreTrySequencePredict(AeroLifeCycle alc, PreTrySequenceManager manager, String ons)
	{
		// TODO
		if (getExactseq() == null)
		{
			setExactseq(new Sequence(true));
		}
		getExactseq().HandleNewInDirectlyToAddOneSentence(ons);
		
		if (getExactmatch() == null)
		{
			// first line.
			setExactmatch(new Sequence(true));
			getExactmatch().HandleNewInDirectlyToAddOneSentence(ons);
			return this;
		}
		else
		{
			ArrayList<SequenceManager> smarray = new ArrayList<SequenceManager>();
			int existSize = 1 + getNotexactmatch().size();
			int averagePredict = PredictMetaInfo.PredictMaxSequence / existSize;
			SequenceManager sm = getExactmatch().HandleNewInSentence(alc, ons, averagePredict + 5);
			smarray.add(sm);
			
			Iterator<Sequence> itr = getNotexactmatch().iterator();
			int isize = existSize;
			while (itr.hasNext())
			{
				isize--;
				Sequence seq = itr.next();
				smarray.add(seq.HandleNewInSentence(alc, ons, averagePredict + (int)(5*(isize*1.0/(existSize*1.0)))));
			}
			return new SequenceManager(smarray, getExactseq());
		}
	}
	
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