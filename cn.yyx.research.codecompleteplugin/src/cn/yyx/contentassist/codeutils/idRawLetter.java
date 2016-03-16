package cn.yyx.contentassist.codeutils;

import cn.yyx.contentassist.codepredict.PredictMetaInfo;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CodeSynthesisHelper;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SimilarityHelper;
import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetRefHandler;

public class idRawLetter extends identifier{
	
	String text = null;
	
	public idRawLetter(String text) {
		this.text = text;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof idRawLetter)
		{
			double similar = SimilarityHelper.ComputeTwoStringSimilarity(text, ((idRawLetter) t).text);
			if (similar > PredictMetaInfo.TwoStringSimilarThreshold)
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof idRawLetter)
		{
			double similar = SimilarityHelper.ComputeTwoStringSimilarity(text, ((idRawLetter) t).text);
			return similar;
		}
		return 0;
	}

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue<String> squeue, ScopeOffsetRefHandler handler,
			StringBuilder result, AdditionalInfo ai) {
		return CodeSynthesisHelper.HandleRawTextSynthesis(text, squeue, handler, result, null);
	}
	
}