package cn.yyx.contentassist.commonutils;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

public class ProposalHelper {
	
	public static void ProposalContentToFormalFormat(JavaContentAssistInvocationContext context, List<String> proposalcnt, List<ICompletionProposal> proposals)
	{
		Iterator<String> itr = proposalcnt.iterator();
		while (itr.hasNext())
		{
			String pol = itr.next();
			proposals.add(new CompletionProposal(pol, context.getInvocationOffset(), 0, pol.length()));
		}
	}
	
}
