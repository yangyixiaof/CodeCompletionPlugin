package cn.yyx.contentassist.commonutils;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

public class ProposalHelper {
	
	public static void ProposalContentToFormalFormat(JavaContentAssistInvocationContext context, ArrayList<String> proposalcnt, ArrayList<ICompletionProposal> proposals)
	{
		Iterator<String> itr = proposalcnt.iterator();
		while (itr.hasNext())
		{
			String pol = itr.next();
			proposals.add(new CompletionProposal(pol, context.getInvocationOffset(), 0, pol.length()));
		}
	}
	
}
