package cn.yyx.contentassist.parsehelper;

import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.parse.specialparse.ParseRoot;

public class ComplexParser {

	public static Sentence GetSentence(String str) {
		OneSentenceVisitor evalVisitor = new OneSentenceVisitor();
		try {
			ParseRoot.ParseOneSentence(str, evalVisitor, false);
		} catch (Exception e) {
			System.err.println("Parse One Sentence error! serious error, the system will exit. The error parsed setence is :" + str + ".");
			e.printStackTrace();
			System.exit(1);
		} catch (Error e) {
			System.err.println("Parse One Sentence error! serious error, the system will exit. The error parsed setence is :" + str + ".");
			e.printStackTrace();
			System.exit(1);
		}
		Sentence set = new Sentence(str, evalVisitor.getSmt());
		return set;
	}

}
