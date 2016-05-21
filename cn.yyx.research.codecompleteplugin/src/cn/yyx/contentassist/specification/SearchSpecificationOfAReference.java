package cn.yyx.contentassist.specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
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
import org.eclipse.jdt.internal.ui.text.java.LazyGenericTypeProposal;
import org.eclipse.jdt.internal.ui.text.java.ParameterGuessingProposal;
import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jdt.ui.text.java.CompletionProposalCollector;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

import cn.yyx.contentassist.codecompletion.PredictMetaInfo;
import cn.yyx.contentassist.codehelper.MyCompilationUnit;

@SuppressWarnings("restriction")
public class SearchSpecificationOfAReference {
	
	public static List<TypeMember> SearchTypeSpecificationByPrefix(String prefix, JavaContentAssistInvocationContext javacontext, IProgressMonitor monitor)
	{
		List<TypeMember> tmlist = new LinkedList<TypeMember>();
		CompletionProposalCollector collector = GetTypeMemberProposalCollector(javacontext);
		List<ICompletionProposal> proposals = SearchSpecificationByPrefix(collector, prefix, javacontext, null);
		Iterator<ICompletionProposal> itr = proposals.iterator();
		int total = 0;
		while (itr.hasNext())
		{
			total++;
			if (total > PredictMetaInfo.MaxTypeSpecificationSize)
			{
				break;
			}
			ICompletionProposal icp = itr.next();
			try {
				tmlist.add(0, new TypeMember((LazyGenericTypeProposal)icp));
			} catch (ClassNotFoundException e) {
				// e.printStackTrace();
				continue;
			}
		}
		return tmlist;
	}
	
	public static List<FieldMember> SearchFieldSpecificationByPrefix(String prefix, JavaContentAssistInvocationContext javacontext, IProgressMonitor monitor)
	{
		List<FieldMember> fmlist = new LinkedList<FieldMember>();
		CompletionProposalCollector collector = GetFieldMemberProposalCollector(javacontext);
		List<ICompletionProposal> proposals = SearchSpecificationByPrefix(collector, prefix, javacontext, null);
		Iterator<ICompletionProposal> itr = proposals.iterator();
		while (itr.hasNext())
		{
			ICompletionProposal icp = itr.next();
			fmlist.add(0, new FieldMember((JavaCompletionProposal)icp));
		}
		return fmlist;
	}
	
	public static List<MethodMember> SearchMethodSpecificationByPrefix(String prefix, JavaContentAssistInvocationContext javacontext, IProgressMonitor monitor)
	{
		List<MethodMember> mmlist = new LinkedList<MethodMember>();
		CompletionProposalCollector collector = GetMethodMemberProposalCollector(javacontext);
		List<ICompletionProposal> proposals = SearchSpecificationByPrefix(collector, prefix, javacontext, null);
		Iterator<ICompletionProposal> itr = proposals.iterator();
		while (itr.hasNext())
		{
			ICompletionProposal icp = itr.next();
			if (icp instanceof JavaMethodCompletionProposal)
			{
				mmlist.add(0, new MethodMember((JavaMethodCompletionProposal)icp));
			}
			if (icp instanceof ParameterGuessingProposal)
			{
				mmlist.add(0, new MethodMember((ParameterGuessingProposal)icp));
			}
		}
		return mmlist;
	}
	
	public static MembersOfAReference SearchFunctionSpecificationByPrefix(String prefix, JavaContentAssistInvocationContext javacontext, IProgressMonitor monitor)
	{
		/*
		 * the prefix must be as the following form:
		 * <form:System.out.>
		 */
		MembersOfAReference result = new MembersOfAReference();
		CompletionProposalCollector collector = GetProposalCollector(javacontext);
		List<ICompletionProposal> proposals = SearchSpecificationByPrefix(collector, prefix, javacontext, null);
		// System.out.println("start print proposals. proposals length:" + proposals.size());
		Iterator<ICompletionProposal> itr = proposals.iterator();
		 int idx = 0;
		while (itr.hasNext())
		{
			 idx++;
			ICompletionProposal icp = itr.next();
			// interested
			if (icp instanceof JavaMethodCompletionProposal || icp instanceof ParameterGuessingProposal)
			{
				result.AddMethodMember(new MethodMember(icp));
			}
			if (icp instanceof JavaCompletionProposal)
			{
				result.AddFieldMember(new FieldMember((JavaCompletionProposal)icp));
			}
			if (icp instanceof LazyGenericTypeProposal)
			{
				try {
					result.AddTypeMember(new TypeMember((LazyGenericTypeProposal)icp));
				} catch (ClassNotFoundException e) {
					continue;
				}
			}
			 System.err.println("proposal" + idx + " display : " + icp.getDisplayString());
			 System.err.println("proposal" + idx + " type : " + icp.getClass());
			 System.err.println("proposal" + idx + " : " + icp.toString());
			 System.err.println("========================");
		}
		// testing
		// System.out.println(result);
		return result;
	}
	
