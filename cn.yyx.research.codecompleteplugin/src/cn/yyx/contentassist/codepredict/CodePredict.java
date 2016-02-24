package cn.yyx.contentassist.codepredict;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

public interface CodePredict {
	
	public ArrayList<ICompletionProposal> PredictCodes(ContentAssistInvocationContext context,
			IProgressMonitor monitor);
	
}
