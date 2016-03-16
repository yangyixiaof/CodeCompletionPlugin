package cn.yyx.contentassist.codepredict;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.research.AeroSpikeHandle.AeroLifeCycle;

public class PredictSequence extends Sequence {
	
	protected Queue<Sentence> predicts = new LinkedList<Sentence>();
	protected Stack<Integer> cstack = new Stack<Integer>();
	protected CodeSynthesisQueue<String> sstack = new CodeSynthesisQueue<String>();
	
	public PredictSequence(Sequence hint) {
		this.last = hint.last;
		this.sequence = hint.sequence;
		this.prob = hint.prob;
	}
	
	@SuppressWarnings("unchecked")
	public PredictSequence(Sequence hint, PredictSequence current) {
		this.last = hint.last;
		this.sequence = hint.sequence;
		this.prob = hint.prob;
		this.predicts = (Queue<Sentence>) ((LinkedList<Sentence>)(current.predicts)).clone();
		this.cstack = (Stack<Integer>) current.cstack.clone();
		this.sstack = (CodeSynthesisQueue<String>) current.sstack.clone();
	}
	
	public void PredictStart()
	{
		cstack.push(PredictMetaInfo.AllKindWaitingOver);
	}
	
	private boolean HandleNewInSentence(SynthesisHandler handler)
	{
		this.predicts.add(last);
		boolean conflict = last.smt.HandleOverSignal(cstack);
		if (conflict)
		{
			return true;
		}
		conflict = last.smt.HandleCodeSynthesis(sstack, handler, null, null);
		return conflict;
	}
	
	public PredictSequenceManager ExtendOneSentence(SynthesisHandler handler, AeroLifeCycle alc, int neededSize)
	{
		int extendSize = neededSize * 2;
		PredictSequenceManager pm = new PredictSequenceManager();
		SequenceManager pdm = PredictSentences(alc, neededSize);
		Iterator<Sequence> itr = pdm.Iterator();
		while (itr.hasNext())
		{
			Sequence seq = itr.next();
			PredictSequence ps = new PredictSequence(seq, this);
			boolean conflict = ps.HandleNewInSentence(handler);
			if (!conflict)
			{
				pm.AddSequence(ps, extendSize);
			}
		}
		return pm;
	}
	
	public boolean isOver()
	{
		return cstack.empty();
	}
	
	public String GetSynthesisedCode()
	{
		return sstack.getFirst();
	}
	
}