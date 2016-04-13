package cn.yyx.contentassist.specification;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import cn.yyx.contentassist.commonutils.ContextHandler;
import cn.yyx.contentassist.commonutils.RefAndModifiedMember;
import cn.yyx.contentassist.commonutils.SimilarityHelper;

public class SpecificationHelper {
	
	public static RefAndModifiedMember GetMostLikelyRef(ContextHandler ch, Map<String, String> po, String hint, boolean hintismethod, String concator)
	{
		String maxRef = null;
		String maxMember = null;
		String maxMemberType = null;
		MethodMember maxMm = null;
		Set<String> keys = po.keySet();
		Iterator<String> itr = keys.iterator();
		double maxsimilar = 0;
		while (itr.hasNext())
		{
			String type = itr.next();
			String refname = po.get(type);
			MembersOfAReference members = SearchSpecificationOfAReference.SearchFunctionSpecificationByPrefix(refname + concator, ch.getJavacontext(), ch.getMonitor());
			if (hintismethod)
			{
				Iterator<MethodMember> mmitr = members.GetMethodMemberIterator();
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
						maxMm = mm;
					}
				}
			}
			else
			{
				Iterator<FieldMember> fmitr = members.GetFieldMemberIterator();
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
		RefAndModifiedMember result = new RefAndModifiedMember(maxRef, maxMember, maxMemberType, maxMm);
		return result;
	}
	
	public static RefAndModifiedMember GetMostLikelyRef(ContextHandler ch, Set<String> codes, String hint, boolean hintismethod, String concator)
	{
		String maxRef = null;
		String maxMember = null;
		String maxMemberType = null;
		double maxsimilar = 0;
		Iterator<String> itr = codes.iterator();
		while (itr.hasNext())
		{
			String code = itr.next();
			MembersOfAReference members = SearchSpecificationOfAReference.SearchFunctionSpecificationByPrefix(code + concator, ch.getJavacontext(), ch.getMonitor());
			if (hintismethod)
			{
				Iterator<MethodMember> mmitr = members.GetMethodMemberIterator();
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
					}
				}
			}
			else
			{
				Iterator<FieldMember> fmitr = members.GetFieldMemberIterator();
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
		RefAndModifiedMember result = null;
		if (maxMember == null || maxMemberType == null)
		{
			result = new RefAndModifiedMember(maxRef, maxMember, maxMemberType, null);
		}
		return result;
	}
	
}