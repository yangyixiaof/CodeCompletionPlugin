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
import cn.yyx.contentassist.codesynthesis.flowline.CodeSynthesisFlowLines;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineHelper;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.PreTryFlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.PreTryFlowLines;
import cn.yyx.contentassist.codeutils.statement;
import cn.yyx.contentassist.commonutils.ASTOffsetInfo;
import cn.yyx.contentassist.commonutils.ContextHandler;
import cn.yyx.contentassist.commonutils.ProbabilityComputer;
import cn.yyx.contentassist.commonutils.SimilarityHelper;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
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
		List<CodeSynthesisFlowLines> csfll = DoRealCodePredictAndSynthesis(sh, alc, fls, aoi);
		
		alc.Destroy();
		alc = null;
		
		List<String> list = new LinkedList<String>();
		Iterator<CodeSynthesisFlowLines> itr = csfll.iterator();
		while (itr.hasNext())
		{
			CodeSynthesisFlowLines csfl = itr.next();
			list.addAll(0, csfl.GetSynthesisedCode());
		}
		return list;
	}

	private List<CodeSynthesisFlowLines> DoRealCodePredictAndSynthesis(SynthesisHandler sh, AeroLifeCycle alc, PreTryFlowLines<Sentence> fls, ASTOffsetInfo aoi) {
		
		// TODO every item from fls must be considered separately, I am sure this implementation could not do this.
		List<CodeSynthesisFlowLines> csfll = new LinkedList<CodeSynthesisFlowLines>();
		List<CodeSynthesisPredictTask> csptl = new LinkedList<CodeSynthesisPredictTask>();
		List<PreTryFlowLineNode<Sentence>> ots = fls.getOvertails();
		Iterator<PreTryFlowLineNode<Sentence>> otsitr = ots.iterator();
		while (otsitr.hasNext())
		{
			PreTryFlowLineNode<Sentence> fln = otsitr.next();
			CodeSynthesisFlowLines csfl = new CodeSynthesisFlowLines();
			csptl.add(new CodeSynthesisPredictTask(fln, sh, alc, csfl, aoi));
			csfll.add(csfl);
		}
		List<Thread> tl = new LinkedList<Thread>();
		Iterator<CodeSynthesisPredictTask> csptlitr = csptl.iterator();
		while (csptlitr.hasNext())
		{
			CodeSynthesisPredictTask cspt = csptlitr.next();
			Thread t = new Thread(cspt);
			tl.add(t);
			t.start();
		}
		Iterator<Thread> tlitr = tl.iterator();
		while (tlitr.hasNext())
		{
			Thread t = tlitr.next();
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return csfll;
	}
	
	// split line, below are pre try predict.
	
	private void DoPreTrySequencePredict(AeroLifeCycle alc, PreTryFlowLines<Sentence> fls, List<Sentence> setelist,
			List<statement> smtlist, int needsize, final char lastchar) {
		PredictInfer pi = new PredictInfer();
		// Map<String, Boolean> keynull = new TreeMap<String, Boolean>();
		
		Iterator<Sentence> itr = setelist.iterator();
		while (itr.hasNext())
		{
			Sentence ons = itr.next();
			DoOnePreTrySequencePredict(alc, fls, ons, smtlist, (int)(needsize), 2*needsize, lastchar, pi);
		}
		int size = fls.GetValidOveredSize();
		int turn = 0;
		while (size < ((int)(needsize)) && turn < PredictMetaInfo.PreTryMaxStep)
		{
			turn++;
			DoOnePreTrySequencePredict(alc, fls, null, smtlist, (int)((needsize-size)), 2*(needsize-size), lastchar, pi);
			size = fls.GetValidOveredSize();
		}
		fls.TrimOverTails(needsize);
		
		// keynull.clear();
		pi.Clear();
		pi = null;
	}
	
	private void DoOnePreTrySequencePredict(AeroLifeCycle alc, PreTryFlowLines<Sentence> fls, Sentence ons, final List<statement> oraclelist, int neededsize, int maxparsize, final char lastchar, PredictInfer pi)
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
			pi.BeginOperation();
			
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
				Map<String, Boolean> happenedinfer = new TreeMap<String, Boolean>();
				List<PredictProbPair> pps = pi.InferNextGeneration(alc, sizeitr.next(), fln, null);
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
			pi.EndOperation();
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