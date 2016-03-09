package cn.yyx.contentassist.codeutils;

import java.util.Stack;

public class variableDeclarationHolderStatement extends statement{
	
	referedExpression rexp = null;
	
	public variableDeclarationHolderStatement(referedExpression rexp) {
		this.rexp = rexp;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof variableDeclarationHolderStatement)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		double prob = 0;
		if (t instanceof variableDeclarationHolderStatement)
		{
			if (rexp == null && ((variableDeclarationHolderStatement)t).rexp == null)
			{
				prob = 1;
			}
			else
			{
				if ((rexp != null && ((variableDeclarationHolderStatement)t).rexp == null) || (rexp == null && ((variableDeclarationHolderStatement)t).rexp != null))
				{
					prob = 0.5;
				}
				else
				{
					prob = rexp.Similarity(((variableDeclarationHolderStatement) t).rexp);
				}
			}
			return 0.4 + 0.6*(prob);
		}
		return 0;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
}