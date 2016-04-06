package cn.yyx.contentassist.codeutils;

import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;

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
	
	/*@Override
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
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> tps = type.HandleCodeSynthesis(squeue, smthandler);
		if (drexp != null)
		{
			List<FlowLineNode<CSFlowLineData>> drs = drexp.HandleCodeSynthesis(squeue, smthandler);
			List<FlowLineNode<CSFlowLineData>> cect = CSFlowLineHelper.ConcateTwoFlowLineNodeList(null, tps, "() default", drs, null, TypeComputationKind.NoOptr, squeue, smthandler, null);
			return cect;
		}
		else
		{
			CSFlowLineHelper.ConcateOneFlowLineNodes(null, tps, "()");
			return tps;
		}
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		// do nothing.
		return false;
	}
	
}