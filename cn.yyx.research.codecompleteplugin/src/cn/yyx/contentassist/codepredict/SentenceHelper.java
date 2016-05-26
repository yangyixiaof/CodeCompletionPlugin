package cn.yyx.contentassist.codepredict;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codeutils.methodInvocationStatement;
import cn.yyx.contentassist.codeutils.statement;
import cn.yyx.contentassist.commonutils.ClassInstanceOfUtil;
import cn.yyx.contentassist.commonutils.StatementsMIs;
import cn.yyx.contentassist.parsehelper.ComplexParser;

public class SentenceHelper {
	
	public static List<Sentence> TranslateStringsToSentences(List<String> analist)
	{
		List<Sentence> result = new LinkedList<Sentence>();
		Iterator<String> itr = analist.iterator();
		while (itr.hasNext())
		{
			String setr = itr.next();
			Sentence ons = ComplexParser.GetSentence(setr);
			result.add(ons);
		}
		return result;
	}

	public static StatementsMIs TranslateSentencesToStatements(List<Sentence> setelist) {
		List<statement> smtlist = new LinkedList<statement>();
		List<statement> smilist = new LinkedList<statement>();
		Iterator<Sentence> itr = setelist.iterator();
		while (itr.hasNext())
		{
			Sentence sete = itr.next();
			statement smt = sete.getSmt();
			smtlist.add(smt);
			if (ClassInstanceOfUtil.ObjectInstanceOf(smt, methodInvocationStatement.class))
			{
				smilist.add(smt);
			}
		}
		return new StatementsMIs(smilist, smtlist);
	}
	
}