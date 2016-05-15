package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
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
	
	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		String type = handler.getRecenttype();
		String name = NameConvention.GetAbbreviationOfType(type);
		ScopeOffsetRefHandler schandler = handler.getScopeOffsetRefHandler();
		if (rexp != null)
		{
			String modifidedname = schandler.GenerateNewDeclaredVariable(name, type);
			CSNode cs = new CSNode(CSNodeType.HalfFullExpression);
			boolean conflict = rexp.HandleCodeSynthesis(squeue, expected, handler, cs, ai);
			if (conflict)
			{
				return true;
			}
			Map<String, TypeCheck> po = cs.getDatas();
			if (po.size() >= 1)
			{
				TypeCheck tc = cs.GetFirstTypeCheck();
				if (tc != null && tc.getExpreturntypeclass() != null)
				{
					schandler.DeleteRecentlyAddedType(type);
					modifidedname = schandler.GenerateNewDeclaredVariable(NameConvention.GetAbbreviationOfType(tc.getExpreturntype()), tc.getExpreturntype());
				}
			}
			cs.setPrefix(modifidedname+"=");
		}
		else
		{
			String modifidedname = schandler.GenerateNewDeclaredVariable(name, type);
			CSNode cs = new CSNode(CSNodeType.HalfFullExpression);
			cs.AddOneData(modifidedname, null);
			squeue.add(cs);
		}
		return false;
	}*/
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		FlowLineNode<CSFlowLineData> typenode = squeue.SearcheForRecentVariableDeclaredNode();
		if (typenode == null)
		{
			throw new CodeSynthesisException("typenode null, what the fuck?");
		}
		String typecode = typenode.getData().getData();
		String name = NameConvention.GetAbbreviationOfType(typecode);
		List<FlowLineNode<CSFlowLineData>> result = null;
		String modified = null;
		if (rexp != null)
		{
			modified = GetModifiedName(squeue, smthandler, typecode, name);
			List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, smthandler);
			CSFlowLineHelper.ConcateOneFlowLineList(modified + " = ", rels, null);
			result = rels;
		}
		else
		{
			result = new LinkedList<FlowLineNode<CSFlowLineData>>();
			modified = GetModifiedName(squeue, smthandler, typecode, name);
			result.add(new FlowLineNode<CSFlowLineData>(new CSVariableHolderData(modified, squeue.GenerateNewNodeId(), smthandler.getSete(), modified, null, true, true, null, null, squeue.GetLastHandler()), smthandler.getProb()));
		}
		if (modified == null)
		{
			throw new CodeSynthesisException("modified name in variableHolder null, what the fuck?");
		}
		CSFlowLineHelper.AddToEveryRexpParNodeExtraVariableHolderInfo(result, modified);
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