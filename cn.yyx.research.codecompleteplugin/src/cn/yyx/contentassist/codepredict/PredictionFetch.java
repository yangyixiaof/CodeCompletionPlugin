package cn.yyx.contentassist.codepredict;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;

import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSFlowLineStamp;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.VirtualCSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.CodeSynthesisFlowLine;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineHelper;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.flowline.PreTryFlowLines;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.codeutils.statement;
import cn.yyx.contentassist.commonutils.ContextHandler;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.research.AeroSpikeHandle.AeroLifeCycle;
import cn.yyx.research.AeroSpikeHandle.PredictProbPair;
import cn.yyx.research.language.simplified.JDTHelper.SimplifiedCodeGenerateASTVisitor;
import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetRefHandler;

public class PredictionFetch {
	
	// public static final int ParallelSize = 10;
	
	/*public List<String> FetchPrediction(JavaContentAssistInvocationContext javacontext, IProgressMonitor monitor, SimplifiedCodeGenerateASTVisitor fmastv, List<String> analist, ArrayList<String> result) {
		
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
	}*/
	
	public List<String> FetchPredictionInSerial(JavaContentAssistInvocationContext javacontext, IProgressMonitor monitor, SimplifiedCodeGenerateASTVisitor fmastv, List<String> analist, ArrayList<String> result)
	{
		AeroLifeCycle alc = new AeroLifeCycle();
		alc.Initialize();
		
		int size = analist.size();
		if (size > PredictMetaInfo.PrePredictWindow) {
			analist = analist.subList(size - PredictMetaInfo.PrePredictWindow, size);
			size = PredictMetaInfo.PrePredictWindow;
		}
		
		List<Sentence> setelist = SentenceHelper.TranslateStringsToSentences(analist);
		List<statement> smtlist = SentenceHelper.TranslateSentencesToStatements(setelist);
		PreTryFlowLines<Sentence> fls = new PreTryFlowLines<Sentence>();
		DoPreTrySequencePredict(alc, fls, setelist, smtlist, PredictMetaInfo.PreTryNeedSize);
		
		ScopeOffsetRefHandler handler = fmastv.GenerateScopeOffsetRefHandler();
		ContextHandler ch = new ContextHandler(javacontext, monitor);
		SynthesisHandler sh = new SynthesisHandler(handler, ch);
		CodeSynthesisFlowLine csfl = new CodeSynthesisFlowLine();
		DoRealCodePredictAndSynthesis(sh, alc, fls, csfl);
		
		alc.Destroy();
		alc = null;
		List<String> list = csfl.GetSynthesisedCode();
		return list;
	}

	private void DoRealCodePredictAndSynthesis(SynthesisHandler sh, AeroLifeCycle alc, PreTryFlowLines<Sentence> fls, CodeSynthesisFlowLine csfl) {
		DoFirstRealCodePredictAndSynthesis(sh, alc, fls, csfl);
		// normal extend.
		int extendtimes = 1;
		while (extendtimes < PredictMetaInfo.MaxExtendLength)
		{
			DoOneRealCodePredictAndSynthesis(alc, csfl);
		}
	}
	
	private void DoFirstRealCodePredictAndSynthesis(SynthesisHandler sh, AeroLifeCycle alc, PreTryFlowLines<Sentence> fls, CodeSynthesisFlowLine csfl) {
		// first level initial the CodeSynthesisFlowLine.
		csfl.BeginOperation();
		
		VirtualCSFlowLineQueue vcsdflq = new VirtualCSFlowLineQueue(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(-1, null, null, null, false, false, TypeComputationKind.NoOptr, TypeComputationKind.NoOptr, sh), 0));
		List<FlowLineNode<Sentence>> ots = fls.getOvertails();
		Iterator<FlowLineNode<Sentence>> itr = ots.iterator();
		while (itr.hasNext())
		{
			FlowLineNode<Sentence> fln = itr.next();
			List<Sentence> ls = FlowLineHelper.LastNeededSentenceQueue(fln, PredictMetaInfo.NgramMaxSize-1);
			List<PredictProbPair> pps = PredictHelper.PredictSentences(alc, ls, PredictMetaInfo.ExtendFinalMaxSequence);
			HandleExtendOneCodeSynthesis(pps, vcsdflq, fln, csfl);
		}
		
