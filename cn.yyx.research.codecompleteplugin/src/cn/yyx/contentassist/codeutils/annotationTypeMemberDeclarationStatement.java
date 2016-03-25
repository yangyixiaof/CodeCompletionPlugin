package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CSNodeType;
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
	public boolean HandleOverSignal(Stack<Integer> cstack) {
		return false;
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
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, SynthesisHandler handler, CSNode result,
			AdditionalInfo ai) {
		CSNode rdexpcn = new CSNode(CSNodeType.ReferedExpression);
		boolean conflict = false;
		if (drexp != null)
		{
			conflict = drexp.HandleCodeSynthesis(squeue, handler, rdexpcn, ai);
		}
		if (conflict)
		{
			return true;
		}
		CSNode tcn = new CSNode(CSNodeType.ReferedExpression);
		conflict = this.type.HandleCodeSynthesis(squeue, handler, tcn, ai);
		if (conflict)
		{
			return true;
		}
		CSNode res = new CSNode(CSNodeType.WholeStatement);
		String content = tcn.GetFirstDataWithoutTypeCheck()+"()"+(drexp != null ? (" default " + rdexpcn.GetFirstDataWithoutTypeCheck()) : "");
		res.AddPossibleCandidates(content, null);
		squeue.add(res);
		return false;
	}
	
}