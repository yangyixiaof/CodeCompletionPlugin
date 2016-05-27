package cn.yyx.contentassist.codepredict;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

import cn.yyx.contentassist.codecompletion.IntelliJavaProposalComputer;
import cn.yyx.contentassist.commonutils.ClassInstanceOfUtil;
import cn.yyx.contentassist.specification.SearchSpecificationOfAReference;

public class CodePredictTest implements CodePredict{
	
	@Override
	public ArrayList<ICompletionProposal> PredictCodes(ContentAssistInvocationContext context, IProgressMonitor monitor)
	{
		ArrayList<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
		proposals.add(new CompletionProposal("www.yyx.com", context.getInvocationOffset(), 0, "www.yyx.com".length()));
		proposals.add(new CompletionProposal("<yyx proposal here>", context.getInvocationOffset(), 0,
				"<yyx proposal here>".length()));
		if (ClassInstanceOfUtil.ObjectInstanceOf(context, JavaContentAssistInvocationContext.class))
		{
			try {
				JavaContentAssistInvocationContext javacontext = (JavaContentAssistInvocationContext)context;
				IDocument doc = javacontext.getDocument();
				int offset = javacontext.getInvocationOffset();
				String precontentraw = doc.get(0, offset);
				String precontent = precontentraw.trim();
				char lastchar = precontent.charAt(precontent.length()-1);
				if (lastchar == ';' || lastchar == '}' || lastchar == ',' || lastchar == '{' || lastchar == '(' || lastchar == ':')
				{
					SearchSpecificationOfAReference.SearchMethodSpecificationByPrefix("new StringBuffer", javacontext);
					SearchSpecificationOfAReference.SearchFunctionSpecificationByPrefix("System.", javacontext);
					System.err.println("===================split line=================");
					SearchSpecificationOfAReference.SearchMethodSpecificationByPrefix("System.", javacontext);
					SearchSpecificationOfAReference.SearchFieldSpecificationByPrefix("System.class", javacontext);
					System.err.println("===================begin class=================");
					SearchSpecificationOfAReference.SearchFunctionSpecificationByPrefix("PrintStream.", javacontext);
					SearchSpecificationOfAReference.SearchFieldClassMemberSpecificationByPrefix("System.", javacontext);
					System.err.println("===================class split=================");
					SearchSpecificationOfAReference.SearchFieldClassMemberSpecificationByPrefix("PrintStream.", javacontext);
					System.err.println("===================end class=================");
					SearchSpecificationOfAReference.SearchTypeSpecificationByPrefix("PrintStream", javacontext);
					SearchSpecificationOfAReference.SearchTypeSpecificationByPrefix("Integer", javacontext);
				}
				else
				{
					proposals.add(new CompletionProposal(IntelliJavaProposalComputer.OnlyExpressionSupport, context.getInvocationOffset(), 0,
							IntelliJavaProposalComputer.OnlyExpressionSupport.length()));
				}
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
		else
		{
			System.err.println(IntelliJavaProposalComputer.OnlyJavaSupport);
			proposals.add(new CompletionProposal(IntelliJavaProposalComputer.OnlyJavaSupport, context.getInvocationOffset(), 0,
					IntelliJavaProposalComputer.OnlyJavaSupport.length()));
		}
		return proposals;
	}
	
}