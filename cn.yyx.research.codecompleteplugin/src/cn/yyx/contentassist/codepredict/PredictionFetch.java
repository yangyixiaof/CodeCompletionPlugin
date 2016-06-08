package cn.yyx.contentassist.codepredict;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;

import cn.yyx.contentassist.aerospikehandle.AeroLifeCycle;
import cn.yyx.contentassist.codecompletion.AeroMetaData;
import cn.yyx.contentassist.codecompletion.PredictMetaInfo;
import cn.yyx.contentassist.codesynthesis.flowline.CodeSynthesisFlowLines;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineHelper;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.PreTryFlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.PreTryFlowLines;
import cn.yyx.contentassist.codeutils.statement;
import cn.yyx.contentassist.commonutils.ASTOffsetInfo;
import cn.yyx.contentassist.commonutils.ContextHandler;
import cn.yyx.contentassist.commonutils.ListHelper;
import cn.yyx.contentassist.commonutils.StatementsMIs;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TKey;
import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetRefHandler;

public class PredictionFetch {
	
	public List<String> FetchPrediction(JavaContentAssistInvocationContext javacontext, ScopeOffsetRefHandler handler, List<String> analist, ArrayList<String> result, ASTOffsetInfo aoi)
	{
		AeroLifeCycle alc = AeroLifeCycle.GetInstance();
		
		int size = analist.size();
		if (size > PredictMetaInfo.PrePredictWindow) {
			analist = analist.subList(size - PredictMetaInfo.PrePredictWindow, size);
			size = PredictMetaInfo.PrePredictWindow;
		}
		
		LinkedList<Sentence> setelist = SentenceHelper.TranslateStringsToSentences(analist);
		final Class<?> lastkind = setelist.getLast().getSmt().getClass();
		StatementsMIs smtmis = SentenceHelper.TranslateSentencesToStatements(setelist);
		// PreTryFlowLines<Sentence> fls = new PreTryFlowLines<Sentence>();
		PreTryFlowLines<Sentence> fls = DoPreTrySequencePredict(alc, setelist, smtmis, lastkind);// fls, PredictMetaInfo.PreTryNeedSize, 
		fls.TrimOverTails(PredictMetaInfo.PreTryNeedSize);
		
		ContextHandler ch = new ContextHandler(javacontext);
		SynthesisHandler sh = new SynthesisHandler(handler, ch);
		// List<CodeSynthesisFlowLines> csfll = DoRealCodePredictAndSynthesis(sh, alc, fls, aoi);
		List<CodeSynthesisFlowLines> csfll = DoRealCodePredictAndSynthesisInSerial(sh, alc, fls, aoi);
		
		List<String> list = new LinkedList<String>();
		Iterator<CodeSynthesisFlowLines> itr = csfll.iterator();
		while (itr.hasNext())
		{
			CodeSynthesisFlowLines csfl = itr.next();
			list.addAll(0, csfl.GetSynthesisOverCode());
		}
		return list;
	}
	
