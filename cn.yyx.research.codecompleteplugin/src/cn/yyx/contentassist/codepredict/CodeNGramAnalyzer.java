package cn.yyx.contentassist.codepredict;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.IDocument;

import cn.yyx.contentassist.commonutils.ASTOffsetInfo;
import cn.yyx.contentassist.commonutils.ASTTreeReducer;
import cn.yyx.research.language.JDTHelper.ASTTraversal;
import cn.yyx.research.language.simplified.JDTHelper.SimplifiedCodeGenerateASTVisitor;

public class CodeNGramAnalyzer {

	@SuppressWarnings("unchecked")
	public static List<String> PossibleCodes(JavaContentAssistInvocationContext javacontext) {
		// TODO Auto-generated method stub
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
			AbstractTypeDeclaration atype = ASTTreeReducer.GetSimplifiedContent(cu.types(), offset, aoi);
			if (atype == null)
			{
				return list;
			}
			
			// System.err.println("Document:" + doc.get());
			// System.err.println("========================== ==========================");
			// System.err.println("RetainedDocument:" + atype.toString());
			
			SimplifiedCodeGenerateASTVisitor fmastv = new SimplifiedCodeGenerateASTVisitor();
			atype.accept(fmastv);
			ArrayList<String> analist = fmastv.GetMainAnalyseList(aoi.isInAnonymousClass());
			
			return PredictionFetch.FetchPrediction(fmastv, analist, list);
			
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}