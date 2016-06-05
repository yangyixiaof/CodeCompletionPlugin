package cn.yyx.contentassist.specification;

import java.util.Iterator;
import java.util.List;

import cn.yyx.contentassist.commonutils.ContextHandler;
import cn.yyx.contentassist.commonutils.RefAndModifiedMember;
import cn.yyx.contentassist.commonutils.SimilarityHelper;
import cn.yyx.contentassist.commonutils.StringUtil;
import cn.yyx.research.language.JDTManager.GCodeMetaInfo;
import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetResult;
import cn.yyx.research.language.simplified.JDTManager.TypeVar;

public class SpecificationHelper {
	
	public static String GetPrefixCmp(String prefix)
	{
		if (prefix.endsWith(".") || prefix.endsWith("::")) {
			return null;
		} else {
			prefix = StringUtil.GetContentBehindFirstWhiteSpace(prefix);
			int dotidx = prefix.lastIndexOf('.');
			if (dotidx < 0) {
				return prefix;
			} else {
				return prefix.substring(dotidx+1);
			}
		}
	}
	
	/*public static String GetAdditionInfo(String spechint)
	{
		String addition = "";
		if (spechint.startsWith("new ") || spechint.contains(".new "))
		{
			addition = "new ";
		}
		if (spechint.startsWith("super.") || spechint.contains(".super."))
		{
			addition = "super.";
		}
		if (spechint.startsWith("this.") || spechint.contains(".this."))
		{
			addition = "this.";
		}
		return addition;
	}*/
	
	// Map<String, String> 
	public static RefAndModifiedMember GetMostLikelyRef(ContextHandler ch, ScopeOffsetResult po, String hint, boolean hintismethod, String concator)
	{
		String maxRef = null;
		String maxMember = null;
		String maxMemberType = null;
		// MethodMember maxMm = null;
		double maxsimilar = 0;
		po.BeginIterate();
		// Set<String> keys = po.keySet();
		// Iterator<String> itr = keys.iterator();
		while (po.HasNext()) // itr.hasNext()
		{
			TypeVar tv = po.Next();
			// String type = tv.getType(); // itr.next();
			String refname = tv.getName(); // po.get(type);
			// MembersOfAReference members = SearchSpecificationOfAReference.SearchFunctionSpecificationByPrefix(refname + concator, ch.getJavacontext(), ch.getMonitor());
			if (hintismethod)
			{
				List<MethodMember> mmls = SearchSpecificationOfAReference.SearchMethodSpecificationByPrefix(refname + concator, ch.getJavacontext());
				Iterator<MethodMember> mmitr = mmls.iterator();
				while (mmitr.hasNext())
				{
					MethodMember mm = mmitr.next();
					String cmp = mm.getName();
					// String checktp = mm.getReturntype();
					double similar = SimilarityHelper.ComputeTwoStringSimilarity(hint, cmp);
					if (maxsimilar < similar)
					{
						maxsimilar = similar;
						maxMember = cmp;
						maxRef = refname;
						maxMemberType = mm.getReturntype();
						// maxMm = mm;
					}
				}
			}
			else
			{
				List<FieldMember> fmls = SearchSpecificationOfAReference.SearchFieldSpecificationByPrefix(refname + concator, ch.getJavacontext());
				Iterator<FieldMember> fmitr = fmls.iterator();
				while (fmitr.hasNext())
				{
					FieldMember fm = fmitr.next();
					String cmp = fm.getName();
					// String checktp = fm.getType();
					double similar = SimilarityHelper.ComputeTwoStringSimilarity(hint, cmp);
					if (maxsimilar < similar)
					{
						maxsimilar = similar;
						maxMember = cmp;
						maxRef = refname;
						maxMemberType = fm.getType();
					}
				}
			}
		}
		if (maxRef == null || maxMember == null || maxMemberType == null)
		{
			return null;
		}
		if (concator.equals("::"))
		{
			maxMemberType = GCodeMetaInfo.InferedType;
		}
		RefAndModifiedMember result = new RefAndModifiedMember(maxRef, maxMember, maxMemberType); // , maxMm
		return result;
	}
	
	/*public static RefAndModifiedMember GetMostLikelyRef(ContextHandler ch, Set<String> codes, String hint, boolean hintismethod, String concator)
	{
		String maxRef = null;
		String maxMember = null;
		String maxMemberType = null;
		MethodMember maxMm = null;
		double maxsimilar = 0;
		Iterator<String> itr = codes.iterator();
		while (itr.hasNext())
		{
			String code = itr.next();
			// MembersOfAReference members = SearchSpecificationOfAReference.SearchFunctionSpecificationByPrefix(code + concator, ch.getJavacontext(), ch.getMonitor());
			if (hintismethod)
			{
				List<MethodMember> mmls = SearchSpecificationOfAReference.SearchMethodSpecificationByPrefix(code + concator, ch.getJavacontext());
				Iterator<MethodMember> mmitr = mmls.iterator();
				while (mmitr.hasNext())
				{
					MethodMember mm = mmitr.next();
					String cmp = mm.getName();
					// String checktp = mm.getReturntype();
					double similar = SimilarityHelper.ComputeTwoStringSimilarity(hint, cmp);
					if (maxsimilar < similar)
					{
						maxsimilar = similar;
						maxMember = cmp;
						maxRef = code;
						maxMemberType = mm.getReturntype();
						maxMm = mm;
					}
				}
			}
			else
			{
				List<FieldMember> fmls = SearchSpecificationOfAReference.SearchFieldSpecificationByPrefix(code + concator, ch.getJavacontext());
				Iterator<FieldMember> fmitr = fmls.iterator();
				while (fmitr.hasNext())
				{
					FieldMember fm = fmitr.next();
					String cmp = fm.getName();
					// String checktp = fm.getType();
					double similar = SimilarityHelper.ComputeTwoStringSimilarity(hint, cmp);
					if (maxsimilar < similar)
					{
						maxsimilar = similar;
						maxMember = cmp;
						maxRef = code;
						maxMemberType = fm.getType();
					}
				}
			}
		}
		if (maxRef == null || maxMember == null || maxMemberType == null)
		{
			return null;
		}
		RefAndModifiedMember result = new RefAndModifiedMember(maxRef, maxMember, maxMemberType, maxMm);
		return result;
	}*/
	
}