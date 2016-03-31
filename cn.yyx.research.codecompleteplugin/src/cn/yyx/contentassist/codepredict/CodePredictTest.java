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
import cn.yyx.contentassist.specification.SearchSpecificationOfAReference;

public class CodePredictTest implements CodePredict{
	
	@Override
	public ArrayList<ICompletionProposal> PredictCodes(ContentAssistInvocationContext context, IProgressMonitor monitor)
	{
		ArrayList<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
		proposals.add(new CompletionProposal("www.yyx.com", context.getInvocationOffset(), 0, "www.yyx.com".length()));
		proposals.add(new CompletionProposal("<yyx proposal here>", context.getInvocationOffset(), 0,
				"<yyx proposal here>".length()));
		if (context instanceof JavaContentAssistInvocationContext)
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
					SearchSpecificationOfAReference.SearchFunctionSpecificationByPrefix("System.", javacontext, monitor);
					SearchSpecificationOfAReference.SearchFunctionSpecificationByPrefix("PrintStream", javacontext, monitor);
					SearchSpecificationOfAReference.SearchFunctionSpecificationByPrefix("Integer", javacontext, monitor);
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