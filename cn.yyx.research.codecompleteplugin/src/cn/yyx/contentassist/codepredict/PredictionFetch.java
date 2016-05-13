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
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TKey;
import cn.yyx.research.aerospikehandle.AeroLifeCycle;
import cn.yyx.research.aerospikehandle.PredictProbPair;
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
		List<FlowLineNode<Sentence>> ots = fls.getOvertails();
		Iterator<FlowLineNode<Sentence>> itr = ots.iterator();
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
		if (size >= needsize || fls.GetAllOveredSize() > needsize)
		{
			fls.TrimOverTails(needsize);
		}
		
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
				FlowLineNode<Sentence> fln = itr.next();
				int ngramtrim1size = PredictMetaInfo.NgramMaxSize-1;
				int remainsize = sizeitr.next();
				
				// HandleOneInOneTurnPreTrySequencePredict
				while ((remainsize > 0) && (ngramtrim1size >= 1))
				{
					List<Sentence> ls = FlowLineHelper.LastNeededSentenceQueue(fln, ngramtrim1size);
					
					// update n-gram size.
					ngramtrim1size = ls.size();
					ngramtrim1size--;
					// if key or trim1key return no records, just continue.
					TKey tkey = ListHelper.ConcatJoin(ls);
					if (tkey.getTrim1key() != null)
					{
						if (keynull.containsKey(tkey.getTrim1key()) || keynull.containsKey(tkey.getKey()))
						{
							continue;
						}
					}
					
					// record handled key.
					if (handledkey.containsKey(tkey.getKey()))
					{
						break;
					}
					else
					{
						handledkey.put(tkey.getKey(), true);
					}
					
					// not handled key.
					List<PredictProbPair> pps = alc.AeroModelPredict(tkey.getKey(), remainsize);
					
					// set the key-null optimized map to speed up.
					if (pps.isEmpty())
					{
						keynull.put(tkey.getKey(), true);
					}
					
					Iterator<PredictProbPair> ppsitr = pps.iterator();
					List<statement> triedcmp = FlowLineHelper.LastToFirstStatementQueue(fln);
					while (ppsitr.hasNext())
					{
						PredictProbPair ppp = ppsitr.next();
						Sentence pred = ppp.getPred();
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
				FlowLineNode<Sentence> fln = fls.getExactmatchtail();
				PreTryFlowLineNode<Sentence> nf = new PreTryFlowLineNode<Sentence>(ons, tempexactmatchprob + enhancedenergy + fln.getProbability(), ((fln.getLength()+1)*1.0)/(oraclelist.size()*1.0), fln);
				fls.AddToNextLevel(nf, nf.getParent());
				fls.setExactmatchtail(nf);
			}
			
			fls.EndOperation();
		}
	}
	
	private List<Integer> ComputeInferSizes(List<FlowLineNode<Sentence>> tails, int neededsize, int maxparsize)
	{
		List<Integer> infersize = new LinkedList<Integer>();
		int allsize = tails.size();
		int halfallsize = (int)((allsize*1.0)/2);
		if (halfallsize == 0)
		{
			halfallsize = 1;
		}
		int avgsize = (int)((maxparsize*1.0)/(allsize*1.0));
		if (avgsize == 0)
		{
			avgsize = 1;
		}
		int minsize = 2;
		Iterator<FlowLineNode<Sentence>> itr = tails.iterator();
		int idx = 0;
		while (itr.hasNext())
		{
			idx++;
			itr.next();
			if (avgsize <= minsize)
			{
				infersize.add(avgsize + (int)(((idx*1.0)/(allsize*1.0))*3));
			}
			else
			{
				int size = avgsize + (int)((((idx-halfallsize)*1.0)/(halfallsize*1.0))*3);
				if (size < minsize)
				{
					size = minsize;
				}
				if (size > neededsize)
				{
					size = neededsize;
				}
				infersize.add(size);
			}
		}
		return infersize;
	}
	
}