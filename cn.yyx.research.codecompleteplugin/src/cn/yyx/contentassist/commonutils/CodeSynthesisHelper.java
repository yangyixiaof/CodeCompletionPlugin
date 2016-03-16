package cn.yyx.contentassist.commonutils;

import cn.yyx.contentassist.codeutils.identifier;
import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetRefHandler;

public class CodeSynthesisHelper {
	
	public static boolean HandleRawTextSynthesis(String text, CodeSynthesisQueue<String> squeue, ScopeOffsetRefHandler handler,
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
	}
	
	public static boolean HandleBreakContinueCodeSynthesis(identifier id, CodeSynthesisQueue<String> squeue, SynthesisHandler handler,
			StringBuilder result, AdditionalInfo ai)
	{
		StringBuilder fin = new StringBuilder("break");
		StringBuilder idsb = new StringBuilder();
		boolean conflict = id.HandleCodeSynthesis(squeue, handler, idsb, null);
		if (conflict)
		{
			return true;
		}
		if (idsb.length() > 0)
		{
			fin.append(" " + idsb.toString());
		}
		squeue.add(fin.toString());
		return false;
	}
	
}
