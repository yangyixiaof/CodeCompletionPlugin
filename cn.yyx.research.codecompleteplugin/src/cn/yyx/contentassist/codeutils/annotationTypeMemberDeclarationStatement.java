package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.research.language.simplified.JDTHelper.SimplifiedCodeGenerateASTVisitor;

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
			similar = 0.4 + 0.6*(type.Similarity(((annotationTypeMemberDeclarationStatement) t).type)*0.7 + drexp.Similarity(((annotationTypeMemberDeclarationStatement) t).drexp)*0.3);
		}
		return similar;
	}
	
	@Override
	public String GetCodeText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, SimplifiedCodeGenerateASTVisitor fmastv) {
		// TODO Auto-generated method stub
		return false;
	}
	
}