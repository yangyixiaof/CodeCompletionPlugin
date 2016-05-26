package cn.yyx.contentassist.codepredict;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

import cn.yyx.contentassist.codecompletion.IntelliJavaProposalComputer;
import cn.yyx.contentassist.commonutils.ClassInstanceOfUtil;
import cn.yyx.contentassist.commonutils.DocumentContentHelper;
import cn.yyx.contentassist.commonutils.ProposalHelper;

public class CodePredictUtil implements CodePredict {

	@Override
	public ArrayList<ICompletionProposal> PredictCodes(ContentAssistInvocationContext context,
			IProgressMonitor monitor) {
		ArrayList<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
		if (!(ClassInstanceOfUtil.ObjectInstanceOf(context, JavaContentAssistInvocationContext.class))) {
			NotifyError(proposals, context);
			return proposals;
		}
		try {
			JavaContentAssistInvocationContext javacontext = (JavaContentAssistInvocationContext) context;
			IDocument doc = javacontext.getDocument();
			int offset = javacontext.getInvocationOffset();
			char lastchar = DocumentContentHelper.GetOffsetLastChar(doc, offset);
			if (TerminationHelper.isTerminatedChar(lastchar)) {
				// detailed completion will be realized later. this is difficult in technique.
				//  || lastchar == ',' || lastchar == '(' || lastchar == ')'
				List<String> proposalcnt = CodeNGramAnalyzer.PossibleCodes(javacontext, lastchar);
				ProposalHelper.ProposalContentToFormalFormat(javacontext, proposalcnt, proposals);
			} else {
				proposals.add(new CompletionProposal(IntelliJavaProposalComputer.OnlyExpressionSupport,
						context.getInvocationOffset(), 0, IntelliJavaProposalComputer.OnlyExpressionSupport.length()));
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return proposals;
	}

	private void NotifyError(ArrayList<ICompletionProposal> proposals, ContentAssistInvocationContext context) {
		System.err.println(IntelliJavaProposalComputer.OnlyJavaSupport);
		proposals.add(new CompletionProposal(IntelliJavaProposalComputer.OnlyJavaSupport, context.getInvocationOffset(),
				0, IntelliJavaProposalComputer.OnlyJavaSupport.length()));
	}
	
}