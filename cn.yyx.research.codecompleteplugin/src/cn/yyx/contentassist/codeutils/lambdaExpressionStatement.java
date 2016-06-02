package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codecompletion.PredictMetaInfo;
import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSLambdaData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineStack;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

public class lambdaExpressionStatement extends statement{
	
	argTypeList typelist = null; //warning: typelist could be null.
	referedExpression rexp = null; //warning: rexp could be null.
	
	public lambdaExpressionStatement(String smtcode, argTypeList tlist, referedExpression rexp) {
		super(smtcode);
		this.typelist = tlist;
		this.rexp = rexp;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof lambdaExpressionStatement)
		{
			if (Similarity(t) > PredictMetaInfo.SequenceSimilarThreshold)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof lambdaExpressionStatement)
		{
			int count = 0;
			if (typelist != null)
			{
				count = typelist.Size();
			}
			int tcount = 0;
			if (((lambdaExpressionStatement) t).typelist != null)
			{
				tcount = ((lambdaExpressionStatement) t).typelist.Size();
			}
			int div = Math.max(count+1, tcount+1);
			int dvi = Math.min(count+1, tcount+1);
			return (dvi*1.0)/(div*1.0);
		}
		return 0;
	}
	
	/*@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		CSNode cs = new CSNode(CSNodeType.HalfFullExpression);
		if (typelist == null)
		{
			cs.AddOneData("()->", null);
		}
		else
		{
			boolean conflict = typelist.HandleCodeSynthesis(squeue, expected, handler, cs, ai);
			if (conflict)
			{
				return true;
			}
			cs.setPrefix("(");
			cs.setPostfix(")->");
		}
		squeue.add(cs);
		return false;
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		List<FlowLineNode<CSFlowLineData>> fres = null;
		if (typelist == null)
		{
			//if (rexp != null)
			//{
			List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, smthandler);
			fres = CSFlowLineHelper.ConcateOneFlowLineList("()->", rels, null);
			/*}
			else
			{
				List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
				result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), "()->", null, null, squeue.GetLastHandler()), smthandler.getProb()));
				fres = result;
			}*/
		}
		else
		{
			//if (rexp != null)
			//{
			List<FlowLineNode<CSFlowLineData>> tpls = typelist.HandleCodeSynthesis(squeue, smthandler);
			List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, smthandler);
			fres = CSFlowLineHelper.ForwardConcate("(", tpls, ")->", rels, null, squeue, smthandler, null);
			/*}
			else
			{
				List<FlowLineNode<CSFlowLineData>> tpls = typelist.HandleCodeSynthesis(squeue, smthandler);
				fres = CSFlowLineHelper.ConcateOneFlowLineList("(", tpls, ")->{\n\n}");
			}*/
		}
		// return ListHelper.AddExtraPropertyToAllListNodes(fres, new CSLambdaProperty());
		if (fres == null || fres.size() == 0)
		{
			return null;
		}
		Iterator<FlowLineNode<CSFlowLineData>> itr = fres.iterator();
		while (itr.hasNext())
		{
			FlowLineNode<CSFlowLineData> fln = itr.next();
			result.add(new FlowLineNode<CSFlowLineData>(new CSLambdaData(GetDeclares(fln.getData().getData()), fln.getData()), fln.getProbability()));
		}
		return result;
	}
	
	private String[] GetDeclares(String data)
	{
		data = data.substring(1, data.indexOf(")->"));
		String[] sts = data.split(",");
		int len = sts.length;
		for (int i=0;i<len;i++)
		{
			sts[i] = sts[i].trim();
		}
		return sts;
	}
	
	@Override
	public boolean HandleOverSignal(FlowLineStack cstack) throws CodeSynthesisException {
		cstack.EnsureAllSignalNull();
		return true;
	}
	
}