package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineBackTraceGenerationHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSHoleData;
import cn.yyx.contentassist.codesynthesis.data.CSVariableHolderData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.commonutils.NameConvention;

public class variableDeclarationHolderStatement extends statement{
	
	referedExpression rexp = null; // warning: rexp could be null.
	
	public variableDeclarationHolderStatement(String smtcode, referedExpression rexp) {
		super(smtcode);
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
		if (t instanceof variableDeclarationHolderStatement)
		{
			double prob = 0;
			if (rexp == null && ((variableDeclarationHolderStatement)t).rexp == null)
			{
				prob = 1;
			}
			else
			{
				if ((rexp != null && ((variableDeclarationHolderStatement)t).rexp == null) || (rexp == null && ((variableDeclarationHolderStatement)t).rexp != null))
				{
					prob = 0.4;
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
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		FlowLineNode<CSFlowLineData> previousvh = squeue.SearcheForRecentVHolderNode();
		if (previousvh != null)
		{
			CSFlowLineBackTraceGenerationHelper.GenerateSynthesisCode(squeue, smthandler, squeue.getLast(), previousvh);
		}
		FlowLineNode<CSFlowLineData> typenode = squeue.SearcheForRecentVariableDeclaredNode();
		if (typenode == null)
		{
			throw new CodeSynthesisException("typenode null, what the fuck?");
		}
		String typecode = typenode.getData().getData();
		String name = NameConvention.GetAbbreviationOfType(typecode);
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		String modified = null;
		if (rexp != null)
		{
			modified = GetModifiedName(squeue, smthandler, typecode, name);
			List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, smthandler);
			List<FlowLineNode<CSFlowLineData>> tmpls = CSFlowLineHelper.ConcateOneFlowLineList(" " + modified + " = ", rels, null);
			if (tmpls == null || tmpls.size() == 0)
			{
				return null;
			}
			Iterator<FlowLineNode<CSFlowLineData>> itr = tmpls.iterator();
			while (itr.hasNext())
			{
				FlowLineNode<CSFlowLineData> fln = itr.next();
				boolean needensuretype = false;
				CSFlowLineData data = fln.getData();
				if (data instanceof CSHoleData)
				{
					needensuretype = true;
				}
				result.add(new FlowLineNode<CSFlowLineData>(new CSVariableHolderData(modified, needensuretype, data), fln.getProbability()));
			}
		}
		else
		{
			modified = GetModifiedName(squeue, smthandler, typecode, name);
			result.add(new FlowLineNode<CSFlowLineData>(new CSVariableHolderData(modified, false, squeue.GenerateNewNodeId(), smthandler.getSete(), " "+modified, null, true, true, null, null, squeue.GetLastHandler()), smthandler.getProb()));
		}
		if (modified == null)
		{
			throw new CodeSynthesisException("modified name in variableHolder null, what the fuck?");
		}
		// CSFlowLineHelper.AddToEveryRexpParNodeExtraVariableHolderInfo(result, modified);
		return result;
	}
	
	private String GetModifiedName(CSFlowLineQueue squeue, CSStatementHandler smthandler, String typecode, String name) throws CodeSynthesisException
	{
		List<String> holderlist = squeue.SearchForVariableDeclareHolderNames();
		if (holderlist == null)
		{
			throw new CodeSynthesisException("No CSVariableDeclaration Node when handling CSVariableHolder.");
		}
		String modified = squeue.GetLastHandler().getScopeOffsetRefHandler().GenerateNewDeclaredVariable(name, typecode, holderlist, smthandler.getAoi().isInFieldLevel());
		return modified;
	}

	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		return false;
	}
	
}