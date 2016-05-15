package cn.yyx.contentassist.test;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.LCSComparison;
import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codeutils.commonMethodInvocationStatement;
import cn.yyx.contentassist.codeutils.statement;
import cn.yyx.contentassist.parsehelper.ComplexParser;

public class LCSTest {
	
	public static void main(String[] args) {
		String[] strs = {"CD@CodeCompTest", "MD@void(String)isString", "DH@{", "VD@int", "VH@=@HO", "MI@length(@C0?0)", "DH@;"};
		List<statement> smts = new LinkedList<statement>();
		int len = strs.length;
		for (int i=0;i<len;i++)
		{
			String os = strs[i];
			Sentence ossete = ComplexParser.GetSentence(os);
			smts.add(ossete.getSmt());
		}
		commonMethodInvocationStatement mi = (commonMethodInvocationStatement) smts.get(len-2);
		Class<?> micls = mi.getClass();
		double misim = mi.Similarity(mi);
		System.err.println("mi type:" + micls + ";mi self similarity:" + misim);
		double sim1 = LCSComparison.LCSSimilarity(smts, smts.subList(0, len-1));
		System.err.println("sim1:" + sim1);
		double sim2 = LCSComparison.LCSSimilarity(smts, smts.subList(0, len-2));
		System.err.println("sim2:" + sim2);
	}
	
}