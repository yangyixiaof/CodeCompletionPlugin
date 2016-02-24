package cn.yyx.research.language.simplified.JDTManager;

import java.util.ArrayList;
import java.util.Iterator;

import cn.yyx.research.language.JDTManager.GCodeMetaInfo;
import cn.yyx.research.language.JDTManager.NodeCode;

public abstract class JavaCode {
	
	protected StringBuilder sb = new StringBuilder("");
	protected ArrayList<String> codes = new ArrayList<String>();
	
	public abstract void AddOneMethodNodeCode(NodeCode nc);
	public abstract void OneSentenceEnd();
	public void AddOneNodeCode(NodeCode nc)
	{
		Iterator<String> itr = nc.GetCodeIterator();
		String prestr = null;
		while (itr.hasNext())
		{
			String onesentence = itr.next();
			CheckAllHavePrefixHint(onesentence, prestr);
			prestr = onesentence;
			sb.append(" " + onesentence);
			codes.add(onesentence);
		}
	}
	
	public boolean IsEmpty() {
		return sb.length() == 0;
	}
	
	@Override
	public String toString() {
		return sb.toString();
	}
	
	public ArrayList<String> toList()
	{
		return codes;
	}
	
	public void CheckAllHavePrefixHint(String onesentence, String prestr)
	{
		int atidx = onesentence.indexOf('@');
		if (atidx < 0)
		{
			System.err.println("There is no @ in str, What is the problem? The wrong sentence is:"+onesentence);
			System.exit(1);
		}
		String prefixhint = onesentence.substring(0, atidx+1);
		switch (prefixhint)
		{
			case GCodeMetaInfo.AnonymousClassBegin:
			case GCodeMetaInfo.AnonymousClassPreHint:
			case GCodeMetaInfo.ClassDeclarationHint:
			case GCodeMetaInfo.ClassInnerDeclarationHint:
			case GCodeMetaInfo.EnumDeclarationHint:
			case GCodeMetaInfo.MethodDeclarationHint:
			case GCodeMetaInfo.EnumConstantDeclarationHint:
			case GCodeMetaInfo.LabelDeclarationHint:
			case GCodeMetaInfo.VariableDeclarationHint:
			case GCodeMetaInfo.LambdaExpressionHint:
			case GCodeMetaInfo.MethodReferenceHint:
			case GCodeMetaInfo.CastExpressionHint:
			case GCodeMetaInfo.AssignmentHint:
			case GCodeMetaInfo.BreakHint:
			case GCodeMetaInfo.ContinueHint:
			case GCodeMetaInfo.DoWhileHint:
			case GCodeMetaInfo.InfixExpressionHint:
			case GCodeMetaInfo.InstanceofExpressionHint:
			case GCodeMetaInfo.PostfixExpressionHint:
			case GCodeMetaInfo.PrefixExpressionHint:
			case GCodeMetaInfo.ReturnHint:
			case GCodeMetaInfo.SwitchHint:
			case GCodeMetaInfo.CaseHint:
			case GCodeMetaInfo.DefaultHint:
			case GCodeMetaInfo.SynchronizedHint:
			case GCodeMetaInfo.ThrowStatementHint:
			case GCodeMetaInfo.CatchHint:
			case GCodeMetaInfo.WhileStatementHint:
			case GCodeMetaInfo.IfStatementHint:
			case GCodeMetaInfo.MethodInvocationHint:
			case GCodeMetaInfo.ArrayCreationHint:
			case GCodeMetaInfo.LiteralHint:
			case GCodeMetaInfo.NameHint:
			case GCodeMetaInfo.QualifiedNameHint:
			case GCodeMetaInfo.FieldAccessHint:
			case GCodeMetaInfo.QualifiedHint:
			case GCodeMetaInfo.Initializer:
			case GCodeMetaInfo.DescriptionHint:
			case GCodeMetaInfo.VariableDeclarationHolder:
			case GCodeMetaInfo.EnhancedFor:
			case GCodeMetaInfo.ArrayAccess:
				break;
			default:
				System.err.println("Unrecognized code prefix hint, What is the problem? The wrong sentence is:"+onesentence+";prestr:"+prestr);
				System.exit(1);
				break;
		}
	}
}