	protected List<CodeSynthesisFlowLines> DoRealCodePredictAndSynthesis(SynthesisHandler sh, AeroLifeCycle alc, PreTryFlowLines<Sentence> fls, ASTOffsetInfo aoi) {
		List<CodeSynthesisFlowLines> csfll = new LinkedList<CodeSynthesisFlowLines>();
		List<CodeSynthesisPredictTask> csptl = new LinkedList<CodeSynthesisPredictTask>();
		List<PreTryFlowLineNode<Sentence>> ots = fls.getOvertails();
		Iterator<PreTryFlowLineNode<Sentence>> otsitr = ots.iterator();
		int alen = AeroMetaData.codengram.length;
		int aidx = 0;
		while (otsitr.hasNext())
		{
			PreTryFlowLineNode<Sentence> fln = otsitr.next();
			aidx++;
			if (aidx > alen)
			{
				break;
			}
			CodeSynthesisFlowLines csfl = new CodeSynthesisFlowLines();
			csptl.add(new CodeSynthesisPredictTask(fln, sh, alc, csfl, aoi, AeroMetaData.codengram[aidx-1]));
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
	
	/**
	 * just for test use.
	 * @param sh
	 * @param alc
	 * @param fls
	 * @param aoi
	 * @return
	 */
	protected List<CodeSynthesisFlowLines> DoRealCodePredictAndSynthesisInSerial(SynthesisHandler sh, AeroLifeCycle alc, PreTryFlowLines<Sentence> fls, ASTOffsetInfo aoi) {
		List<CodeSynthesisFlowLines> csfll = new LinkedList<CodeSynthesisFlowLines>();
		List<CodeSynthesisPredictTask> csptl = new LinkedList<CodeSynthesisPredictTask>();
		List<PreTryFlowLineNode<Sentence>> ots = fls.getOvertails();
		Iterator<PreTryFlowLineNode<Sentence>> otsitr = ots.iterator();
		int alen = AeroMetaData.codengram.length;
		int aidx = 0;
		while (otsitr.hasNext())
		{
			PreTryFlowLineNode<Sentence> fln = otsitr.next();
			aidx++;
			if (aidx > alen)
			{
				break;
			}
			CodeSynthesisFlowLines csfl = new CodeSynthesisFlowLines();
			csptl.add(new CodeSynthesisPredictTask(fln, sh, alc, csfl, aoi, AeroMetaData.codengram[aidx-1]));
			csfll.add(csfl);
		}
		Iterator<CodeSynthesisPredictTask> csptlitr = csptl.iterator();
		while (csptlitr.hasNext())
		{
			CodeSynthesisPredictTask cspt = csptlitr.next();
			cspt.run();
		}
		return csfll;
	}
	
	protected PreTryFlowLines<Sentence> DoPreTrySequencePredict(AeroLifeCycle alc, final List<Sentence> setelist,
			final StatementsMIs smtmis, final Class<?> lastkind) {
		List<PreTryFlowLineNode<Sentence>> ots = new LinkedList<PreTryFlowLineNode<Sentence>>();
		final List<statement> smtlist = smtmis.getSmts();
		int smtsize = smtlist.size();
		final List<statement> smilist = smtmis.getSmis();
		int trysize = PredictMetaInfo.NgramMaxSize/2;
		for (int i=0;i<trysize;i++)
		{
			PreTryFlowLines<Sentence> fls = new PreTryFlowLines<Sentence>();
			DoOnePreTrySequencePredict(alc, fls, setelist.subList(i, smtsize), smtlist.subList(i, smtsize), smilist, lastkind);
			fls.TrimOverTails(PredictMetaInfo.PreTryNeedSize);
			List<PreTryFlowLineNode<Sentence>> ot = fls.getOvertails();
			ots.addAll(ot);
		}
		return new PreTryFlowLines<Sentence>(ots);
	}
	
	protected void DoOnePreTrySequencePredict(AeroLifeCycle alc, PreTryFlowLines<Sentence> fls, List<Sentence> setelist,
			List<statement> smtlist, final List<statement> smtmilist, final Class<?> lastkind) { // int needsize,
		boolean first = true;
		Iterator<Sentence> itr = setelist.iterator();
		while (itr.hasNext())
		{
			Sentence ons = itr.next();
			if (!first)
			{
				DoOneRoundPreTrySequencePredict(alc, fls, setelist, smtlist, smtmilist, lastkind);
			}
			if (first)
			{
				fls.InitialSeed(ons);
				first = false;
			}
		}
		int size = fls.GetValidOveredSize();
		int turn = 0;
		while ((size == 0) && turn < PredictMetaInfo.PreTryMaxExtendStep)
		{
			turn++;
			DoOneRoundPreTrySequencePredict(alc, fls, setelist, smtlist, smtmilist, lastkind);
			size = fls.GetValidOveredSize();
		}
	}
	
	protected void DoOneRoundPreTrySequencePredict(AeroLifeCycle alc, PreTryFlowLines<Sentence> fls, List<Sentence> setelist,
			List<statement> smtlist, final List<statement> smtmilist, final Class<?> lastkind) { // int needsize,
		List<FlowLineNode<Sentence>> tails = fls.getTails();
		int tailsize = tails.size();
		int avgsize = (int)Math.ceil((tailsize*1.0)/(AeroMetaData.codengram.length*1.0));
		if (avgsize == 0)
		{
			avgsize = 1;
		}
		List<PreTryPredictTask> ptpts = new LinkedList<PreTryPredictTask>();
		int count = 0;
		int taskid = 0;
		List<TKey> keys = new LinkedList<TKey>();
		List<StatementsMIs> smtmises = new LinkedList<StatementsMIs>();
		Iterator<FlowLineNode<Sentence>> itr = tails.iterator();
		while (itr.hasNext())
		{
			PreTryFlowLineNode<Sentence> fln = (PreTryFlowLineNode<Sentence>) itr.next();
			
			StatementsMIs smtmis = FlowLineHelper.LastToFirstStatementQueueWithMethodInvocationExtracted(fln);
			smtmises.add(smtmis);
			
			List<Sentence> ls = FlowLineHelper.LastNeededSentenceQueue(PredictMetaInfo.NgramMaxSize-1, fln, null);
			TKey tkey = ListHelper.ConcatJoin(ls);
			keys.add(tkey);
			
			count++;
			if (count >= avgsize)
			{
				taskid++;
				PreTryPredictTask ptpt = new PreTryPredictTask(taskid, alc, keys, smtmises);
				ptpts.add(ptpt);
				keys.clear();
				smtmises.clear();
				count = 0;
			}
		}
		
		List<Thread> llths = new LinkedList<Thread>();
		Iterator<PreTryPredictTask> ptptitr = ptpts.iterator();
		while (ptptitr.hasNext())
		{
			PreTryPredictTask ptpt = ptptitr.next();
			Thread nt = new Thread(ptpt);
			nt.start();
			llths.add(nt);
		}
		
		Queue<PreTryFlowLineNode<Sentence>> pppqueue = new PriorityQueue<PreTryFlowLineNode<Sentence>>();
		Iterator<Thread> llitr = llths.iterator();
		ptptitr = ptpts.iterator();
		while (llitr.hasNext())
		{
			Thread t = llitr.next();
			PreTryPredictTask ptpt = ptptitr.next();
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			List<PreTryFlowLineNode<Sentence>> ls = ptpt.GetResultList();
			pppqueue.addAll(ls);
		}
		
		fls.BeginOperation();
		int ndsize = PredictMetaInfo.PreTryTotalMaxParSize;
		while (ndsize > 0 && (!pppqueue.isEmpty()))
		{
			PreTryFlowLineNode<Sentence> nf = pppqueue.poll();
			
			boolean couldterminate = TerminationHelper.couldTerminate(nf.getData(), lastkind, nf.getParent().getLength()+1, smtlist.size());
			if (couldterminate)
			{
				fls.AddOverFlowLineNode(nf, nf.getParent());
			}
			else
			{
				fls.AddToNextLevel(nf, nf.getParent());
				ndsize--;
			}
		}
		fls.EndOperation();
	}
	
}