		csfl.EndOperation();
	}
	
	/**
	 * for second and beyond turns.
	 * @param alc
	 * @param fls
	 * @param csfl
	 */
	private void DoOneRealCodePredictAndSynthesis(AeroLifeCycle alc, CodeSynthesisFlowLine csfl) {
		csfl.BeginOperation();
		
		List<FlowLineNode<CSFlowLineData>> tails = csfl.getTails();
		Iterator<FlowLineNode<CSFlowLineData>> itr = tails.iterator();
		while (itr.hasNext())
		{
			FlowLineNode<CSFlowLineData> tail = itr.next();
			if (!tail.isCouldextend())
			{
				continue;
			}
			List<Sentence> ls = FlowLineHelper.LastNeededSentenceQueue(tail, csfl, PredictMetaInfo.NgramMaxSize-1);
			List<PredictProbPair> pps = PredictHelper.PredictSentences(alc, ls, PredictMetaInfo.ExtendFinalMaxSequence);
			CSFlowLineQueue csdflq = new CSFlowLineQueue(tail);
			HandleExtendOneCodeSynthesis(pps, csdflq, tail, csfl);
		}
		
		csfl.EndOperation();
	}
	
	@SuppressWarnings("unchecked")
	private void HandleExtendOneCodeSynthesis(List<PredictProbPair> pps, CSFlowLineQueue csdflq, FlowLineNode<?> fln, CodeSynthesisFlowLine csfl)
	{
		Iterator<PredictProbPair> pitr = pps.iterator();
		while (pitr.hasNext())
		{
			PredictProbPair ppp = pitr.next();
			Sentence pred = ppp.getPred();
			CSStatementHandler csh = new CSStatementHandler(pred, ppp.getProb());
			statement predsmt = pred.getSmt();
			try {
				CSFlowLineStamp addnodes = predsmt.HandleCodeSynthesis(csdflq, csh);
				if (addnodes != null && addnodes.Size() > 0)
				{
					Iterator<FlowLineNode<CSFlowLineData>> aitr = addnodes.Iterator();
					while (aitr.hasNext())
					{
						FlowLineNode<CSFlowLineData> addnode = aitr.next();
						try
						{
							boolean over = predsmt.HandleOverSignal(new FlowLineStack(addnode));
							addnode.setCouldextend(!over);
						}  catch (CodeSynthesisException e) {
							// testing
							System.err.println("Error occurs when doing code synthesis, this predict and the following will be ignored.");
							e.printStackTrace();
							continue;
						}
						if (fln != null)
						{
							csfl.AddToFirstLevel(addnode, (FlowLineNode<Sentence>) fln);
						}
						else
						{
							csfl.AddToNextLevel(addnode, (FlowLineNode<CSFlowLineData>) fln);
						}
					}
				}
			} catch (CodeSynthesisException e) {
				// testing
				System.err.println("Error occurs when doing code synthesis, this predict and the following will be ignored.");
				e.printStackTrace();
				continue;
			}
		}
	}
	
	/*private PredictSequenceManager DoSequencesPredictAndRealCodeSynthesis(SynthesisHandler handler, AeroLifeCycle alc, SequenceManager manager)
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
	}*/
	
	private void DoPreTrySequencePredict(AeroLifeCycle alc, PreTryFlowLines<Sentence> fls, List<Sentence> setelist,
			List<statement> smtlist, int needsize) {
		Iterator<Sentence> itr = setelist.iterator();
		while (itr.hasNext())
		{
			Sentence ons = itr.next();
			DoOnePreTrySequencePredict(alc, fls, ons, smtlist);
		}
		int size = fls.GetOveredSize();
		int turn = 0;
		while (size < needsize && turn < PredictMetaInfo.PreTryMaxStep)
		{
			turn++;
			DoOnePreTrySequencePredict(alc, fls, null, smtlist);
			size = fls.GetOveredSize();
		}
	}

	private void DoOnePreTrySequencePredict(AeroLifeCycle alc, PreTryFlowLines<Sentence> fls, Sentence ons, final List<statement> oraclelist)
	{
		// final int orclesize = oraclelist.size();
		if (fls.IsEmpty())
		{
			assert ons != null;
			fls.InitialSeed(ons);
		}
		else {
			fls.BeginOperation();
			
			List<statement> exactcmp = oraclelist;
			if (ons != null)
			{
				FlowLineHelper.LastToFirstStatementQueueWithAddedStatement(fls.getExactmatchtail(), ons.getSmt());
			}
			List<FlowLineNode<Sentence>> tails = fls.getTails();
			final int existSize = tails.size();
			Iterator<FlowLineNode<Sentence>> itr = tails.iterator();
			int averagePredict = PredictMetaInfo.PredictMaxSequence / existSize;
			int isize = existSize;
			boolean exactmatchhandled = false;
			while (itr.hasNext())
			{
				boolean exactmatch = false;
				final int neededsize = averagePredict + (int)(5*(isize*1.0/(existSize*1.0)));
				int remainsize = neededsize;
				isize--;
				PriorityQueue<PredictProbPair> pppqueue = new PriorityQueue<PredictProbPair>();
				FlowLineNode<Sentence> fln = itr.next();
				if (fln == fls.getExactmatchtail())
				{
					exactmatch = true;
				}
				// Sentence sete = fln.getData();
				List<Sentence> ls = FlowLineHelper.LastNeededSentenceQueue(fln, PredictMetaInfo.NgramMaxSize-1);
				List<PredictProbPair> pps = PredictHelper.PredictSentences(alc, ls, neededsize);
				Iterator<PredictProbPair> ppsitr = pps.iterator();
				List<statement> triedcmp = FlowLineHelper.LastToFirstStatementQueue(fln);
				while (ppsitr.hasNext())
				{
					PredictProbPair ppp = ppsitr.next();
					Sentence pred = ppp.getPred();
					
					// exact match handle.
					if (exactmatch)
					{
						if (ons.Similarity(pred) > PredictMetaInfo.OneSentenceSimilarThreshold)
						{
							exactmatchhandled = true;
							fls.CompareAndSetTempExactMatchInfo(new FlowLineNode<Sentence>(pred, ppp.getProb() + fln.getProbability()));
						}
					}
					
					triedcmp.add(pred.getSmt());
					
					// double prob = ppp.getProb();
					double sim = LCSComparison.LCSSimilarity(exactcmp, triedcmp);
					if (sim > PredictMetaInfo.SequenceSimilarThreshold)
					{
						FlowLineNode<Sentence> nf = new FlowLineNode<Sentence>(pred, ppp.getProb() + fln.getProbability());
						fls.AddToNextLevel(nf, fln);
						remainsize--;
					}
					else
					{
						double sim2 = LCSComparison.LCSSimilarity(oraclelist, triedcmp);
						if (sim2 > PredictMetaInfo.SequenceSimilarThreshold)
						{
							FlowLineNode<Sentence> nf = new FlowLineNode<Sentence>(pred, ppp.getProb() + fln.getProbability());
							fls.AddOverFlowLineNode(nf, fln);
							remainsize--;
						}
						else
						{
							pppqueue.add(ppp);
						}
					}
					
					((LinkedList<statement>)triedcmp).removeLast();
				}
				while (remainsize > 0 && (!pppqueue.isEmpty()))
				{
					PredictProbPair ppp = pppqueue.poll();
					Sentence pred = ppp.getPred();

					FlowLineNode<Sentence> nf = new FlowLineNode<Sentence>(pred, ppp.getProb() + fln.getProbability());
					fls.AddToNextLevel(nf, fln);
					remainsize--;
				}
			}
			if (!exactmatchhandled)
			{
				if (ons != null)
				{
					fls.CompareAndSetTempExactMatchInfo(new FlowLineNode<Sentence>(ons, fls.getExactmatchtail().getProbability()));
				}
				else
				{
					fls.ClearExactMatch();
				}
			}
			
			fls.EndOperation();
			
			/*PreTrySequenceManager sm = null;
			Iterator<Sequence> itr = manager.Iterator();
			while (itr.hasNext())
			{
				PreTrySequence seq = (PreTrySequence) itr.next();
				SequenceManager tempsm = seq.PredictSentences(alc, );
				PreTrySequenceManager ptsm = new PreTrySequenceManager(tempsm, ons, seq.isExactmatch());
				if (sm == null)
				{
					sm = ptsm;
				}
				else
				{
					sm.Merge(ptsm);
				}
			}*/
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