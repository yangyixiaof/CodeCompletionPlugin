package cn.yyx.contentassist.commonutils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import cn.yyx.contentassist.specification.FieldMember;
import cn.yyx.contentassist.specification.MembersOfAReference;
import cn.yyx.contentassist.specification.MethodMember;
import cn.yyx.contentassist.specification.SearchSpecificationOfAReference;

public class TypeCheckHelper {
	
	public static RefAndModifiedMember GetMostLikelyRef(ContextHandler ch, Map<String, String> po, String hint, boolean hintismethod, String matchtype)
	{
		Set<String> keys = po.keySet();
		Iterator<String> itr = keys.iterator();
		while (itr.hasNext())
		{
			String type = itr.next();
			String refname = po.get(type);
			MembersOfAReference members = SearchSpecificationOfAReference.SearchFunctionSpecificationByPrefix(refname + ".", ch.getJavacontext(), ch.getMonitor());
			if (hintismethod)
			{
				Iterator<MethodMember> mmitr = members.GetMethodMemberIterator();
				while (mmitr.hasNext())
				{
					MethodMember mm = mmitr.next();
					
				}
			}
			else
			{
				Iterator<FieldMember> fmitr = members.GetFieldMemberIterator();
				while (fmitr.hasNext())
				{
					FieldMember fm = fmitr.next();
					
				}
			}
			
		}
		return null;
	}
	
}