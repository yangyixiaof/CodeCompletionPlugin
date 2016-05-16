package cn.yyx.contentassist.codepredict;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;

import cn.yyx.contentassist.aerospikehandle.AeroLifeCycle;
import cn.yyx.contentassist.aerospikehandle.PredictProbPair;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.VirtualCSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.CodeSynthesisFlowLines;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineHelper;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.flowline.PreTryFlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.PreTryFlowLines;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.codeutils.statement;
import cn.yyx.contentassist.commonutils.ASTOffsetInfo;
import cn.yyx.contentassist.commonutils.ContextHandler;
import cn.yyx.contentassist.commonutils.ListHelper;
import cn.yyx.contentassist.commonutils.ProbabilityComputer;
import cn.yyx.contentassist.commonutils.SimilarityHelper;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TKey;
import cn.yyx.research.language.simplified.JDTHelper.SimplifiedCodeGenerateASTVisitor;
import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetRefHandler;

public class PredictionFetch {
	
	public List<String> FetchPredictionInSerial(JavaContentAssistInvocationContext javacontext, IProgressMonitor monitor, SimplifiedCodeGenerateASTVisitor fmastv, List<String> analist, ArrayList<String> result, ASTOffsetInfo aoi, char lastchar)
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
		DoPreTrySequencePredict(alc, fls, setelist, smtlist, PredictMetaInfo.PreTryNeedSize, lastchar);
		
		ScopeOffsetRefHandler handler = fmastv.GenerateScopeOffsetRefHandler();
		ContextHandler ch = new ContextHandler(javacontext, monitor);
		SynthesisHandler sh = new SynthesisHandler(handler, ch);
		CodeSynthesisFlowLines csfl = new CodeSynthesisFlowLines();
		DoRealCodePredictAndSynthesis(sh, alc, fls, csfl, aoi);
		
