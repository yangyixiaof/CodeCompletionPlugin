package cn.yyx.contentassist.codepredict;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import cn.yyx.research.AeroSpikeHandle.AeroLifeCycle;

public class PredictSequence extends Sequence {
	
	protected Queue<Sentence> predicts = new LinkedList<Sentence>();
	protected Stack<Integer> cstack = new Stack<Integer>();
	protected Stack<Sentence> sstack = new Stack<Sentence>();
	protected String syncode = "";
	
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
		this.sstack = (Stack<Sentence>) current.sstack.clone();
		this.syncode = current.syncode;
		this.predicts.add(last);
		last.smt.HandleOverSignal(cstack);
	}
	
	public void PredictStart()
	{
		cstack.push(PredictMetaInfo.AllKindWaitingOver);
	}
	
	public PredictSequenceManager ExtendOneSentence(AeroLifeCycle alc, int neededSize)
	{
		int extendSize = neededSize * 2;
		PredictSequenceManager pm = new PredictSequenceManager();
		SequenceManager pdm = PredictSentences(alc, neededSize);
		Iterator<Sequence> itr = pdm.Iterator();
		while (itr.hasNext())
		{
			Sequence seq = itr.next();
			PredictSequence ps = new PredictSequence(seq, this);
			pm.AddSequence(ps, extendSize);
		}
		return pm;
	}

	public boolean isOver() {
		return cstack.empty();
	}
	
	public String GetSynthesisedCode()
	{
		return syncode;
	}
	
}