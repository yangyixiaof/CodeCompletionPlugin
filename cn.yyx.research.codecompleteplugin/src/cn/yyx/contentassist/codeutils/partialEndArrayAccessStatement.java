package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class partialEndArrayAccessStatement extends statement{
	
	expressionStatement es = null;
	
	public partialEndArrayAccessStatement(expressionStatement es) {
		this.es = es;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof partialEndArrayAccessStatement)
		{
			return es.CouldThoughtSame(((partialEndArrayAccessStatement) t).es);
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof partialEndArrayAccessStatement)
		{
			return 0.2 + 0.8*(es.Similarity(((partialEndArrayAccessStatement) t).es));
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}