		alc.Destroy();
		alc = null;
		List<String> list = csfl.GetSynthesisedCode();
		return list;
	}

	private void DoRealCodePredictAndSynthesis(SynthesisHandler sh, AeroLifeCycle alc, PreTryFlowLines<Sentence> fls, CodeSynthesisFlowLines csfl, ASTOffsetInfo aoi) {
		
		// TODO every item from fls must be considered separately, I am sure this implementation could not do this.
		
		
		
		DoFirstRealCodePredictAndSynthesis(sh, alc, fls, csfl, aoi);
		// normal extend.
		int extendtimes = 1;
		while (extendtimes < PredictMetaInfo.MaxExtendLength)
		{
			DoOneRealCodePredictAndSynthesis(alc, csfl, aoi);
		}
	}
	
	private void DoFirstRealCodePredictAndSynthesis(SynthesisHandler sh, AeroLifeCycle alc, PreTryFlowLines<Sentence> fls, CodeSynthesisFlowLines csfl, ASTOffsetInfo aoi) {
		// first level initial the CodeSynthesisFlowLine.
		csfl.BeginOperation();
		
		VirtualCSFlowLineQueue vcsdflq = new VirtualCSFlowLineQueue(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(-1, null, null, null, false, false, TypeComputationKind.NoOptr, TypeComputationKind.NoOptr, sh), 0));
		List<PreTryFlowLineNode<Sentence>> ots = fls.getOvertails();
		Iterator<PreTryFlowLineNode<Sentence>> itr = ots.iterator();
		while (itr.hasNext())
		{
			FlowLineNode<Sentence> fln = itr.next();
			List<Sentence> ls = FlowLineHelper.LastNeededSentenceQueue(fln, PredictMetaInfo.NgramMaxSize-1);
			List<PredictProbPair> pps = PredictHelper.PredictSentences(alc, ls, PredictMetaInfo.ExtendFinalMaxSequence);
			HandleExtendOneCodeSynthesis(pps, vcsdflq, fln, csfl, aoi);
		}
		
		csfl.EndOperation();
	}
	
	/**
	 * for second and beyond turns.
	 * @param alc
	 * @param fls
	 * @param csfl
	 */
	private void DoOneRealCodePredictAndSynthesis(AeroLifeCycle alc, CodeSynthesisFlowLines csfl, ASTOffsetInfo aoi) {
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
			HandleExtendOneCodeSynthesis(pps, csdflq, tail, csfl, aoi);
		}
		
		csfl.EndOperation();
	}
	
	@SuppressWarnings("unchecked")
	private void HandleExtendOneCodeSynthesis(List<PredictProbPair> pps, CSFlowLineQueue csdflq, FlowLineNode<?> fln, CodeSynthesisFlowLines csfl, ASTOffsetInfo aoi)
	{
		// does VirtualCSFlowLineQueue handled?
		Iterator<PredictProbPair> pitr = pps.iterator();
		while (pitr.hasNext())
		{
			PredictProbPair ppp = pitr.next();
			Sentence pred = ppp.getPred();
			CSStatementHandler csh = new CSStatementHandler(pred, ppp.getProb(), aoi);
			statement predsmt = pred.getSmt();
			try {
				List<FlowLineNode<CSFlowLineData>> addnodes = predsmt.HandleCodeSynthesis(csdflq, csh);
				if (addnodes != null && addnodes.size() > 0)
				{
					Iterator<FlowLineNode<CSFlowLineData>> aitr = addnodes.iterator();
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
	
	// split line, below are pre try predict.
	
	private void DoPreTrySequencePredict(AeroLifeCycle alc, PreTryFlowLines<Sentence> fls, List<Sentence> setelist,
			List<statement> smtlist, int needsize, final char lastchar) {
		Map<String, Boolean> keynull = new TreeMap<String, Boolean>();
		
		Iterator<Sentence> itr = setelist.iterator();
		while (itr.hasNext())
		{
			Sentence ons = itr.next();
			DoOnePreTrySequencePredict(alc, fls, ons, smtlist, (int)(needsize), 2*needsize, lastchar, keynull);
		}
		int size = fls.GetValidOveredSize();
		int turn = 0;
		while (size < ((int)(needsize)) && turn < PredictMetaInfo.PreTryMaxStep)
		{
			turn++;
			DoOnePreTrySequencePredict(alc, fls, null, smtlist, (int)((needsize-size)), 2*(needsize-size), lastchar, keynull);
			size = fls.GetValidOveredSize();
		}
		fls.TrimOverTails(needsize);
		
		keynull.clear();
	}
	
	private void DoOnePreTrySequencePredict(AeroLifeCycle alc, PreTryFlowLines<Sentence> fls, Sentence ons, final List<statement> oraclelist, int neededsize, int maxparsize, final char lastchar, Map<String, Boolean> keynull)
	{
		if (fls.IsEmpty())
		{
			if (ons == null)
			{
				System.err.println("fls isEmpty and ons == null? What the fuck, the system will exit.");
				System.exit(1);
			}
			fls.InitialSeed(ons);
		}
		else {
			fls.BeginOperation();
			
			Map<String, Boolean> handledkey = new TreeMap<String, Boolean>();
			Queue<PreTryFlowLineNode<Sentence>> pppqueue = new PriorityQueue<PreTryFlowLineNode<Sentence>>();
			
			// exact match.
			double maxexactmatchsimilarity = -100000;
			boolean exactmatchhandled = false;
			double tempexactmatchprob = 0;
			
			List<FlowLineNode<Sentence>> tails = fls.getTails();
			List<Integer> sizes = ComputeInferSizes(tails, neededsize, maxparsize);
			Iterator<FlowLineNode<Sentence>> itr = tails.iterator();
			Iterator<Integer> sizeitr = sizes.iterator();
			while (itr.hasNext())
			{
				PreTryFlowLineNode<Sentence> fln = (PreTryFlowLineNode<Sentence>) itr.next();
				int ngramtrim1size = PredictMetaInfo.NgramMaxSize-1;
				int remainsize = sizeitr.next();
				
				Map<String, Boolean> happenedinfer = new TreeMap<String, Boolean>();
				// HandleOneInOneTurnPreTrySequencePredict
				while ((remainsize > 0) && (ngramtrim1size >= 1))
				{
					List<Sentence> ls = FlowLineHelper.LastNeededSentenceQueue(fln, ngramtrim1size);
					
					// update n-gram size.
					ngramtrim1size = ls.size();
					ngramtrim1size--;
					// if key or trim1key return no records, just continue.
					TKey tkey = ListHelper.ConcatJoin(ls);
					String key = tkey.getKey();
					String trim1key = tkey.getTrim1key();
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
					List<PredictProbPair> pps = alc.AeroModelPredict(key, remainsize);
					
					// set the key-null optimized map to speed up.
					if (pps.isEmpty())
					{
						keynull.put(key, true);
					}
					
					Iterator<PredictProbPair> ppsitr = pps.iterator();
					List<statement> triedcmp = FlowLineHelper.LastToFirstStatementQueue(fln);
					while (ppsitr.hasNext())
					{
						PredictProbPair ppp = ppsitr.next();
						Sentence pred = ppp.getPred();
						
						// check whether this prediction has happened.
						if (happenedinfer.containsKey(pred.getSentence()))
						{
							continue;
						}
						else {
							happenedinfer.put(pred.getSentence(), true);
						}
						
						triedcmp.add(pred.getSmt());
						double sim = LCSComparison.LCSSimilarity(oraclelist, triedcmp);
						PreTryFlowLineNode<Sentence> nf = new PreTryFlowLineNode<Sentence>(pred, ppp.getProb() + fln.getProbability(), sim, fln);
						// fls.AddToNextLevel(nf, fln);
						pppqueue.add(nf);
						remainsize--;
						((LinkedList<statement>)triedcmp).removeLast();
						
						// record information which ons and exact match need.
						if (ons != null)
						{
							double similarity = ons.getSmt().Similarity(pred.getSmt());
							if (maxexactmatchsimilarity < similarity)
							{
								maxexactmatchsimilarity = similarity;
								tempexactmatchprob = ppp.getProb();
							}
						}
						
					}
				}
				happenedinfer.clear();
				// HandleOneInOneTurnPreTrySequencePredict(alc, fls, fln, oraclelist, handledkey, neededsize);
			}
			
			
			// sort and put the all one turn infers, exact match handled here.
			int ndsize = neededsize;
			while (ndsize > 0 && (!pppqueue.isEmpty()))
			{
				PreTryFlowLineNode<Sentence> nf = pppqueue.poll();
				
				if (TerminationHelper.couldTerminate(nf.getData(), lastchar))
				{
					fls.AddOverFlowLineNode(nf, nf.getParent());
				}
				else
				{
					fls.AddToNextLevel(nf, nf.getParent());
					ndsize--;
				}
				
				// judge if exact match is handled.
				if (ons != null && ons.getSentence().equals(nf.getData().getSentence()))
				{
					exactmatchhandled = true;
					fls.setExactmatchtail(nf);
				}
			}
			
			if (ons != null && !exactmatchhandled)
			{
				if (ndsize == 0 && neededsize > 0)
				{
					// delete the least probability node.
					fls.DeleteLastAddedNode();
				}
				double enhancedenergy = ProbabilityComputer.ComputeProbability(maxexactmatchsimilarity);
				PreTryFlowLineNode<Sentence> fln = fls.getExactmatchtail();
				PreTryFlowLineNode<Sentence> nf = new PreTryFlowLineNode<Sentence>(ons, tempexactmatchprob + enhancedenergy + fln.getProbability(), ((fln.getLength()+1)*1.0)/(oraclelist.size()*1.0), fln);
				fls.AddToNextLevel(nf, nf.getParent());
				fls.setExactmatchtail(nf);
				fls.MoveTempTailLastToFirst();
			}
			
			fls.EndOperation();
		}
	}
	
	private List<Integer> ComputeInferSizes(List<FlowLineNode<Sentence>> tails, int neededsize, int maxparsize)
	{
		ArrayList<Integer> diffs = new ArrayList<Integer>();
		Iterator<FlowLineNode<Sentence>> itr = tails.iterator();
		ComputeDiffs(itr, null, diffs, 0);
		ArrayList<Integer> sizes = ComputeSizes(diffs, neededsize, maxparsize);
		return sizes;
	}
	
	private ArrayList<Integer> ComputeSizes(ArrayList<Integer> diffs, int neededsize, int maxparsize) {
		ArrayList<Integer> sizes = new ArrayList<Integer>();
		int all = SumIntegerList(diffs);
		Iterator<Integer> itr = diffs.iterator();
		while (itr.hasNext())
		{
			int df = itr.next();
			int sz = (int)((df*1.0)/(all*1.0)*(maxparsize*1.0));
			if (sz == 0)
			{
				sz = 1;
			}
			if (sz > neededsize)
			{
				sz = neededsize;
			}
			sizes.add(sz);
		}
		int szall = SumIntegerList(sizes);
		if (szall > maxparsize)
		{
			int pregap = 0;
			int gap = szall - maxparsize;
			while (gap > 0 && pregap != gap)
			{
				int sslen = sizes.size();
				for (int i=0;i<sslen-1 && gap > 0;i++)
				{
					if (sizes.get(i) > sizes.get(i+1))
					{
						sizes.set(i, sizes.get(i)-1);
						gap--;
					}
					if (gap > 0 && i == sslen-2)
					{
						if (sizes.get(i+1) > 1)
						{
							sizes.set(i+1, sizes.get(i+1)-1);
							gap--;
						}
					}
				}
				pregap = gap;
			}
		} else {
			int pregap = 0;
			int gap = maxparsize - szall;
			while (gap > 0 && pregap != gap)
			{
				int sslen = sizes.size();
				for (int i=0;i<sslen-1 && gap > 0;i++)
				{
					if (sizes.get(i) < neededsize)
					{
						sizes.set(i, sizes.get(i)+1);
						gap--;
					}
				}
				pregap = gap;
			}
		}
		return sizes;
	}
	
	private int SumIntegerList(List<Integer> ils)
	{
		int total = 0;
		Iterator<Integer> itr = ils.iterator();
		while (itr.hasNext())
		{
			total += itr.next();
		}
		return total;
	}
	
	private int ComputeDiffs(Iterator<FlowLineNode<Sentence>> itr, PreTryFlowLineNode<Sentence> flnpre, ArrayList<Integer> diffs, int idx)
	{
		int onediff = 0;
		PreTryFlowLineNode<Sentence> fln = null;
		if (itr.hasNext())
		{
			fln = (PreTryFlowLineNode<Sentence>) itr.next();
			diffs.add(1);
			onediff = ComputeDiffs(itr, fln, diffs, idx+1);
			diffs.set(idx, 1+onediff);
		}
		else {
			return 0;
		}
		int rtdiff = onediff;
		if (flnpre != null)
		{
			if (!SimilarityHelper.CouldThoughtTwoDoubleSame(flnpre.getSeqencesimilarity(), fln.getSeqencesimilarity()))
			{
				rtdiff++;
			}
		}
		return rtdiff;
	}
	
}