package cn.yyx.contentassist.codepredict;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import cn.yyx.research.AeroSpikeHandle.AeroLifeCycle;

public class Predict implements Comparable<Predict> {
	
	Sequence hint = null;
	Queue<String> predicts = new LinkedList<String>();
	Stack<Integer> cstack = new Stack<Integer>();
	private int kindofpredict;
	private boolean over = false;
	
	public Predict(Sequence hint) {
		this.hint = (Sequence) hint.clone();
		cstack.push(PredictMetaInfo.AllKindWaitingOver);
	}
	
	public PredictManager ExtendOneSentence(AeroLifeCycle alc, int maxsize)
	{
		PredictManager pm = new PredictManager();
		hint.HandleNewInSentence(alc, ons, maxsize);
		int nowsize = predicts.size();
		if (nowsize >= PredictMetaInfo.NgramMaxSize)
		{
			
		}
		
		int ssize = sequence.size();
		Queue<String> useq = (LinkedList<String>) ((LinkedList<String>)sequence).clone();
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
	}
	
	@Override
	public int compareTo(Predict o) {
		return hint.compareTo(o.hint);
	}

	public boolean isOver() {
		return over;
	}

	public void setOver(boolean over) {
		this.over = over;
	}

	public int getKindofpredict() {
		return kindofpredict;
	}

	public void setKindofpredict(int kindofpredict) {
		this.kindofpredict = kindofpredict;
	}
	
}