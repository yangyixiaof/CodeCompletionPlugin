package cn.yyx.contentassist.codepredict;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import cn.yyx.contentassist.codesynthesis.CodeSynthesisFlowLine;
import cn.yyx.contentassist.commonutils.StructureSignalMetaInfo;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.research.AeroSpikeHandle.AeroLifeCycle;

public class PredictSequence extends Sequence {
	
	protected Queue<Sentence> predicts = new LinkedList<Sentence>();
	protected Stack<Integer> cstack = new Stack<Integer>();
	protected CodeSynthesisFlowLine csfl = new CodeSynthesisFlowLine();
	// protected CodeSynthesisQueue sstack = new CodeSynthesisQueue();
	// protected Stack<TypeCheck> tpstack = new Stack<TypeCheck>();
	
	public PredictSequence(Sequence hint) {
		this.last = hint.last;
		this.sequence = hint.sequence;
		this.prob = hint.prob;
	}
	
	@SuppressWarnings("unchecked")
	public PredictSequence(Sequence hint, PredictSequence current) throws CloneNotSupportedException {
		this.last = hint.last;
		this.sequence = hint.sequence;
		this.prob = hint.prob;
		this.predicts = (Queue<Sentence>) ((LinkedList<Sentence>)(current.predicts)).clone();
		this.cstack = (Stack<Integer>) current.cstack.clone();
		// this.csfl = (CodeSynthesisFlowLine) current.csfl.clone();
		// this.sstack = (CodeSynthesisQueue) current.sstack.clone();
		// this.tpstack = (Stack<TypeCheck>) tpstack.clone();
	}
	
	public void PredictStart()
	{
		cstack.push(StructureSignalMetaInfo.AllKindWaitingOver);
	}
	
	private boolean HandleNewInSentence(SynthesisHandler handler)
	{
		this.predicts.add(last);
		boolean conflict = last.getSmt().HandleOverSignal(cstack);
		if (conflict)
		{
			return true;
		}
		conflict = csfl.ExtendOneSentence(last.getSmt());
		// conflict = last.smt.HandleCodeSynthesis(sstack, tpstack, handler, null, null);
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
			PredictSequence ps = null;
			try {
				ps = new PredictSequence(seq, this);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				continue;
			}
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
	
	public List<String> GetSynthesisedCode()
	{
		return csfl.GetSynthesisedCode();
	}
	
}