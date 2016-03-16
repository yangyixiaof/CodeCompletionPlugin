package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class annotationTypeMemberDeclarationStatement extends statement{
	
	type type = null;
	referedExpression drexp = null;
	
	public annotationTypeMemberDeclarationStatement(type type, referedExpression drexp) {
		this.type = type;
		this.drexp = drexp;
	}
	
	@Override
	public void HandleOverSignal(Stack<Integer> cstack) {
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof annotationTypeMemberDeclarationStatement)
		{
			if (type.CouldThoughtSame(((annotationTypeMemberDeclarationStatement)t).type))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		double similar = 0;
		if (t instanceof annotationTypeMemberDeclarationStatement)
		{
			double drexpsimilar = 1;
			if (drexp != null)
			{
				drexpsimilar = drexp.Similarity(((annotationTypeMemberDeclarationStatement) t).drexp);
			}
			similar = 0.4 + 0.6*(type.Similarity(((annotationTypeMemberDeclarationStatement) t).type)*0.7 + drexpsimilar*0.3);
		}
		return similar;
	}
	
	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SynthesisHandler handler, StringBuilder result, AdditionalInfo ai) {
		StringBuilder rdexpsb = new StringBuilder();
		boolean conflict = false;
		if (drexp != null)
		{
			conflict = drexp.HandleCodeSynthesis(squeue, handler, rdexpsb, ai);
		}
		if (conflict)
		{
			return true;
		}
		StringBuilder tsb = new StringBuilder();
		conflict = this.type.HandleCodeSynthesis(squeue, handler, tsb, ai);
		if (conflict)
		{
			return true;
		}
		squeue.add(tsb.toString()+"()"+(drexp != null ? (" default " + rdexpsb.toString()) : ""));
		return false;
	}
	
}