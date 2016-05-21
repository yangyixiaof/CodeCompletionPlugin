package cn.yyx.contentassist.parsehelper;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codeutils.type;
import cn.yyx.parse.specialparse.ParseRoot;

public class ComplexParser {

	public static Sentence GetSentence(String str) {
		OneSentenceVisitor evalVisitor = new OneSentenceVisitor();
		try {
			ParseRoot.ParseOneSentence(str, evalVisitor, false);
		} catch (Exception e) {
			System.err.println("Parse One Sentence error! serious error, the system will exit. The error parsed setence is :" + str + ".");
			if (e instanceof ParseCancellationException)
			{
				ParseCancellationException pce = (ParseCancellationException)e;
				Throwable pc = pce.getCause();
				if (pc instanceof RecognitionException)
				{
					RecognitionException re = (RecognitionException)pce.getCause();
				    ParserRuleContext context = (ParserRuleContext)re.getCtx();
				    System.err.println(re);
				    System.err.println(context);
				}
			}
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
	
	public static type GetType(String str) {
		OneTypeVisitor evalVisitor = new OneTypeVisitor();
		try {
			ParseRoot.ParseOneType(str, evalVisitor, false);
		} catch (Exception e) {
			System.err.println("Parse One Sentence error! serious error, the system will exit. The error parsed setence is :" + str + ".");
			if (e instanceof ParseCancellationException)
			{
				ParseCancellationException pce = (ParseCancellationException)e;
				Throwable pc = pce.getCause();
				if (pc instanceof RecognitionException)
				{
					RecognitionException re = (RecognitionException)pce.getCause();
				    ParserRuleContext context = (ParserRuleContext)re.getCtx();
				    System.err.println(re);
				    System.err.println(context);
				}
			}
			e.printStackTrace();
			System.exit(1);
		} catch (Error e) {
			System.err.println("Parse One Sentence error! serious error, the system will exit. The error parsed setence is :" + str + ".");
			e.printStackTrace();
			System.exit(1);
		}
		return evalVisitor.getTp();
	}
	
	public static Sentence GetSentenceWithNoExit(String str) {
		OneSentenceVisitor evalVisitor = new OneSentenceVisitor();
		try {
			ParseRoot.ParseOneSentence(str, evalVisitor, false);
		} catch (Exception e) {
			System.err.println("Parse One Sentence error! serious error, the system will exit. The error parsed setence is :" + str + ".");
			return null;
		} catch (Error e) {
			System.err.println("Parse One Sentence error! serious error, the system will exit. The error parsed setence is :" + str + ".");
			return null;
		}
		Sentence set = new Sentence(str, evalVisitor.getSmt());
		return set;
	}

}
