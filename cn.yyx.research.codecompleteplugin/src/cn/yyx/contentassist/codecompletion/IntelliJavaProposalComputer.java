package cn.yyx.contentassist.codecompletion;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jdt.ui.text.java.IJavaCompletionProposalComputer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;

import cn.yyx.contentassist.codepredict.CodePredict;
import cn.yyx.contentassist.codepredict.CodePredictTest;
import cn.yyx.contentassist.codepredict.CodePredictUtil;

public class IntelliJavaProposalComputer implements IJavaCompletionProposalComputer {
	
	public final static String OnlyJavaSupport = "<Advanced Completion: Only support java code completion, other formats are not supported yet.>";
	public final static String OnlyExpressionSupport = "<Advanced Completion: Only support expressions completions, other formats are not supported yet.>";
	
	// this is unchanged once created.
	CodePredict cpt = new CodePredictTest();
	// this is formal CodePredict utility class.
	CodePredict cpu = new CodePredictUtil();
	
	public IntelliJavaProposalComputer() {
	}

	@Override
	public List<ICompletionProposal> computeCompletionProposals(ContentAssistInvocationContext context,
			IProgressMonitor monitor) {
		ArrayList<ICompletionProposal> proposal = null;
		proposal = cpu.PredictCodes(context, monitor);
		// proposal = cpu.PredictCodes(context, monitor);
		return proposal;
	}

	@Override
	public List<IContextInformation> computeContextInformation(ContentAssistInvocationContext context,
			IProgressMonitor monitor) {
		return null;
	}

	@Override
	public String getErrorMessage() {
		return null;// "Java Intell My Code Proposal generate some strange errors, but may not influence the use.";
	}

	@Override
	public void sessionEnded() {
		//System.err.println("YyxContentAssitEndInvoked");
	}

	@Override
	public void sessionStarted() {
		//System.err.println("YyxContentAssitBeginToInvoke");
	}

}