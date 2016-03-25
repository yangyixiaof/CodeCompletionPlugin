package cn.yyx.contentassist.commonutils;

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
	
	public static boolean HandleBreakContinueCodeSynthesis(identifier id, CodeSynthesisQueue squeue, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai)
	{
		StringBuilder fin = new StringBuilder("break");
		CSNode csn = new CSNode(CSNodeType.TempUsed);
		boolean conflict = id.HandleCodeSynthesis(squeue, handler, csn, null);
		if (conflict)
		{
			return true;
		}
		fin.append("break " + csn.GetFirstDataWithoutTypeCheck());
		CSNode cs = new CSNode(CSNodeType.WholeStatement);
		cs.AddPossibleCandidates(fin.toString(), null);
		squeue.add(cs);
		return false;
	}
	
}