	private static List<ICompletionProposal> SearchSpecificationByPrefix(CompletionProposalCollector collector, String prefix, JavaContentAssistInvocationContext javacontext, IProgressMonitor monitor)
	{
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
		return proposals;
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
	
	private static CompletionProposalCollector GetTypeMemberProposalCollector(JavaContentAssistInvocationContext javacontext)
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
		// collector.setIgnored(CompletionProposal.FIELD_REF, false);
		// collector.setIgnored(CompletionProposal.FIELD_REF_WITH_CASTED_RECEIVER, false);
		// collector.setIgnored(CompletionProposal.KEYWORD, false);
		// collector.setIgnored(CompletionProposal.LABEL_REF, false);
		// collector.setIgnored(CompletionProposal.LOCAL_VARIABLE_REF, false);
		// collector.setIgnored(CompletionProposal.METHOD_DECLARATION, false);
		// collector.setIgnored(CompletionProposal.METHOD_NAME_REFERENCE, false);
		// collector.setIgnored(CompletionProposal.METHOD_REF, false);
		// collector.setIgnored(CompletionProposal.CONSTRUCTOR_INVOCATION, false);
		// collector.setIgnored(CompletionProposal.METHOD_REF_WITH_CASTED_RECEIVER, false);
		// collector.setIgnored(CompletionProposal.PACKAGE_REF, false);
		// collector.setIgnored(CompletionProposal.POTENTIAL_METHOD_DECLARATION, false);
		// collector.setIgnored(CompletionProposal.VARIABLE_DECLARATION, false);
		collector.setIgnored(CompletionProposal.TYPE_REF, false);

		// Allow completions for unresolved types - since 3.3
		// collector.setAllowsRequiredProposals(CompletionProposal.FIELD_REF, CompletionProposal.TYPE_REF, true);
		// collector.setAllowsRequiredProposals(CompletionProposal.FIELD_REF, CompletionProposal.TYPE_IMPORT, true);
		// collector.setAllowsRequiredProposals(CompletionProposal.FIELD_REF, CompletionProposal.FIELD_IMPORT, true);
		// collector.setAllowsRequiredProposals(CompletionProposal.METHOD_REF, CompletionProposal.TYPE_REF, true);
		// collector.setAllowsRequiredProposals(CompletionProposal.METHOD_REF, CompletionProposal.TYPE_IMPORT, true);
		// collector.setAllowsRequiredProposals(CompletionProposal.METHOD_REF, CompletionProposal.METHOD_IMPORT, true);
		// collector.setAllowsRequiredProposals(CompletionProposal.CONSTRUCTOR_INVOCATION, CompletionProposal.TYPE_REF, true);
		// collector.setAllowsRequiredProposals(CompletionProposal.ANONYMOUS_CLASS_CONSTRUCTOR_INVOCATION, CompletionProposal.TYPE_REF, true);
		// collector.setAllowsRequiredProposals(CompletionProposal.ANONYMOUS_CLASS_DECLARATION, CompletionProposal.TYPE_REF, true);
		collector.setAllowsRequiredProposals(CompletionProposal.TYPE_REF, CompletionProposal.TYPE_REF, true);
		
		return collector;
	}
	
