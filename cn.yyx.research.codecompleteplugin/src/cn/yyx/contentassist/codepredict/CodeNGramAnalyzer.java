package cn.yyx.contentassist.codepredict;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.IDocument;

import cn.yyx.contentassist.codecompletion.CodeCompletionMetaInfo;
import cn.yyx.contentassist.codecompletion.PredictMetaInfo;
import cn.yyx.contentassist.commonutils.ASTOffsetInfo;
import cn.yyx.contentassist.commonutils.PrintUtil;
import cn.yyx.contentassist.jdtastvisitor.PartialProcessVisitor;
import cn.yyx.contentassist.specification.SearchSpecificationOfAReference;
import cn.yyx.research.language.JDTHelper.ASTTraversal;
import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetRefHandler;
import cn.yyx.research.language.simplified.JDTManager.UniqueOrder;

public class CodeNGramAnalyzer {

	public static List<String> PossibleCodes(JavaContentAssistInvocationContext javacontext) {
		// TODO This whole mechanism needs to be fully tested.
		System.err.println("HaHa Test!!!!!!!!!!!!!!");
		SearchSpecificationOfAReference.Reset();
		UniqueOrder.Reset();
		ArrayList<String> list = new ArrayList<String>();
		try {
			int offset = javacontext.getInvocationOffset();
			
			ICompilationUnit icu = javacontext.getCompilationUnit();
			String javaname = icu.getCorrespondingResource().getName();
			
			IDocument doc = javacontext.getDocument();
			ASTTraversal astmdf = new ASTTraversal(javaname, doc.get());
			CompilationUnit cu = astmdf.getCompilationUnit();
			ASTOffsetInfo aoi = new ASTOffsetInfo();
			String indent = GetIndent(doc.get(), offset);
			aoi.setIndent(indent);
			
			PartialProcessVisitor ppv = new PartialProcessVisitor(offset, aoi);
			cu.accept(ppv);
			
			ArrayList<String> rawanalist = ppv.GetMainAnalyseList(aoi.isInAnonymousClass());
			rawanalist = TrimAfterMdOrIniBegin(rawanalist);
			int size = rawanalist.size();
			List<String> analist = rawanalist;
			if (size > PredictMetaInfo.PrePredictWindow) {
				analist = analist.subList(size - PredictMetaInfo.PrePredictWindow, size);
				size = PredictMetaInfo.PrePredictWindow;
			}
			
			// debugging.
			PrintUtil.PrintList(analist, "analysis list");
			
			ScopeOffsetRefHandler sohandler = ppv.GenerateScopeOffsetRefHandler();
			
			if (CodeCompletionMetaInfo.ExactMatchMode) {
				ExactPredictionFetch epf = new ExactPredictionFetch();
				return epf.FetchPrediction(javacontext, sohandler, analist, list, aoi);
			} else {
				PredictionFetch pf = new PredictionFetch();
				return pf.FetchPrediction(javacontext, sohandler, analist, list, aoi);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	private static ArrayList<String> TrimAfterMdOrIniBegin(ArrayList<String> analist) {
		int i = 0;
		int len = analist.size();
		for (i=0;i<len;i++)
		{
			String an = analist.get(i).trim();
			if (an.startsWith("HT@") || an.startsWith("HOT@") || an.startsWith("IB@") || an.startsWith("MD@"))
			{
				ArrayList<String> result = new ArrayList<String>();
				result.addAll(analist.subList(i, len));
				return result;
			}
		}
		return analist;
	}

	private static String GetIndent(String document, int invokeoffset) {
		char[] doccs = document.toCharArray();
		int i = invokeoffset-1;
		while (i >= 0) {
			char c = doccs[i];
			if (c == '\n') {
				int start = i + 1;
				int j = start;
				char jc = doccs[j];
				while (jc == '\t' || jc == ' ' || jc == '\u000C') {
					j++;
					jc = doccs[j];
				}
				if (j > start) {
					String indent = document.substring(start, j);
					/*if (CodeCompletionMetaInfo.DebugMode) {
						System.err.println("indent:" + indent);
						System.err.println("before:" + document.substring(0, invokeoffset));
						System.err.println("after:" + document.substring(invokeoffset));
						char[] cs = indent.toCharArray();
						int klen = cs.length;
						for (int k = 0; k < klen; k++) {
							System.err.println("one char:" + cs[k]);
						}
					}*/
					return indent;
				}
			}
			i--;
		}
		return "";
	}

}