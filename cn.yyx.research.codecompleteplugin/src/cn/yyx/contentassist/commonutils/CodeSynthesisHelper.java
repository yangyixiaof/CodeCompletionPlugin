package cn.yyx.contentassist.commonutils;

import java.util.Stack;

import cn.yyx.contentassist.codeutils.identifier;

public class CodeSynthesisHelper {
	
	/*public static boolean HandleRawTextSynthesis(String text, CodeSynthesisQueue squeue, SynthesisHandler handler,
			StringBuilder result, AdditionalInfo ai)
	{
		if (result != null)
		{
			result.append(text);
		}
		else
		{
			ErrorUtil.ErrorAndStop("What the fuch the rawText put where?");
		}
		return false;
	}*/
	
	public static boolean HandleBreakContinueCodeSynthesis(identifier id, CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai, String wheretp)
	{
		StringBuilder fin = new StringBuilder(wheretp);
		CSNode csn = new CSNode(CSNodeType.TempUsed);
		boolean conflict = id.HandleCodeSynthesis(squeue, expected, handler, csn, null);
		if (conflict)
		{
			return true;
		}
		fin.append(wheretp + " " + csn.GetFirstDataWithoutTypeCheck());
		CSNode cs = new CSNode(CSNodeType.WholeStatement);
		cs.AddPossibleCandidates(fin.toString(), null);
		squeue.add(cs);
		return false;
	}
	
	public static String GenerateDimens(int count)
	{
		StringBuilder sb = new StringBuilder("");
		for (int i=0;i<count;i++)
		{
			sb.append("[]");
		}
		return sb.toString();
	}
	
}