	private static CompletionProposalCollector GetFieldMemberProposalCollector(JavaContentAssistInvocationContext javacontext)
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
		// collector.setIgnored(CompletionProposal.METHOD_DECLARATION, false);
		// collector.setIgnored(CompletionProposal.METHOD_NAME_REFERENCE, false);
		// collector.setIgnored(CompletionProposal.METHOD_REF, false);
		// collector.setIgnored(CompletionProposal.CONSTRUCTOR_INVOCATION, false);
		// collector.setIgnored(CompletionProposal.METHOD_REF_WITH_CASTED_RECEIVER, false);
		// collector.setIgnored(CompletionProposal.PACKAGE_REF, false);
		// collector.setIgnored(CompletionProposal.POTENTIAL_METHOD_DECLARATION, false);
		// collector.setIgnored(CompletionProposal.VARIABLE_DECLARATION, false);
		// collector.setIgnored(CompletionProposal.TYPE_REF, false);

		// Allow completions for unresolved types - since 3.3
		collector.setAllowsRequiredProposals(CompletionProposal.FIELD_REF, CompletionProposal.TYPE_REF, true);
		collector.setAllowsRequiredProposals(CompletionProposal.FIELD_REF, CompletionProposal.TYPE_IMPORT, true);
		collector.setAllowsRequiredProposals(CompletionProposal.FIELD_REF, CompletionProposal.FIELD_IMPORT, true);
		// collector.setAllowsRequiredProposals(CompletionProposal.METHOD_REF, CompletionProposal.TYPE_REF, true);
		// collector.setAllowsRequiredProposals(CompletionProposal.METHOD_REF, CompletionProposal.TYPE_IMPORT, true);
		// collector.setAllowsRequiredProposals(CompletionProposal.METHOD_REF, CompletionProposal.METHOD_IMPORT, true);
		// collector.setAllowsRequiredProposals(CompletionProposal.CONSTRUCTOR_INVOCATION, CompletionProposal.TYPE_REF, true);
		// collector.setAllowsRequiredProposals(CompletionProposal.ANONYMOUS_CLASS_CONSTRUCTOR_INVOCATION, CompletionProposal.TYPE_REF, true);
		// collector.setAllowsRequiredProposals(CompletionProposal.ANONYMOUS_CLASS_DECLARATION, CompletionProposal.TYPE_REF, true);
		// collector.setAllowsRequiredProposals(CompletionProposal.TYPE_REF, CompletionProposal.TYPE_REF, true);
		
		return collector;
	}
	
	private static CompletionProposalCollector GetMethodMemberProposalCollector(JavaContentAssistInvocationContext javacontext)
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
		// collector.setIgnored(CompletionProposal.FIELD_REF, false);
		// collector.setIgnored(CompletionProposal.FIELD_REF_WITH_CASTED_RECEIVER, false);
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
		// collector.setIgnored(CompletionProposal.TYPE_REF, false);

		// Allow completions for unresolved types - since 3.3
		// collector.setAllowsRequiredProposals(CompletionProposal.FIELD_REF, CompletionProposal.TYPE_REF, true);
		// collector.setAllowsRequiredProposals(CompletionProposal.FIELD_REF, CompletionProposal.TYPE_IMPORT, true);
		// collector.setAllowsRequiredProposals(CompletionProposal.FIELD_REF, CompletionProposal.FIELD_IMPORT, true);
		collector.setAllowsRequiredProposals(CompletionProposal.METHOD_REF, CompletionProposal.TYPE_REF, true);
		collector.setAllowsRequiredProposals(CompletionProposal.METHOD_REF, CompletionProposal.TYPE_IMPORT, true);
		collector.setAllowsRequiredProposals(CompletionProposal.METHOD_REF, CompletionProposal.METHOD_IMPORT, true);
		collector.setAllowsRequiredProposals(CompletionProposal.CONSTRUCTOR_INVOCATION, CompletionProposal.TYPE_REF, true);
		// collector.setAllowsRequiredProposals(CompletionProposal.ANONYMOUS_CLASS_CONSTRUCTOR_INVOCATION, CompletionProposal.TYPE_REF, true);
		// collector.setAllowsRequiredProposals(CompletionProposal.ANONYMOUS_CLASS_DECLARATION, CompletionProposal.TYPE_REF, true);
		// collector.setAllowsRequiredProposals(CompletionProposal.TYPE_REF, CompletionProposal.TYPE_REF, true);
		
		return collector;
	}
	
}