package cn.yyx.contentassist.specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.CompletionProposal;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.internal.codeassist.CompletionEngine;
import org.eclipse.jdt.internal.core.DefaultWorkingCopyOwner;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jdt.internal.core.SearchableEnvironment;
import org.eclipse.jdt.internal.ui.text.java.FillArgumentNamesCompletionProposalCollector;
import org.eclipse.jdt.internal.ui.text.java.JavaCompletionProposal;
import org.eclipse.jdt.internal.ui.text.java.JavaMethodCompletionProposal;
import org.eclipse.jdt.internal.ui.text.java.ParameterGuessingProposal;
import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jdt.ui.text.java.CompletionProposalCollector;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

import cn.yyx.contentassist.codehelper.MyCompilationUnit;

@SuppressWarnings("restriction")
public class SearchSpecificationOfAReference {
	
	public static MembersOfAReference SearchFunctionSpecificationByPrefix(String prefix, JavaContentAssistInvocationContext javacontext, IProgressMonitor monitor)
	{
		/*
		 * the prefix must be as the following form:
		 * <form:System.out.>
		 */
		CompletionProposalCollector collector = GetProposalCollector(javacontext);
		
		int rawoffset = javacontext.getInvocationOffset();
		int position = rawoffset + prefix.length();
		
		DefaultWorkingCopyOwner owner = DefaultWorkingCopyOwner.PRIMARY;
		
		try {
			ICompilationUnit sourceunit = javacontext.getCompilationUnit();
			MyCompilationUnit mcu = new MyCompilationUnit((org.eclipse.jdt.internal.compiler.env.ICompilationUnit)sourceunit, javacontext.getDocument(), prefix, rawoffset);
			JavaProject project = (JavaProject) sourceunit.getJavaProject();
			SearchableEnvironment environment = project.newSearchableNameEnvironment(owner);
			// code complete
			CompletionEngine engine = new CompletionEngine(environment, collector, project.getOptions(true), project, owner, null);
			engine.complete(mcu, position, 0, sourceunit);
		} catch (Exception x) {
			x.printStackTrace();
		}

		ICompletionProposal[] javaProposals= collector.getJavaCompletionProposals();

		List<ICompletionProposal> proposals= new ArrayList<ICompletionProposal>(Arrays.asList(javaProposals));
		if (proposals.size() == 0) {
			String error= collector.getErrorMessage();
			if (error.length() > 0)
				new Exception().printStackTrace();
		}
		
		MembersOfAReference result = new MembersOfAReference();
		System.out.println("start print proposals. proposals length:" + proposals.size());
		Iterator<ICompletionProposal> itr = proposals.iterator();
		while (itr.hasNext())
		{
			ICompletionProposal icp = itr.next();
			// interested
			if (icp instanceof JavaMethodCompletionProposal || icp instanceof ParameterGuessingProposal)
			{
				result.AddMethodMember(new MethodMember(icp));
			}
			if (icp instanceof JavaCompletionProposal)
			{
				result.AddFieldMember(new FieldMember(icp));
			}
			// System.err.println("proposal" + idx + " display : " + icp.getDisplayString());
			// System.err.println("proposal" + idx + " type : " + icp.getClass());
			// System.err.println("proposal" + idx + " : " + icp.toString());
			// System.err.println("========================");
		}
		
		// testing
		System.out.println(result);
		
		return result;
	}
	
	private static CompletionProposalCollector GetProposalCollector(JavaContentAssistInvocationContext javacontext)
	{
		CompletionProposalCollector collector = null;
		if (PreferenceConstants.getPreferenceStore().getBoolean(PreferenceConstants.CODEASSIST_FILL_ARGUMENT_NAMES))
		{
			collector = new FillArgumentNamesCompletionProposalCollector(javacontext);
		}
		else
		{
			collector = new CompletionProposalCollector(javacontext.getCompilationUnit(), true);
		}
		collector.setInvocationContext(javacontext);
		// collector.setIgnored(CompletionProposal.ANNOTATION_ATTRIBUTE_REF, false);
		// collector.setIgnored(CompletionProposal.ANONYMOUS_CLASS_DECLARATION, false);
		// collector.setIgnored(CompletionProposal.ANONYMOUS_CLASS_CONSTRUCTOR_INVOCATION, false);
		collector.setIgnored(CompletionProposal.FIELD_REF, false);
		collector.setIgnored(CompletionProposal.FIELD_REF_WITH_CASTED_RECEIVER, false);
		// collector.setIgnored(CompletionProposal.KEYWORD, false);
		// collector.setIgnored(CompletionProposal.LABEL_REF, false);
		// collector.setIgnored(CompletionProposal.LOCAL_VARIABLE_REF, false);
		collector.setIgnored(CompletionProposal.METHOD_DECLARATION, false);
		collector.setIgnored(CompletionProposal.METHOD_NAME_REFERENCE, false);
		collector.setIgnored(CompletionProposal.METHOD_REF, false);
		collector.setIgnored(CompletionProposal.CONSTRUCTOR_INVOCATION, false);
		collector.setIgnored(CompletionProposal.METHOD_REF_WITH_CASTED_RECEIVER, false);
		// collector.setIgnored(CompletionProposal.PACKAGE_REF, false);
		// collector.setIgnored(CompletionProposal.POTENTIAL_METHOD_DECLARATION, false);
		// collector.setIgnored(CompletionProposal.VARIABLE_DECLARATION, false);
		collector.setIgnored(CompletionProposal.TYPE_REF, false);

		// Allow completions for unresolved types - since 3.3
		collector.setAllowsRequiredProposals(CompletionProposal.FIELD_REF, CompletionProposal.TYPE_REF, true);
		collector.setAllowsRequiredProposals(CompletionProposal.FIELD_REF, CompletionProposal.TYPE_IMPORT, true);
		collector.setAllowsRequiredProposals(CompletionProposal.FIELD_REF, CompletionProposal.FIELD_IMPORT, true);
		collector.setAllowsRequiredProposals(CompletionProposal.METHOD_REF, CompletionProposal.TYPE_REF, true);
		collector.setAllowsRequiredProposals(CompletionProposal.METHOD_REF, CompletionProposal.TYPE_IMPORT, true);
		collector.setAllowsRequiredProposals(CompletionProposal.METHOD_REF, CompletionProposal.METHOD_IMPORT, true);
		collector.setAllowsRequiredProposals(CompletionProposal.CONSTRUCTOR_INVOCATION, CompletionProposal.TYPE_REF, true);
		// collector.setAllowsRequiredProposals(CompletionProposal.ANONYMOUS_CLASS_CONSTRUCTOR_INVOCATION, CompletionProposal.TYPE_REF, true);
		// collector.setAllowsRequiredProposals(CompletionProposal.ANONYMOUS_CLASS_DECLARATION, CompletionProposal.TYPE_REF, true);
		collector.setAllowsRequiredProposals(CompletionProposal.TYPE_REF, CompletionProposal.TYPE_REF, true);
		
		return collector;
	}
	
}