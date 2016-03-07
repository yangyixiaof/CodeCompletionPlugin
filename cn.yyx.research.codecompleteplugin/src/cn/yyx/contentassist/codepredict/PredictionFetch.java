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
		
		PredictManager pm = DoSequencesPredict(alc, manager);
		List<String> list = DoRealCodeSynthesis(fmastv, pm);
		// AeroHelper.testListStrings(2);
		// System.out.println("ArrayListType:" + analist.getClass());
		// System.out.println("ArrayListRealSize:" + analist.size() + ";OSize;" + size + ";They should be the same.");
		
		alc.Destroy();
		alc = null;
		
		return list;
	}
	
	private PredictManager DoSequencesPredict(AeroLifeCycle alc, SequenceManager manager)
	{
		Sequence exactmatch = manager.getExactmatch();
		PredictManager pm = DoOneSequencePredict(alc, exactmatch, PredictMetaInfo.ExtendFinalMaxSequence, PredictMetaInfo.ExtendTempMaxSequence);
		PriorityQueue<Sequence> nemqueue = manager.getNotexactmatch();
		Iterator<Sequence> itr = nemqueue.iterator();
		while (itr.hasNext())
		{
			Sequence s = itr.next();
			PredictManager temppm = DoOneSequencePredict(alc, s, PredictMetaInfo.ExtendFinalMaxSequence, PredictMetaInfo.ExtendTempMaxSequence);
			pm.Merge(temppm);
		}
		pm.Restrain();
		return pm;
	}
	
	private PredictManager DoOneSequencePredict(AeroLifeCycle alc, Sequence oneseq, int finalsize, int maxextendsize)
	{
		int normalExtendSize = (int)Math.max(Math.sqrt(maxextendsize), maxextendsize/2);
		PredictManager result = new PredictManager();
		PredictManager firstlevel = new PredictManager();
		Predict tpd = new Predict(oneseq);
		firstlevel.AddOnePredict(tpd, -1);
		PredictManager olevel = firstlevel;
		while (!result.CouldOver(finalsize) && !olevel.IsEmpty())
		{
			PredictManager tempolevel = new PredictManager();
			Iterator<Predict> oitr = olevel.Iterator();
			while (oitr.hasNext())
			{
				Predict pd = oitr.next();
				PredictManager pm = pd.ExtendOneSentence(alc, normalExtendSize);
				Iterator<Predict> itr = pm.Iterator();
				while (itr.hasNext())
				{
					Predict opd = itr.next();
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
	
	private List<String> DoRealCodeSynthesis(SimplifiedCodeGenerateASTVisitor fmastv, PredictManager pm) {
		// TODO Auto-generated method stub
		
		return null;
	}
	
	private PreTrySequenceManager DoPreTrySequencePredict(AeroLifeCycle alc, PreTrySequenceManager manager, String ons)
	{
		// TODO
		
		PreTrySequenceManager managerresult = manager.HandleANewInSentence(alc, ons);
		return managerresult;
	}
	

	public PreTrySequenceManager HandleANewInSentence(AeroLifeCycle alc, String ons) {
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