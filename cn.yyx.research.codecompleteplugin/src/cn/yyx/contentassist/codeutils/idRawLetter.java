package cn.yyx.contentassist.codeutils;

import cn.yyx.contentassist.codepredict.PredictMetaInfo;
import cn.yyx.contentassist.commonutils.SimilarityHelper;

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
	
}