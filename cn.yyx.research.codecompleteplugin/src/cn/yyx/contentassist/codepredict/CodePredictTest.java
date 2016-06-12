package cn.yyx.contentassist.codepredict;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

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
					List<Runnable> rls = new LinkedList<Runnable>();
					rls.add(new CPTMethodTask("new StringBuffe", javacontext));
					rls.add(new CPTMethodTask("System.out::", javacontext));
					rls.add(new CPTMethodTask("System.out.", javacontext));
					rls.add(new CPTMethodTask("new ActionListener", javacontext));
					rls.add(new CPTTypeTask("System.class", javacontext));
					rls.add(new CPTTypeTask("System.", javacontext));
					rls.add(new CPTTypeTask("System", javacontext));
					rls.add(new CPTFieldTask("System", javacontext));
					rls.add(new CPTFieldTask("PrintStream", javacontext));
					
					Iterator<Runnable> ritr = rls.iterator();
					while (ritr.hasNext())
					{
						Runnable ra = ritr.next();
						new Thread(ra).start();
					}
					
					// SearchSpecificationOfAReference.SearchMethodSpecificationByPrefix("new StringBuffer", javacontext);
					// SearchSpecificationOfAReference.SearchMethodSpecificationByPrefix("System.out::", javacontext);
					// System.err.println("===================split line=================");
					// SearchSpecificationOfAReference.SearchFunctionSpecificationByPrefix("new ActionListener", javacontext);
					// SearchSpecificationOfAReference.SearchMethodSpecificationByPrefix("keys.iterator", javacontext);
					// SearchSpecificationOfAReference.SearchFieldSpecificationByPrefix("System.class", javacontext);
					// System.err.println("===================begin class=================");
					// SearchSpecificationOfAReference.SearchFunctionSpecificationByPrefix("PrintStream.", javacontext);
					// SearchSpecificationOfAReference.SearchFieldClassMemberSpecificationByPrefix("System", javacontext);
					// SearchSpecificationOfAReference.SearchFieldClassMemberSpecificationByPrefix("System", javacontext);
					// System.err.println("===================class split=================");
					// SearchSpecificationOfAReference.SearchFieldClassMemberSpecificationByPrefix("PrintStream", javacontext);
					// SearchSpecificationOfAReference.SearchFieldClassMemberSpecificationByPrefix("PrintStream", javacontext);
					// System.err.println("===================end class=================");
					// SearchSpecificationOfAReference.SearchTypeSpecificationByPrefix("PrintStream", javacontext, true);
					// SearchSpecificationOfAReference.SearchTypeSpecificationByPrefix("Integer", javacontext, true);
				}
				else
				{
					// proposals.add(new CompletionProposal(IntelliJavaProposalComputer.OnlyExpressionSupport, context.getInvocationOffset(), 0,
					//		IntelliJavaProposalComputer.OnlyExpressionSupport.length()));
				}
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
		else
		{
			// System.err.println(IntelliJavaProposalComputer.OnlyJavaSupport);
			// proposals.add(new CompletionProposal(IntelliJavaProposalComputer.OnlyJavaSupport, context.getInvocationOffset(), 0,
			//		IntelliJavaProposalComputer.OnlyJavaSupport.length()));
		}
		return proposals;
	}
	
	class CPTFieldTask implements Runnable
	{
		String prefix = null;
		JavaContentAssistInvocationContext javacontext = null;
		public CPTFieldTask(String prefix, JavaContentAssistInvocationContext javacontext) {
			this.prefix = prefix;
			this.javacontext = javacontext;
		}
		@Override
		public void run() {
			SearchSpecificationOfAReference.SearchFieldSpecificationByPrefix(prefix, javacontext);
		}
	}
	
	class CPTTypeTask implements Runnable
	{
		String prefix = null;
		JavaContentAssistInvocationContext javacontext = null;
		public CPTTypeTask(String prefix, JavaContentAssistInvocationContext javacontext) {
			this.prefix = prefix;
			this.javacontext = javacontext;
		}
		@Override
		public void run() {
			SearchSpecificationOfAReference.SearchFieldClassMemberSpecificationByPrefix(prefix, javacontext);
		}
	}
	
	class CPTMethodTask implements Runnable
	{
		String prefix = null;
		JavaContentAssistInvocationContext javacontext = null;
		public CPTMethodTask(String prefix, JavaContentAssistInvocationContext javacontext) {
			this.prefix = prefix;
			this.javacontext = javacontext;
		}
		@Override
		public void run() {
			SearchSpecificationOfAReference.SearchMethodSpecificationByPrefix(prefix, javacontext);
		}
	}
	
}