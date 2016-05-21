package cn.yyx.contentassist.codepredict;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.IDocument;

import cn.yyx.contentassist.codecompletion.CodeCompletionMetaInfo;
import cn.yyx.contentassist.commonutils.ASTOffsetInfo;
import cn.yyx.contentassist.commonutils.PrintUtil;
import cn.yyx.contentassist.jdtastvisitor.PartialProcessVisitor;
import cn.yyx.research.language.JDTHelper.ASTTraversal;
import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetRefHandler;

public class CodeNGramAnalyzer {

	public static List<String> PossibleCodes(JavaContentAssistInvocationContext javacontext, IProgressMonitor monitor,
			char lastchar) {
		// TODO This whole mechanism needs to be fully tested.
		System.err.println("HaHa Test!!!!!!!!!!!!!!");
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

			/*
			 * AbstractTypeDeclaration atype =
			 * ASTTreeReducer.GetSimplifiedContent(cu.types(), offset, aoi); if
			 * (atype == null) { return list; }
			 * 
			 * // System.err.println("Document:" + doc.get()); //
			 * System.err.println(
			 * "========================== =========================="); //
			 * System.err.println("RetainedDocument:" + atype.toString());
			 * 
			 * SimplifiedCodeGenerateASTVisitor fmastv = new
			 * SimplifiedCodeGenerateASTVisitor(); atype.accept(fmastv);
			 */

			PartialProcessVisitor ppv = new PartialProcessVisitor(offset, aoi);
			cu.accept(ppv);
			ArrayList<String> analist = ppv.GetMainAnalyseList(aoi.isInAnonymousClass());
			TrimRightBrace(analist);
			
			// debugging.
			PrintUtil.PrintList(analist, "analysis list");

			ScopeOffsetRefHandler sohandler = ppv.GenerateScopeOffsetRefHandler();

			PredictionFetch pf = new PredictionFetch();
			return pf.FetchPrediction(javacontext, monitor, sohandler, analist, list, aoi, lastchar);

		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return list;
	}

	private static void TrimRightBrace(ArrayList<String> analist) {
		int len = analist.size();
		for (int i = len - 1; i >= 0; i--) {
			String str = analist.get(i);
			if (str.startsWith("DH@}")) {
				analist.remove(i);
			} else {
				break;
			}
		}
	}

	private static String GetIndent(String document, int invokeoffset) {
		char[] doccs = document.toCharArray();
		int i = invokeoffset;
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
					if (CodeCompletionMetaInfo.DebugMode) {
						System.err.println("indent:" + indent);
						System.err.println("before:" + document.substring(0, start));
						System.err.println("after:" + document.substring(j));
						char[] cs = indent.toCharArray();
						int klen = cs.length;
						for (int k = 0; k < klen; k++) {
							System.err.println("one char:" + cs[k]);
						}
					}
					return indent;
				}
			}
			i--;
		}
		return "";
	}

}