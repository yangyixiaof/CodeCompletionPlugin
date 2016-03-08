package cn.yyx.contentassist.codepredict;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import cn.yyx.research.AeroSpikeHandle.AeroLifeCycle;

public class PredictSequence extends Sequence {
	
	protected Queue<Sentence> predicts = new LinkedList<Sentence>();
	protected Stack<Integer> cstack = new Stack<Integer>();
	protected int kindofpredict;
	
	public PredictSequence(Sequence hint) {
		this.last = hint.last;
		this.sequence = hint.sequence;
		this.prob = hint.prob;
		cstack.push(PredictMetaInfo.AllKindWaitingOver);
	}
	
	@SuppressWarnings("unchecked")
	public PredictSequenceManager ExtendOneSentence(AeroLifeCycle alc, int neededSize)
	{
		// TODO
		PredictSequenceManager pm = new PredictSequenceManager();
		SequenceManager pdm = PredictSentences(alc, neededSize);
		
		int nowsize = predicts.size();
		if (nowsize >= PredictMetaInfo.NgramMaxSize)
		{
			
		}
		
		int ssize = sequence.size();
		Queue<Sentence> useq = (LinkedList<Sentence>) ((LinkedList<Sentence>)sequence).clone();
		int maxsize = Math.min(ssize, PredictMetaInfo.NgramMaxSize);
		int skip = ssize - maxsize;
		for (int i=0;i<skip;i++)
		{
			useq.poll();
		}
		
		boolean canterminate = false;
		while (!canterminate)
		{
			
		}
		return pm;
	}

	public boolean isOver() {
		return cstack.empty();
	}

	public int getKindofpredict() {
		return kindofpredict;
	}
	
}