package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.codepredict.PredictMetaInfo;
import cn.yyx.contentassist.codesynthesis.CSNode;
import cn.yyx.contentassist.codesynthesis.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.SimilarityHelper;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

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

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		TypeCheck tc = new TypeCheck();
		Class<?> c = null;
		try {
			c = Class.forName(text);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (c == null)
		{
			tc = null;
		}
		else
		{
			tc.setExpreturntype(text);
			tc.setExpreturntypeclass(c);
		}
		result.AddOneData(text, tc);
		return false;
	}
	
}