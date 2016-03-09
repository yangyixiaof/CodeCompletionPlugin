package cn.yyx.contentassist.codeutils;

import cn.yyx.contentassist.codepredict.PredictMetaInfo;
import cn.yyx.contentassist.commonutils.SimilarityHelper;

public class simpleType extends type{
	
	String text = null;
	
	public simpleType(String text) {
		this.text = text;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof simpleType)
		{
			double sim = SimilarityHelper.ComputeTwoStringSimilarity(text, ((simpleType) t).text);
			if (sim >= PredictMetaInfo.OneSentenceSimilarThreshold)
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof simpleType)
		{
			return SimilarityHelper.ComputeTwoStringSimilarity(text, ((simpleType) t).text);
		}
		return 0;
	}
	
}