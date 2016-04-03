package cn.yyx.contentassist.codeutils;

import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class annotationTypeMemberDeclarationStatement extends statement{
	
	type type = null;
	referedExpression drexp = null;
	
	public annotationTypeMemberDeclarationStatement(type type, referedExpression drexp) {
		this.type = type;
		this.drexp = drexp;
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
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler, CSNode result,
			AdditionalInfo ai) {
		CSNode rdexpcn = new CSNode(CSNodeType.ReferedExpression);
		boolean conflict = false;
		if (drexp != null)
		{
			conflict = drexp.HandleCodeSynthesis(squeue, expected, handler, rdexpcn, ai);
		}
		if (conflict)
		{
			return true;
		}
		CSNode tcn = new CSNode(CSNodeType.ReferedExpression);
		conflict = this.type.HandleCodeSynthesis(squeue, expected, handler, tcn, ai);
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

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue)
			throws CodeSynthesisException {
		// TODO Auto-generated method stub
		List<FlowLineNode<CSFlowLineData>> tps = type.HandleCodeSynthesis(squeue);
		if (drexp != null)
		{
			List<FlowLineNode<CSFlowLineData>> drs = drexp.HandleCodeSynthesis(squeue);
			
		}
		
		return null;
	}

	@Override
	public void HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		// do nothing.
	}
	
}