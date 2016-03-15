package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class partialEndArrayInitializerStatement extends statement{
	
	expressionStatement es = null;
	
	public partialEndArrayInitializerStatement(expressionStatement es) {
		this.es = es;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof partialEndArrayInitializerStatement)
		{
			return es.CouldThoughtSame(((partialEndArrayInitializerStatement) t).es);
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof partialEndArrayInitializerStatement)
		{
			return 0.2 + 0.8*(es.Similarity(((partialEndArrayInitializerStatement) t).es));
		}
		return 0;
	}

	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}